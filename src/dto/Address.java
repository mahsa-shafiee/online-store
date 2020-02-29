package dto;

public class Address {
    private int id;
    private String state;
    private String city;
    private String street;
    private int postalCode;
    private User user;

    public Address(int id, String state, String city, String street, int postalCode, User user) {
        this.id = id;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.user = user;
    }
}
