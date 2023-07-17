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

    // EN LOS CASOS QUE RETIRA, ingreso a manopla "lo retira"



    public long insertarPedido(Integer order_id, String item_name, Integer cantidad, Integer precio, Boolean completado, Integer foto) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            // funcion insertar registro
            ContentValues values = new ContentValues();
            values.put("order_id", order_id);
            values.put("item_name", item_name);
            values.put("cantidad", cantidad);
            values.put("precio", precio);
            values.put("completado", completado);
            values.put("foto", foto);

            //1ero nonmbre de la tabla
            // aca inserta esos valores a esta tabla
            id = db.insert(TABLE_PEDIDOS, null, values);


        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }


    public void update_pedido_complete(int order_id) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            Cursor cursorPedido = null;

            // id = db.insert(TABLE_PEDIDOS, null, values);

            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET completado = 1 WHERE completado = " + 0 + " and order_id = " + order_id); // id del user que apunta a order_id

            // db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET completado = 1 WHERE completado = 0");

            // UPDATE MERCADERIAS
            //SET M_NAME = 'Galletitas Nueve de oro'
            //WHERE M_ID = 1;

            //  cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE id = " + id + "completado = " + false, null);

        } catch (Exception ex) {
            ex.toString();
        }

    };

    public void update_pedido_user(int id, int idUser) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            Cursor cursorPedido = null;

            // id = db.insert(TABLE_PEDIDOS, null, values);

            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET order_id = " + idUser + "WHERE id = " + id); // id es el id del pedido

            // db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET completado = 1 WHERE completado = 0");

            // UPDATE MERCADERIAS
            //SET M_NAME = 'Galletitas Nueve de oro'
            //WHERE M_ID = 1;

            //  cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE id = " + id + "completado = " + false, null);

        } catch (Exception ex) {
            ex.toString();
        }

    };



    public void update_item_cantidad(int cantidad, int agregar, String nombre, int orderId) {

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            Cursor cursorPedido = null;

            //db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET cantidad = " + (cantidad + agregar) + " WHERE item_name = " + nombre + "and completado = " + 0); // id es el id del pedido
            db.execSQL("UPDATE " + TABLE_PEDIDOS + " SET cantidad = " + (cantidad + agregar) + " WHERE item_name = '" + nombre + "' and completado = 0");

            // UPDATE pedidos SET cantidad = cantidad + 30 WHERE item_name = 'bolsas';

        } catch (Exception ex) {
            ex.toString();
        }

    };



    // order_id = user_id
    public ArrayList<Pedido> mostrarPedidos(int idUser){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Pedido> listaPedidos = new ArrayList<>();
        Pedido pedido = null;
        Cursor cursorPedido = null;

        // me va a mostrar muchos pedidos
        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + idUser + " and completado = " + 0, null); // MMMM


        if(cursorPedido.moveToFirst()){
            do{
                pedido = new Pedido();
                pedido.setId(cursorPedido.getInt(0));
                pedido.setOrder_id(cursorPedido.getInt(1));
                pedido.setItem_name(cursorPedido.getString(2));
                pedido.setCantidad(cursorPedido.getInt(3));
                pedido.setPrecio(cursorPedido.getInt(4));
                pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5))); // convierte el "True" a true:boolean
                pedido.setFoto(cursorPedido.getInt(6));

                // cursor.getInt(cursor.getColumnIndex("flag")) == 1
//                String str = "True";
//                Boolean bool = Boolean.parseBoolean(str);

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

        // me va a mostrar muchos pedidos
        cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + idUser + " and completado = " + 1, null); // MMMM


        if(cursorPedido.moveToFirst()){
            do{
                pedido = new Pedido();
                pedido.setId(cursorPedido.getInt(0));
                pedido.setOrder_id(cursorPedido.getInt(1));
                pedido.setItem_name(cursorPedido.getString(2));
                pedido.setCantidad(cursorPedido.getInt(3));
                pedido.setPrecio(cursorPedido.getInt(4));
                pedido.setCompletado(Boolean.parseBoolean(cursorPedido.getString(5))); // convierte el "True" a true:boolean
                pedido.setFoto(cursorPedido.getInt(6));

                // cursor.getInt(cursor.getColumnIndex("flag")) == 1
//                String str = "True";
//                Boolean bool = Boolean.parseBoolean(str);

                listaPedidos.add(pedido);
            } while (cursorPedido.moveToNext()); // nos  mueve al siguiente contacto
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

        // cursorPedido = db.rawQuery("SELECT * FROM " + TABLE_PEDIDOS + " WHERE order_id = " + idUser + " and completado = " + 0, null);

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
