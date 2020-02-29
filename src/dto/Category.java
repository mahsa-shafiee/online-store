package dto;

import java.util.HashSet;

public class Category {
    private int id;
    private String name;
    private HashSet<Item> items;
    private Admin admin;


    public HashSet<Item> getItems() {
        return items;
    }

    public void setItems(HashSet<Item> items) {
        this.items = items;
    }
}
