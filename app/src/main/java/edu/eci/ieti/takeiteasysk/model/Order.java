package edu.eci.ieti.takeiteasysk.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private ArrayList<Purchase> purchases;
    private double total;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String shop;
    private String state;
    private String user;
    private String date;

    public Order(String shop, double total, String currency, String method, String intent, String description, List<Purchase> purchases, String user) {
        this.shop = shop;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.state = "not payed";
        this.total = total;
        this.purchases = (ArrayList<Purchase>) purchases;
        this.user=user;
        setDate();
    }

    public Order() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        this.date = formatter.format(date);
    }
}
