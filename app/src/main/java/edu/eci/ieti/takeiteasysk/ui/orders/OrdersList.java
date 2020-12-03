package edu.eci.ieti.takeiteasysk.ui.orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.persistence.OrdersPersistence;
import edu.eci.ieti.takeiteasysk.persistence.impl.OrdersPersistenceImpl;
import edu.eci.ieti.takeiteasysk.repository.OrdersRepository;
import edu.eci.ieti.takeiteasysk.repository.ProductRepository;
import edu.eci.ieti.takeiteasysk.storage.Storage;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsAdapter;
import edu.eci.ieti.takeiteasysk.ui.viewmodel.OrdersViewModel;
import edu.eci.ieti.takeiteasysk.ui.viewmodel.ProductsViewModel;

public class OrdersList extends AppCompatActivity {
    RecyclerView recyclerView;
    OrdersAdapter ordersAdapter;
    OrdersRepository ordersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        ordersRepository = new OrdersRepository(this);
        OrdersPersistence ordersPersistence=new OrdersPersistenceImpl();
        recyclerView = findViewById(R.id.orders_list);
        Storage storage= new Storage(this);
        ordersAdapter= new OrdersAdapter();
        configureRecyclerView();
        OrdersViewModel model = new ViewModelProvider(this).get(OrdersViewModel.class);
        model.setOrdersRepository(ordersRepository);
        model.getOrders(storage.getShopName()).observe(this, products -> {
            ordersAdapter.updateOrders(products);
        });
        ordersAdapter.updateOrders(ordersPersistence.getOrders());
    }

    private void configureRecyclerView()
    {
        recyclerView.setHasFixedSize( true );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        recyclerView.setAdapter( ordersAdapter );
        recyclerView.setLayoutManager(layoutManager);
    }
}