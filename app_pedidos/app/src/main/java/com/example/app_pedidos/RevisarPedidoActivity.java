package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import java.io.Serializable;
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
    int total;
    int precio;
    int cantidad;
    TextView viewTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revisar_pedido);

//        // IDUSER ES PARA EL ORDER_ID que tiene el id del user
//        if(savedInstanceState == null){
//            Bundle extras = getIntent().getExtras();
//            if(extras == null){
//                idUser = Integer.parseInt(null);
//                idPedido = Integer.parseInt(null);
//                //listaArrayPedidoFinal = null;
//            } else {
//                idUser = extras.getInt("idUser");
//                idPedido = extras.getInt("idPedido");
//                //listaArrayPedidoFinal = (ArrayList<Pedido>) extras.get("ListaArrayPedidoFinal");
//            }
//        } else {
//            idUser = (int) savedInstanceState.getSerializable("idUser");
//            idPedido = (int) savedInstanceState.getSerializable("idPedido");
//            //listaArrayPedidoFinal = (ArrayList<Pedido>)getIntent().getSerializableExtra("ListaArrayPedidoFinal");
//
//            System.out.println("En else de Revisar pedido activity");
//            System.out.println("id del user: ");
//            System.out.println(idUser);
//            System.out.println("#-----------");
//            System.out.println("#-----------");
//            System.out.println("#-----------");
//            System.out.println(" ");
//        }


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

        System.out.println("En el Revisar pedido activity");
        System.out.println("id del user: ");
        System.out.println(idUser);
        System.out.println("#-----------");
        System.out.println("#-----------");
        System.out.println("#-----------");
        System.out.println(" ");

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
            System.out.println("Id: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("Dirección: " + cliente.getDireccion());
            System.out.println("Teléfono: " + cliente.getTelefono());
            System.out.println("DNI: " + cliente.getDni());

            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            if (!cliente.getDireccion().isEmpty()) {
                txtDireccion.setText(cliente.getDireccion());
            } else {txtDireccion.setText("No corresponde dirección.");}

            if (!cliente.getTelefono().isEmpty()) {
                txtTelefono.setText(cliente.getTelefono());
            } else {txtTelefono.setText("No corresponde teléfono");}
            txtDni.setText(cliente.getDni());


        } else {
            System.out.println("No se encontró el cliente con id " + idUser);

            txtNombre.setText("");
            txtApellido.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
            txtDni.setText("");
        }


        // consultas sql del carrito y cliente
        // o los envio con intent



//        clientes = dbClientes.mostrarClientes();

        DbPedidos dbPedidos = new DbPedidos(RevisarPedidoActivity.this);

        try {

            //DbPedidos dbPedidos = new DbPedidos(RevisarPedidoActivity.this);
            // almacena aparte los elementos pedidos por el user
            listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);

            System.out.println(" ");
            System.out.println("Consulta SQL: listaArrayPedidoFinal");
            System.out.println(listaArrayPedidoFinal);
            System.out.println(" ");
            System.out.println(" ");
        } catch (Exception ex){
            ex.toString();
            System.out.println();
            System.out.println(ex);
            System.out.println();
        }

//        try {
//            StringBuilder sb = new StringBuilder();
//            for (Pedido pedido : listaArrayPedidoFinal) {
//                sb.append(pedido.getItem_name()).append(" ").append(pedido.getCantidad()).append(" ").append(pedido.getPrecio()).append("\n");
//
//                txtDatos.setText(sb.toString());
//            }
//        } catch (Exception ex){
//            ex.toString();
//            System.out.println();
//            System.out.println(ex);
//            System.out.println();
//        }

        String cadena = "";

        try {
            for (Pedido pedido : listaArrayPedidoFinal) {
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" OBJETO: ");
                System.out.println((pedido.getItem_name()) + " x " + (pedido.getCantidad()) + " = " + (pedido.getPrecio()));
                System.out.println(" ");
                System.out.println(" ");


                cadena += ("⚫" + (pedido.getItem_name()) + "  x" + (pedido.getCantidad()) + "  $" + (pedido.getPrecio()*pedido.getCantidad()) + ("\n"));
            }
        } catch (Exception ex){
            ex.toString();
            System.out.println();
            System.out.println(ex);
            System.out.println();
        }

        txtDatos.setText(cadena); // elimina cualquier espacio en blanco adicional al final de la cadena


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

        // txtDatos.setText("Hola Jorge, Gordo misil de grasa");

        // txtDatos.setText();


        // FINALIZA EL PEDIDO
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                //intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
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
                //intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
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
                //volver.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
                startActivity(volver);
            }
        });
    }

//    // valida si el dni que ingresa el usuario ya existe
//    private boolean existeCliente(String dni){
//        for(Cliente c: clientes){
//            if(c.getDni().equals(dni)){
//                return true;
//            }
//        }
//        return false;
//    }

//    // lleva a la pantalla de ver Productos
//    private void confirmacionActivity(String msgFinal){
//        Intent intent = new Intent(this, ConfirmacionActivity.class);
//        intent.putExtra("msgFinal", msgFinal);
//        intent.putExtra("IdUser", idUser);
//        intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
//        startActivity(intent);
//    }

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
