package services;

import dao.*;
import model.Item;
import model.Order;
import model.ShoppingCart;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import util.DateUtil;

import java.util.HashSet;
import java.util.List;

@Component
@Lazy
public class PurchaseService {

    @Autowired(required = false)
    private CategoryDao categoryDao;
    @Autowired(required = false)
    private ShoppingCartDao shoppingCartDao;
    @Autowired(required = false)
    private OrderDao orderDao;
    @Autowired(required = false)
    private UserDao userDao;
    @Autowired(required = false)
    private ItemDao itemDao;
    @Autowired(required = false)
    private DateUtil dateUtil;

    public HashSet<String> getAllCategories() {
        HashSet<String> names = categoryDao.findAll();
        return names;
    }

    public Item[] getProductsOfCategory(String categoryName) {
        int categoryId = categoryDao.getIdIfExist(categoryName);
        return itemDao.showItemsOfCategory(categoryId);
    }

    public boolean checkProductAvailability(Item product) {
        return itemDao.getStock(product) > 0;
    }

    public List<Item> finalizeOrder(User user) {
        user.getShoppingCart().setId(shoppingCartDao.getIdIfExist(userDao.getIdIfExist(user)));
        List<Item> itemsToOrder = user.getShoppingCart().getItems();
        for (Item item : user.getShoppingCart().getItems()) {
            if (itemDao.getStock(item) <= 0) {
                user.getShoppingCart().getItems().remove(item);
                System.out.println(item.getName() + " is not available.");
                continue;
            }
            Order order = new Order(dateUtil.getCurrentDate(), user, item);
            orderDao.insert(order);
        }
        for (Item item : user.getShoppingCart().getItems()) {
            itemDao.setStock(itemDao.getStock(item) - 1, item);
        }
        shoppingCartDao.deleteCartOfUser(userDao.getIdIfExist(user));
        user.setShoppingCart(new ShoppingCart());
        return itemsToOrder;
    }

    public List getOrders(User user) {
        int userId = userDao.getIdIfExist(user);
        return orderDao.findOrdersOfUser(userId);
    }
}
