package view;

import model.Item;
import model.Order;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import services.PurchaseService;
import services.ShoppingCartManager;
import services.UserService;
import util.OperationType;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Lazy
public class UserPurchaseMenu {

    @Autowired(required = false)
    private Scanner scanner;
    @Autowired(required = false)
    private PurchaseService purchaseService;
    @Autowired(required = false)
    private UserService userService;
    @Autowired(required = false)
    private ShoppingCartManager shoppingCartManager;

    public void showPurchaseMenu(User user) throws Exception {
        outer:
        while (true) {
            try {
                System.out.println(Main.GREEN_BOLD + "What do you want to do?" + Main.ANSI_RESET +
                        "\n1)View Product Categories 2)View shopping cart (Sorted by the unit price of the product) 3)View orders 4)Sign out of account 5)exit");
                String choice = scanner.next();
                switch (choice) {
                    case "1":
                        displayCategoriesAndProducts(user);
                        break;
                    case "2":
                        displayAndManageShoppingCart(user);
                        break;
                    case "3":
                        displayOrders(user);
                        break;
                    case "4":
                        Main.SIGN_OUT = true;
                        userService.recordNewLog(OperationType.LOGOUT, user.getUserName());
                        return;
                    case "5":
                        Main.EXIT = true;
                        userService.recordNewLog(OperationType.EXIT, user.getUserName());
                        break outer;
                    default:
                        System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                throw new Exception();
            }
        }
    }

    public void displayCategoriesAndProducts(User user) {
        String categoryName = displayAndGetCategoryName();
        if (categoryName == null)
            return;
        switch (displayProductsOfCategory(categoryName)) {
            case 0:
                return;
            case -1:
                break;
        }
        System.out.println("Enter the number of product you want to see in detail:");
        Item selectedProduct = getAndDisplayDetailsOfProduct(categoryName);
        if (selectedProduct == null)
            return;
        if (!purchaseService.checkProductAvailability(selectedProduct)) {
            System.out.println(Main.ANSI_RED + "Sorry, This product is not available." + Main.ANSI_RESET);
            return;
        }
        OperationType.VIEW_PRODUCT.setItemId(selectedProduct.getId());
        userService.recordNewLog(OperationType.VIEW_PRODUCT, user.getUserName());
        System.out.println(Main.BLACK_BOLD + "Would you like to add this product to your shopping Cart?"
                + Main.ANSI_RESET + "\n(Help: enter Y or N)");
        String answer = scanner.next();

        switch (answer) {
            case "Y":
                if (!shoppingCartManager.updateShoppingCart(user, selectedProduct)) {
                    System.out.println(Main.ANSI_RED + "Sorry ,Your Cart is full\n" +
                            "You have to reduce the items or complete your purchase." + Main.ANSI_RESET);
                    break;
                }
                OperationType.ADD_TO_CART.setItemId(selectedProduct.getId());
                userService.recordNewLog(OperationType.ADD_TO_CART, user.getUserName());
                break;
            case "N":
                break;
            default:
                System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
        }
    }

    public void displayAndManageShoppingCart(User user) {
        String answer;
        if (!displayShoppingCart(user)) {
            OperationType.VIEW_CART.setItemsIds(new ArrayList<>());
            userService.recordNewLog(OperationType.VIEW_CART, user.getUserName());
            return;
        }
        OperationType.VIEW_CART.setItemsIds(user.getShoppingCart().getItems().stream().map(Item::getId).collect(Collectors.toList()));
        userService.recordNewLog(OperationType.VIEW_CART, user.getUserName());
        System.out.println("Enter " + Main.BLACK_BOLD + "order" + Main.ANSI_RESET + " to complete your purchase" +
                "\n(If you don't want to delete an item or complete your purchase, Press enter.)");
        scanner.nextLine();
        answer = scanner.nextLine();
        if (answer == null || answer.length() == 0)
            return;
        if (Objects.equals(answer, "order")) {
            List<Item> orderedItems = purchaseService.finalizeOrder(user);
            orderedItems.forEach(item -> {
                try {
                    OperationType.ORDER.setItemId(item.getId());
                    userService.recordNewLog(OperationType.ORDER, user.getUserName());
                } catch (Exception ignored) {
                }
            });
            System.out.println("Purchased successfully.");
            return;
        }
        if (!answer.contains("del ") && !Objects.equals(answer, "order")) {
            System.out.println(Main.ANSI_RED + "Invalid!" + Main.ANSI_RESET);
            return;
        }
        int productId = shoppingCartManager.deleteProductFromShoppingCart(answer);
        if (productId == 0) {
            System.out.println(Main.ANSI_RED + "The information entered is incorrect!" + Main.ANSI_RESET);
        } else {
            OperationType.DELETE_FROM_CART.setItemId(productId);
            userService.recordNewLog(OperationType.DELETE_FROM_CART, user.getUserName());
        }
    }

    private String displayAndGetCategoryName() {
        System.out.println("\nEnter the category you want:\n");
        HashSet<String> categoryNames = purchaseService.getAllCategories();
        for (String categoryName : categoryNames) {
            System.out.println(categoryName);
        }
        scanner.nextLine();
        String categoryName = scanner.nextLine();
        if (!categoryNames.contains(categoryName)) {
            System.out.println(Main.ANSI_RED + "There is no such category." + Main.ANSI_RESET);
            return null;
        }
        return categoryName;
    }

    private int displayProductsOfCategory(String categoryName) {
        Item[] itemsOfCategory = purchaseService.getProductsOfCategory(categoryName);
        if (itemsOfCategory.length == 0) {
            System.out.println(Main.ANSI_RED + "This category is empty." + Main.ANSI_RESET);
            return 0;
        }
        int itemNumber = 1;
        System.out.println(Main.BLUE_BOLD + categoryName.toUpperCase() + ":" + Main.ANSI_RESET);
        for (Item item : itemsOfCategory) {
            if (item != null) {
                item.getCategory().setName(categoryName);
                System.out.println(Main.BLACK_BOLD + itemNumber + ") " + item.getName() + Main.ANSI_RESET);
                itemNumber++;
            }
        }
        if (itemsOfCategory[0] == null)
            return -1;
        return 1;
    }

    private Item getAndDisplayDetailsOfProduct(String categoryName) {
        try {
            int itemNumber = scanner.nextInt();
            Item[] itemsOfCategory = purchaseService.getProductsOfCategory(categoryName);
            itemsOfCategory[itemNumber - 1].getCategory().setName(categoryName);
            System.out.println(itemsOfCategory[itemNumber - 1].toString());
            return itemsOfCategory[itemNumber - 1];
        } catch (Exception e) {
            System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
            return null;
        }
    }

    private boolean displayShoppingCart(User user) {

        user.setShoppingCart(null);
        shoppingCartManager.setItemsOfCart(user);
        if (user.getShoppingCart() == null || user.getShoppingCart().getItems() == null || user.getShoppingCart().getItems().size() == 0) {
            System.out.println(Main.BLACK_BOLD + "Your shopping Cart is empty :(" + Main.ANSI_RESET);
            return false;
        }
        List<Item> items = user.getShoppingCart().getItems();
        items = items.stream().sorted().collect(Collectors.toList());
        HashSet<Item> itemHashSet = new HashSet<>();
        int number = 1;
        long totalPrice = 0;
        for (Item item : items) {
            int count = 0;
            for (Item item1 : items) {
                if (item.equals(item1))
                    count++;
            }
            totalPrice += item.getPrice();
            user.getShoppingCart().setTotalPrice(totalPrice);
            if (!itemHashSet.contains(item)) {
                System.out.println(Main.BLUE_BOLD + "number " + number + ":\n" + Main.ANSI_RESET + item.toString());
                System.out.println("count=" + count);
                System.out.println("if you want to delete this item (one of them) from your cart enter" +
                        Main.BLACK_BOLD + " del " + item.getName() + Main.ANSI_RESET);
                number++;
            }
            itemHashSet.add(item);
        }
        System.out.println(Main.BLUE_BOLD + "Total Price= " + user.getShoppingCart().getTotalPrice() + " Rials" + Main.ANSI_RESET);
        return true;

    }

    private void displayOrders(User user) {
        List<Order> orders = purchaseService.getOrders(user);
        int number = 1;
        for (Order order : orders) {
            System.out.println(Main.BLUE_BOLD + "NUMBER " + number + " :\n" + Main.ANSI_RESET + order.toString());
            number++;
        }
        userService.recordNewLog(OperationType.VIEW_ORDERS, user.getUserName());
    }

}
