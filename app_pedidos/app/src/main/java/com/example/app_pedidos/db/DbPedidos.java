package com.example.app_pedidos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_pedidos.entidades.Pedido;

import java.util.ArrayList;

public class DbPedidos extends DbHelper {

    Context context;

    public DbPedidos(@Nullable Context context) {
        super(context);

        this.context = context;
    }



    public long insertarPedido(Integer order_id, String item_name, Integer cantidad, Integer precio, Boolean completado, Integer foto) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("order_id", order_id);
            values.put("item_name", item_name);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("completado", completado);
            values.put("foto", foto);


            id = db.insert(TABLE_PEDIDOS, null, values);


        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }


    public void update_pedido_complete(int order_id) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorPedido = null;


            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET completado = 1 WHERE completado = " + 0 + " and order_id = " + order_id);

        } catch (Exception ex) {
            ex.toString();
        }

    };

    public void update_pedido_user(int id, int idUser) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorPedido = null;


            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET order_id = " + idUser + "WHERE id = " + id);


        } catch (Exception ex) {
            ex.toString();
        }

    };



    public void update_item_cantidad(int cantidad, int agregar, String nombre, int orderId) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursorPedido = null;

            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET cantidad = " + (cantidad + agregar) + " WHERE item_name = '" + nombre + "' and completado = 0");


        } catch (Exception ex) {
            ex.toString();
        }

    };



    public ArrayList<Pedido> mostrarPedidos(int idUser){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        Pedido pedido = null;
        Cursor cursorPedido = null;


        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + idUser + " and completado = " + 0, null); // MMMM


        if(cursorPedido.moveToFirst()){
            do{
                pedido = new Pedido();
                pedido.setId(cursorPedido.getInt(0));
                pedido.setOrder_id(cursorPedido.getInt(1));
                pedido.setItem_name(cursorPedido.getString(2));
                pedido.setCantidad(cursorPedido.getInt(3));
                pedido.setPrecio(cursorPedido.getInt(4));
                pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5)));
                pedido.setFoto(cursorPedido.getInt(6));


                listaPedidos.add(pedido);
            } while (cursorPedido.moveToNext()); // nos  mueve al siguiente contacto
        }

        cursorPedido.close();

        return listaPedidos;
    }



    public ArrayList<Pedido> mostrarPedidosFinalizados(int idUser){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        Pedido pedido = null;
        Cursor cursorPedido = null;


        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + idUser + " and completado = " + 1, null);


        if(cursorPedido.moveToFirst()){
            do{
                pedido = new Pedido();
                pedido.setId(cursorPedido.getInt(0));
                pedido.setOrder_id(cursorPedido.getInt(1));
                pedido.setItem_name(cursorPedido.getString(2));
                pedido.setCantidad(cursorPedido.getInt(3));
                pedido.setPrecio(cursorPedido.getInt(4));
                pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5)));
                pedido.setFoto(cursorPedido.getInt(6));


                listaPedidos.add(pedido);
            } while (cursorPedido.moveToNext());
        }

        cursorPedido.close();

        return listaPedidos;
    }


//    public Pedido verPedido(int userId, String item_name){
//        DbHelper dbHelper = new DbHelper(context);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Pedido pedido = null;
//        Cursor cursorPedido;
//
//        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + userId + " AND item_name = '" + item_name + "'", null);
//
//        if(cursorPedido.moveToFirst()){
//            pedido = new Pedido();
//            pedido.setId(cursorPedido.getInt(0));
//            pedido.setOrder_id(cursorPedido.getInt(1));
//            pedido.setItem_name(cursorPedido.getString(2));
//            pedido.setCantidad(cursorPedido.getInt(3));
//            pedido.setPrecio(cursorPedido.getInt(4));
//            pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5))); // convierte el "True" a true:boolean
//        }
//
//        cursorPedido.close();
//
//        return pedido;
//    }


    public Pedido verPedido(String item_name){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Pedido pedido = null;
        Cursor cursorPedido;

        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE item_name = '" + item_name + "'", null);

        if(cursorPedido.moveToFirst()){
            pedido = new Pedido();
            pedido.setId(cursorPedido.getInt(0));
            pedido.setOrder_id(cursorPedido.getInt(1));
            pedido.setItem_name(cursorPedido.getString(2));
            pedido.setCantidad(cursorPedido.getInt(3));
            pedido.setPrecio(cursorPedido.getInt(4));
            pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5))); // convierte el "True" a true:boolean
            pedido.setFoto(cursorPedido.getInt(6));
        }

        cursorPedido.close();

        return pedido;
    }


    public boolean editarPedido(int id, Integer order_id, String item_name, Integer cantidad, Integer precio, Boolean completado) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET order_id = '"+order_id+"', item_name = '"+item_name+"', cantidad = '"+cantidad+"', precio = '"+precio+"', completado = '"+completado+"', WHERE id='" + id +"' ");
            correcto = true;


        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    public boolean eliminarPedido(String item_name, int idUser) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        try {
            db.execSQL("DELETE FROM " + TABLE_PEDIDOS + " WHERE item_name = '" + item_name + "' AND order_id = '" + idUser + "' AND completado = '0'");
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
