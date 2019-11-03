package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    public void goToWebsite(String websiteURL){
        Uri uriUrl = Uri.parse(websiteURL);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void goBackToList(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void fillScreenData(){
        final String name = getIntent().getExtras().getString("name");
        final String desc = getIntent().getExtras().getString("desc");
        final String img = getIntent().getExtras().getString("img");
        final int ranking = getIntent().getExtras().getInt("ranking");
        final String id = getIntent().getExtras().getString("emp_id");
        final String website = getIntent().getExtras().getString("website");

        TextView nameView = findViewById(R.id.businessName);
        nameView.setText(name);
        ImageView logo = findViewById(R.id.logoView);
        Picasso.get()
                .load(img)
                .into(logo);

        TextView descView = findViewById(R.id.businessDesc);
        descView.setText(desc);

        Button websiteButton = findViewById(R.id.websiteButton);
        websiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToWebsite(website);

            }
        });

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
