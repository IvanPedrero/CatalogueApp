package com.example.catalogueapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="emp_id")
    public String emp_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name="image")
    public String image;

    @ColumnInfo(name="ranking")
    public int ranking;

    @ColumnInfo(name="website")
    public String website;
}
