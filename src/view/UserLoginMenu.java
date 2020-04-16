package view;

import model.Address;
import model.User;
import services.UserService;

import java.util.Scanner;

public class UserLoginMenu {
    Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();
    private UserPurchaseMenu userPurchaseMenu = new UserPurchaseMenu();

    public void showUserMenu() throws Exception {
        outer:
        while (true) {
            System.out.println("1)Login 2)Register \n(If you already have an account, select Login.)");
            String userChoice = scanner.next();
            switch (userChoice) {
                case "1":
                    while (true) {
                        if (Main.EXIT)
                            return;
                        if (Main.SIGN_OUT) {
                            Main.SIGN_OUT = false;
                            continue outer;
                        }
                        getUserInfoToLogin();
                    }
                case "2":
                    while (true) {
                        try {
                            if (Main.EXIT)
                                return;
                            if (Main.SIGN_OUT) {
                                Main.SIGN_OUT = false;
                                continue outer;
                            }
                            getUserInfoToRegister();
                        } catch (Exception e) {
                            System.out.println(Main.ANSI_RED + e + "\nInvalid input!" + Main.ANSI_RESET);
                            continue;
                        }
                    }
                default:
                    System.out.println(Main.ANSI_RED + "Invalid input!" + Main.ANSI_RESET);
            }
        }
    }

    private void getUserInfoToLogin() throws Exception {
        System.out.println(Main.BLACK_BOLD + "Account Login:" + Main.ANSI_RESET);
        String userName = getUserName();
        String userPassword = getUserPassword();
        User user = userService.validateUser(userName, userPassword);
        if (user != null) {
            System.out.println("Hi " + Main.ANSI_YELLOW + user.getFirstName() + Main.ANSI_RESET + "!");
            userPurchaseMenu.showPurchaseMenu(user);
        } else {
            System.out.println(Main.ANSI_RED + "The information entered is incorrect!" + Main.ANSI_RESET);
        }
    }

    private void getUserInfoToRegister() throws Exception {
        System.out.println(Main.BLACK_BOLD + "Account Registration:" + Main.ANSI_RESET);
        String firstName = getUserFirstName();
        String lastName = getUserLastName();
        int age = getUserAge();
        String mobileNumber = getUserMobileNumber();
        String emailAddress = getUserEmailAddress();
        Address homeAddress = getUserHomeAddress();
        String userName = getUserSelectedUserName();
        String password = getUserSelectedPassword();
        User user = new User(userName, password, firstName, lastName, age, mobileNumber, emailAddress, homeAddress);
        userService.registerNewUser(user, homeAddress);
        userPurchaseMenu.showPurchaseMenu(user);
    }

    private String getUserName() {
        System.out.println("Enter your user name:");
        return scanner.next();
    }

    private String getUserPassword() {
        System.out.println("Enter your password :");
        return scanner.next();
    }

    private String getUserFirstName() {
        System.out.println("Enter your first name:");
        return scanner.next();
    }

    private String getUserLastName() {
        System.out.println("Enter your last name:");
        return scanner.next();
    }

    private int getUserAge() throws Exception {
        System.out.println("Enter your age:");
        int age = scanner.nextInt();
        if (age <= 0) {
            throw new Exception("< You have entered an invalid age! >");
        } else return age;
    }


    private String getUserMobileNumber() throws Exception {
        System.out.println("Mobile number:\nHelp : It should be 11 digits!");
        String mobileNumber = scanner.next();
        for (int i = 0; i < mobileNumber.length(); i++) {
            if (!Character.isDigit(mobileNumber.charAt(i)) || mobileNumber.length() != 11)
                throw new Exception("< It's not 11 digits! >");
        }
        return mobileNumber;
    }

    private String getUserEmailAddress() throws Exception {
        System.out.println("Email address:");
        String emailAddress = scanner.next();
        if (!emailAddress.contains("@"))
            throw new Exception("< It doesn't contain '@' >");
        return emailAddress;
    }

    private Address getUserHomeAddress() throws Exception {
        System.out.println("Home address:(state,city,street,postal_code)");
        String[] homeAddress = scanner.next().split(",");
        if (homeAddress[3].length() != 10)
            throw new Exception("< Postal code should be 10 digits! >");
        Address address = new Address(homeAddress[0], homeAddress[1], homeAddress[2], Long.parseLong(homeAddress[3]));
        return address;
    }

    private String getUserSelectedUserName() {
        System.out.println("Choose user name:");
        return scanner.next();
    }

    private String getUserSelectedPassword() {
        System.out.println("Choose password:");
        return scanner.next();
    }
}
