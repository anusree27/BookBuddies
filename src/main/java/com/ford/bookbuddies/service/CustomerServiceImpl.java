package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.*;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.CustomerException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private BookOrderRepository bookOrderRepository;


    @Override
    public Customer createCustomerAccount(Customer newCustomer) throws CustomerException {
        if (newCustomer == null) {
            throw new CustomerException("Customer cannot be null");
        }
        Optional<Customer> testUserName = this.customerRepository.findByUserName(newCustomer.getUserName());

        //if username not unique throw exception
        if (testUserName.isPresent() && newCustomer.getUserName().equals(testUserName.get().getUserName())) {
            throw new CustomerException("User name already exists, create new username");
        }
        if (!newCustomer.getEmail().contains("@")) {
            throw new CustomerException("Enter proper email");
        }
        if (newCustomer.getPassword().length() < 3) {
            throw new CustomerException("Password should be greater than 3");
        }
        Cart cart = new Cart();
        cart = this.cartRepository.save(cart);
        newCustomer.setCart(cart);
        return this.customerRepository.save(newCustomer);
    }
    @Override
    public Customer login(String email, String password)throws CustomerException{
        Optional<Customer> opt=this.customerRepository.findByEmail(email);
        if(opt.isEmpty()) throw new CustomerException("User Email is not registered");
        Customer found=opt.get();
        if(!password.equals(found.getPassword())) throw new CustomerException("Invalid Password!");
        return found;
    }

    @Override
    public Cart getCart(Integer id) throws CustomerException{
        Optional<Customer> users=this.customerRepository.findById(id);
        if(users.isEmpty()) throw new CustomerException("User is not registered");
        return users.get().getCart();
    }
    @Override
    public List<Book> displayAllBooks(){
        return this.bookRepository.findAll();
    }

    @Override
    public List<BookOrders> getMyOrders(Integer userId) throws CustomerException {
        List<BookOrders> ordersList=this.bookOrderRepository.findAll();
        for(BookOrders bo:ordersList){
            Long days=java.time.temporal.ChronoUnit.DAYS.between(bo.getOrderDate(), LocalDate.now());
            if(days==0) bo.setOrderStatus(OrderStatus.CONFIRMED);
            else if(days>=0 && days<=2) bo.setOrderStatus(OrderStatus.PACKED);
            else if(days>2 && days<=7) bo.setOrderStatus(OrderStatus.SHIPPED);
            else bo.setOrderStatus(OrderStatus.DELIVERED);
            this.bookOrderRepository.save(bo);
        }
        Optional<Customer> customer=this.customerRepository.findById(userId);
        return customer.get().getOrderList();
    }

    @Override
    public List<Book> getBooksByCategory(BookCategory category) {
        return this.bookRepository.findAllByBookCategory(category);
    }

    @Override
    public Customer getUserById(Integer userId) throws CustomerException {
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            throw new CustomerException("Customer not present");
        }
        return customerOptional.get();
    }

    @Override
    public Customer editCustomerAccount(Customer editCustomer) throws CustomerException {
        if (editCustomer == null) {
            throw new CustomerException("Customer cannot be null");
        }


        if (!editCustomer.getEmail().contains("@")) {
            throw new CustomerException("Enter proper email");
        }
        if (editCustomer.getPassword().length() < 3) {
            throw new CustomerException("Password should be greater than 3");
        }

        return this.customerRepository.save(editCustomer);
    }


}
