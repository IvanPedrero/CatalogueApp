package com.example.catalogueapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1)
public abstract class CatalogueDatabase extends RoomDatabase {

    public abstract ProductDAO productDao();

}
