package view;

import model.Address;
import model.User;
import services.UserService;
import util.OperationType;

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
            userService.recordNewLog(OperationType.LOGIN, userName);
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
        userService.recordNewLog(OperationType.REGISTER, user.getUserName());
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
        String firstName = scanner.next();
        if (isValidName(firstName))
            return firstName;
        throw new IllegalArgumentException("< You have entered invalid last name! >");
    }

    private String getUserLastName() {
        System.out.println("Enter your last name:");
        String lastName = scanner.next();
        if (isValidName(lastName)) return lastName;
        throw new IllegalArgumentException("< You have entered invalid last name! >");
    }

    public boolean isValidName(String name) {
        return isAlphabetic(name) && name.length() > 3 && name.length() < 15;
    }

    private int getUserAge() {
        System.out.println("Enter your age:");
        String age = scanner.next();
        if (isValidAge(age)) return Integer.parseInt(age);
        throw new IllegalArgumentException("< You have entered an invalid age! >");
    }

    public boolean isValidAge(String age) {
        return isNumeric(age) && Integer.parseInt(age) > 7 && Integer.parseInt(age) < 120;
    }

    private String getUserMobileNumber() {
        System.out.println("Mobile number:\nHelp : It should be 11 digits!");
        String mobileNumber = scanner.next();
        if (isValidMobileNumber(mobileNumber)) return mobileNumber;
        throw new IllegalArgumentException("< You have entered invalid mobile number! >");
    }

    public boolean isValidMobileNumber(String mobileNumber) {
        return isNumeric(mobileNumber) && mobileNumber.length() == 11 && mobileNumber.charAt(0) == '0';
    }

    private String getUserEmailAddress() {
        System.out.println("Email address:");
        String emailAddress = scanner.next();
        if (isValidEmailAddress(emailAddress)) return emailAddress;
        throw new IllegalArgumentException("< You have entered invalid email address! >");
    }

    public boolean isValidEmailAddress(String emailAddress) {
        return (emailAddress.contains("@") && emailAddress.contains(".com")
                && isAlphabeticOrNumeric(emailAddress.split("@")[0]));
    }

    private Address getUserHomeAddress() {
        System.out.println("Home address:(state,city,street,postal_code)");
        String[] homeAddress = scanner.next().split(",");
        Address address = null;
        if (isValidHomeAddress(homeAddress[0], homeAddress[1], homeAddress[2], homeAddress[3])) {
            address = new Address(homeAddress[0], homeAddress[1], homeAddress[2], Long.parseLong(homeAddress[3]));
            return address;
        }
        throw new IllegalArgumentException("< You have entered invalid home address! >");
    }

    public boolean isValidHomeAddress(String state, String city, String street, String postal_code) {
        return isAlphabetic(state) && isAlphabetic(city) && isAlphabetic(street)
                && isNumeric(postal_code) && postal_code.length() == 10;
    }

    private String getUserSelectedUserName() {
        System.out.println("Choose user name:");
        String userName = scanner.next();
        if (isValidUserName(userName)) return userName;
        throw new IllegalArgumentException("< You have entered invalid user name! >");
    }

    public boolean isValidUserName(String userName) {
        return isAlphabetic(userName) && userName.length() < 16 && userName.length() > 4;
    }

    private String getUserSelectedPassword() {
        System.out.println("Choose password:");
        String password = scanner.next();
        if (isValidPassword(password)) return password;
        throw new IllegalArgumentException("< You have entered invalid password! >");
    }

    public boolean isValidPassword(String password) {
        return isAlphabeticOrNumeric(password) && password.length() < 8 && password.length() > 4;
    }

    private static boolean isNumeric(String number) {
        return number.chars().allMatch(Character::isDigit);
    }

    private static boolean isAlphabetic(String name) {
        return name.chars().allMatch(Character::isLetter);
    }

    private static boolean isAlphabeticOrNumeric(String name) {
        return name.chars().allMatch(Character::isLetterOrDigit);
    }
}
