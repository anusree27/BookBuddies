package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.*;
import com.ford.BookBuddies.dto.ConfirmedBooks;
import com.ford.BookBuddies.entity.*;
import com.ford.BookBuddies.exception.BookException;
import com.ford.BookBuddies.exception.CartException;
import com.ford.BookBuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookDetailRepository bookDetailRepository;
    @Autowired
    private CartRepository cartRepository;
//    @Autowired
//    private PaymentService paymentService;
    static List<BookDetail> orderedBooks = new ArrayList<>();
    static Map<Integer,List<BookDetail>> confirmedBooksMap=new HashMap<>();

    @Override
    public List<BookDetail> buyBooksinCart(Integer userId,List<Integer> list) throws Exception {
        Cart userCart = customerService.getCart(userId);
        if(userCart==null) throw new CartException("Cart not Present!");
        for (BookDetail bd : userCart.getBooksDetails()) {
            if (bd.getBook() != null) {
                if (list.contains(bd.getBook().getBookId())) {
                    //send it along with loginId
                    orderedBooks.add(bd);
                }
            }
        }
//        Integer loginId = this.customerService.getCustomerLoginId();
//        Optional<Customer> customer = this.customerRepository.findById(loginId);
//        BookOrders bookOrders = new BookOrders(orderList);
//        this.bookOrderRepository.save(bookOrders);
//        customer.get().getOrderList().add(bookOrders);
//        this.customerRepository.save(customer.get());

//        paymentService.order(confirmedBooks);
//        ConfirmedBooks confirmedBooks=new ConfirmedBooks(userId,orderedBooks);

          confirmedBooksMap.put(userId,orderedBooks);
        //        paymentService.order(confirmedBooks);
          return orderedBooks;
    }

    @Override
    public Cart deleteProductFromCart(Integer userId, Integer bookId)throws Exception{
        Optional<Customer> customerOptional = this.customerRepository.findById(userId);
        if(customerOptional.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> bookOptional = this.bookRepository.findById(bookId);
        if(bookOptional.isEmpty()) throw new BookException("Book doesn't exists");
        Customer customer = customerOptional.get();
        Book book = bookOptional.get();
        Optional<BookDetail> bookDetailOptional=this.bookDetailRepository.findByBook(book);
        customer.getCart().getBooksDetails().remove(bookDetailOptional.get());
        this.cartRepository.save(customer.getCart());
        return customer.getCart();
    }

    @Override
    public Cart increaseQuantity(Integer userId, Integer bookId)throws Exception{
        Optional<Customer> customer = this.customerRepository.findById(userId);
        if(customer.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> book=this.bookRepository.findById(bookId);
        if(book.isEmpty()) throw new BookException("Book doesn't exists");
        BookDetail bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();
        if(bookDetail==null) throw new BookException("Book not found in Cart!");
        bookDetail.setQuantity(bookDetail.getQuantity()+1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

    @Override
    public Cart decreaseQuantity(Integer userId, Integer bookId) throws Exception{
        Optional<Customer> customer = this.customerRepository.findById(userId);
        if(customer.isEmpty()) throw new CustomerException("User is not registered");
        Optional<Book> book=this.bookRepository.findById(bookId);
        if(book.isEmpty()) throw new BookException("Book doesn't exists");
        BookDetail bookDetail=customer.get().getCart().getBooksDetails().stream().filter((bd)->bd.getBook().equals(book.get())).findAny().get();
        if(bookDetail==null) throw new BookException("Book not found in Cart!");
        if(bookDetail.getQuantity()>1) bookDetail.setQuantity(bookDetail.getQuantity()-1);
        this.bookDetailRepository.save(bookDetail);
        return customer.get().getCart();
    }

//    @Override
//    public List<BookDetail> getItemsToBuy() {
//        return orderedBooks;
//    }

}
