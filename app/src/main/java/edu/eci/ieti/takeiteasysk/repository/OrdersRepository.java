package edu.eci.ieti.takeiteasysk.repository;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.network.services.OrderService;
import edu.eci.ieti.takeiteasysk.storage.Storage;

public class OrdersRepository {
    private OrderService orderService;

    public OrdersRepository(Context context) {
        String token= new Storage(context).getToken();
        orderService=new RetrofitNetwork(token).getOrderService();
    }

    public List<Order> getOrders(String shopName){
        List<Order> orders=null;
        try {
            orders= orderService.getOrders(shopName).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
