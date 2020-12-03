package edu.eci.ieti.takeiteasysk.ui.products;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.MainActivity;
import edu.eci.ieti.takeiteasysk.NewProductForm;
import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.repository.ProductRepository;
import edu.eci.ieti.takeiteasysk.storage.Storage;
import edu.eci.ieti.takeiteasysk.ui.orders.OrdersList;
import edu.eci.ieti.takeiteasysk.ui.viewmodel.ProductsViewModel;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity);
        productRepository =new ProductRepository(this);
        recyclerView= findViewById(R.id.products_grid);
        Storage storage= new Storage(this);
        ProductsViewModel model = new ViewModelProvider(this).get(ProductsViewModel.class);
        productsAdapter= new ProductsAdapter(this,model);
        configureRecyclerView();
        model.setProductRepository(productRepository);
        model.getProducts(storage.getShopId()).observe(this, products -> {
            productsAdapter.updateTasks(products);
        });


        productsAdapter.updateTasks(productsPersistence.getProducts());
        findViewById(R.id.floatingActionButton).setOnClickListener((View)->newProduct());
        findViewById(R.id.floatingActionButton2).setOnClickListener((View)->myOrders());
    }
    public void newProduct() {
        Intent intent = new Intent(this, NewProductForm.class);
        startActivity(intent);

    }

    public void myOrders(){
        Intent intent = new Intent(this, OrdersList.class);
        startActivity(intent);
    }
    private void configureRecyclerView()
    {
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setAdapter( productsAdapter );
        recyclerView.setLayoutManager(layoutManager);
    }
    public void onLogoutClicked(View view){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferece_file_key), Context.MODE_PRIVATE );
        sharedPref.edit().clear().commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
