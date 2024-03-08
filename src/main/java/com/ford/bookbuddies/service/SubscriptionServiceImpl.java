package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.SubscriptionRepository;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.SubscriptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    private static final String SUBSCRIPTION_NOTFOUND_MESSAGE="Subscription not found with ID";
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
    public Subscription subscribeToBook(String bookName, String userName, SubscriptionPlan plan) throws SubscriptionException
    {
        if(bookName==null || bookName.isEmpty() || userName==null || userName.isEmpty() || plan==null)
        {
            throw new SubscriptionException("Book name,username and plan cannot be null or empty");
        }
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
        subscription.setSubscriptionStatus("PAYMENT PENDING");
        return this.subscriptionRepository.save(subscription);

    }

    @Override
    public String cancelExpiredSubscriptions() throws SubscriptionException{

        LocalDate currentDate = LocalDate.now();
        List<Subscription> expiredSubscriptions = this.subscriptionRepository.findByExpireDateBefore(currentDate);
        for (Subscription subscription : expiredSubscriptions) {
            if(subscription==null) {
                throw new SubscriptionException("Subscription doesn't exist");
            }
            subscription.setSubscriptionStatus("EXPIRED");
            this.subscriptionRepository.save(subscription);
        }
        return "Subscription(s) cancelled";

    }


    @Override
    public List<Subscription> findSubscriptionsByUsername(String username) throws SubscriptionException {
        if(username==null || username.isEmpty())
        {
            throw new SubscriptionException("Username cannot be null or empty");
        }
        Optional<Customer> customer=this.customerRepository.findByUserName(username);
        if(customer.isEmpty())
        {
            throw new SubscriptionException("User "+ username +" not found");
        }
        return this.subscriptionRepository.findByCustomerUserName(username);
    }

    @Override
    public String extendSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException {
        if(subscriptionId==null || subscriptionId<=0)
        {
            throw new SubscriptionException("Subscription ID must be a positive non-zero value");
        }
        if(plan==null)
        {
            throw new SubscriptionException("Subscription plan cannot be null");
        }
        Optional<Subscription> subscriptionOpt=this.subscriptionRepository.findById(subscriptionId);
        if(subscriptionOpt.isPresent())
        {
            Subscription subscription=subscriptionOpt.get();
            LocalDate currentDate=LocalDate.now();
            LocalDate expiryDate=subscription.getExpireDate();
            LocalDate extensionDate=expiryDate.minusDays(1);//going to get expired
            if(currentDate.isBefore(extensionDate) || currentDate.isEqual(extensionDate) || currentDate.isEqual(expiryDate))
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
            throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE + " " + subscriptionId);
        }
    }

    @Override
    public List<Subscription> getAllSubscriptions() throws SubscriptionException {
        return this.subscriptionRepository.findAllBy();
    }

    @Override
    public Double cancelSubscriptions(Integer subscriptionId) throws SubscriptionException{
        if(subscriptionId==null || subscriptionId<=0)
        {
            throw new SubscriptionException("Invalid Subscription Id: "+subscriptionId);
        }
        Optional<Subscription> subscriptionOpt = this.subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isEmpty()) {
            throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE + " " + subscriptionId);

        }
        Subscription subscription = subscriptionOpt.get();
        Double refundAmount = calculateRefundAmount(subscription);
        subscription.setSubscriptionStatus("CANCELLED");
        this.subscriptionRepository.save(subscription);
        return refundAmount;
    }

    @Override
    public List<Subscription> findSubscriptionsByBookName(String bookName) throws SubscriptionException{
        if(bookName==null || bookName.isEmpty())
        {
            throw new SubscriptionException("Book name cannot be null or empty");
        }
        Optional<Book> book=this.bookRepository.findByBookTitle(bookName);
        if(book.isEmpty())
        {
            throw new SubscriptionException("Book "+ bookName +" not found");
        }
        return this.subscriptionRepository.findByBook(book);
    }

    @Override
    public String renewSubscription(Integer subscriptionId, SubscriptionPlan plan) throws SubscriptionException {
        if(subscriptionId==null || subscriptionId<=0)
        {
            throw new SubscriptionException("Subscription ID must be a positive non-zero value");
        }
        if(plan==null)
        {
            throw new SubscriptionException("Subscription plan cannot be null");
        }
        Optional<Subscription> subscriptionOpt = this.subscriptionRepository.findById(subscriptionId);
        if (subscriptionOpt.isPresent()) {
            Subscription subscription = subscriptionOpt.get();
            LocalDate currentDate = LocalDate.now();
            LocalDate expiryDate = subscription.getExpireDate();
            if (expiryDate.isBefore(currentDate))//already expired
            {
                LocalDate newExpireDate = currentDate.plusDays(plan.getDuration());
                subscription.setExpireDate(newExpireDate);
                subscription.setSubscriptionStatus("PAYMENT PENDING");
                this.subscriptionRepository.save(subscription);
                return "Subscription renewed successfully";
            } else {
                throw new SubscriptionException("Subscription hasn't expired,Please try again later");
            }
        }
        else {
            throw new SubscriptionException(SUBSCRIPTION_NOTFOUND_MESSAGE + " " + subscriptionId);
        }
    }



    public Double calculateRefundAmount(Subscription subscription)  throws SubscriptionException{
        LocalDate currentDate=LocalDate.now();
        if(subscription.getSubscriptionCost()==null)
        {
            Double planCost=subscription.getPaymentPlan().getCost();
            subscription.setSubscriptionCost(planCost);
        }
        if(currentDate.isBefore(subscription.getExpireDate().minusDays(subscription.getPaymentPlan().getDuration()/2)))
        {
            if(subscription.getSubscriptionCost()!=null) {
                return subscription.getSubscriptionCost() / 2;
            }
            else{
                throw new SubscriptionException("Subscription cost is null");
            }
        }
        else {
            return 0.0;
        }

    }



}
