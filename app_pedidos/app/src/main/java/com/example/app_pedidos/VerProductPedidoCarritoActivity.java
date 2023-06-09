package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.db.DbProducts;
import com.example.app_pedidos.entidades.Pedido;
import com.example.app_pedidos.entidades.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VerProductPedidoCarritoActivity extends AppCompatActivity {

    TextView txtNombre, txtPrecio, aclaranioUnidad, txtCantidad;
    ImageView item_foto;

    Button btnEliminar, btnVolver;
    FloatingActionButton fabAñadir;

    Product product;
    int idProductCarrito;
    int idUser;
    int idPedido;
    String nameProductCarrito;

    String nombre;

    Pedido pedido;
    ArrayList<Pedido> pedidos;


    FloatingActionButton fabEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_product_carrito);

        txtNombre = findViewById(R.id.viewNombre);
        txtPrecio = findViewById(R.id.viewPrecio);
        aclaranioUnidad = findViewById(R.id.aclaracionUnidad);
        item_foto = findViewById(R.id.item_foto); // MMMMMM
        txtCantidad = findViewById(R.id.viewCantidad);


        // recibo el id del producto
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){

                //idProductCarrito = extras.getInt("idProductCarrito");
                nameProductCarrito = extras.getString("nameProductCarrito");

                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");

                System.out.println();
                System.out.println("en el if con la key");
                System.out.println("En el VerProduct");
                System.out.println("id del user: ");
                System.out.println(idUser);
                System.out.println();

            } else {
                //idProductCarrito = Integer.parseInt(null);
                nameProductCarrito = String.valueOf(Integer.parseInt(null));

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
            //idProductCarrito = (int) savedInstanceState.getSerializable("idProductCarrito");
            nameProductCarrito = savedInstanceState.getString("nameProductCarrito");

            idUser = (int) savedInstanceState.getInt("idUser");

            idPedido = (int) savedInstanceState.getInt("idPedido");

        }


        DbPedidos dbPedidos = new DbPedidos(VerProductPedidoCarritoActivity.this);
        pedido = dbPedidos.verPedido(nameProductCarrito);

        nombre = pedido.getItem_name();


        if (pedido != null) {

            txtNombre.setText(pedido.getItem_name());


            if (nombre.equals("Pan")) {
                txtPrecio.setText(product.getPrecio() + ". (Precio por 1 kg)");
                aclaranioUnidad.setText("1 = 1kg");
            } else if (product.getNombre().equals("Factura")) {
                txtPrecio.setText(product.getPrecio() + ". (Precio por paquete de 250g)");
                aclaranioUnidad.setText("1 = Un paquete de 250");
            } else if (product.getNombre().equals("Sandwich de Miga")) {
                txtPrecio.setText(product.getPrecio() + ". (Precio por docena)");
                aclaranioUnidad.setText("1 = Un paquete de 250");
            } else if (product.getNombre().equals("Bizcochos")) {
                txtPrecio.setText(product.getPrecio() + ". (Precio por paquete de 250g)");
                aclaranioUnidad.setText("1 = Un paquete de 250");
            }


            switch (1){
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


            txtCantidad.setText(String.valueOf(pedido.getCantidad()));

            txtCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);

            Integer cantidad = txtCantidad.getInputType();

            btnEliminar = findViewById(R.id.btnEliminar);
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(dbPedidos.eliminarPedido(pedido.getItem_name(), idUser)) {
                        carrito();
                    }
                }
            });

        } else {

            Log.d("Error!","Pedido es null !!!");

        }



        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(getApplicationContext(), CarritoActivity.class);
                volver.putExtra("idUser", idUser);
                volver.putExtra("idPedido", idPedido);
                startActivity(volver);
            }
        });


    }

    private void carrito(){
        DbPedidos dbPedidos = new DbPedidos(this);
        pedidos = dbPedidos.mostrarPedidos(idUser);


        if (pedidos.isEmpty()){
            Intent intent = new Intent(this,ProductsActivity.class);
            intent.putExtra("idUser", idUser);
            intent.putExtra("idPedido", idPedido);
            startActivity(intent);

        } else {
            Intent intent = new Intent(this,CarritoActivity.class);
            intent.putExtra("idUser", idUser);
            intent.putExtra("idPedido", idPedido);
            startActivity(intent);
        }
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