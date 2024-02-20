package com.ford.BookBuddies.service;

import com.ford.BookBuddies.dao.BookOrderRepository;
import com.ford.BookBuddies.dao.BookStockRepository;
import com.ford.BookBuddies.dao.CustomerRepository;
import com.ford.BookBuddies.entity.BookDetail;
import com.ford.BookBuddies.entity.BookOrders;
import com.ford.BookBuddies.entity.Cart;
import com.ford.BookBuddies.entity.Customer;
import com.ford.BookBuddies.exception.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookStockRepository bookStockRepository;
    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Override
    public List<BookDetail> buyBooks(List<Integer>list)throws CustomerException {
        Integer loginId = customerService.getCustomerLoginId();
        Cart userCart=customerService.getCart(loginId);
        List<BookDetail> orderList=new ArrayList<>();
        for(BookDetail bd:userCart.getBooksDetails()){
            if(bd.getBook()!=null){
                if(list.contains(bd.getBook().getBookId())){
                    orderList.add(bd);
                }}
        }
        Optional<Customer> customer=this.customerRepository.findById(loginId);
        BookOrders bookOrders=new BookOrders();
        bookOrders.setBookList(orderList);
        customer.get().getOrderList().add(bookOrders);
        this.bookOrderRepository.save(bookOrders);
        this.customerRepository.save(customer.get());
        return orderList;
    }

    @Override
    public void subsribeBooks() {

    }

    @Override
    public Cart increaseQuantity() {
        return null;
    }

    @Override
    public Cart decreaseQuantity() {
        return null;
    }
}
