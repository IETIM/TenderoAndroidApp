package edu.eci.ieti.takeiteasysk.persistence.impl;

import java.util.ArrayList;
import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.persistence.ProductsPersistence;

public class ProductsPersistenceImpl implements ProductsPersistence {
    @Override
    public List<Product> getProducts() {
        ArrayList<Product> products;
        products = new ArrayList<>();
        products.add(new Product("Limón","Limón fresco.", 500L));
        products.add(new Product("Papas Margarita","Paquete de papas sabor a pollo. (Cont. Neto 45g)", 1500L));
        products.add(new Product("Leche Alquería","Leche alquería deslactosa (Cont. Neto 1100 ml)", 3500L));
        products.add(new Product("Yogurt Alpina","Yogurt semidescremado con dulce, con melocotón y cultivos probióticos (Cont. Neto 200g)", 1800L));
        products.add(new Product("DeTodito","Detodito picante (Cont. Neto 45g)", 2000L));
        return  products;
    }
}
