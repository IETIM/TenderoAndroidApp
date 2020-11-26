package edu.eci.ieti.takeiteasysk.repository;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.network.services.ProductService;
import edu.eci.ieti.takeiteasysk.storage.Storage;

public class ProductRepository {
    private ProductService productService;

    public ProductRepository(Context context) {
        String token= new Storage(context).getToken();
        productService=new RetrofitNetwork(token).getProductService();
    }

    public List<Product> getProducts(String idShop){
        List<Product> products=null;
        try {
            products= productService.getProducts(idShop).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}

