package services;

import dao.ItemDao;
import dao.ShoppingCartDao;
import model.Item;
import model.ShoppingCart;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Lazy
public class ShoppingCartManager {

    @Autowired(required = false)
    private ShoppingCartDao shoppingCartDao;
    @Autowired(required = false)
    private ItemDao itemDao;

    public boolean updateShoppingCart(User user, Item item) {

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

    public int deleteProductFromShoppingCart(String productName) {
        String[] splitAnswer = productName.split("del ");
        int productId = itemDao.getIdIfExist(splitAnswer[1]);
        List<Item> search = itemDao.search(productId);
        if (search != null) {
            shoppingCartDao.deleteRow(productId);
            return productId;
        } else {
            return 0;
        }
    }

    public void setItemsOfCart(User user) {
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
