package com.example.catalogueapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogueapp.database.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductCatalogueAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_item_view, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder pHolder = (ProductViewHolder)holder;
        if( products != null){
            Product p = products.get(position);
            pHolder.productName.setText(p.name);
            Picasso.get()
                    .load(p.image)
                    .into(pHolder.recyclerImage);
            pHolder.recyclerRating.setText("Rating: "+p.ranking);

        }else{
            pHolder.productName.setText("NONE");
        }
    }

    @Override
    public int getItemCount() {
        if( products != null)
            return products.size();
        return 0;
    }
    class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView productName;
        private ImageView recyclerImage;
        private TextView recyclerRating;

        public int index;

        public ProductViewHolder(View itemView){
            super(itemView);
            // change this!
            productName = itemView.findViewById(R.id.recyclerProductName);
            recyclerImage = itemView.findViewById(R.id.recyclerImage);
            recyclerRating = itemView.findViewById(R.id.recyclerRating);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //Send the index of the clicked position.
                    index = getAdapterPosition();
                    callTo.sendToNextScreen((String) productName.getText());
                }
            });
        }
    }
    private final LayoutInflater inflater;
    private List<Product> products;

    MainActivity callTo;
    public ProductCatalogueAdapter(MainActivity context){
        inflater = LayoutInflater.from(context);
        callTo = context;
    }
    void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

}
