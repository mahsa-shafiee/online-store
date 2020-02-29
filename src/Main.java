import dao.*;
import dto.*;

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
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN


    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println(BLUE_BOLD + "****Hi, Welcome To Online Store****\n" + ANSI_RESET +
                        "Choose your role:\n1)User 2)Admin");
                Scanner scanner = new Scanner(System.in);
                int role = scanner.nextInt();
                switch (role) {
                    case 1:
                        System.out.println("1)Login 2)Register \n(If you already have an account, select Login.)");
                        int userChoice = scanner.nextInt();
                        switch (userChoice) {
                            case 1:
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
                                        menu();
                                    } else
                                        System.out.println(ANSI_RED + "The information entered is incorrect!" + ANSI_RESET);
                                }
                                break;
                            case 2:
                                System.out.println(BLACK_BOLD + "Account Registration:" + ANSI_RESET);
                                System.out.println("First name:");
                                String firstName = scanner.next();
                                System.out.println("Last name:");
                                String lastName = scanner.next();
                                System.out.println("Mobile number:");
                                String mobileNumber = scanner.next();
                                System.out.println("Email address:");
                                String emailAdress = scanner.next();
                                System.out.println("Home address:(state,city,street,postal_code)");
                                String[] homeAddress = scanner.next().split(",");
                                Address address = new Address(homeAddress[0], homeAddress[1], homeAddress[2],
                                        Long.parseLong(homeAddress[3]));
                                AddressDao addressDao = new AddressDao();
                                addressDao.insert(address);
                                System.out.println("Choose user name:");
                                userName = scanner.next();
                                System.out.println("Choose password:");
                                password = scanner.next();
                                User user = new User(userName, password, firstName, lastName, mobileNumber, emailAdress, address);
                                userDao = new UserDao();
                                address.setId(addressDao.getId(address));
                                userDao.insert(user);
                                menu();
                        }
                        break;
                    case 2:
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
                                    int choice = scanner.nextInt();
                                    switch (choice) {
                                        case 1:
                                            System.out.println(
                                                    "1)Add new category 2)Rename existing category 3)Delete existing category");
                                            choice = scanner.nextInt();
                                            switch (choice) {
                                                case 1:
                                                    CategoryDao categoryDao = new CategoryDao();
                                                    System.out.println("category name:");
                                                    scanner.nextLine();
                                                    name = scanner.nextLine();
                                                    Category category = new Category(name, admin);
                                                    admin.setId(adminDao.getId(admin));
                                                    categoryDao.insert(category);
                                                    break;
                                                case 2:
                                                    categoryDao = new CategoryDao();
                                                    categoryDao.showAll();
                                                    System.out.println("Enter in this way " + BLACK_BOLD +
                                                            "old name,new name" + ANSI_RESET + " to rename:");
                                                    scanner.nextLine();
                                                    String[] splitName = scanner.nextLine().split(",");
                                                    categoryDao.rename(splitName[0], splitName[1]);
                                                    break;
                                                case 3:
                                                    categoryDao = new CategoryDao();
                                                    categoryDao.showAll();
                                                    System.out.println("\nEnter name of the category you want to delete:");
                                                    scanner.nextLine();
                                                    name = scanner.nextLine();
                                                    categoryDao.delete(name);
                                                    break;
                                            }
                                            break;
                                        case 2:
                                            System.out.println("1)Add new product 2)delete existing product");
                                            choice = scanner.nextInt();
                                            switch (choice) {
                                                case 1:
                                                    CategoryDao categoryDao = new CategoryDao();
                                                    categoryDao.showAll();
                                                    System.out.println("Enter the category of the product you want to add:");
                                                    scanner.nextLine();
                                                    String categoryName = scanner.nextLine();
                                                    ItemDao itemDao = new ItemDao();
                                                    System.out.println("Enter information of product in this way " + BLACK_BOLD +
                                                            "name,description,price,stock" + ANSI_RESET);
                                                    String[] splitInformation = scanner.nextLine().split(",");
                                                    Item item = new Item(splitInformation[0], splitInformation[1],
                                                            Long.parseLong(splitInformation[2]),
                                                            Integer.parseInt(splitInformation[3]), admin);
                                                    Category category = new Category(categoryDao.getId(categoryName),
                                                            categoryName, admin);
                                                    item.setCategory(category);
                                                    admin.setId(adminDao.getId(admin));
                                                    itemDao.insert(item);
                                                    break;
                                                case 2:
                                                    itemDao = new ItemDao();
                                                    itemDao.showAll();
                                                    System.out.println("\nEnter the name of the product you want to delete:");
                                                    scanner.nextLine();
                                                    name = scanner.nextLine();
                                                    itemDao.delete(name);
                                                    break;
                                            }
                                            break;
                                        case 3:
                                            return;
                                    }
                                }
                            } else
                                System.out.println(ANSI_RED + "The information entered is incorrect!" + ANSI_RESET);
                        }
                        break;
                    default:
                        throw new Exception("<Invalid Input, Try again.>");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public static void menu() {
        System.out.println(GREEN_BOLD + "What do you want to do?" + ANSI_RESET +
                "\n1)View Product Categories 2)View shopping cart 3)Sign out of account");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
