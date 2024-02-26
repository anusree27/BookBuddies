package com.ford.BookBuddies.dto;

//import com.ford.BookBuddies.entity.BookDetail;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;

import java.util.List;
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class OrderBooksdto {
    private Integer userId;
    private List<Integer> idList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public OrderBooksdto(Integer userId, List<Integer> idList) {
        this.userId = userId;
        this.idList = idList;
    }

    public OrderBooksdto() {
    }
}
