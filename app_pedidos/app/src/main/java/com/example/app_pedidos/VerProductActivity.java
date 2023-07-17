package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.db.DbProducts;
import com.example.app_pedidos.entidades.Pedido;
import com.example.app_pedidos.entidades.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VerProductActivity extends AppCompatActivity {

    TextView txtNombre, txtPrecio, aclaranioUnidad;
    EditText edtCantidad;
    ImageView item_foto;

    Button btnAñadir, btnVolver;

    Product product;
    int idProduct = 0;
    int idUser;
    int idPedido;
    String nombre;
    int cantidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        txtNombre = findViewById(R.id.viewNombre);
        txtPrecio = findViewById(R.id.viewPrecio);
        aclaranioUnidad = findViewById(R.id.aclaracionUnidad);
        item_foto = findViewById(R.id.item_foto); // MMMMMM
        edtCantidad = findViewById(R.id.edtCantidad);


        // recibo el id del producto
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                idProduct = extras.getInt("IdProduct");
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");

                System.out.println();
                System.out.println("en el if con la key");
                System.out.println("En el VerProduct");
                System.out.println("id del user: ");
                System.out.println(idUser);
                System.out.println();

            } else {
                idProduct = Integer.parseInt(null);
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);

                System.out.println();
                System.out.println("en el else con null");
                System.out.println("En el VerProduct");
                System.out.println("id del user: ");
                System.out.println(idUser);
                System.out.println();
            }
        } else {
            idProduct = (int) savedInstanceState.getSerializable("IdProduct");

            idUser = (int) savedInstanceState.getSerializable("idUser");

            idPedido = (int) savedInstanceState.getSerializable("idPedido");

            System.out.println();
            System.out.println("(int) savedInstanceState.getSerializable");
            System.out.println("En el VerProduct");
            System.out.println("id del user: ");
            System.out.println(idUser);
            System.out.println();
        }



        // muestro este producto particular
        DbProducts dbProducts = new DbProducts(VerProductActivity.this);
        product = dbProducts.verProduct(idProduct);

        System.out.println("el producto es: ");
        System.out.println(product);


        // llamo a dbPedidos para luego insertar el producto en el pedido
        DbPedidos dbPedidos = new DbPedidos(VerProductActivity.this);


        // Mensaje que le aclara al usuario a que corresponde el valor
        nombre = product.getNombre();
        System.out.println(nombre);


        txtNombre.setText(product.getNombre());


