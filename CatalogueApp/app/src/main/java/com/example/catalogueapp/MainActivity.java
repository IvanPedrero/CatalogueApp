package com.example.catalogueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.catalogueapp.database.CatalogueDatabase;
import com.example.catalogueapp.database.DatabaseReceiver;
import com.example.catalogueapp.database.DatabaseTask;
import com.example.catalogueapp.database.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DatabaseReceiver {

    public static String MESSAGE = "com.example.catalogueApp.MainActivity";
    ProductViewModel products;
    ProductCatalogueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = ViewModelProviders.of(this).get(ProductViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter= new ProductCatalogueAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        startService(new Intent(this, ToastService.class));

        setAutocomplete();
    }

    @Override
    protected void onRestart() {
        setAutocomplete();
        reloadScreen();
        super.onRestart();
    }

    @Override
    public void onBackPressed() {}

    public void actionFromButton(View view){
        Log.d("CLICKED" , "FROM VIEW " + view);
    }

    public void getAll(List<Product> products){

    }
    public void doAction(View view){
        reloadScreen();
    }

    void setAutocomplete(){
        final List<String> names = new ArrayList<>();
        products.searchProducts(getApplicationContext(),"%%").observe(this,
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {

                        if(!products.isEmpty()){
                            for(int i = 0; i < products.size(); i++){
                                if (!names.contains(products.get(i).name)) {
                                    names.add(products.get(i).name);
                                }
                            }
                        }
                    }
                });

            AutoCompleteTextView editText = findViewById(R.id.searchText);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, names);
            editText.setAdapter(adapter);
    }

    private void reloadScreen(){
        String src = "%"+((EditText)findViewById(R.id.searchText)).getText()+"%";
        products.searchProducts(getApplicationContext(),src).observe(this,
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        adapter.setProducts(products);
                    }
                });
    }

    public void goToVolleyScreen(View view){

        Intent goToNextActivity = new Intent(getApplicationContext(), VolleyActivity.class);
        startActivity(goToNextActivity);
    }

    public void sendToNextScreen(final String name) {
        final Intent intent = new Intent(this, DetailActivity.class);

        products.searchProducts(getApplicationContext(),name).observe(this,
                new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> companies) {
                        intent.putExtra("name", companies.get(0).name);
                        intent.putExtra("desc", companies.get(0).description);
                        intent.putExtra("img", companies.get(0).image);
                        intent.putExtra("ranking", companies.get(0).ranking);
                        intent.putExtra("emp_id", companies.get(0).emp_id);
                        intent.putExtra("website", companies.get(0).website);

                        startActivity(intent);
                    }
                });

    }
}
