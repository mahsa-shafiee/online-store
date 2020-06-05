package model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "item")
public class Item implements Comparable<Item> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    private String name;
    private String description;
    private long price;
    private int stock;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Admin admin;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getId() == item.getId();
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "category=" + category.getName() +
                "\nname='" + name + '\'' +
                "\ndescription='\n" + description + '\'' +
                "\nprice=" + price + " Rials" +
                "\nstock=" + stock;
    }

    public Item() {
    }

    public Item(String name, String description, long price, int stock, Admin admin) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @Override
    public int compareTo(Item item) {
        return this.price == item.getPrice() ? 0 : this.price > item.getPrice() ? 1 : -1;
    }

}
