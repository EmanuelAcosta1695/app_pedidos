package com.example.app_pedidos;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pedidos.db.DbClientes;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.entidades.Cliente;
import com.example.app_pedidos.entidades.Pedido;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;

import java.util.ArrayList;

import okhttp3.*;

public class ConfirmacionActivity extends AppCompatActivity {

    private Button btnSeguirComprando, btnGuardarDatos, btnEmail;
    private TextView confirmacionCompra;
    private String msgFinal;

    private EditText edtEmail;
    private String edEmail;

    String name, email;

    int idUser, idPedido;
    ArrayList<Pedido> listaArrayPedidoFinal;
    private ArrayList<Cliente> clientes;
    Cliente cliente;

    String datos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmacion);

        DbClientes dbClientes = new DbClientes(ConfirmacionActivity.this);
        DbPedidos dbPedidos = new DbPedidos(ConfirmacionActivity.this);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);

            } else {
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");

            }
        } else {

            idUser = (int) savedInstanceState.getInt("idUser");
            idPedido = (int) savedInstanceState.getInt("idPedido");
        }


        confirmacionCompra = findViewById(R.id.confirmacionCompra);
        confirmacionCompra.setText("Compra registrada exitosamente. Gracias por hacer su pedido.");

        btnSeguirComprando = findViewById(R.id.btnSeguirComprando);
        btnSeguirComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                startActivity(intent);
            }
        });



        listaArrayPedidoFinal = dbPedidos.mostrarPedidosFinalizados(idUser);



//        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
//        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmacionActivity.this);
//                builder.setMessage("Desea guardar los datos de los clientes?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                String filename = "Datos de los Clientes.txt";
//                                //ArrayList<Cliente> list_Clientes = dbClientes.mostrarClientes();
//
//
//                                // CON EL USER ID PUEDO TRAERME SOLAMENTE ESE USER ID NO TODO
//                                clientes = dbClientes.mostrarClientes();
//
//                                // FALTA LO Q COMPRO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//                                // pero no puedo traerlo por bd px ya estan en TRUE el complete
//
//                                try {
//                                    OutputStreamWriter file = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));
//
//                                    file.write("[ " + clientes.get(idUser).getId() + " \n");
//                                    file.write(clientes.get(idUser).getNombre() + " \n");
//                                    file.write(clientes.get(idUser).getApellido() + " \n");
//                                    file.write(clientes.get(idUser).getDni() + " \n");
//                                    file.write(clientes.get(idUser).getTelefono() + " \n");
//                                    file.write(clientes.get(idUser).getDireccion() + " \n");
//
//                                    try{
//                                        for (int y = 0; y<listaArrayPedidoFinal.size(); y++){
//                                            file.write("[ " + listaArrayPedidoFinal.get(idUser).getId() + " \n");
//                                            file.write(listaArrayPedidoFinal.get(idUser).getOrder_id() + " \n");
//                                            file.write(listaArrayPedidoFinal.get(idUser).getItem_name() + " \n");
//                                            file.write(listaArrayPedidoFinal.get(idUser).getCantidad() + " \n");
//                                            file.write(listaArrayPedidoFinal.get(idUser).getPrecio() + " ], " + " \n");
//
//                                            file.write(" \n");
//                                        }
//
//                                    } catch (Exception e) {
//
//                                    }
//
//                                    file.write(" \n");
//
//                                    file.flush();
//                                    file.close();
//                                    Toast.makeText(ConfirmacionActivity.this, "Los datos se guardaron exitosamente", Toast.LENGTH_LONG).show();
//
//                                } catch (IOException e) {
//                                    Toast.makeText(ConfirmacionActivity.this, "No se pudieron guardar los datos", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        })
//                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick (DialogInterface dialogInterface,int i){
//                            }
//                        }).show();
//                }
//
//            });


        // EMAILS ----------------------------------------------------------


        listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);


        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtEmail = findViewById(R.id.edtEmail);
                edEmail = edtEmail.getText().toString();


                cliente = dbClientes.verCliente(idUser);


                datos = (cliente.getId() + " \n" +
                        cliente.getNombre() + " \n" +
                        cliente.getDni() + " \n" +
                        cliente.getTelefono() + " \n" +
                        cliente.getDireccion() + " \n");


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Datos de la compra.");
                intent.putExtra(Intent.EXTRA_TEXT, datos);  // -> simplemente escribe en datos

                try {
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Correo enviado", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No hay aplicaciones de correo electrónico instaladas.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
