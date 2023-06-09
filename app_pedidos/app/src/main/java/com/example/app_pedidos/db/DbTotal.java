package com.example.app_pedidos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_pedidos.entidades.Total;

public class DbTotal extends DbHelper{
    Context context;

    public DbTotal(@Nullable Context context) {
        super(context);

        this.context = context;
    }

    public long insertarTotal(Integer order_id, Integer total) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("order_id", order_id);
            values.put("total", total);

            id = db.insert(TABLE_TOTAL, null, values);


        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }


//    public ArrayList<Total> mostrarTotal(){
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ArrayList<Total> listaTotal = new ArrayList<>();
//        Total total = null;
//        Cursor cursorTotal = null;
//
//        cursorTotal = db.rawQuery("SELECT * FROM " + TABLE_TOTAL, null);
//
//
//        if(cursorTotal.moveToFirst()){
//            do{
//                total = new Total();
//                total.setId(cursorTotal.getInt(0));
//                total.setOrder_id(cursorTotal.getInt(1));
//                total.setTotal(cursorTotal.getFloat(2));
//
//                listaTotal.add(total);
//            } while (cursorTotal.moveToNext()); // nos  mueve al siguiente contacto
//        }
//
//        cursorTotal.close();
//
//        return listaTotal;
//    }


    public Total verTotal(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Total total = null;
        Cursor cursorTotal;

        cursorTotal = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE id = " + id + " LIMIT 1", null);

        if(cursorTotal.moveToFirst()){
            total = new Total();
            total.setId(cursorTotal.getInt(0));
            total.setOrder_id(cursorTotal.getInt(1));
            total.setTotal(cursorTotal.getInt(2));
        }

        cursorTotal.close();

        return total;
    }


    public boolean editarTotal(int id, Integer order_id, Integer total) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET order_id = '"+order_id+"', total = '"+total+"' WHERE id='" + id +"' ");
            correcto = true;


        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    public boolean eliminarTotal(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_TOTAL + " WHERE id = '" + id + "'");
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
