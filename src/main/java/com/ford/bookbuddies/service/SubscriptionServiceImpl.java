package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.SubscriptionRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.entity.Subscription;
import com.ford.bookbuddies.entity.SubscriptionPlan;
import com.ford.bookbuddies.exception.SubscriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    private static final String SUBSCRIPTION_NOTFOUND_MESSAGE="Subscription not found";
    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, BookRepository bookRepository, CustomerRepository customerRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.bookRepository = bookRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Book subscribeToBook(String bookName, String userName, SubscriptionPlan plan) throws SubscriptionException
    {
        Optional<Customer> customerOpt=this.customerRepository.findByUserName(userName);
        if(customerOpt.isEmpty())
        {
            throw new SubscriptionException("The given user name is not valid");
        }
        Optional<Book> bookOpt=this.bookRepository.findByBookTitle(bookName);
        if(bookOpt.isEmpty())
        {
            throw new SubscriptionException("The given book name is not found");
        }

        Customer customer=customerOpt.get();
        Book book=bookOpt.get();

        LocalDate subscriptionDate=LocalDate.now();
        LocalDate expireDate=subscriptionDate.plusDays(plan.getDuration());

        Subscription subscription=new Subscription();
        subscription.setBook(book);
        subscription.setCustomer(customer);
        subscription.setSubscriptionDate(subscriptionDate);
        subscription.setExpireDate(expireDate);
        subscription.setPaymentPlan(plan);
        subscription.setSubscriptionCost(plan.getCost());
        subscription.setSubscriptionStatus("ACTIVE");
        this.subscriptionRepository.save(subscription);
        return book;


    }

    @Override
    public String cancelExpiredSubscriptions(){

            LocalDate currentDate = LocalDate.now();
            List<Subscription> expiredSubscriptions = subscriptionRepository.findByExpireDateBefore(currentDate);
            for (Subscription subscription : expiredSubscriptions) {
                subscription.setSubscriptionStatus("EXPIRED");
                this.subscriptionRepository.save(subscription);
            }
            return "Subscription cancelled";

    }


    @Override
    public List<Subscription> findSubscriptionsByUsername(String username) {
        return this.subscriptionRepository.findByCustomerUserName(username);
    }

    @Override
    public String extendSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException {
        Optional<Subscription> subscriptionOpt=this.subscriptionRepository.findById(subscriptionId);

        if(subscriptionOpt.isPresent())
        {
            Subscription subscription=subscriptionOpt.get();
            LocalDate currentDate=LocalDate.now();
            LocalDate expiryDate=subscription.getExpireDate();
            LocalDate extensionDate=expiryDate.minusDays(2);//going to get expired
            if(currentDate.isBefore(extensionDate) || currentDate.isEqual(expiryDate))
                {
                    LocalDate newExpireDate=expiryDate.plusDays(plan.getDuration());
                    subscription.setExpireDate(newExpireDate);
                    this.subscriptionRepository.save(subscription);
                    return "Subscription extended successfully";
                } else {
                    throw new SubscriptionException("Cannot extend subscription,expire date is not close enough");
                }
        }
        else {
            throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return this.subscriptionRepository.findAllBy();
    }

    @Override
    public Double cancelSubscriptions(Integer subscriptionId) throws SubscriptionException {
        Optional<Subscription> subscriptionOpt=this.subscriptionRepository.findById(subscriptionId);
        if(subscriptionOpt.isEmpty())
        {
            throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE);
        }
        Subscription subscription=subscriptionOpt.get();
        Double refundAmount=calculateRefundAmount(subscription);
        subscription.setSubscriptionStatus("CANCELLED");
        this.subscriptionRepository.save(subscription);
        return refundAmount;

    }

    @Override
    public List<Subscription> findSubscriptionsByBookName(String bookName) {
        Optional<Book> book=this.bookRepository.findByBookTitle(bookName);
        return this.subscriptionRepository.findByBook(book);
    }

    @Override
    public String renewSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException {
        Optional<Subscription> subscriptionOpt = this.subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isPresent()) {
            Subscription subscription = subscriptionOpt.get();
            LocalDate currentDate = LocalDate.now();
            LocalDate expiryDate = subscription.getExpireDate();
            if (expiryDate.isBefore(currentDate))//already expired
            {
                LocalDate newExpireDate = currentDate.plusDays(plan.getDuration());
                subscription.setExpireDate(newExpireDate);
                subscription.setSubscriptionStatus("ACTIVE");
                this.subscriptionRepository.save(subscription);
                return "Subscription renewed successfully";
            } else {
                throw new SubscriptionException("Subscription hasn't expired,Please try again later");
            }
        }
        else {
                throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE);
        }
    }

    private Double calculateRefundAmount(Subscription subscription) {
        LocalDate currentDate=LocalDate.now();
        if(currentDate.isBefore(subscription.getExpireDate().minusDays(subscription.getPaymentPlan().getDuration()/2)))
        {
            return subscription.getSubscriptionCost()/2;
        }
        else {
            return 0.0;
        }
    }


}
