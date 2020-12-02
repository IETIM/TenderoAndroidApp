package edu.eci.ieti.takeiteasysk.ui.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.Order;
import edu.eci.ieti.takeiteasysk.model.Product;
import edu.eci.ieti.takeiteasysk.ui.products.ProductsAdapter;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    List<Order> orders = null;

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType )
    {
        return new OrdersAdapter.ViewHolder( LayoutInflater.from( parent.getContext() ).inflate( R.layout.orders, parent, false ) );
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position )
    {

        Order order = orders.get( position );
        holder.idOrder.setText(order.getId());

        //TODO implement update view holder using the task values
    }

    @Override
    public int getItemCount()
    {
        return orders != null ? orders.size() : 0;
    }

    public void updateOrders(List<Order> products){
        this.orders = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView idOrder;
        ViewHolder( @NonNull View productView )
        {
            super( productView );

            idOrder = productView.findViewById(R.id.idOrden);
        }
    }
}
