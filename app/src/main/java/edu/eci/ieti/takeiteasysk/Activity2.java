package edu.eci.ieti.takeiteasysk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.eci.ieti.takeiteasysk.storage.Storage;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        Storage storage= new Storage(this);
        TextView correo = (TextView) findViewById(R.id.correoTendero);
        correo.setText(storage.getTenderoEmail());
    }

  }