//        if (nombre.equals("Pan")){
//            txtPrecio.setText(String.valueOf(product.getPrecio())); //  + ". (Precio por 1 Kg)"
//            aclaranioUnidad.setText("1 = 1kg");
//        }

        // txtPrecio.setText(product.getPrecio() + ". (Precio por 1 kg)\nSegunda línea de texto");

        if (nombre.equals("Pan")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por 1 kg");
            aclaranioUnidad.setText("1 = 1kg");
        } else if (product.getNombre().equals("Factura")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por docena");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        } else if (product.getNombre().equals("Sandwich de Miga")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por docena");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        } else if (product.getNombre().equals("Bizcochos")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por paquete \n     de 250g");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        }


        // POR AHORA LO DEJO ASI
        // LUEGO CAMBIO LA FOTO SEGUN NOMBRE. EJ:
        // CASE "factura": item_foto.setImageResource(R.drawable.factura);
        switch (product.getFoto()){
            case 1:
                item_foto.setImageResource(R.drawable.pan);
                break;
            case 2:
                item_foto.setImageResource(R.drawable.facturas);
                break;
            case 3:
                item_foto.setImageResource(R.drawable.sandwichmiga);
                break;
            case 4:
                item_foto.setImageResource(R.drawable.bizcochos);
                break;
        }


        // recupero precio y nombre del producto
        int precio = product.getPrecio();
        String nombre = product.getNombre();


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



        // boton para añadir el producto al carrito
        btnAñadir = findViewById(R.id.btnAñadir);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // setea el EdiText de edtCantidad a solo numeros
//        edtCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                String cantidadString = edtCantidad.getText().toString();
                if (cantidadString.equals("") || cantidadString.equals(" ")) {
                    Toast.makeText(VerProductActivity.this, "Añade una cantidad o pulsa volver atras.", Toast.LENGTH_LONG).show();
                } else {
                    cantidad = Integer.parseInt(cantidadString);

                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Cantidad: " + cantidad);
                    System.out.println("Cantidad: " + cantidad);

                    // tengo que mostrar los que no estan update. ESTA EN QUERY
                    ArrayList<Pedido> listaCarritoPedidos = dbPedidos.mostrarPedidos(idUser);

                    System.out.println(" ");
                    System.out.println(" Carrito: ");
                    System.out.println(listaCarritoPedidos);
                    System.out.println(" listaCarritoPedidos.isEmpty() " + listaCarritoPedidos.isEmpty());
                    System.out.println(" ");


                    // Verifica si el producto ya está en el carrito
                    boolean encontrado = false;
                    for (Pedido item : listaCarritoPedidos) {
                        if (nombre.equals(item.getItem_name())) {
                            // El producto ya está en el carrito, actualiza la cantidad
                            encontrado = true;
                            dbPedidos.update_item_cantidad(item.getCantidad(), cantidad, nombre, item.getOrder_id());

                            Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("idUser", idUser);
                            startActivity(intent);

                            break;
                        }
                    }

                    // Si el producto no está en el carrito, agrega un nuevo pedido
                    if (!encontrado) {
                        long pedido = dbPedidos.insertarPedido(idUser, nombre, cantidad, precio, false, product.getFoto());
                        Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
                        intent.putExtra("idPedido", (int) pedido);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                    }

//                    // si el carrito no esta vacio
//                    if (!listaCarritoPedidos.isEmpty()) {
//
//                        System.out.println(" ");
//                        System.out.println(" ");
//                        System.out.println("CARRITO NO ESTA VACIO");
//                        System.out.println(" ");
//                        System.out.println(" ");
//
//                        // Itera buscando si el producto ya esta en el pedido
//                        for (Pedido item : listaCarritoPedidos){
//
//                            System.out.println(" ");
//                            System.out.println(" ");
//                            System.out.println("ITERANDO");
//                            System.out.println(" ");
//                            System.out.println(" ");
//
//                            // es el elemento unico q estoy añadiendo al carrito
//                            // si el producto ya esta en el carrito
//                            if (nombre.equals(item.getItem_name())){
//
//                                System.out.println(" ");
//                                System.out.println(" ");
//                                System.out.println(" ");
//                                System.out.println(" NOMBRE EQUAL ITEM EN CARRITO ");
//                                System.out.println(" ");
//                                System.out.println(" ");
//
//                                // TENGO QUE UPDATEAR EL ITEM
//                                dbPedidos.update_item_cantidad(item.getCantidad(), cantidad, nombre, item.getOrder_id());
//                                System.out.println("Pedido updatiado correctamente");
//                                System.out.println(" ");
//                                System.out.println(" ");
//
//
//                                // SI SE CUMPLE ESTO, SE VA POR ACA
//                                Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
//                                intent.putExtra("idPedido", idPedido);
//                                intent.putExtra("idUser", idUser);
//                                startActivity(intent);
//
//                                break;
//
//                            }
//
//                            // ¿¿¿¿¿Si lo meto aca adentro, cada vez q no se cumpla el if, inserta los datos????
//
//
//                            // SI TERMINA EL FOR, Y NO SE CUMPLIO EL IF, SE VA POR ACA
//                            System.out.println("producto no esta en el pedido");
//
//                            // idUser para order_id
//                            long pedido = dbPedidos.insertarPedido(idUser, nombre, cantidad, precio, false);
//                            System.out.println("pedido en VerProduct");
//                            System.out.println("pedido");
//                            System.out.println(pedido);
//                            System.out.println("");
//                            System.out.println("");
//
//                            // --------------------------------------------------------------------------------------
//                            Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
//
//                            // le envio el id del pedido. NO, PX NO LO NESECITO, EL DEL CLIENTE NESECITO
//                            intent.putExtra("idPedido", (int) pedido);
//                            System.out.println("pedido");
//                            System.out.println(pedido);
//                            System.out.println("");
//                            System.out.println("");
//
//
//                            intent.putExtra("idUser", idUser);
//                            System.out.println();
//                            System.out.println("En el VerProduct, en btnAñadir");
//                            System.out.println("id del user: ");
//                            System.out.println(idUser);
//                            System.out.println();
//
//                            startActivity(intent);
//                            // --------------------------------------------------------------------------------------
//
//                            // ACA LE PUEDO ENVIAR EL ID
//                            //productsActivity();
//                        }
//
//
//
//                    // Si el carrito esta directamente vacio
//                    } else {
//
//                        System.out.println(" ");
//                        System.out.println(" ");
//                        System.out.println(" ");
//                        System.out.println(" CARRITO ESTA VACIO ");
//                        System.out.println(" ");
//                        System.out.println(" ");
//
//                        // idUser para order_id
//                        long pedido = dbPedidos.insertarPedido(idUser, nombre, cantidad, precio, false);
//                        System.out.println("pedido en VerProduct");
//                        System.out.println("pedido");
//                        System.out.println(pedido);
//                        System.out.println("");
//                        System.out.println("");
//
//                        // --------------------------------------------------------------------------------------
//                        Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
//
//                        // le envio el id del pedido. NO, PX NO LO NESECITO, EL DEL CLIENTE NESECITO
//                        intent.putExtra("idPedido", (int) pedido);
//                        System.out.println("pedido");
//                        System.out.println(pedido);
//                        System.out.println("");
//                        System.out.println("");
//
//
//                        intent.putExtra("idUser", idUser);
//                        System.out.println();
//                        System.out.println("En el VerProduct, en btnAñadir");
//                        System.out.println("id del user: ");
//                        System.out.println(idUser);
//                        System.out.println();
//
//                        startActivity(intent);
//                        // --------------------------------------------------------------------------------------
//
//                        // ACA LE PUEDO ENVIAR EL ID
//                        //productsActivity();
//                    }



                }
            }
        });


        // para q no habra teclado
