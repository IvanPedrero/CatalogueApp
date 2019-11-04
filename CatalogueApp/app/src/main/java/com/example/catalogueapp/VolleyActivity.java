package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.catalogueapp.database.DatabaseTask;
import com.example.catalogueapp.database.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyActivity extends AppCompatActivity {

    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        getJsonRequest();
    }

    public void goBackToList(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void getJsonRequest() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:8000";

        final DatabaseTask databaseTask = new DatabaseTask(getApplicationContext(), null);

        spinner.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("empresas");

                            Product[] empresas = new Product[jsonArray.length()];

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject empresa = jsonArray.getJSONObject(i);

                                String id = empresa.getString("id");
                                String name = empresa.getString("nombre");
                                String desc = empresa.getString("desc");
                                String img = empresa.getString("img");
                                int ranking = empresa.getInt("ranking");
                                String website = empresa.getString("website");

                                Product e = new Product();
                                e.emp_id = id;
                                e.name = name;
                                e.description = desc;
                                e.image = img;
                                e.ranking = ranking;
                                e.website = website;

                                empresas[i] = e;
                            }

                            databaseTask.execute(empresas);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);

        spinner.setVisibility(View.GONE);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}