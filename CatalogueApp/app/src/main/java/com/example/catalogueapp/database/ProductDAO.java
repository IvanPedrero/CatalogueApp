package com.example.catalogueapp.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertProduct(Product... products);

    @Update
    public void update(Product... products);

    @Query ("UPDATE product SET name=:nom, description=:desc, image=:img, website=:website WHERE emp_id=:id")
    public void updateProduct(String id, String nom, String desc, String img, String website);

    @Query("SELECT COUNT(*) FROM product WHERE emp_id LIKE :id")
    public int businessExists(String id);

    @Query ("INSERT INTO product VALUES(:id, :name, :description, :img, :rank, :web)")
    public void insertSingle(String id, String name, String description, String img, int rank, String web);

    @Delete
    public void deleteProduct(Product... products);

    @Query("SELECT * FROM product")
    public LiveData<List<Product>> getAll();

    @Query("SELECT * FROM product WHERE name LIKE :searchName")
    public LiveData<List<Product>> search(String searchName);

    @Query("UPDATE product SET ranking=:ranking WHERE emp_id LIKE :id")
    public void updateRanking(String id, int ranking);

    @Query("SELECT count(*) FROM product")
    public int getDatabaseCount();
}
