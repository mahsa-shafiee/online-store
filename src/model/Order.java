package model;

import java.util.Date;

public class Order {
    private int id;
    private Date date;
    private User user;
    private Item item;

    public Order() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Order(Date date, User user, Item item) {

        this.date = date;
        this.user = user;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "date=" + date +
                "\nitem={ name= " + item.getName() +
                "  Price= " + item.getPrice() +" Rials}"+
                "}";
    }
}
