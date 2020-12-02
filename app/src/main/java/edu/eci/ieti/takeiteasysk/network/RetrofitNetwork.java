package edu.eci.ieti.takeiteasysk.network;

import java.io.IOException;

import edu.eci.ieti.takeiteasysk.network.services.AuthService;
import edu.eci.ieti.takeiteasysk.network.services.OrderService;
import edu.eci.ieti.takeiteasysk.network.services.ProductService;
import edu.eci.ieti.takeiteasysk.network.services.StoreService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Santiago Carrillo
 * 4/23/19.
 */
public class RetrofitNetwork
{
    AuthService authService;
    ProductService productService;
    StoreService storeService;
    OrderService orderService;


    public RetrofitNetwork()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl( "http:/10.0.2.2:8080/" ) //localhost for emulator
                .addConverterFactory( GsonConverterFactory.create() ).build();

        authService = retrofit.create( AuthService.class );
        storeService = retrofit.create(StoreService.class);
    }
    public RetrofitNetwork( final String token )
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor( new Interceptor()
        {
            @Override
            public okhttp3.Response intercept( Chain chain )
                    throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder().header( "Accept", "application/json" ).header( "Authorization",
                        token ).method(
                        original.method(), original.body() ).build();
                return chain.proceed( request );
            }
        } );
        Retrofit retrofit =
                new Retrofit.Builder().baseUrl("http:/10.0.2.2:8080/").addConverterFactory( GsonConverterFactory.create() ).client(
                        httpClient.build() ).build();
        productService=retrofit.create(ProductService.class);
        storeService = retrofit.create(StoreService.class);
        orderService = retrofit.create(OrderService.class);

    }

    public ProductService getProductService() {
        return productService;
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
