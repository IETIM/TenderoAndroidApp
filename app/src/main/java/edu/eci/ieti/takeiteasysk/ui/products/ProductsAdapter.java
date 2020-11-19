package edu.eci.ieti.takeiteasysk.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.eci.ieti.takeiteasysk.R;
import edu.eci.ieti.takeiteasysk.model.Product;

public class ProductsAdapter extends BaseAdapter {
    private List<Product>products;
    private Context context;

    public ProductsAdapter(Context context, List<Product> products) {
        this.context=context;
        this.products=products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product=getItem(position);
        View view= LayoutInflater.from(context).inflate(R.layout.products,null).findViewById(R.id.idProduct_ViewCard);
        ((TextView)view.findViewById(R.id.idProduct_ViewNameProduct)).setText(product.getName());
        ((TextView)view.findViewById(R.id.idProduct_ViewPriceProduct)).setText(product.getPrice().toString());
        ((TextView)view.findViewById(R.id.idProduct_ViewDescriptionProduct)).setText(product.getDescription());

        return view;
    }
}
