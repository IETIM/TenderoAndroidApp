package edu.eci.ieti.takeiteasysk.model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private String id;

    private String name;

    private List<Product> products;

    private String location;

    private String type;

    private String apiClient;

    private String apiSecret;

    public Shop() {
        this.products = new ArrayList<>();
    }

    public Shop(String name, List<Product> products, String location, String type) {
        this.name = name;
        this.products = products;
        this.location = location;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApiClient() {
        return apiClient;
    }

    public void setApiClient(String apiClient) {
        this.apiClient = apiClient;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", apiClient='" + apiClient + '\'' +
                ", apiSecret='" + apiSecret + '\'' +
                '}';
    }
}