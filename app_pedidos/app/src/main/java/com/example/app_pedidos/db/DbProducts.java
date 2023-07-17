package com.example.app_pedidos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_pedidos.entidades.Product;

import java.util.ArrayList;

public class DbProducts extends DbHelper{

    Context context;

    public DbProducts(@Nullable Context context) {
        super(context);

        this.context = context;
    }

    public long insertarProduct(String nombre, Integer precio, Integer foto_producto) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            // funcion insertar registro
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("precio", precio);
            values.put("foto_producto", foto_producto);


            //1ero nonmbre de la tabla
            // aca inserta esos valores a esta tabla
            id = db.insert(TABLE_PRODUCTS, null, values);


        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Product> mostrarProducts(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Product> listaProducts = new ArrayList<>();
        Product producto = null;
        Cursor cursorProduct = null;

        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);


        if(cursorProduct.moveToFirst()){
            do{
                producto = new Product();
                producto.setId(cursorProduct.getInt(0));
                producto.setNombre(cursorProduct.getString(1));
                producto.setPrecio(cursorProduct.getInt(2));
                producto.setFoto(cursorProduct.getInt(3));

                listaProducts.add(producto);
            } while (cursorProduct.moveToNext()); // nos  mueve al siguiente contacto
        }

        cursorProduct.close();

        return listaProducts;
    }


    public Product verProduct(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Product producto = null;
        Cursor cursorProduct;

        cursorProduct = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE id = " + id + " LIMIT 1", null);

        if(cursorProduct.moveToFirst()){
            producto = new Product();
            producto.setId(cursorProduct.getInt(0));
            producto.setNombre(cursorProduct.getString(1));
            producto.setPrecio(cursorProduct.getInt(2));
            producto.setFoto(cursorProduct.getInt(3));
        }

        cursorProduct.close();

        return producto;
    }

    public boolean editarProduct(int id, String nombre, Integer precio, Integer foto_producto) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("UPDATE " + TABLE_PRODUCTS + " SET nombre = '"+nombre+"', precio = '"+precio+"', foto_producto = '"+foto_producto+"' WHERE id='" + id +"' ");
            correcto = true;


        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean eliminarProduct(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }
}
