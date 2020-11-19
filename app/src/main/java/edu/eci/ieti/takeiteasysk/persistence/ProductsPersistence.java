package edu.eci.ieti.takeiteasysk.persistence;

import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Product;

public interface ProductsPersistence {
    List<Product> getProducts();
}
