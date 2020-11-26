package edu.eci.ieti.takeiteasysk.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.network.services.ProductService;
import edu.eci.ieti.takeiteasysk.repository.ProductRepository;
import edu.eci.ieti.takeiteasysk.storage.Storage;

public class ProductsViewModel extends ViewModel {
    private MutableLiveData<List<Product>> products;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private ProductRepository productRepository;

    public LiveData<List<Product>> getProducts(String idShop) {
        if (products == null) {
            products = new MutableLiveData<List<Product>>();
            loadProducts(idShop);
        }
        return products;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private void loadProducts(String idShop) {
        executorService.execute(()->products.postValue(productRepository.getProducts(idShop)));

        // Do an asynchronous operation to fetch users.
    }
}

