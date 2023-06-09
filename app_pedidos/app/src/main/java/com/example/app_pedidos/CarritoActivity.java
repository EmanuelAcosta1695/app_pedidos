package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pedidos.adaptadores.CarritoAdapter;
import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.entidades.Cliente;
import com.example.app_pedidos.entidades.Pedido;

import com.example.app_pedidos.db.DbTotal;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {

    RecyclerView listaCarritoPedidos;
    ArrayList<Pedido> listaArrayCarritoProducts;
    private Button btnFinalizarPedido, btnVolver;
    TextView viewTotal;


    Integer idUser;
    int idPedido;
    int id; // este es para almacenar el id q me devuelve dbTotal
    String metodoEnvio;

    ArrayList<Pedido> listaArrayPedidoFinal;
    int total;
    int precio;
    int cantidad;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");
                metodoEnvio = extras.getString("metodoEnvio");
            }
        } else {
            idUser = savedInstanceState.getInt("idUser");
            idPedido = savedInstanceState.getInt("idPedido");
            metodoEnvio = savedInstanceState.getString("metodoEnvio");
        }



        listaCarritoPedidos = findViewById(R.id.listaCarritoProducts);
        listaCarritoPedidos.setLayoutManager(new LinearLayoutManager(this));

        DbPedidos dbPedidos = new DbPedidos(CarritoActivity.this);

        DbTotal dbtotal = new DbTotal(CarritoActivity.this);


        CarritoAdapter adapterCarrito = new CarritoAdapter(dbPedidos.mostrarPedidos(idUser), idUser, idPedido);
        listaCarritoPedidos.setAdapter(adapterCarrito);


        try {
            listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);

            System.out.println(listaArrayPedidoFinal);

        } catch (Exception ex){
            ex.toString();
            System.out.println(ex);
        }

        try{
            for (Pedido pedidoFinal: listaArrayPedidoFinal){
                precio = pedidoFinal.getPrecio();
                cantidad = pedidoFinal.getCantidad();

                total += (precio*cantidad);
            }

            viewTotal = findViewById(R.id.viewTotal);
            viewTotal.setText(String.valueOf("Total: " + total));


        } catch (Exception ex){
            System.out.println(ex);
        }


        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = (int) dbtotal.insertarTotal(idUser, total); //QUEDA REGISTRADO EL TOTAL


                DbClientes dbClientes = new DbClientes(CarritoActivity.this);
                cliente = dbClientes.verCliente(idUser);


                Intent finalizarPedido = new Intent(getApplicationContext(), FinalizarPedidoActivity.class);

                finalizarPedido.putExtra("idPedido", idPedido);
                finalizarPedido.putExtra("idUser", idUser);
                finalizarPedido.putExtra("metodoEnvio", metodoEnvio);

                startActivity(finalizarPedido);

            }
        });



        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(getApplicationContext(), ProductsActivity.class);
                volver.putExtra("idUser", idUser);
                volver.putExtra("idPedido", idPedido);
                volver.putExtra("metodoEnvio", metodoEnvio);
                startActivity(volver);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return true;

    }

    @Override
    public void onBackPressed (){

    }

}
