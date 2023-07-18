package com.example.app_pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbHelper;
import com.example.app_pedidos.db.DbProducts;
import com.example.app_pedidos.entidades.Cliente;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button  btnVerProductos;
    Integer idUser;
    int idPedido = 0;
    String metodoEnvio = "";

    ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaro este condicional por si el usuario vuelve de productActivity aca
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if(extras != null){
                idUser = extras.getInt("idUser");
            } else {
                idUser = null;
            }
        } else {
            idUser = savedInstanceState.getInt("idUser");

        }


        if (!checkDatabaseExists()) {

            try {
                // crea db
                DbHelper dbHelper = new DbHelper(MainActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Toast.makeText(MainActivity.this, "base de datos creada", Toast.LENGTH_LONG).show();


                DbProducts dbProducts = new DbProducts(MainActivity.this);

                // Creacion de productos a visualizar
                dbProducts.insertarProduct("Pan", 300, 1); // 1 kg
                dbProducts.insertarProduct("Factura", 700, 2); // 1 docena
                dbProducts.insertarProduct("Sandwich de Miga", 2000, 3); // 1 docena
                dbProducts.insertarProduct("Bizcochos", 400, 4); // 250gr

                DbClientes dbClientes = new DbClientes(MainActivity.this);

                idUser = (int) dbClientes.insertarCliente("","","","","");
                Log.d("idUser", "" + idUser);


            } catch (Exception e) {
                System.out.println("Error al crear la DB: " + e);
                Toast.makeText(MainActivity.this, "no se pudo crear la base de datos", Toast.LENGTH_LONG).show();
            }

        } else {
            // La db ya existe
            DbClientes dbClientes = new DbClientes(MainActivity.this);
            clientes = dbClientes.mostrarClientes();
            idUser = clientes.get(0).getId();
        }


        // Ver lista de productos
        btnVerProductos = findViewById(R.id.btnProductos);
        btnVerProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);

            //id de user con datos vacio
            intent.putExtra("idUser", idUser);

            // int idPedido = 0
            intent.putExtra("idPedido", idPedido);

            startActivity(intent);
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // hide the back button
        return true;

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        // Agrego la opcion de acceder a la pantalla de presentacion al menu
        switch (item.getItemId()){
            case R.id.menuNuevo:
                presentacion();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    // Funcion para ver la pantalla de presentacion del comercio
    private void presentacion(){
        Intent intent = new Intent(this, PresentacionActivity.class);
        startActivity(intent);
    }


    // comprueba si la BD existe
    private boolean checkDatabaseExists() {
        File dbFile = getApplicationContext().getDatabasePath("clientes_pedidos.db");
        return dbFile.exists();
    }

}