package dto;

import java.util.List;

public class ShoppingCart {
    private int id;
    private User user;
    private long totalPrice;
    private List<Item> items;
    private Item item;

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "user=" + user.getFirstName() + " " + user.getLastName() +
                ", items=" + items +
                '}';
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
