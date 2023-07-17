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

// los pedidos deberia tbm guarda la info del usuario!!!!

public class FinalizarPedidoActivity extends AppCompatActivity {

    private Button btnEnvio, btnRetiro, btnVolver;
    ArrayList<Pedido> listaArrayPedidoFinal;
    int idUser;
    int idPedido;
    String metodoEnvio;

    //private ArrayList<Product> products;

    // A LO DEL DNI LE AGREGO LO DEL USER, que no sean lo mismo

    private EditText txtNombre, txtApellido, txtDireccion, txtTelefono, txtDni;
    private ArrayList<Cliente> clientes;
    private String msgFinal = "";
    Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_finalizar_pedido);

        // ArrayList<ContactBean> filelist =  (ArrayList<ContactBean>)getIntent().getSerializableExtra("FILES_TO_SEND");

        // IDUSER ES PARA EL ORDER_ID que tiene el id del user
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if(extras == null){
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);
                //listaArrayPedidoFinal = null;
            } else {
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");
                //listaArrayPedidoFinal = (ArrayList<Pedido>) extras.get("ListaArrayPedidoFinal");
            }
        } else {
            idUser = (int) savedInstanceState.getSerializable("idUser");
            idPedido = (int) savedInstanceState.getSerializable("idPedido");
            //listaArrayPedidoFinal = (ArrayList<Pedido>)getIntent().getSerializableExtra("ListaArrayPedidoFinal");

            System.out.println("En el finalizar pedido activity");
            System.out.println("id del user: ");
            System.out.println(idUser);
            System.out.println("#-----------");
            System.out.println("#-----------");
            System.out.println("#-----------");
            System.out.println(" ");

            System.out.println("En el finalizar pedido activity");
            System.out.println("id del pedido: ");
            System.out.println(idPedido);
            System.out.println("#-----------");
            System.out.println("#-----------");
            System.out.println("#-----------");
            System.out.println(" ");
        }




//        DbProducts dbProducts = new DbProducts(FinalizarPedidoActivity.this);
//        products = dbProducts.mostrarProducts();

        DbClientes dbClientes = new DbClientes(FinalizarPedidoActivity.this);
        clientes = dbClientes.mostrarClientes();

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDni = findViewById(R.id.txtDni);


        cliente = dbClientes.verCliente(idUser);

        if(cliente != null){

            System.out.println("Id: " + cliente.getId());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Apellido: " + cliente.getApellido());
            System.out.println("Dirección: " + cliente.getDireccion());
            System.out.println("Teléfono: " + cliente.getTelefono());
            System.out.println("DNI: " + cliente.getDni());


            txtNombre.setText(cliente.getNombre());
            txtApellido.setText(cliente.getApellido());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtDni.setText(cliente.getDni());
        }

        // PARA QUE ESTO? si igual las seteo en ""
