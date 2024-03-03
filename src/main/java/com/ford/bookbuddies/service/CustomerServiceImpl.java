package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookDetailRepository;
import com.ford.bookbuddies.dao.BookRepository;
import com.ford.bookbuddies.dao.CartRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.entity.Book;
import com.ford.bookbuddies.entity.BookCategory;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.entity.Customer;
import com.ford.bookbuddies.exception.CustomerException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Book> getBooksByCategory(BookCategory category) {
        return this.bookRepository.findAllByBookCategory(category);
    }

}
