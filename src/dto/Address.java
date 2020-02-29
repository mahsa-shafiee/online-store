package dto;

public class Address {
    private int id;
    private String state;
    private String city;
    private String street;
    private long postalCode;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address() {
    }

    public Address(String state, String city, String street, long postalCode) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
