package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.entidades.Cliente;
import com.example.app_pedidos.entidades.Pedido;

import java.util.ArrayList;


public class FinalizarPedidoActivity extends AppCompatActivity {

    private Button btnEnvio, btnRetiro, btnVolver;
    ArrayList<Pedido> listaArrayPedidoFinal;
    int idUser;
    int idPedido;
    String metodoEnvio;


    private EditText txtNombre, txtApellido, txtDireccion, txtTelefono, txtDni;
    private ArrayList<Cliente> clientes;
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finalizar_pedido);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if(extras == null){
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);
                //listaArrayPedidoFinal = null;
            } else {
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");

            }
        } else {
            idUser = savedInstanceState.getInt("idUser");
            idPedido = savedInstanceState.getInt("idPedido");

        }


        DbClientes dbClientes = new DbClientes(FinalizarPedidoActivity.this);
        clientes = dbClientes.mostrarClientes();

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);


        cliente = dbClientes.verCliente(idUser);

        if(cliente != null){
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtDni.setText(cliente.getDni());
        }


        try {

            DbPedidos dbPedidos = new DbPedidos(FinalizarPedidoActivity.this);

            listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);

            System.out.println(listaArrayPedidoFinal);

        } catch (Exception ex){
            ex.toString();
            System.out.println(ex);
        }

        btnEnvio = findViewById(R.id.btnEnvio);
        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") &&!txtDireccion.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtDni.getText().toString().equals("")){

                    if(!existeCliente(txtDni.getText().toString())){

                        boolean id = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), txtDireccion.getText().toString(), txtTelefono.getText().toString(), txtDni.getText().toString());


                        if(id){
                            Toast.makeText(FinalizarPedidoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();

                            metodoEnvio = "Envio";


                            Intent intent = new Intent(FinalizarPedidoActivity.this, RevisarPedidoActivity.class);

                            intent.putExtra("idUser", idUser);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("metodoEnvio", metodoEnvio);

                            startActivity(intent);

                        } else {
                            Toast.makeText(FinalizarPedidoActivity.this, "Error al guardar registro.", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(FinalizarPedidoActivity.this, "El DNI ingresado ya está en uso.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FinalizarPedidoActivity.this, "SE DEBEN LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnRetiro = findViewById(R.id.btnRetiro);
        btnRetiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("")&& !txtTelefono.getText().toString().equals("") && !txtDni.getText().toString().equals("")){
                    if(!existeCliente(txtDni.getText().toString())){

                        boolean id = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), "", "", txtDni.getText().toString());


                        if(id){
                            Toast.makeText(FinalizarPedidoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();

                            metodoEnvio = "Retiro";


                            Intent intent = new Intent(FinalizarPedidoActivity.this, RevisarPedidoActivity.class);

                            intent.putExtra("idUser", idUser);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("metodoEnvio", metodoEnvio);

                            startActivity(intent);

                        } else {
                            Toast.makeText(FinalizarPedidoActivity.this, "Error al guardar registro.", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(FinalizarPedidoActivity.this, "El DNI ingresado ya está en uso.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FinalizarPedidoActivity.this, "Por favor, ingresa nombre, apellido, telefono y DNI.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent volver = new Intent(getApplicationContext(), ProductsActivity.class);

                volver.putExtra("idUser", idUser);
                volver.putExtra("idPedido", idPedido);

                startActivity(volver);
            }
        });


    }


    private boolean existeCliente(String dni){
        for(Cliente c: clientes){
            if (idUser != c.getId()){
                if(c.getDni().equals(dni)){
                    return true;
                }
            }
        }
        return false;
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



