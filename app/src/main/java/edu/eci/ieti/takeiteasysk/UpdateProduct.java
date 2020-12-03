package edu.eci.ieti.takeiteasysk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.network.RetrofitNetwork;
import edu.eci.ieti.takeiteasysk.network.services.ProductService;
import edu.eci.ieti.takeiteasysk.storage.Storage;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsActivity;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsAdapter;
import retrofit2.Response;

public class UpdateProduct extends AppCompatActivity {
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private final int PICK_IMAGE_REQUEST = 23;
    private Product oldProduct;
    private ImageView imageView;
    private EditText nameView,priceView,descriptionView,stockView,categoryView;
    private Button btnSelect,btnUpload;
    private final ExecutorService executorService = Executors.newFixedThreadPool( 1 );
    private Uri filePath;
    private ProductService productService;
    private Storage sharedPreferences;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        oldProduct=(Product)getIntent().getExtras().get(ProductsAdapter.PRODUCT);
        imageView = findViewById(R.id.imageView3);
        btnSelect = findViewById(R.id.button5);
        btnUpload = findViewById(R.id.button4);
        nameView=(EditText) findViewById(R.id.nombreproducto);
        priceView=(EditText)findViewById(R.id.precioproducto);
        descriptionView=(EditText)findViewById(R.id.descripcionproducto);
        stockView=(EditText)findViewById(R.id.existenciasproducto);
        categoryView=(EditText)findViewById(R.id.categoriaproducto);
        sharedPreferences=new Storage(this);
        productService=new RetrofitNetwork(sharedPreferences.getToken()).getProductService();

        setInitialValues();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        btnSelect.setOnClickListener((view)->{selectImage();});
        btnUpload.setOnClickListener((view)->{updateProduct();});
    }

    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen del producto"), PICK_IMAGE_REQUEST);
    }
    public Product validateInput() {
        Product product = null;
        String name, price, description, stock, category;
        name = nameView.getText().toString();
        price = priceView.getText().toString();
        description = descriptionView.getText().toString();
        stock = stockView.getText().toString();
        category = categoryView.getText().toString();
        Boolean validInput = true;
        if (name.isEmpty()) {
            nameView.setError("Este campo no puede ser vacio");
            validInput = false;
        }
        if (description.isEmpty()) {
            descriptionView.setError("Este campo no puede ser vacio");
            validInput = false;
        }
        if (category.isEmpty()) {
            categoryView.setError("Este campo no puede ser vacio");
            validInput = false;
        }
        if (price.isEmpty()){
            priceView.setError("Este campo no puede ser vacio");
            validInput = false;
        }
        if(stock.isEmpty()) {
            stockView.setError("Este campo no puede ser vacio");
            validInput = false;
        }
        if (validInput){
            product = new Product();
            product.setCategory(category);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Long.parseLong(price));
            product.setStocks(Long.parseLong(stock));
        }
        return product;
    }

    public void updateProduct(){
        if(filePath!=null){
            uploadProduct();
        }
        else {
            Product product= validateInput();
            if(product!=null){
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Actualizando...");
                progressDialog.show();
                product.setId(oldProduct.getId());
                product.setImage(oldProduct.getImage());
                saveProduct(product);
            }

        }
    }
    public void saveProduct(Product product){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Void> response= productService.updateProduct(product.getId(),product).execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()){
                                Toast.makeText(UpdateProduct.this, "Producto Actualizao!!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), ProductsActivity.class);
                                progressDialog.dismiss();
                                startActivity(intent);
                                finish();
                            }
                            else{
                                showDialog("Error al actualizar el producto","Ocurrió un problema al actualizar el producto, intente de nuevo");
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setInitialValues() {
        nameView.setText(oldProduct.getName());
        priceView.setText(oldProduct.getPrice().toString());
        descriptionView.setText(oldProduct.getDescription());
        stockView.setText(oldProduct.getStocks().toString());
        categoryView.setText(oldProduct.getCategory());
        Glide.with(this).load(oldProduct.getImage()).into(imageView);
    }
    public void uploadProduct() {
        Product product =validateInput();
        if (product != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Actualizando...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            UploadTask uploadTask=ref.putFile(filePath);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        product.setImage(downloadUri.toString());
                        saveProduct(product);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProduct.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            uploadTask.addOnProgressListener(
                    new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Registrando producto...");
                        }
                    });
        }

    }
    public void showDialog(String title,String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}