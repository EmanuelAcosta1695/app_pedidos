package com.example.app_pedidos;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.io.OutputStreamWriter;
import java.util.Properties;

import java.util.ArrayList;

import okhttp3.*;

public class ConfirmacionActivity extends AppCompatActivity {

    private Button btnSeguirComprando, btnGuardarDatos, btnEmail;
    private TextView confirmacionPedido;

    private EditText edtEmail;
    private String edEmail;

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
//                msgFinal = null;
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);
            } else {
//                msgFinal = extras.getString("msgFinal");
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");
            }
        } else {
//            msgFinal = (String) savedInstanceState.getSerializable("msgFinal");
            idUser = (int) savedInstanceState.getInt("idUser");
            idPedido = (int) savedInstanceState.getInt("idPedido");
        }



        listaArrayPedidoFinal = dbPedidos.mostrarPedidos(idUser);

        // update pedidos completado = 1

        try {
            dbPedidos.update_pedido_complete(idUser);
            confirmacionPedido = findViewById(R.id.confirmacionPedido);
            confirmacionPedido.setText("Pedido registrado exitosamente." + "\n" +  "      Gracias por elegirnos.");

        } catch (Exception e) {
            confirmacionPedido = findViewById(R.id.confirmacionPedido);
            confirmacionPedido.setText("El pedido no se pudo registrar. Intentelo mas tarde.");
        }


        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" Array desde RevisarPedido: " + listaArrayPedidoFinal);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        btnSeguirComprando = findViewById(R.id.btnSeguirComprando);
        btnSeguirComprando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductsActivity.class);
                intent.putExtra("idUser", idUser);
                intent.putExtra("idPedido", idPedido);
                startActivity(intent);
            }
        });


        // ----------------------------------------------

        //TENGO QUE MODIFICAR
        // NESECITO DATOS DE LA COMPRA Y DEL CLIENTE
        // ADEMAS ME VA A SERVIR PARA ENVIARSELOS POR MAIL AL USER
