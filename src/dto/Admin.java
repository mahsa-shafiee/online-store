package dto;

import java.util.HashSet;

public class Admin {
    private int id;
    private String name;
    private String password;
    private HashSet<Item> items;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public void setItems(HashSet<Item> items) {
        this.items = items;
    }

    public HashSet<Category> getCategories() {
        return categories;
    }

    public void setCategories(HashSet<Category> categories) {
        this.categories = categories;
    }

    private HashSet<Category> categories;

}
