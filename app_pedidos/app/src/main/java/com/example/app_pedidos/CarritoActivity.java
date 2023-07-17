package com.example.app_pedidos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pedidos.adaptadores.CarritoAdapter;
import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.entidades.Cliente;
import com.example.app_pedidos.entidades.Pedido;

import com.example.app_pedidos.db.DbTotal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                // listaArrayPedidoFinal = (ArrayList<Pedido>) extras.get("ListaArrayPedidoFinal");
            }
        } else {
            idUser = savedInstanceState.getInt("idUser");
            idPedido = savedInstanceState.getInt("idPedido");
            metodoEnvio = savedInstanceState.getString("metodoEnvio");
            // listaArrayPedidoFinal = (ArrayList<Pedido>) savedInstanceState.get("ListaArrayPedidoFinal");
        }


        System.out.println();
        System.out.println("#-----------");
        System.out.println("EN CARRITO ACTIVITY");
        System.out.println("id del user: ");
        System.out.println(idUser);
        System.out.println("#-----------");


        listaCarritoPedidos = findViewById(R.id.listaCarritoProducts);
        listaCarritoPedidos.setLayoutManager(new LinearLayoutManager(this));

        DbPedidos dbPedidos = new DbPedidos(CarritoActivity.this);

        DbTotal dbtotal = new DbTotal(CarritoActivity.this);


        CarritoAdapter adapterCarrito = new CarritoAdapter(dbPedidos.mostrarPedidos(idUser), idUser, idPedido);  // DEVUELVE UNA LISTA
        listaCarritoPedidos.setAdapter(adapterCarrito);


        try {
            // almacena aparte los elementos pedidos por el user
            listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);
            System.out.println();
            System.out.println("Consulta SQL");
            System.out.println(listaArrayPedidoFinal);
            System.out.println();
            System.out.println();
        } catch (Exception ex){
            ex.toString();
            System.out.println();
            System.out.println(ex);
            System.out.println();
        }



        // LO NESECITO ACA AFUERA PARA QUE CADA VER Q SE MUESTRE EL CARRITO, ME MUESTRA EL VALOR DE
        // TOTAL
        // itero el array para obtener precio*cantidad y sumar al final al otal

        try{
            for (Pedido pedidoFinal: listaArrayPedidoFinal){
                precio = pedidoFinal.getPrecio();
                cantidad = pedidoFinal.getCantidad();

                total += (precio*cantidad);
            }

            viewTotal = findViewById(R.id.viewTotal);
            viewTotal.setText(String.valueOf("Total: $" + total));


        } catch (Exception ex){
            System.out.println(ex);
        }


        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);
        btnFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // idUser es el valor que recibe order_id px cada order_id hace referencia a un usuario especifico
                id = (int) dbtotal.insertarTotal(idUser, total); //QUEDA REGISTRADO EL TOTAL


                DbClientes dbClientes = new DbClientes(CarritoActivity.this);
                cliente = dbClientes.verCliente(idUser);

                try {
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(cliente);
                    System.out.println(cliente.getNombre());
                    System.out.println(cliente.getApellido());
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println(" ");
                } catch (Exception e) {
                    System.out.println(" ");
                    System.out.println(e);
                    System.out.println("Cliente Vacio");
                    System.out.println(" ");
                }

//                if (cliente == null || (cliente.getNombre().equals("") && cliente.getApellido().equals("") && cliente.getDireccion().equals("") && cliente.getDni().equals("") && cliente.getTelefono().equals(""))) {

                Intent finalizarPedido = new Intent(getApplicationContext(), FinalizarPedidoActivity.class);

                finalizarPedido.putExtra("idPedido", idPedido);
                finalizarPedido.putExtra("idUser", idUser);
                finalizarPedido.putExtra("metodoEnvio", metodoEnvio);

                startActivity(finalizarPedido);

//                } else {
//
//                    Intent revisarPedido = new Intent(getApplicationContext(), RevisarPedidoActivity.class);
//
//                    revisarPedido.putExtra("idPedido", idPedido);
//                    revisarPedido.putExtra("idUser", idUser);
//                    revisarPedido.putExtra("metodoEnvio", metodoEnvio);
//
//                    startActivity(revisarPedido);
//                }
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // hide the back button
        return true;

    }

    @Override
    public void onBackPressed (){

    }

}