//        // Guarda datos de los clientes en un .txt


        //listaArrayPedidoFinal = dbPedidos.mostrarPedidosFinalizados(idUser);

        for (Pedido pedido : listaArrayPedidoFinal) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" pedido ");
            System.out.println(pedido);
            System.out.println(" ");
            System.out.println(" ");
        }


        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" listaArrayPedidoFinal ");
        System.out.println(listaArrayPedidoFinal);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" idUser ");
        System.out.println(idUser);
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");


        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmacionActivity.this);
                builder.setMessage("Desea guardar los datos de los clientes?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String filename = "Datos-de-la-compra.txt";
                                //ArrayList<Cliente> list_Clientes = dbClientes.mostrarClientes();


                                cliente = dbClientes.verCliente(idUser);


                                try {
                                    OutputStreamWriter file = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));

                                    file.write("[ " + cliente.getId() + " \n");
                                    file.write(cliente.getNombre() + " \n");
                                    file.write(cliente.getApellido() + " \n");
                                    file.write(cliente.getDni() + " \n");
                                    file.write(cliente.getTelefono() + " \n");
                                    file.write(cliente.getDireccion() + " \n");

                                    try {
                                        for (Pedido pedido : listaArrayPedidoFinal) {
                                            file.write("[ " + pedido.getId() + " \n");
                                            file.write(pedido.getOrder_id() + " \n");
                                            file.write(pedido.getItem_name() + " \n");
                                            file.write(pedido.getCantidad() + " \n");
                                            file.write(pedido.getPrecio() + " ], " + " \n");

                                            file.write(" \n");
                                        }

                                        } catch (Exception e) {
                                            Toast.makeText(ConfirmacionActivity.this, "No se pudieron iterar los datos de la compra", Toast.LENGTH_LONG).show();
                                        }


                                    file.write(" \n");

                                    file.flush();
                                    file.close();
                                    Toast.makeText(ConfirmacionActivity.this, "Los datos se guardaron exitosamente", Toast.LENGTH_LONG).show();

                                } catch (IOException e) {
                                    Toast.makeText(ConfirmacionActivity.this, "No se pudieron guardar los datos", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialogInterface,int i){
                            }
                        }).show();
                }

            });


        // EMAILS ----------------------------------------------------------


        // e.acosta1695@gmail.com

        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail = findViewById(R.id.edtEmail);
                edEmail = edtEmail.getText().toString();

                cliente = dbClientes.verCliente(idUser);

                // Obtener los datos del cliente
                String datosCliente = "Datos del cliente: \n" +
                        "ID: " + cliente.getId() + "\n" +
                        "Nombre: " + cliente.getNombre() + "\n" +
                        "DNI: " + cliente.getDni() + "\n" +
                        "Teléfono: " + cliente.getTelefono() + "\n" +
                        "Dirección: " + cliente.getDireccion() + "\n";

                // Obtener los datos de los pedidos
                StringBuilder datosPedidos = new StringBuilder("Datos de los pedidos: \n");
                for (Pedido pedido : listaArrayPedidoFinal) {
                    datosPedidos.append("ID Pedido: ").append(pedido.getId()).append("\n")
                            .append("Order ID: ").append(pedido.getOrder_id()).append("\n")
                            .append("Nombre del ítem: ").append(pedido.getItem_name()).append("\n")
                            .append("Cantidad: ").append(pedido.getCantidad()).append("\n")
                            .append("Precio: ").append(pedido.getPrecio()).append("\n\n");
                }

                // Concatenar los datos del cliente y los datos de los pedidos
                String datos = datosCliente + "\n" + datosPedidos.toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Datos de la compra.");
                intent.putExtra(Intent.EXTRA_TEXT, datos);

                try {
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Selecciona un gestor de correo", Toast.LENGTH_SHORT).show();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "No hay aplicaciones de correo electrónico instaladas.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // FUNCIONAAAAA
//        btnEmail = findViewById(R.id.btnEmail);
//        btnEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                edtEmail = findViewById(R.id.edtEmail);
//                edEmail = edtEmail.getText().toString();
//
//
//                // datos del cliente
//                cliente = dbClientes.verCliente(idUser);
//
//                // listaArrayPedidoFinal
//                // .toString()
//
//                datos = (cliente.getId() + " \n" +
//                        cliente.getNombre() + " \n" +
//                        cliente.getDni() + " \n" +
//                        cliente.getTelefono() + " \n" +
//                        cliente.getDireccion() + " \n");
//
//
//                for (Pedido pedido : listaArrayPedidoFinal) {
//
//                    System.out.println(" ");
//                    System.out.println(" ");
//                    System.out.println(" ");
//                    System.out.println(" pedido ");
//                    System.out.println(pedido);
//                    System.out.println(" ");
//                    System.out.println(" ");
//
//                    datos += (" \n" +
//                            pedido.getId() + " \n" +
//                            pedido.getOrder_id() + " \n" +
//                            pedido.getItem_name() + " \n" +
//                            pedido.getCantidad() + " \n" +
//                            pedido.getPrecio() + " ], " + " \n");
//                };
//
//
//
//                System.out.println(" ");
//                System.out.println(" ");
//                System.out.println(" datos ");
//                System.out.println(datos);
//                System.out.println(" ");
//                System.out.println(" ");
//                System.out.println(" ");
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{edEmail});
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Datos de la compra.");
//                intent.putExtra(Intent.EXTRA_TEXT, datos);  // -> simplemente escribe en datos
//
//                try {
//                    startActivity(intent);
//                    //startActivity(Intent.createChooser(intent, "Enviar correo"));
//                    Toast.makeText(getApplicationContext(), "Selecciona un gestor de mail", Toast.LENGTH_SHORT).show();
//                } catch (android.content.ActivityNotFoundException ex) {
//                    Toast.makeText(getApplicationContext(), "No hay aplicaciones de correo electrónico instaladas.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });



    }
}




