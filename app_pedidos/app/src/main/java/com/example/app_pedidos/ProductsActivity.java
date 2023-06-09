package com.example.app_pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pedidos.adaptadores.ProductAdapter;
import com.example.app_pedidos.db.DbPedidos;
import com.example.app_pedidos.db.DbProducts;
import com.example.app_pedidos.entidades.Pedido;
import com.example.app_pedidos.entidades.Product;

import java.util.ArrayList;


public class ProductsActivity extends AppCompatActivity  {

    RecyclerView listaProducts;
    ArrayList<Product> listaArrayProducts;
    private Button btnCarrito, btnVolver;
    ArrayList<Pedido> listaArrayCarritoProducts;

    Integer idUser;
    int idPedido;
    String metodoEnvio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listaProducts = findViewById(R.id.listaProducts);

        listaProducts.setLayoutManager(new LinearLayoutManager(this));

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();

            if(extras != null){
                idUser = extras.getInt("idUser");
                idPedido = extras.getInt("idPedido");
            } else {
                try {
                    idUser = Integer.parseInt(null);
                    idPedido = Integer.parseInt(null);
                } catch (Exception ex) {
                    ex.toString();
                } finally {

                }
            }
        } else {
            idUser = (int) savedInstanceState.getSerializable("idUser");

            idPedido = (int) savedInstanceState.getSerializable("idPedido");

        }


        DbProducts dbProducts = new DbProducts(ProductsActivity.this);
        DbPedidos dbPedidos = new DbPedidos(ProductsActivity.this);


        listaArrayProducts = new ArrayList<>();

        ProductAdapter adapterCliente = new ProductAdapter(dbProducts.mostrarProducts(), idUser, idPedido);
        listaProducts.setAdapter(adapterCliente);



        btnCarrito = findViewById(R.id.btnCarrito);
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listaArrayCarritoProducts = dbPedidos.mostrarPedidos(idUser);

                if (!listaArrayCarritoProducts.isEmpty()) {

                    Intent intent = new Intent(getApplicationContext(), CarritoActivity.class);

                    intent.putExtra("idPedido", idPedido);
                    intent.putExtra("idUser", idUser);

                    intent.putExtra("metodoEnvio", metodoEnvio);

                    startActivity(intent);

                } else {

                    Toast.makeText(ProductsActivity.this, "Aún no sumaste productos a tu carrito.", Toast.LENGTH_LONG).show();
                }

            }
        });


        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver = new Intent(getApplicationContext(), MainActivity.class);
                volver.putExtra("idUser", idUser);
                startActivity(volver);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // hide the back button
        return true;

    }

    @Override
    public void onBackPressed (){

    }

}
