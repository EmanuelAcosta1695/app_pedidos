package com.example.app_pedidos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if(extras != null){
                idUser = extras.getInt("idUser");

            } else {
                idUser = null;

            }
        } else {
            idUser = (int) savedInstanceState.getSerializable("idUser");
        }


        if (!checkDatabaseExists()) {

            try {
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


            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "no se pudo crear la base de datos", Toast.LENGTH_LONG).show();
            }

        } else {

            DbClientes dbClientes = new DbClientes(MainActivity.this);
            clientes = dbClientes.mostrarClientes();
            idUser = clientes.get(0).getId();

        }


        btnVerProductos = findViewById(R.id.btnProductos);
        btnVerProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);


            intent.putExtra("idUser", idUser);

            intent.putExtra("idPedido", idPedido);

            intent.putExtra("metodoEnvio", metodoEnvio);

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

        switch (item.getItemId()){
            case R.id.menuNuevo:
                presentacion();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void presentacion(){
        Intent intent = new Intent(this, PresentacionActivity.class);
        startActivity(intent);
    }

    private boolean checkDatabaseExists() {
        File dbFile = getApplicationContext().getDatabasePath("clientes_pedidos.db");
        return dbFile.exists();
    }

}