import dao.AddressDao;
import dao.UserDao;
import dto.Address;
import dto.User;

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

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println(BLUE_BOLD + "****Hi, Welcome To Online Store****\n" + ANSI_RESET + "Choose your role:\n1)User 2)Admin");
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
                                    if (user != null)
                                        System.out.println("Hi " + ANSI_YELLOW + user.getFirstName() + ANSI_RESET + "!");
                                    else
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
                                Address address = new Address(homeAddress[0], homeAddress[1], homeAddress[2], Integer.parseInt(homeAddress[3]));
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
                        }
                        break;
                    case 2:
                        break;
                    default:
                        throw new Exception("<Invalid Input, Try again.>");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
