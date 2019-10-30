package com.example.catalogueapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.catalogueapp.database.CatalogueDatabase;
import com.example.catalogueapp.database.Product;

import java.util.List;

public class ProductViewModel extends ViewModel {

    private LiveData<List<Product>> products;

    public ProductViewModel(){
        super();
    }
    public LiveData<List<Product>> getProducts(Context ctx){
        if( products == null){

            CatalogueDatabase db = Room.databaseBuilder(ctx,
                    CatalogueDatabase.class ,"catalogue-database").build();
            products = db.productDao().getAll();
        }

        return products;
    }

    public LiveData<List<Product>> searchProducts(Context ctx, String searchString){
        CatalogueDatabase db = Room.databaseBuilder(ctx,
                CatalogueDatabase.class ,"catalogue-database").build();

        if(searchString.isEmpty()){
            products = db.productDao().getAll();
        }else{
            products = db.productDao().search(searchString);
        }

        return products;
    }

}
