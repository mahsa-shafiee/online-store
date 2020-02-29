package dto;

import java.util.HashSet;

public class Category {
    private int id;
    private String name;
    private HashSet<Item> items;
    private Admin admin;

    public Category(int id, String name,  Admin admin) {
        this.id = id;
        this.name = name;
        this.admin = admin;
    }

    public Category(String name, Admin admin) {
        this.name = name;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public void setItems(HashSet<Item> items) {
        this.items = items;
    }
}
