package edu.eci.ieti.takeiteasysk.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.MainActivity;
import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.ERole;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.model.Role;
import edu.eci.ieti.takeiteasysk.model.Shop;
import edu.eci.ieti.takeiteasysk.model.Storekeeper;
import edu.eci.ieti.takeiteasysk.model.User;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsActivity;

public class RegisterActivity extends AppCompatActivity {
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private RetrofitNetwork rn = new RetrofitNetwork();
    private EditText nombre,correo,contraseña,telefono,direccion,nombreTienda,direccionTienda;
    private Spinner tipoTienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nombre =(EditText) findViewById(R.id.RegisterName);
        correo = (EditText) findViewById(R.id.registerEmail);
        contraseña = (EditText) findViewById(R.id.RegisterPassword);
        telefono = (EditText) findViewById(R.id.registerPhone);
        direccion = (EditText) findViewById(R.id.registerLocalAddress);
        nombreTienda = (EditText) findViewById(R.id.registerShopName);
        direccionTienda = (EditText) findViewById(R.id.registerShopAddress);
        tipoTienda = (Spinner) findViewById(R.id.registerShopType);
    }

    public void onRegisterClicked(View view){
        User us = new User(correo.getText().toString(),nombre.getText().toString(),contraseña.getText().toString(),direccion.getText().toString(),telefono.getText().toString());
        Shop shop = new Shop(nombreTienda.getText().toString(),new ArrayList<Product>(),direccion.getText().toString(),tipoTienda.getSelectedItem().toString());
        Storekeeper sk = new Storekeeper(correo.getText().toString(),shop);
        System.out.println("Entré aqui mi chinazo!");
        Role r = new Role();
        r.setRole(ERole.ROLE_TENDERO);
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        us.setAuthorities(roles);
        executorService.execute(registerStoreKeeper(us,sk));
    }

    private Runnable registerStoreKeeper(User us, Storekeeper sk){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    rn.getStoreService().newStorekeeper(sk).execute();
                    rn.getStoreService().newUser(us).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}