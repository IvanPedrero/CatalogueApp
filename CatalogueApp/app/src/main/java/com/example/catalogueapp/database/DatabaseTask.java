package com.example.catalogueapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;

public class DatabaseTask extends AsyncTask<Product,Void, List<Product>> {
    CatalogueDatabase db;
    DatabaseReceiver receiver;
    public DatabaseTask(Context ctx , DatabaseReceiver receiver){
        this.receiver = receiver;
        db = Room.databaseBuilder(ctx,
                CatalogueDatabase.class ,
                "catalogue-database").build();
    }

    @Override
    public void onPreExecute(){
    }
    @Override
    protected List<Product> doInBackground(Product... params) {

        //If the database is empty, insert all the products.
        if(db.productDao().getDatabaseCount() <= 0){
            db.productDao().insertProduct(params);
        }else{
            //Else, update everything except the ranking, which is local.
            for(int i = 0; i< params.length; i++){
                db.productDao().updateProduct(params[i].emp_id, params[i].name, params[i].description, params[i].image, params[i].website);
            }
        }

        return null;
    }
    @Override
    public void onPostExecute(List<Product> result){
        // do something on ui!
       // receiver.getAll(result);
    }
}
