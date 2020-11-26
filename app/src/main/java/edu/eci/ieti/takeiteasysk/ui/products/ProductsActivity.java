package edu.eci.ieti.takeiteasysk.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.eci.ieti.takeiteasysk.NewProductForm;
import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.persistence.ProductsPersistence;
import edu.eci.ieti.takeiteasysk.persistence.impl.ProductsPersistenceImpl;
import edu.eci.ieti.takeiteasysk.repository.ProductRepository;
import edu.eci.ieti.takeiteasysk.storage.Storage;
import edu.eci.ieti.takeiteasysk.ui.viewmodel.ProductsViewModel;

public class ProductsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductsAdapter productsAdapter;
    ProductRepository productRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity);
        productRepository =new ProductRepository(this);
        ProductsPersistence productsPersistence=new ProductsPersistenceImpl();
        recyclerView= findViewById(R.id.products_grid);
        Storage storage= new Storage(this);
        productsAdapter= new ProductsAdapter();
        configureRecyclerView();
        ProductsViewModel model = new ViewModelProvider(this).get(ProductsViewModel.class);
        model.setProductRepository(productRepository);
        model.getProducts(storage.getShopId()).observe(this, products -> {
            productsAdapter.updateTasks(products);
        });
        productsAdapter.updateTasks(productsPersistence.getProducts());
        findViewById(R.id.floatingActionButton).setOnClickListener((View)->newProduct());
    }
    public void newProduct() {
        Intent intent = new Intent(this, NewProductForm.class);
        startActivity(intent);
        finish();
    }
    private void configureRecyclerView()
    {
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setAdapter( productsAdapter );
        recyclerView.setLayoutManager(layoutManager);
    }

}
