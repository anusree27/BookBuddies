package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.BookDetailRepository;
import com.ford.BookBuddies.dao.BookRepository;
import com.ford.BookBuddies.dao.CartRepository;
import com.ford.BookBuddies.dao.CustomerRepository;
import com.ford.BookBuddies.entity.*;
//import com.training.bookbuddies.entity.*;
import com.ford.BookBuddies.exception.CustomerException;
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
        Optional<Customer> opt=this.customerRepository.findByEmail(email);
        if(opt.isEmpty()) throw new CustomerException("Email is not registered");
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


//    @Override
//    public Cart addProductToCart(Integer userId, String bookName, Integer quantity) {
//        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
//        Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
//        Customer customer = customerOptional.get();
//        Book book = bookOptional.get();
//        BookDetail bookDetail = new BookDetail(quantity,book);
//        customer.getCart().getBooksDetails().add(bookDetail);
//        this.bookDetailRepository.save(bookDetail);
//        this.cartRepository.save(customer.getCart());
//        return customer.getCart();
//}

//    @Override
//    public Cart deleteProductFromCart(Integer userId, String bookName) {
//        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
//        Optional<Book> bookOptional = this.bookRepository.findByBookTitle(bookName);
//        Customer customer = customerOptional.get();
//        Book book = bookOptional.get();
//        Optional<BookDetail> bookDetailOptional=this.bookDetailRepository.findByBook(book);
//        customer.getCart().getBooksDetails().remove(bookDetailOptional.get());
////        this.bookDetailRepository.deleteByBook(book);
//        this.cartRepository.save(customer.getCart());
//        return customer.getCart();
//    }


    @Override
    public Cart getCart(Integer id) throws CustomerException{
        if(id==null) throw new CustomerException("User not logged in");
        Optional<Customer> users=this.customerRepository.findById(id);
        return users.get().getCart();
    }
    @Override
    public List<Book> getBooksByCategory(BookCategory category) {
        return this.bookRepository.findAllByBookCategory(category);
    }


}
