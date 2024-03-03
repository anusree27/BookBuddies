package com.ford.bookbuddies.entity;

public enum SubscriptionPlan {
    DAILY(1,"Daily",1,20.0),
    WEEKLY(2,"Weekly",7, 100.0),
    MONTHLY(3,"Monthly",30,500.0);

    private Integer id;
    private String name;
    private Integer duration; //in days
    private Double cost;

    SubscriptionPlan() {
    }

    SubscriptionPlan(Integer id, String name, Integer duration, Double cost) {
        this.id=id;
        this.name = name;
        this.duration = duration;
        this.cost = cost;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public Integer getDuration() {
        return duration;
    }
    public Double getCost() {
        return cost;
    }


}
