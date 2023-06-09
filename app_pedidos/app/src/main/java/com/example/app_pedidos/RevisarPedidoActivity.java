package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.entidades.Cliente;
import com.example.app_pedidos.entidades.Pedido;

import java.util.ArrayList;

public class RevisarPedidoActivity extends AppCompatActivity {

    private Button btnEdtCliente, btnEdtPedido, btnConfirmar, btnVolver;
    ArrayList<Pedido> listaArrayPedidoFinal;
    int idUser;
    int idPedido;
    String metodoEnvio;

    private TextView txtNombre, txtApellido, txtDireccion, txtTelefono, txtDni, txtDatos;
    private ArrayList<Cliente> clientes;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revisar_pedido);


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


        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);

        txtDatos = findViewById(R.id.txtDatos);


        DbClientes dbClientes = new DbClientes(RevisarPedidoActivity.this);
        cliente = dbClientes.verCliente(idUser);

        // Seteo datos cliente
        if (cliente != null) {

            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtDni.setText(cliente.getDni());

        } else {

            txtNombre.setText("");
            txtApellido.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
            txtDni.setText("");
        }



        DbPedidos dbPedidos = new DbPedidos(RevisarPedidoActivity.this);

        try {

            listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);

        } catch (Exception ex){
            ex.toString();;
            System.out.println(ex);
        }


        String cadena = "";

        try {
            for (Pedido pedido : listaArrayPedidoFinal) {

                cadena += ("- " + (pedido.getItem_name()) + " " + (pedido.getCantidad()) + " " + (pedido.getPrecio()) + ("\n"));
            }
        } catch (Exception ex){
            ex.toString();
            System.out.println(ex);
        }

        txtDatos.setText(cadena);


        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbPedidos.update_pedido_complete(idUser);

                Intent intent = new Intent(getApplicationContext(), ConfirmacionActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("idPedido", idPedido);

                startActivity(intent);

            }
        });


        btnEdtPedido = findViewById(R.id.btnEdtPedido);
        btnEdtPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CarritoActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("idPedido", idPedido);
                intent.putExtra("metodoEnvio", metodoEnvio);

                startActivity(intent);
            }
        });


        btnEdtCliente = findViewById(R.id.btnEdtCliente);
        btnEdtCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditarDatosActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("idPedido", idPedido);
                intent.putExtra("metodoEnvio", metodoEnvio);

                startActivity(intent);
            }
        });


        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(getApplicationContext(), CarritoActivity.class);
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
