package edu.eci.ieti.takeiteasysk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.model.auth.LoginWrapper;
import edu.eci.ieti.takeiteasysk.model.auth.Token;
import edu.eci.ieti.takeiteasysk.network.services.AuthService;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsActivity;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText textEmail;
    private EditText textPassword;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

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

    private void storeToken(Token token) {
        SharedPreferences sharedPref =
                getSharedPreferences(getString(R.string.preferece_file_key), Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN_KEY",token.getToken());
        editor.commit();
    }

    private Runnable callAPI(AuthService authService,String correo,String pass){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Token> response =authService.login(new LoginWrapper(correo, pass)).execute();
                    Token token = response.body();
                    if (token!=null) {
                        storeToken(token);
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