//        btnEmail = findViewById(R.id.btnEmail);
//        btnEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                edtEmail = findViewById(R.id.edtEmail);
//                edEmail = edtEmail.getText().toString();
//
//                cliente = dbClientes.verCliente(idUser);
//
//                datos = (cliente.getId() + " \n" +
//                        cliente.getNombre() + " \n" +
//                        cliente.getDni() + " \n" +
//                        cliente.getTelefono() + " \n" +
//                        cliente.getDireccion() + " \n");
//
//                // Configurar las propiedades para la conexión SMTP
//                Properties props = new Properties();
//                props.put("mail.smtp.auth", "true");
//                props.put("mail.smtp.starttls.enable", "true");
//                props.put("mail.smtp.host", "smtp.gmail.com"); // Reemplaza con tu servidor SMTP
//                props.put("mail.smtp.port", "587"); // Reemplaza con el puerto SMTP adecuado
//
//                // Configurar las credenciales de autenticación
//                final String username = "e.acosta1695@gmail.com";
//                final String password = "3065794576Em";
//
//                // Crear una sesión de correo
//                Session session = Session.getInstance(props, new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//
//                try {
//                    // Crear el mensaje de correo
//                    Message message = new MimeMessage(session);
//                    message.setFrom(new InternetAddress("e.acosta1695@gmail.com")); // Reemplaza con tu dirección de correo
//                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(edEmail));
//                    message.setSubject("Datos de la compra.");
//                    message.setText("datos");
//
//                    // Enviar el mensaje
//                    Transport.send(message);
//
//                    Toast.makeText(getApplicationContext(), "Correo enviado", Toast.LENGTH_SHORT).show();
//                } catch (MessagingException e) {
//                    Toast.makeText(getApplicationContext(), "Error al enviar el correo", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }
//        });






        // API FormSubmit  -> todo bien pero no llega el mail
//        btnEmail = findViewById(R.id.btnEmail);
//        btnEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                edtEmail = findViewById(R.id.edtEmail);
//                edEmail = edtEmail.getText().toString();
//
//                cliente = dbClientes.verCliente(idUser);
//
////                FormSubmitHelper.submitForm(ConfirmacionActivity.this, cliente.getNombre(), edEmail);
//                submitForm(cliente.getNombre(), edEmail);
//
//            }
//        });
//    }
//
//    private void submitForm(String name, String email) {
//        OkHttpClient client = new OkHttpClient();
//
//        RequestBody requestBody = new FormBody.Builder()
//                .add("name", name)
//                .add("email", email)
//                .build();
//
//        //String url = "https://formsubmit.co/" + email;
//        String url = "https://formsubmit.co/ema_016@hotmail.com.ar";
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("ConfirmacionActivity", "Error al enviar el correo electrónico: " + e.getMessage());
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ConfirmacionActivity.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                call.cancel();
//                client.dispatcher().executorService().shutdown();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    Log.i("ConfirmacionActivity", "Correo electrónico enviado con éxito");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ConfirmacionActivity.this, "Correo electrónico enviado con éxito", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Log.e("ConfirmacionActivity", "Error al enviar el correo electrónico: " + response.code());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(ConfirmacionActivity.this, "Error al enviar el correo electrónico", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                response.close();
//                client.dispatcher().executorService().shutdown();
//            }
//
//
//        });
//    }
//}






//        class FormSubmitHelper {
//            private final MediaType MEDIA_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded");
//
//            public void submitForm(Context context, String name, String email) {
//                OkHttpClient client = new OkHttpClient();
//
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("name", name)
//                        .add("email", email)
//                        .build();
//
//                String url = "https://formsubmit.co/" + email;
//
//                Request request = new Request.Builder()
//                        .url(url)
//                        .post(requestBody)
//                        .build();
//
//                client.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        // Mostrar mensaje de error al usuario
//                        Toast.makeText(context, "Error en la solicitud", Toast.LENGTH_SHORT).show();
//
//                        // Registrar el error en el registro de errores
//                        Log.e("FormSubmitHelper", "Error en la solicitud: " + e.getMessage());
//                    }
//
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Manejar la respuesta de la solicitud
//                                if (response.isSuccessful()) {
//                                    Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(context, "Error al enviar el correo", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//
//
//                        });
//
//                    }
//
//                });
//            }
//        }
//
//    }
//}

