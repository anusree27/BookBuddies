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
    public Subscription subscribeToBook(Integer bookId,Integer userId, SubscriptionPlan plan) throws SubscriptionException
    {
        if(bookId==null || userId==null || plan==null)
        {
            throw new SubscriptionException("Book name,username and plan cannot be null or empty");
        }
        Optional<Customer> customerOpt=this.customerRepository.findById(userId);
        if(customerOpt.isEmpty())
        {
            throw new SubscriptionException("The given user ID is not valid");
        }
        Optional<Book> bookOpt=this.bookRepository.findByBookId(bookId);
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
    public List<Subscription> findSubscriptionsByUserId(Integer userId) throws SubscriptionException {
        if(userId==null)
        {
            throw new SubscriptionException("Username cannot be null");
        }
        Optional<Customer> customer=this.customerRepository.findById(userId);
        if(customer.isEmpty())
        {
            throw new SubscriptionException("User "+ userId +" not found");
        }
        return this.subscriptionRepository.findByCustomerId(userId);
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
            if(currentDate.isEqual(extensionDate) || currentDate.isEqual(expiryDate))
            {
                LocalDate newExpireDate=expiryDate.plusDays(plan.getDuration());
                subscription.setSubscriptionDate(LocalDate.now());
                subscription.setExpireDate(newExpireDate);
                subscription.setPaymentPlan(plan);
                subscription.setSubscriptionCost(plan.getCost());
                subscription.setSubscriptionStatus("PAYMENT PENDING");
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
    public List<Subscription> findSubscriptionsByBookName(String bookName) throws SubscriptionException{
        if(bookName==null || bookName.isEmpty())
        {
            throw new SubscriptionException("Book name cannot be null or empty");
        }
        Optional<Book> book=this.bookRepository.findByBookTitleIgnoreCase(bookName);
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
                subscription.setSubscriptionDate(LocalDate.now());
                subscription.setExpireDate(newExpireDate);
                subscription.setPaymentPlan(plan);
                subscription.setSubscriptionCost(plan.getCost());
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







}
