package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
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
        item_foto = findViewById(R.id.item_foto);
        edtCantidad = findViewById(R.id.edtCantidad);


        // recibo el id del producto
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                idProduct = extras.getInt("idProduct");
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");

            } else {
                idProduct = Integer.parseInt(null);
                idUser = Integer.parseInt(null);
                idPedido = Integer.parseInt(null);

            }
        } else {
            idProduct = savedInstanceState.getInt("idProduct");

            idUser = savedInstanceState.getInt("idUser");

            idPedido = savedInstanceState.getInt("idPedido");

        }



        DbProducts dbProducts = new DbProducts(VerProductActivity.this);
        product = dbProducts.verProduct(idProduct);


        DbPedidos dbPedidos = new DbPedidos(VerProductActivity.this);


        nombre = product.getNombre();
        System.out.println(nombre);


        txtNombre.setText(product.getNombre());



        if (nombre.equals("Pan")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por 1 kg");
            aclaranioUnidad.setText("1 = 1kg");
        } else if (product.getNombre().equals("Factura")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por paquete de 250g");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        } else if (product.getNombre().equals("Sandwich de Miga")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por docena");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        } else if (product.getNombre().equals("Bizcochos")) {
            txtPrecio.setText("$" + product.getPrecio() + "\n Precio por paquete de 250g");
            aclaranioUnidad.setText("1 = Un paquete de 250");
        }


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



        btnAñadir = findViewById(R.id.btnAñadir);
        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cantidadString = edtCantidad.getText().toString();

                if (cantidadString.equals("") || cantidadString.equals(" ")) {

                    Toast.makeText(VerProductActivity.this, "Añade una cantidad o pulsa volver atras.", Toast.LENGTH_LONG).show();

                } else {

                    cantidad = Integer.parseInt(cantidadString);

                    ArrayList<Pedido> listaCarritoPedidos = dbPedidos.mostrarPedidos(idUser);

                    boolean encontrado = false;

                    for (Pedido item : listaCarritoPedidos) {
                        if (nombre.equals(item.getItem_name())) {

                            encontrado = true;
                            dbPedidos.update_item_cantidad(item.getCantidad(), cantidad, nombre, item.getOrder_id());

                            Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
                            intent.putExtra("idPedido", idPedido);
                            intent.putExtra("idUser", idUser);
                            startActivity(intent);

                            break;
                        }
                    }


                    if (!encontrado) {
                        long pedido = dbPedidos.insertarPedido(idUser, nombre, cantidad, precio, false, product.getFoto());
                        Intent intent = new Intent(VerProductActivity.this, ProductsActivity.class);
                        intent.putExtra("idPedido", (int) pedido);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                    }

                }
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

