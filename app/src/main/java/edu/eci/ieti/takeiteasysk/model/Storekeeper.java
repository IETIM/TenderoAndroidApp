package edu.eci.ieti.takeiteasysk.model;

public class Storekeeper{

    private String id;

    private String email;

    private Shop shop;

    public Storekeeper() {
    }

    public Storekeeper( String email, Shop shop) {
        this.email = email;
        this.shop = shop;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Storekeeper{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", shop=" + shop +
                '}';
    }
}


