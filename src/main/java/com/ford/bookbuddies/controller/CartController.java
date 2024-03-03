package com.ford.bookbuddies.controller;

import com.ford.bookbuddies.dto.Bookdto;
import com.ford.bookbuddies.dto.OrderBooksdto;
import com.ford.bookbuddies.entity.BookDetail;
import com.ford.bookbuddies.entity.Cart;
import com.ford.bookbuddies.exception.BookException;
import com.ford.bookbuddies.exception.CartException;
import com.ford.bookbuddies.exception.CustomerException;
import com.ford.bookbuddies.service.CartService;
import com.ford.bookbuddies.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private DeleteService deleteService;

    @PostMapping("cart/buy")
    public List<BookDetail> buyBooks(@RequestBody OrderBooksdto orderBooksdto)throws Exception {
        if(orderBooksdto==null) throw new CartException("OrderBooksDto is null");
        if(orderBooksdto.getUserId()==null) throw new CustomerException("User not logged in");
        if(orderBooksdto.getIdList()==null) throw new CartException("Books to Buy List is Null");
        if(orderBooksdto.getIdList().isEmpty()) throw new CartException("Books to Buy is Empty!");
        return cartService.buyBooksinCart(orderBooksdto.getUserId(),orderBooksdto.getIdList());
    }
    @PatchMapping("cart/book/quantity/plus")
    public Cart increasebookQuantity(@RequestBody Bookdto quantitydto)throws Exception {
        if(quantitydto==null) throw new CartException("BookDto is null");
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.cartService.increaseQuantity(quantitydto.getUserId(),quantitydto.getBookId());
    }
    @PatchMapping("cart/book/quantity/minus")
    public Cart decreasebookQuantity(@RequestBody Bookdto quantitydto)throws Exception{
        if(quantitydto==null) throw new CartException("BookDto is null");
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.cartService.decreaseQuantity(quantitydto.getUserId(),quantitydto.getBookId());
    }
    @DeleteMapping("cart")
    public Cart deleteProductFromCart(@RequestBody Bookdto quantitydto)throws Exception{
        if(quantitydto==null) throw new CartException("BookDto is null");
        if(quantitydto.getUserId()==null) throw new CustomerException("User not logged in");
        if(quantitydto.getBookId()==null) throw new BookException("Book Id can't be null");
        return this.deleteService.deleteProductFromCart(quantitydto.getUserId(),quantitydto.getBookId());
    }

}
 