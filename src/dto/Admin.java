package dto;

import java.util.HashSet;

public class Admin {
    private int id;
    private String name;
    private String password;
    private HashSet<Item> items;
    private HashSet<Category> categories;

}
