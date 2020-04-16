package services;

import dao.*;
import model.Item;
import model.Order;
import model.ShoppingCart;
import model.User;

import java.util.HashSet;
import java.util.List;

public class PurchaseService {
    private CategoryDao categoryDao = new CategoryDao();
    private ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
    private OrderDao orderDao = new OrderDao();
    private UserDao userDao = new UserDao();
    private ItemDao itemDao = new ItemDao();

    public HashSet<String> getAllCategories() throws Exception {
        HashSet<String> names = categoryDao.findAll();
        return names;
    }

    public Item[] getProductsOfCategory(String categoryName) throws Exception {
        Item[] itemsOfCategory = itemDao.showItemsOfCategory(categoryDao.getIdIfExist(categoryName));
        return itemsOfCategory;
    }

    public boolean checkProductAvailability(Item product) throws Exception {
        return itemDao.getStock(product) > 0;
    }

    public void finalizeOrder(User user) throws Exception {
        shoppingCartDao.findItems(user);
        user.getShoppingCart().setId(shoppingCartDao.getIdIfExist(userDao.getIdIfExist(user)));
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        for (Item item : user.getShoppingCart().getItems()) {
            shoppingCartDao.findItems(user);
            if (itemDao.getStock(item) <= 0) {
                user.getShoppingCart().getItems().remove(item);
                System.out.println(item.getName() + " is not available.");
                continue;
            }
            Order order = new Order(date, user, item);
            orderDao.insert(order);
        }
        for (Item item : user.getShoppingCart().getItems()) {
            itemDao.setStock(itemDao.getStock(item) - 1, item);
        }
        shoppingCartDao.deleteCartOfUser(userDao.getIdIfExist(user));
        user.setShoppingCart(new ShoppingCart());
    }

    public List<Order> getOrders(User user) throws Exception {
        List<Order> orders = orderDao.findOrdersOfUser(userDao.getIdIfExist(user));
        return orders;
    }
}
