package edu.eci.ieti.takeiteasysk.ui.products;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.UpdateProduct;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.ui.viewmodel.ProductsViewModel;

public class ProductsAdapter
        extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
{

    private List<Product> products = null;
    private Context context;
    private ProductsViewModel viewModel;
    public static final String PRODUCT="Product";

    public ProductsAdapter(Context context, ProductsViewModel productsViewModel) {
        this.context = context;
        this.viewModel=productsViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
    {
        return new ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.products, parent, false ) );
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolder holder, int position )
    {
        Product product = products.get( position );
        holder.description.setText("Descripción: "+product.getDescription());
        holder.name.setText(product.getName());
        holder.price.setText("Precio: "+product.getPrice().toString());
        holder.stocks.setText("Existencias: "+product.getStocks().toString());
        //TODO implement update view holder using the task values
        Glide.with(context).load(product.getImage()).into(holder.image);
        holder.delete.setOnClickListener((view)->{confirmDeleteDialog(product);});
        holder.update.setOnClickListener( (view)->{updateProduct(product);} );
    }

    private void updateProduct(Product product) {
        Intent intent = new Intent(context, UpdateProduct.class);
        intent.putExtra(PRODUCT,product);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount()
    {
        return products != null ? products.size() : 0;
    }

    public void updateTasks(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,price,description,stocks;
        ImageView image;
        Button update,delete;
        ViewHolder( @NonNull View productView )
        {
            super( productView );
            name = productView.findViewById(R.id.idProduct_ViewNameProduct);
            price=productView.findViewById(R.id.idProduct_ViewPriceProduct);
            description=productView.findViewById(R.id.idProduct_ViewDescriptionProduct);
            image=productView.findViewById(R.id.imagenProducto);
            stocks=productView.findViewById(R.id.idProduct_ViewStockProduct);
            update=productView.findViewById(R.id.BotonActualizar);
            delete=productView.findViewById(R.id.BotonEliminar);
        }
    }

    private void confirmDeleteDialog(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder
                .setMessage("¿Estas seguro que quieres eliminar este producto?")
                .setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        viewModel.deleteProduct(product,products);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

}