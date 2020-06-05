package view;

import model.Admin;
import model.Item;
import model.OperationLog;
import model.User;
import services.AdminService;
import util.DateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class AdminMenus {
    private Scanner scanner = new Scanner(System.in);
    private AdminService adminService = new AdminService();

    public void showAdminMenu() throws Exception {
        while (true) {
            Admin admin = new Admin();
            String adminUserName = getAdminName();
            String adminPassword = getAdminPassword();
            if (adminService.validateAdmin(adminUserName, adminPassword)) {
                while (true) {
                    System.out.println(Main.GREEN_BOLD + "What do you want to do?" + Main.ANSI_RESET +
                            "\n1)Edit categories 2)Edit products 3)View users status report (Sorted by user age) 4)View user log 5)exit");
                    String choice = scanner.next();
                    admin.setName(adminUserName);
                    admin.setPassword(adminPassword);
                    switch (choice) {
                        case "1":
                            System.out.println(
                                    "1)Add new category 2)Rename existing category 3)Delete existing category");
                            choice = scanner.next();
                            switch (choice) {
                                case "1":
                                    addCategory(admin);
                                    break;
                                case "2":
                                    renameExistingCategory();
                                    break;
                                case "3":
                                    deleteExistingCategory();
                                    break;
                                default:
                                    System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
                                    continue;
                            }
                            continue;
                        case "2":
                            System.out.println("1)Add new product 2)delete existing product");
                            choice = scanner.next();
                            switch (choice) {
                                case "1":
                                    addProduct(admin);
                                    break;
                                case "2":
                                    deleteExistingProduct();
                                    break;
                                default:
                                    System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
                                    break;
                            }
                            break;
                        case "3":
                            showUsersStatusReport();
                            break;
                        case "4":
                            showUserOperationLogsReport();
                            break;
                        case "5":
                            Main.EXIT = true;
                            return;
                        default:
                            System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
                            break;
                    }
                }
            } else {
                System.out.println(Main.ANSI_RED + "The information entered is incorrect!" + Main.ANSI_RESET);
            }
        }
    }

    public String getAdminName() {
        System.out.println("Enter your admin userName:");
        return scanner.next();
    }

    public String getAdminPassword() {
        System.out.println("Enter your password :");
        return scanner.next();
    }

    public void addCategory(Admin admin) throws Exception {
        System.out.println("category name:");
        scanner.nextLine();
        String name = scanner.nextLine();
        adminService.addCategory(admin, name);
    }

    public void renameExistingCategory() throws Exception {
        showAllCategories();
        System.out.println("Enter in this way " + Main.BLACK_BOLD +
                "old name,new name" + Main.ANSI_RESET + " to rename:");
        scanner.nextLine();
        try {
            String[] splitName = scanner.nextLine().split(",");
            adminService.renameCategory(splitName[0], splitName[1]);
            System.out.println("Renamed successfully.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
        }
    }

    public void deleteExistingCategory() throws Exception {
        showAllCategories();
        System.out.println("\nEnter name of the category you want to delete:");
        scanner.nextLine();
        String name = scanner.nextLine();
        adminService.deleteCategoryByName(name);
    }

    public void addProduct(Admin admin) throws Exception {
        showAllCategories();
        System.out.println("Enter the category of the product you want to add:");
        scanner.nextLine();
        String categoryName = scanner.nextLine();
        System.out.println("Enter information of product in this way " + Main.BLACK_BOLD +
                "name,description,price,stock" + Main.ANSI_RESET);
        String[] splitInformation = scanner.nextLine().split(",");
        Item item = new Item(splitInformation[0], splitInformation[1],
                Long.parseLong(splitInformation[2]),
                Integer.parseInt(splitInformation[3]), admin);
        System.out.println(admin.getPassword());
        adminService.addProduct(admin, item, categoryName);
    }

    public void deleteExistingProduct() throws Exception {
        showAllItems();
        System.out.println("\nEnter the name of the product you want to delete:");
        scanner.nextLine();
        String name = scanner.nextLine();
        adminService.deleteItemByName(name);
    }

    private void showAllCategories() throws Exception {
        HashSet<String> allCategory = adminService.findAllCategory();
        for (String category : allCategory) {
            System.out.println(category);
        }
    }

    private void showAllItems() throws Exception {
        HashSet<String> allItems = adminService.findAllItems();
        for (String item : allItems) {
            System.out.println(item);
        }
    }

    private void showUsersStatusReport() {
        List<User> allUsers = adminService.findAllUsers();
        int userNumber = 1;
        for (User user : allUsers) {
            System.out.println(userNumber + ") " + user.toString());
            userNumber++;
        }
    }

    private void showUserOperationLogsReport() throws Exception {
        User user = getUser();
        String dateStr = getSinceDate();
        List<OperationLog> operationLogs = adminService.getOperationLogs(user, dateStr);

        System.out.println(Main.BLACK_BOLD + "Operations of user '" + user.getUserName()
                + "' since " + dateStr + " to a month later:" + Main.ANSI_RESET);

        int logNumber = 1;
        for (OperationLog operationLog : operationLogs) {
            System.out.println(logNumber + ")User with username '" + operationLog.getAuthority()
                    + "' performed an " + operationLog.getOperation()
                    + " operation on " + operationLog.getDate() + " at " + operationLog.getTime());
            logNumber++;
        }
    }

    private String getSinceDate() {
        System.out.println("Since what date do you want to see logs?\ne.g., 2020-04-03");
        while (true) {
            try {
                String dateStr = scanner.next();
                DateUtil.convertStrToDate(dateStr);
                return dateStr;
            } catch (Exception e) {
                System.out.println("Invalid date!\nTry again...");
            }
        }
    }

    private User getUser() {
        List<User> allUsers = adminService.findAllUsers();
        allUsers.forEach(user -> System.out.println((allUsers.indexOf(user) + 1) + ") " + user.getUserName()));
        System.out.println("Enter the number of user:");
        int userNumber = getUserNumber(allUsers.size());
        return allUsers.get(userNumber - 1);
    }

    private int getUserNumber(int listSize) {
        while (true) {
            try {
                int userNumber = scanner.nextInt();
                if (userNumber >= listSize)
                    throw new IllegalArgumentException();
                return userNumber;
            } catch (Exception e) {
                System.out.println("Invalid number!\nTry again...");
                scanner.next();
            }
        }
    }

}