//        try {
//            for(Cliente c: clientes){
//                if(c.getNombre().equals("")){
//                    txtNombre.setText(c.getNombre());
//                }
//                if(c.getApellido().equals("")){
//                    txtApellido.setText(c.getApellido());
//                }
//                if(c.getDireccion().equals("")){
//                    txtDireccion.setText(c.getDireccion());
//                }
//                if(c.getTelefono().equals("")){
//                    txtTelefono.setText(c.getTelefono());
//                }
//                if(c.getDni().equals("")){
//                    txtDni.setText(c.getDni());
//                }
//            }
//        } catch (Exception ex){
//            ex.toString();
//        } finally {
//
//        }


        try {

            DbPedidos dbPedidos = new DbPedidos(FinalizarPedidoActivity.this);
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

        btnEnvio = findViewById(R.id.btnEnvio);
        btnEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtNombre.getText().toString().equals("") && !txtApellido.getText().toString().equals("") &&!txtDireccion.getText().toString().equals("") && !txtTelefono.getText().toString().equals("") && !txtDni.getText().toString().equals("")){
                    if(!existeCliente(txtDni.getText().toString())){

                        // Genera valor random que se le asigna a la propeidad avatar del objeto cliente.
                        // Este valor establece cual foto se le colocara a cada cliente en la lista de clientes y se mantendra asi.
//                        Random r = new Random();
//                        int avatar = r.nextInt(5)+1;

                        // recoge los datos ingresados por el usuario y lo registra
                        boolean id = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), txtDireccion.getText().toString(), txtTelefono.getText().toString(), txtDni.getText().toString());


                        // esto valida que se registro exitosamente el id>0
                        if(id){
                            Toast.makeText(FinalizarPedidoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();

                            metodoEnvio = "Envio";

                            System.out.println(" ");
                            System.out.println(" ");
                            System.out.println(" METODO ENVIO: ");
                            System.out.println(metodoEnvio);
                            System.out.println(" ");
                            System.out.println(" ");

                            Intent intent = new Intent(FinalizarPedidoActivity.this, RevisarPedidoActivity.class);

                            //intent.putExtra("msgFinal", msgFinal);
                            //intent.putExtra("IdUser", idUser);
                            intent.putExtra("idUser", idUser);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("metodoEnvio", metodoEnvio);
                            //intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
                            startActivity(intent);

//                            //msgFinal = "Compra registrada exitosamente. Gracias por hacer su pedido.";
//                            revisarPedido(idUser, idPedido, metodoEnvio);
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

                        // Genera valor random que se le asigna a la propeidad avatar del objeto cliente.
                        // Este valor establece cual foto se le colocara a cada cliente en la lista de clientes y se mantendra asi.
//                        Random r = new Random();
//                        int avatar = r.nextInt(5)+1;

                        // NO TIENE QUE SER INSERTA; TIENE Q SER MODIFICAR EL USER EXISTENTE
                        boolean id = dbClientes.editarCliente(idUser, txtNombre.getText().toString(), txtApellido.getText().toString(), "", txtTelefono.getText().toString(), txtDni.getText().toString());


                        // PUEDO GUARDAR EL PEDIDO EN EL CEL O DARLE LA OPC

                        // esto valida que se registro exitosamente el id>0
                        if(id){
                            Toast.makeText(FinalizarPedidoActivity.this, "Registro Guardado", Toast.LENGTH_LONG).show();

                            metodoEnvio = "Retiro";

                            System.out.println(" ");
                            System.out.println(" ");
                            System.out.println(" METODO ENVIO: ");
                            System.out.println(metodoEnvio);
                            System.out.println(" ");
                            System.out.println(" ");

                            Intent intent = new Intent(FinalizarPedidoActivity.this, RevisarPedidoActivity.class);

                            //intent.putExtra("msgFinal", msgFinal);
                            //intent.putExtra("IdUser", idUser);
                            intent.putExtra("idUser", idUser);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("metodoEnvio", metodoEnvio);
                            //intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
                            startActivity(intent);

//                            //msgFinal = "Compra registrada exitosamente. Gracias por hacer su pedido.";
//                            revisarPedido(idUser, idPedido, metodoEnvio);
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

                System.out.println("  ");
                System.out.println("  ");
                System.out.println(" en el FinalizarPedidoActivity ");
                System.out.println("  ");
                System.out.println("  ");
                System.out.println(" btnVolver ");
                System.out.println("  ");
                System.out.println(" No esta vacio! ");
                System.out.println(" idUser  : " + idUser);
                System.out.println("  ");


                Intent volver = new Intent(getApplicationContext(), ProductsActivity.class);
                volver.putExtra("idUser", idUser);
                volver.putExtra("idPedido", idPedido);
                //volver.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
                startActivity(volver);
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

//    // lleva a la pantalla de ver Productos? (NO, RevisarPedidoActivity)
//    private void revisarPedido(int idUser, int idPedido, String metodoEnvio){
//
//        Intent intent = new Intent(this, RevisarPedidoActivity.class);
//
//        //intent.putExtra("msgFinal", msgFinal);
//        //intent.putExtra("IdUser", idUser);
//        intent.putExtra("idUser", idUser);
//        intent.putExtra("idPedido", idPedido);
//        intent.putExtra("metodoEnvio", metodoEnvio);
//        //intent.putExtra("ListaArrayPedidoFinal", listaArrayPedidoFinal);
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



