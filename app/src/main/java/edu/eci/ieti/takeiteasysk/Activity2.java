package edu.eci.ieti.takeiteasysk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity);
    }
    public void newProduct(View view) {
        Intent intent = new Intent(this, NewProductForm.class);
        startActivity(intent);
    }}