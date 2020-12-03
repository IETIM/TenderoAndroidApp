package edu.eci.ieti.takeiteasysk.persistence;

import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;

public interface OrdersPersistence {
    List<Order> getOrders();
}
