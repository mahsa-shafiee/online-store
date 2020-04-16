package view;

import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String BLACK_BOLD = "\033[1;30m";
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static boolean EXIT = false;
    public static boolean SIGN_OUT = false;

    public static void main(String[] args) {
        UserLoginMenu userLoginMenu = new UserLoginMenu();
        AdminMenus adminMenus = new AdminMenus();

        while (true) {
            try {
                System.out.println(BLUE_BOLD + "****Hi, Welcome To Online Store****\n" + ANSI_RESET +
                        "Choose your role:\n1)User 2)Admin");
                Scanner scanner = new Scanner(System.in);
                int role = scanner.nextInt();
                switch (role) {
                    case 1:
                        userLoginMenu.showUserMenu();
                        if (EXIT)
                            return;
                    case 2:
                        adminMenus.showAdminMenu();
                        if (EXIT)
                            return;
                    default:
                        throw new Exception("Invalid Role!!");
                }
            } catch (Exception e) {
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

}
