package com.example.practica3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.practica3.R;
import com.example.practica3.modelsProducts.Image;
import com.example.practica3.modelsProducts.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>
{
    private final List<Product> products;
    Context context;

    public ProductAdapter(Context context,List<Product> products){
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getSalePrice());
        List<Image> images = new ArrayList<>();
        images = product.getImages();
        Glide.with(context).load(images.get(0).getSrc()).into(holder.productPhoto);
        holder.productLot.setText("Stock = "+product.getStockQuantity());
    }

    @Override
    public int getItemCount() { return products.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView productName;
        final TextView productPrice;
        final TextView productLot;
        final ImageView productPhoto;
        ViewHolder(View view) {
            super(view);
            productName = (TextView) view.findViewById(R.id.product_name);
            productPrice = (TextView) view.findViewById(R.id.product_price);
            productLot = (TextView) view.findViewById(R.id.product_lot);
            productPhoto = view.findViewById(R.id.product_photo);
        }
    }
}