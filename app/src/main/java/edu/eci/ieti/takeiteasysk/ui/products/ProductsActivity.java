package edu.eci.ieti.takeiteasysk.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import edu.eci.ieti.takeiteasysk.NewProductForm;
import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.persistence.ProductsPersistence;
import edu.eci.ieti.takeiteasysk.persistence.impl.ProductsPersistenceImpl;

public class ProductsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity);
        ProductsPersistence productsPersistence=new ProductsPersistenceImpl();
        GridView gridView= findViewById(R.id.products_grid);
        gridView.setAdapter(new ProductsAdapter(this,productsPersistence.getProducts()));
        findViewById(R.id.floatingActionButton).setOnClickListener((View)->newProduct());

    }
    public void newProduct() {
        Intent intent = new Intent(this, NewProductForm.class);
        startActivity(intent);
    }

}
