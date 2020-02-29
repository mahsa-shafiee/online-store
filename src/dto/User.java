package dto;

public class User {
    private int id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String emailAddress;
    private Address homeAddress;
    private ShoppingCart shoppingcart;
    private Order order;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String firstName, String lastName, String mobileNumber, String emailAddress, Address homeAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.homeAddress = homeAddress;
    }
}
