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
import com.example.practica3.modelsCategory.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>
{
    private final List<Category> categories;
    private OnCategoryListener onCategoryListener;
    Context context;

    public CategoryAdapter(Context context,List<Category> categories,OnCategoryListener onCategoryListener){
        this.context = context;
        this.categories = categories;
        this.onCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view,onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        Glide.with(context).load(category.getImage().getSrc()).into(holder.categoryPhoto);
        holder.categoryName.setText(category.getName());
        holder.categoryLot.setText("Articles = "+category.getCount());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView categoryName;
        final TextView categoryLot;
        final ImageView categoryPhoto;
        OnCategoryListener onCategoryListener;
        ViewHolder (View view,OnCategoryListener onCategoryListener){
            super(view);
            categoryName = (TextView) view.findViewById(R.id.category_name);
            categoryLot = (TextView) view.findViewById(R.id.category_lot);
            categoryPhoto = view.findViewById(R.id.category_photo);
            this.onCategoryListener = onCategoryListener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener{
        void onCategoryClick(int position);
    }
}