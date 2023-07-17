package com.example.app_pedidos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.app_pedidos.entidades.Cliente;

import java.util.ArrayList;

// nos ayuda a realizar las transacciones a la tabla
public class DbClientes extends DbHelper {

    Context context;

    public DbClientes(@Nullable Context context) {
        super(context);

        this.context = context;
    }

    // NESECITO ALGUNO MAS PARA EL LOGIN. capaz puedo usar el mostrar clientes

    public long insertarCliente(String nombre, String apellido, String direccion, String telefono, String DNI) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();  //vamos a escribir en nuestra bd

            // funcion insertar registro
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellido", apellido);
            values.put("direccion", direccion);
            values.put("telefono", telefono);
            values.put("DNI", DNI);

            //1ero nonmbre de la tabla
            // aca inserta esos valores a esta tabla
            // the row ID of the newly inserted row, or -1 if an error occurred
            id = db.insert(TABLE_CLIENTES, null, values);


        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }


    public ArrayList<Cliente> mostrarClientes(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Cliente> listaClientes = new ArrayList<>();
        Cliente cliente = null;
        Cursor cursorCliente = null;

        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTES, null);


        if(cursorCliente.moveToFirst()){
            do{
                cliente = new Cliente();
                cliente.setId(cursorCliente.getInt(0));
                cliente.setNombre(cursorCliente.getString(1));
                cliente.setApellido(cursorCliente.getString(2));
                cliente.setDireccion(cursorCliente.getString(3));
                cliente.setTelefono(cursorCliente.getString(4));
                cliente.setDni(cursorCliente.getString(5));

                listaClientes.add(cliente);
            } while (cursorCliente.moveToNext()); // nos  mueve al siguiente contacto
        }

        cursorCliente.close();

        return listaClientes;
    }


    public Cliente verCliente(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cliente cliente = null;
        Cursor cursorCliente;

        cursorCliente = db.rawQuery("SELECT * FROM " + TABLE_CLIENTES + " WHERE id = " + id + " LIMIT 1", null);

        if(cursorCliente.moveToFirst()){
            cliente = new Cliente();
            cliente.setId(cursorCliente.getInt(0));
            cliente.setNombre(cursorCliente.getString(1));
            cliente.setApellido(cursorCliente.getString(2));
            cliente.setDireccion(cursorCliente.getString(3));
            cliente.setTelefono(cursorCliente.getString(4));
            cliente.setDni(cursorCliente.getString(5));
            }

        cursorCliente.close();

        return cliente;
    }


    public boolean editarCliente(int id, String nombre, String apellido, String direccion, String telefono, String DNI) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("UPDATE " + TABLE_CLIENTES + " SET nombre = '"+nombre+"', apellido = '"+apellido+"', direccion = '"+direccion+"', telefono = '"+telefono+"', DNI = '"+DNI+"' WHERE id='" + id +"' ");
            correcto = true;


        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }


    public boolean eliminarCliente(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CLIENTES + " WHERE id = '" + id + "'");
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
