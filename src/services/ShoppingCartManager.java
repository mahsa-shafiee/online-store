package services;

import dao.ItemDao;
import dao.ShoppingCartDao;
import model.Item;
import model.ShoppingCart;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartManager {
    private static ShoppingCartDao shoppingCartDao = new ShoppingCartDao();

    public static boolean updateShoppingCart(User user, Item item) throws Exception {

        setItemsOfCart(user);
        if (user.getShoppingCart() != null && user.getShoppingCart().getItems().size() == 5) {
            return false;
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItem(item);
        shoppingCart.setUser(user);
        shoppingCartDao.insert(shoppingCart);
        return true;
    }

    public static boolean deleteProductFromShoppingCart(String productName) throws Exception {
        String[] splitAnswer = productName.split("del ");
        ItemDao itemDao = new ItemDao();
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        List<Item> search = itemDao.search(itemDao.getIdIfExist(splitAnswer[1]));
        if (search != null) {
            shoppingCartDao.deleteRow(itemDao.getIdIfExist(splitAnswer[1]));
            return true;
        } else {
            return false;
        }
    }

    public static void setItemsOfCart(User user) {
        List<Item> search = shoppingCartDao.findItems(user);
        List<Item> items = new ArrayList<>(search);
        ShoppingCart shoppingcart;
        if (user.getShoppingCart() != null)
            shoppingcart = user.getShoppingCart();
        else
            shoppingcart = new ShoppingCart();
        shoppingcart.setItems(items);
        user.setShoppingCart(shoppingcart);
    }
}
