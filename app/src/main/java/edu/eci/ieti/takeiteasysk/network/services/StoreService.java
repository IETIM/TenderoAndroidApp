package edu.eci.ieti.takeiteasysk.network.services;

import edu.eci.ieti.takeiteasysk.model.Storekeeper;
import edu.eci.ieti.takeiteasysk.model.User;
import edu.eci.ieti.takeiteasysk.model.auth.LoginWrapper;
import edu.eci.ieti.takeiteasysk.model.auth.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoreService {
    String API_ROUTE = "storekeeper/{email}";
    @GET(API_ROUTE)
    public Call<Storekeeper> getStorekeeper(@Path("email") String email);
    @POST("storekeeper/register")
    public Call<Storekeeper> newStorekeeper(@Body Storekeeper sk);
    @POST("/register")
    public Call<User> newUser(@Body User us);
}
