package edu.eci.ieti.takeiteasysk.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.repository.OrdersRepository;
import edu.eci.ieti.takeiteasysk.repository.ProductRepository;

public class OrdersViewModel extends ViewModel {
    private MutableLiveData<List<Order>> orders;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private OrdersRepository ordersRepository;

    public LiveData<List<Order>> getOrders(String idShop) {
        if (orders == null) {
            orders = new MutableLiveData<List<Order>>();
            loadProducts(idShop);
        }
        return orders;
    }

    public void setOrdersRepository(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    private void loadProducts(String shopName) {
        executorService.execute(()->orders.postValue(ordersRepository.getOrders(shopName)));

        // Do an asynchronous operation to fetch users.
    }
}
