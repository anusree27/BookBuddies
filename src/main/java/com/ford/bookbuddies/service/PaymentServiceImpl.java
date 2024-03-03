package com.ford.bookbuddies.service;

import com.ford.bookbuddies.dao.BookOrderRepository;
import com.ford.bookbuddies.dao.BookStockRepository;
import com.ford.bookbuddies.dao.CustomerRepository;
import com.ford.bookbuddies.dao.PaymentRepository;
import com.ford.bookbuddies.dto.ConfirmedBooksDto;
import com.ford.bookbuddies.entity.*;
import com.ford.bookbuddies.exception.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    static ConfirmedBooksDto confirmedBooksDto;
    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DeleteService deleteService;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookStockRepository bookStockRepository;
    public Payment makePayment(Payment payment) throws Exception {
        if(payment.getTotalCost()==null)
        {
            throw new PaymentException("Entered Payment Should not be null");
        }
        if(confirmedBooksDto.getOrderedBooks()==null)
        {
            throw new PaymentException("Books to Order list should not be null");
        }
        System.out.println(confirmedBooksDto.getOrderedBooks());
        double price=0.0;
        Integer totalQuantity=0;
        for(BookDetail books: confirmedBooksDto.getOrderedBooks())
        {
            price += books.getBook().getPrice()*books.getQuantity();
            totalQuantity+=books.getQuantity();
        }

        Double comparePrice=payment.getTotalCost();
        if(comparePrice != price)
        {
            throw new PaymentException("Entered payment not matches with the actual order payment");
        }

        else
        {
            payment.setPaymentStatus(true);
            BookOrders bookOrders=new BookOrders();
            bookOrders.setPayment(payment);
            bookOrders.setOrderStatus(OrderStatus.CONFIRMED);
            bookOrders.setBookList(confirmedBooksDto.getOrderedBooks());
            bookOrders.setTotalBookCount(totalQuantity);
            this.paymentRepository.save(payment);
            this.bookOrderRepository.save(bookOrders);
            Optional<Customer> users=this.customerRepository.findById(confirmedBooksDto.getUserId());
            users.get().getOrderList().add(bookOrders);
            for(BookDetail books: confirmedBooksDto.getOrderedBooks())
            {
                Book book=books.getBook();
                this.deleteService.deleteProductFromCart(users.get().getId(),book.getBookId());
                Optional<BookStock> bookStock=this.bookStockRepository.findBookStockByBook(book);
                bookStock.get().setStockQuantity(bookStock.get().getStockQuantity()-books.getQuantity());
                this.bookStockRepository.save(bookStock.get());
            }

            this.customerRepository.save(users.get());
        }

        return payment;
    }
    @Override
    public void orderDetails(ConfirmedBooksDto confirmedBookDto) {
        confirmedBooksDto=confirmedBookDto;

    }
}
