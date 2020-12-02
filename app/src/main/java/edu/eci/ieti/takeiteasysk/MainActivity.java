package edu.eci.ieti.takeiteasysk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.model.Storekeeper;
import edu.eci.ieti.takeiteasysk.model.auth.LoginWrapper;
import edu.eci.ieti.takeiteasysk.model.auth.Token;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.network.services.AuthService;
import edu.eci.ieti.takeiteasysk.storage.Storage;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsActivity;
import edu.eci.ieti.takeiteasysk.ui.register.RegisterActivity;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText textEmail;
    private EditText textPassword;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    //Storage st = new Storage(this);
    private final Object lock = new Object();
    private boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.button);
        textEmail = (EditText) findViewById(R.id.editTextTextPersonName);
        textPassword = (EditText) findViewById(R.id.editTextTextPassword);

    }

    public void onLoginClicked(View view) {
        String correo = textEmail.getText().toString();
        String pass = textPassword.getText().toString();
        if (correo.isEmpty()) {
            textEmail.setError("Ingresa un valor!");
        }
        if (pass.isEmpty()) {
            textPassword.setError("Ingresa un valor!");
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http:/10.0.2.2:8080") //localhost for emulator
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            AuthService authService = retrofit.create(AuthService.class);
            executorService.execute(callAPI(authService,correo,pass));
        }
    }

    public void onRegisterClicked(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    private void storeToken(Token token) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preferece_file_key), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN_KEY",token.getToken());
        editor.commit();
    }

    private void storeShopId(String id) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preferece_file_key), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SHOP_ID",id);
        editor.commit();
    }
    private void storeShopName(String name) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preferece_file_key), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("SHOP_NAME",name);
        editor.commit();
    }


    private Runnable callAPI(AuthService authService,String correo,String pass){
        return new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    Response<Token> response =authService.login(new LoginWrapper(correo, pass)).execute();
                    Token token = response.body();
                    if (token!=null) {
                        storeToken(token);
                        token.parseToken();
                        flag = false;
                        //executorService.execute(callAPIStore(token));
                        RetrofitNetwork rn = new RetrofitNetwork(token.getToken());
                        Response<Storekeeper>resp = rn.getStoreService().getStorekeeper(token.getPayload().get("sub").toString()).execute();
                        System.out.println("token" + token.getPayload().get("sub").toString());
                        System.out.println("RESP BODY " + resp.body());
                        Storekeeper sk = resp.body();
                        storeShopId(sk.getShop().getId());
                        storeShopName(sk.getShop().getName());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(),ProductsActivity.class);
                                    startActivity(intent);
                                }
                            });
                            finish();


                    }
                    else{
                        wrongCredentials();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private void wrongCredentials() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textEmail.setError("Verify Your email!");
                textPassword.setError("verify Your Password!");
            }
        });
    }

    /** Called when the user taps the Send button */
    public void logIn(View view) {
        Intent intent = new Intent(this, ProductsActivity.class);
        startActivity(intent);
    }
}