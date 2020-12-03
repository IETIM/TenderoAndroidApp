package edu.eci.ieti.takeiteasysk.network.services;

import java.util.List;

import edu.eci.ieti.takeiteasysk.model.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products/{idShop}")
    Call<List<Product>> getProducts(@Path("idShop")String idShop);
    @POST("products/{idShop}")
    Call<Product> addProduct(@Path("idShop")String idShop, @Body Product product);
    @DELETE("products/{idProduct}")
    Call<Void> deleteProduct(@Path("idProduct") String idProduct);
}
