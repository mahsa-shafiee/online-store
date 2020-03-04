import dao.*;
import dto.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static boolean EXIT = false;
    public static boolean SIGNOUT = false;

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println(BLUE_BOLD + "****Hi, Welcome To Online Store****\n" + ANSI_RESET +
                        "Choose your role:\n1)User 2)Admin");
                Scanner scanner = new Scanner(System.in);
                int role = scanner.nextInt();
                switch (role) {
                    case 1:
                        outer:
                        while (true) {
                            System.out.println("1)Login 2)Register \n(If you already have an account, select Login.)");
                            String userChoice = scanner.next();
                            switch (userChoice) {
                                case "1":
                                    while (true) {
                                        if (EXIT)
                                            return;
                                        if (SIGNOUT) {
                                            SIGNOUT = false;
                                            continue outer;
                                        }
                                        System.out.println(BLACK_BOLD + "Account Login:" + ANSI_RESET);
                                        System.out.println("Enter your user name:");
                                        String userName = scanner.next();
                                        System.out.println("Enter your password:");
                                        String password = scanner.next();
                                        UserDao userDao = new UserDao();
                                        User[] users = userDao.search(userName, password);
                                        for (User user : users) {
                                            if (user != null) {
                                                System.out.println("Hi " + ANSI_YELLOW + user.getFirstName() + ANSI_RESET + "!");
                                                menu(user);
                                            } else
                                                System.out.println(ANSI_RED + "The information entered is incorrect!" + ANSI_RESET);
                                        }
                                    }
                                case "2":
                                    while (true) {
                                        try {
                                            if (EXIT)
                                                return;
                                            if (SIGNOUT) {
                                                SIGNOUT = false;
                                                continue outer;
                                            }
                                            System.out.println(BLACK_BOLD + "Account Registration:" + ANSI_RESET);
                                            System.out.println("First name:");
                                            String firstName = scanner.next();
                                            System.out.println("Last name:");
                                            String lastName = scanner.next();
                                            System.out.println("Mobile number:");
                                            String mobileNumber = scanner.next();
                                            for (int i = 0; i < mobileNumber.length(); i++) {
                                                if (!Character.isDigit(mobileNumber.charAt(i)) || mobileNumber.length() != 11)
                                                    throw new Exception();
                                            }
                                            System.out.println("Email address:");
                                            String emailAddress = scanner.next();
                                            if (!emailAddress.contains("@"))
                                                throw new Exception();
                                            System.out.println("Home address:(state,city,street,postal_code)");
                                            String[] homeAddress = scanner.next().split(",");
                                            if (homeAddress[3].length() != 10)
                                                throw new Exception();
                                            Address address = new Address(homeAddress[0], homeAddress[1], homeAddress[2],
                                                    Long.parseLong(homeAddress[3]));
                                            AddressDao addressDao = new AddressDao();
                                            addressDao.insert(address);
                                            System.out.println("Choose user name:");
                                            String userName = scanner.next();
                                            System.out.println("Choose password:");
                                            String password = scanner.next();
                                            User user = new User(userName, password, firstName, lastName, mobileNumber, emailAddress, address);
                                            UserDao userDao = new UserDao();
                                            address.setId(addressDao.getIdFromDataBase(address));
                                            userDao.insert(user);
                                            menu(user);
                                        } catch (Exception e) {
                                            System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                            continue;
                                        }
                                    }
                                default:
                                    System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                            }
                        }
                    case 2:
                        while (true) {
                            System.out.println("enter name:");//name=0
                            String name = scanner.next();
                            System.out.println("enter password:");//password=0
                            String password = scanner.next();
                            AdminDao adminDao = new AdminDao();
                            Admin[] admins = adminDao.search(name, password);
                            for (Admin admin : admins) {
                                if (admin != null) {
                                    while (true) {
                                        System.out.println(GREEN_BOLD + "What do you want to do?" + ANSI_RESET +
                                                "\n1)Edit categories 2)Edit products 3)exit");
                                        String choice = scanner.next();
                                        switch (choice) {
                                            case "1":
                                                System.out.println(
                                                        "1)Add new category 2)Rename existing category 3)Delete existing category");
                                                choice = scanner.next();
                                                switch (choice) {
                                                    case "1":
                                                        CategoryDao categoryDao = new CategoryDao();
                                                        System.out.println("category name:");
                                                        scanner.nextLine();
                                                        name = scanner.nextLine();
                                                        Category category = new Category(name, admin);
                                                        admin.setId(adminDao.getIdFromDataBase(admin));
                                                        categoryDao.insert(category);
                                                        break;
                                                    case "2":
                                                        categoryDao = new CategoryDao();
                                                        categoryDao.showAll();
                                                        System.out.println("Enter in this way " + BLACK_BOLD +
                                                                "old name,new name" + ANSI_RESET + " to rename:");
                                                        scanner.nextLine();
                                                        try {
                                                            String[] splitName = scanner.nextLine().split(",");
                                                            categoryDao.rename(splitName[0], splitName[1]);
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                                        }
                                                        break;
                                                    case "3":
                                                        categoryDao = new CategoryDao();
                                                        categoryDao.showAll();
                                                        System.out.println("\nEnter name of the category you want to delete:");
                                                        scanner.nextLine();
                                                        name = scanner.nextLine();
                                                        categoryDao.delete(name);
                                                        break;
                                                    default:
                                                        System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                                        continue;
                                                }
                                                continue;
                                            case "2":
                                                System.out.println("1)Add new product 2)delete existing product");
                                                choice = scanner.next();
                                                switch (choice) {
                                                    case "1":
                                                        CategoryDao categoryDao = new CategoryDao();
                                                        categoryDao.showAll();
                                                        System.out.println("Enter the category of the product you want to add:");
                                                        scanner.nextLine();
                                                        String categoryName = scanner.nextLine();
                                                        ItemDao itemDao = new ItemDao();
                                                        System.out.println("Enter information of product in this way " + BLACK_BOLD +
                                                                "name,description,price,stock" + ANSI_RESET);
                                                        try {
                                                            String[] splitInformation = scanner.nextLine().split(",");
                                                            Item item = new Item(splitInformation[0], splitInformation[1],
                                                                    Long.parseLong(splitInformation[2]),
                                                                    Integer.parseInt(splitInformation[3]), admin);
                                                            Category category = new Category(categoryDao.getIdFromDataBase(categoryName),
                                                                    categoryName, admin);
                                                            item.setCategory(category);
                                                            admin.setId(adminDao.getIdFromDataBase(admin));
                                                            itemDao.insert(item);
                                                        } catch (ArrayIndexOutOfBoundsException e) {
                                                            System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                                        }
                                                        break;
                                                    case "2":
                                                        itemDao = new ItemDao();
                                                        itemDao.showAll();
                                                        System.out.println("\nEnter the name of the product you want to delete:");
                                                        scanner.nextLine();
                                                        name = scanner.nextLine();
                                                        itemDao.delete(name);
                                                        break;
                                                    default:
                                                        System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                                        break;
                                                }
                                                break;
                                            case "3":
                                                return;
                                            default:
                                                System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                                                break;
                                        }
                                    }
                                } else
                                    System.out.println(ANSI_RED + "The information entered is incorrect!" + ANSI_RESET);
                            }
                        }
                    default:
                        throw new Exception();
                }
            } catch (Exception e) {
                System.out.println(ANSI_RED + e.toString() + " <Invalid Input, Try again.>" + ANSI_RESET);
            }
        }
    }

    public static void menu(User user) throws Exception {
        outer:
        while (true) {
            try {
                System.out.println(GREEN_BOLD + "What do you want to do?" + ANSI_RESET +
                        "\n1)View Product Categories 2)View shopping cart 3)View orders 4)Sign out of account 5)exit");
                Scanner scanner = new Scanner(System.in);
                String choice = scanner.next();
                switch (choice) {
                    case "1":
                        CategoryDao categoryDao = new CategoryDao();
                        HashSet<String> names = categoryDao.showAll();
                        System.out.println("\nEnter the category you want:");
                        scanner.nextLine();
                        String categoryName = scanner.nextLine();
                        if (!names.contains(categoryName)) {
                            System.out.println(ANSI_RED + "There is no such category." + ANSI_RESET);
                            continue;
                        }
                        ItemDao itemDao = new ItemDao();
                        Item[] itemsOfCategory = itemDao.showItemsOfCategory(categoryDao.getIdFromDataBase(categoryName));
                        if (itemsOfCategory.length == 0) {
                            System.out.println(ANSI_RED + "This category is empty." + ANSI_RESET);
                            continue;
                        }
                        int itemNumber = 1;
                        System.out.println(BLUE_BOLD + categoryName.toUpperCase() + ":" + ANSI_RESET);
                        for (Item item : itemsOfCategory) {
                            if (item != null) {
                                item.getCategory().setName(categoryName);
                                System.out.println(BLACK_BOLD + itemNumber + ") " + item.getName() + ANSI_RESET);
                                itemNumber++;
                            }
                        }
                        if (itemsOfCategory[0] == null)
                            break;
                        System.out.println("Enter the number of product you want to see in detail:");
                        try {
                            itemNumber = scanner.nextInt();
                            System.out.println(itemsOfCategory[itemNumber - 1].toString());
                        } catch (Exception e) {
                            System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                            continue;
                        }
                        System.out.println(BLACK_BOLD + "Would you like to add this product to your shopping cart?"
                                + ANSI_RESET + "\n(Help: enter Y or N)");
                        String answer = scanner.next();
                        switch (answer) {
                            case "Y":
                                ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
                                shoppingCartDao.setItemsOfCart(user);
                                if (user.getShoppingcart() != null && user.getShoppingcart().getItems().size() == 5) {
                                    System.out.println(ANSI_RED + "Sorry ,Your cart is full\n" +
                                            "You have to reduce the items or complete your purchase." + ANSI_RESET);
                                    break;
                                }
                                ShoppingCart shoppingCart = new ShoppingCart();
                                shoppingCart.setItem(itemsOfCategory[itemNumber - 1]);
                                shoppingCart.setUser(user);
                                shoppingCartDao.insert(shoppingCart);
                                break;
                            case "N":
                                break;
                            default:
                                System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                        }
                        break;
                    case "2":
                        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
                        user.setShoppingcart(null);
                        shoppingCartDao.setItemsOfCart(user);
                        if (user.getShoppingcart() == null || user.getShoppingcart().getItems() == null) {
                            System.out.println(BLACK_BOLD + "Your shopping cart is empty :(" + ANSI_RESET);
                            break;
                        }
                        List<Item> items = user.getShoppingcart().getItems();
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
                            user.getShoppingcart().setTotalPrice(totalPrice);
                            if (!itemHashSet.contains(item)) {
                                System.out.println(BLUE_BOLD + "number " + number + ":\n" + ANSI_RESET + item.toString());
                                System.out.println("count=" + count);
                                System.out.println("if you want to delete this item (one of them) from your cart enter" +
                                        BLACK_BOLD + " del " + item.getName() + ANSI_RESET);
                                number++;
                            }
                            itemHashSet.add(item);
                        }
                        System.out.println(BLUE_BOLD + "Total Price= " + user.getShoppingcart().getTotalPrice() + " Rials" + ANSI_RESET);
                        System.out.println("Enter " + BLACK_BOLD + "order" + ANSI_RESET + " to complete your purchase" +
                                "\n(If you don't want to delete an item or complete your purchase, Press enter.)");
                        scanner.nextLine();
                        answer = scanner.nextLine();
                        if (answer == null || answer.length() == 0)
                            break;
                        if (Objects.equals(answer, "order")) {
                            OrderDao orderDao = new OrderDao();
                            UserDao userDao = new UserDao();
                            shoppingCartDao.setItemsOfCart(user);
                            user.getShoppingcart().setId(shoppingCartDao.getIdFromDataBase(userDao.getIdFromDataBase(user)));
                            long millis = System.currentTimeMillis();
                            java.sql.Date date = new java.sql.Date(millis);
                            for (Item item : user.getShoppingcart().getItems()) {
                                shoppingCartDao.setItemsOfCart(user);
                                Order order = new Order(date, user, item);
                                orderDao.insert(order);
                            }
                            itemDao = new ItemDao();
                            for (Item item : user.getShoppingcart().getItems()) {
                                itemDao.setStock(itemDao.getStock(item) - 1, item);
                            }
                            shoppingCartDao.deleteCartOfUser(userDao.getIdFromDataBase(user));
                            System.out.println("Purchased successfully.");
                            user.setShoppingcart(new ShoppingCart());
                            break;
                        }
                        if (!answer.contains("del ") && !Objects.equals(answer, "order")) {
                            System.out.println(ANSI_RED + "Invalid!" + ANSI_RESET);
                            break;
                        }
                        String[] splitAnswer = answer.split("del ");
                        itemDao = new ItemDao();
                        List<Item> search = itemDao.search(itemDao.getIdFromDataBase(splitAnswer[1]));
                        if (search != null) {
                            shoppingCartDao.deleteRow(itemDao.getIdFromDataBase(splitAnswer[1]));
                        } else {
                            System.out.println(ANSI_RED + "The information entered is incorrect!" + ANSI_RESET);
                        }
                        break;
                    case "3":
                        OrderDao orderDao = new OrderDao();
                        List<Order> orders = orderDao.showOrdersOfuser(new UserDao().getIdFromDataBase(user));
                        number = 1;
                        for (Order order : orders) {
                            System.out.println(BLUE_BOLD + "NUMBER " + number + " :\n" + ANSI_RESET + order.toString());
                            number++;
                        }
                        break;
                    case "4":
                        SIGNOUT = true;
                        return;
                    case "5":
                        EXIT = true;
                        break outer;
                    default:
                        System.out.println(ANSI_RED + "Invalid input!" + ANSI_RESET);
                        break;
                }
            } catch (Exception e) {
                throw new Exception();
            }
        }
    }
}
