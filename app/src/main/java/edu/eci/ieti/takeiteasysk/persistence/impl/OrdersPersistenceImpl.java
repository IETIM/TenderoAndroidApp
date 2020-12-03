package edu.eci.ieti.takeiteasysk.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.model.Purchase;
import edu.eci.ieti.takeiteasysk.persistence.OrdersPersistence;

public class OrdersPersistenceImpl implements OrdersPersistence {
    @Override
    public List<Order> getOrders() {
        ArrayList<Order> orders;
        orders = new ArrayList<>();
        ArrayList<Purchase> pl= new ArrayList<>();
        Purchase p = new Purchase("1","1",2);
        pl.add(p);
        Order o = new Order();
        o.setId("123");
        o.setTotal(123.4);
        o.setUser("Me");
        o.setPurchases(pl);
        orders.add(o);
        return  orders;
    }
}
