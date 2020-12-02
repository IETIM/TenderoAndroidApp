package edu.eci.ieti.takeiteasysk.ui.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.Product;

public class ProductsAdapter
        extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
{

    List<Product> products = null;

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
        holder.description.setText(product.getDescription());
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice().toString());
        //TODO implement update view holder using the task values
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
        TextView name,price,description;
        ViewHolder( @NonNull View productView )
        {
            super( productView );
            name = productView.findViewById(R.id.idProduct_ViewNameProduct);
            price=productView.findViewById(R.id.idProduct_ViewPriceProduct);
            description=productView.findViewById(R.id.idProduct_ViewDescriptionProduct);
        }
    }

}