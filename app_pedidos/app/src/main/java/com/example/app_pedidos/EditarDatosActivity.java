package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
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

public class EditarDatosActivity extends AppCompatActivity {


    EditText txtNombre, txtApellido, txtDireccion, txtTelefono, txtDni;
    Button btnGuardar;
    private ArrayList<Cliente> clientes;

    boolean correcto = false;

    Cliente cliente;


    ArrayList<Pedido> listaArrayPedidoFinal;
    int idUser;
    int idPedido;
    String metodoEnvio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_datos);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);

        btnGuardar = findViewById(R.id.btnGuardar);


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


        DbClientes dbClientes = new DbClientes(EditarDatosActivity.this);
        cliente = dbClientes.verCliente(idUser);


        if(cliente != null){
            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtDni.setText(cliente.getDni());
        }


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (metodoEnvio.equals("Envio")){

                    if (!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") && !txtDireccion.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtDni.getText().toString().equals("")) {
                        if (!existeCliente(txtDni.getText().toString())) {

                            correcto = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), txtDireccion.getText().toString(), txtTelefono.getText().toString(), txtDni.getText().toString());

                            if (correcto) {
                                Toast.makeText(EditarDatosActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EditarDatosActivity.this, RevisarPedidoActivity.class);
                                intent.putExtra("idUser", idUser);
                                intent.putExtra("idPedido", idPedido);
                                intent.putExtra("metodoEnvio", metodoEnvio);

                                startActivity(intent);

                            } else {
                                Toast.makeText(EditarDatosActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(EditarDatosActivity.this, "El DNI ingresado ya está en uso.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditarDatosActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                    }


                } else if (metodoEnvio.equals("Retiro")){


                    if (!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") && !txtDni.getText().toString().equals("")) {
                        if (!existeCliente(txtDni.getText().toString())) {

                            // editarCliente devuelve un boolean
                            correcto = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), txtDireccion.getText().toString(), txtTelefono.getText().toString(), txtDni.getText().toString());

                            if (correcto) {
                                Toast.makeText(EditarDatosActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EditarDatosActivity.this, RevisarPedidoActivity.class);
                                intent.putExtra("idUser", idUser);
                                intent.putExtra("idPedido", idPedido);
                                intent.putExtra("metodoEnvio", metodoEnvio);

                                startActivity(intent);

                            } else {
                                Toast.makeText(EditarDatosActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(EditarDatosActivity.this, "El DNI ingresado ya está en uso.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(EditarDatosActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(EditarDatosActivity.this, "Error en el metodo seleccionado", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    // valida si el dni que ingresa el usuario ya existe
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

}
