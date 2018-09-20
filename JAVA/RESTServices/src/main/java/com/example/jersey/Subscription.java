package com.example.jersey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "subscriptions")
public class Subscription implements Serializable {

    private static final AtomicLong count = new AtomicLong(0);

    @Id
    @Column(name="subscriptionid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long subscriptionid;
    @Column(name="name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    public Subscription() {}

    public Subscription(String name, String description, int price) {
        this.subscriptionid = count.incrementAndGet();
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(Long subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
