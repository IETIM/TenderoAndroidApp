package edu.eci.ieti.takeiteasysk.network.services;

import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OrderService {
    @GET("orders/{shopName}")
    Call<List<Order>> getOrders(@Path("shopName")String shopName);
}
