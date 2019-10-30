package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.catalogueapp.database.DatabaseTask;
import com.example.catalogueapp.database.Product;
import com.example.catalogueapp.database.UpdateTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    ProductViewModel products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fillScreenData();

        products = ViewModelProviders.of(this).get(ProductViewModel.class);
    }

    private void fillScreenData(){
        final String name = getIntent().getExtras().getString("name");
        String desc = getIntent().getExtras().getString("desc");
        String img = getIntent().getExtras().getString("img");
        final int ranking = getIntent().getExtras().getInt("ranking");
        final String id = getIntent().getExtras().getString("emp_id");

        TextView nameView = findViewById(R.id.businessName);
        nameView.setText(name);
        ImageView logo = findViewById(R.id.logoView);
        Picasso.get()
                .load(img)
                .into(logo);

        TextView descView = findViewById(R.id.businessDesc);
        descView.setText(desc);

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(ranking);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                UpdateTask ut = new UpdateTask(getApplicationContext(), null, id, (int) ratingBar.getRating());
                ut.execute();
            }
        });
    }
}
