package services;

import dao.*;
import model.Item;
import model.Order;
import model.ShoppingCart;
import model.User;
import util.DateUtil;

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

    public List<Item> finalizeOrder(User user) throws Exception {
        user.getShoppingCart().setId(shoppingCartDao.getIdIfExist(userDao.getIdIfExist(user)));
        List<Item> itemsToOrder = user.getShoppingCart().getItems();
        for (Item item : user.getShoppingCart().getItems()) {
            if (itemDao.getStock(item) <= 0) {
                user.getShoppingCart().getItems().remove(item);
                System.out.println(item.getName() + " is not available.");
                continue;
            }
            Order order = new Order(DateUtil.getCurrentDate(), user, item);
            orderDao.insert(order);
        }
        for (Item item : user.getShoppingCart().getItems()) {
            itemDao.setStock(itemDao.getStock(item) - 1, item);
        }
        shoppingCartDao.deleteCartOfUser(userDao.getIdIfExist(user));
        user.setShoppingCart(new ShoppingCart());
        return itemsToOrder;
    }

    public List getOrders(User user) throws Exception {
        List<Order> orders = orderDao.findOrdersOfUser(userDao.getIdIfExist(user));
        return orders;
    }
}
