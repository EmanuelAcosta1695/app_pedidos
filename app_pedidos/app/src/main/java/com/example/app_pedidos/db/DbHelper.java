package com.example.app_pedidos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "clientes_pedidos.db";


    public static final String TABLE_CLIENTES = "t_clientes";
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_PEDIDOS = "pedidos";
    public static final String TABLE_TOTAL = "total";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_CLIENTES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "direccion TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "DNI TEXT NOT NULL)");


        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "precio INTEGER NOT NULL," +
                "foto_producto INTEGER NOT NULL)");


        db.execSQL("CREATE TABLE " + TABLE_PEDIDOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "item_name TEXT NOT NULL," +
                "cantidad INTEGER NOT NULL," +
                "precio INTEGER NOT NULL," +
                "completado BOOLEAN DEFAULT 0 NOT NULL," +
                "foto INTEGER NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES TABLE_CLIENTES(id))");


        db.execSQL("CREATE TABLE " + TABLE_TOTAL + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER NOT NULL," +
                "total INTEGER NOT NULL," +
                "FOREIGN KEY (order_id) REFERENCES TABLE_PEDIDOS(order_id))");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + TABLE_CLIENTES);
        onCreate(db);

        db.execSQL("DROP TABLE " + TABLE_PRODUCTS);
        onCreate(db);

        db.execSQL("DROP TABLE " + TABLE_PEDIDOS);
        onCreate(db);

        db.execSQL("DROP TABLE " + TABLE_TOTAL);
        onCreate(db);

    }
}