//            txtNombre.setInputType(InputType.TYPE_NULL);
//            txtApellido.setInputType(InputType.TYPE_NULL);
//            txtCorreo.setInputType(InputType.TYPE_NULL);
//            txtDireccion.setInputType(InputType.TYPE_NULL);
//            txtTelefono.setInputType(InputType.TYPE_NULL);
//            txtDni.setInputType(InputType.TYPE_NULL);

//        // Guarda datos del cliente en un .txt
//        saveCustomerData = findViewById(R.id.saveCustomerData);
//        saveCustomerData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(VerActivity.this);
//                builder.setMessage("Desea guardar los datos del cliente?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                String filename = "Datos del Cliente.txt";
//                                if (cliente != null) {
//
//                                    try {
//                                        OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));
//
//                                        archivo.write(cliente.getId()+ " \n");
//                                        archivo.write(cliente.getNombre()+ " \n");
//                                        archivo.write(cliente.getApellido()+ " \n");
//                                        archivo.write(cliente.getEmail()+ " \n");
//                                        archivo.write(cliente.getDireccion()+ " \n");
//                                        archivo.write(cliente.getTelefono()+ " \n");
//                                        archivo.write(cliente.getDni()+ " \n");
//                                        archivo.write(" \n");
//
//                                        archivo.flush();
//                                        archivo.close();
//                                        Toast.makeText(VerActivity.this, "Datos del cliente guardados exitosamente.", Toast.LENGTH_LONG).show();
//
//                                    } catch (IOException e) {
//                                        Toast.makeText(VerActivity.this, "No se pudo guardar los datos del Cliente.", Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            }
//                            })
//                            .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick (DialogInterface dialogInterface,int i){
//
//                                }
//                            }).show();
//                        }
//
//                });


    }

//    private void lista(){
//        Intent intent = new Intent(this,CarritoActivity.class);
//        startActivity(intent);
//    }

//    private void productsActivity(){
//        Intent intent = new Intent(this, ProductsActivity.class);
//
//        intent.putExtra("ID", id); // NO PX LE VA A ENVIAR EL ID DE LA CLASE, NO DEL PEDIDO
//
//        startActivity(intent);
//
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

