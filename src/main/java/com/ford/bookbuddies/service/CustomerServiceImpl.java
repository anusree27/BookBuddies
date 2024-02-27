package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookDetailRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CartRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.entity.*;
//import com.training.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    static Integer loginId=null;
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
        if (email == null) {
            throw new CustomerException("Email id should not be null");
        }
        if (password == null) {
            throw new CustomerException("Password should not be null");
        }
        Optional<Customer> opt=this.customerRepository.findByEmail(email);
        if(opt.isEmpty()) {
            throw new CustomerException("Email is not registered");
        }
        Customer found=opt.get();
        if(!password.equals(found.getPassword())) throw new CustomerException("Invalid Password!");
        return found;
    }

    @Override
    public void setCustomerLoginId(Integer user_id) {
        loginId=user_id;
    }

    @Override
    public Integer getCustomerLoginId() {
        return loginId;
    }


    @Override
    public Cart addProductToCart(Integer userId, String bookName, Integer quantity) throws CustomerException, BookException, CartException {
        if (userId == null) {
            throw new CartException("User not present");
        }
        if (bookName == null) {
            throw new CartException("Book name should not be null");
        }
        if (quantity == null) {
            throw new CartException("Quantity should not be null");
        }
        if (quantity < 0) {
            throw new CartException("Quantity should be greater than zero");
        }
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if (customerOptional.isEmpty()) {
            throw new CustomerException("You should log in before adding products to cart");
        }
        Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
        if (bookOptional.isEmpty()) {
            throw new BookException("Book is not present");
        }
        Customer customer = customerOptional.get();
        Book book = bookOptional.get();
        BookDetail bookDetail = new BookDetail(quantity,book);
        customer.getCart().getBooksDetails().add(bookDetail);
        this.bookDetailRepository.save(bookDetail);
        this.cartRepository.save(customer.getCart());
        this.customerRepository.save(customer);
        return customer.getCart();
}
    @Override
    public Cart getCart(Integer id) throws CustomerException{
        if(id == null) {
            throw new CustomerException("User not logged in");
        }
        Optional<Customer> users=this.customerRepository.findById(id);
        return users.get().getCart();
    }
    @Override
    public List<Book> getBooksByCategory(BookCategory category) {
        return this.bookRepository.findAllByBookCategory(category);
    }




}
