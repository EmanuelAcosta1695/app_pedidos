package com.example.app_pedidos.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pedidos.R;
import com.example.app_pedidos.VerProductActivity;
import com.example.app_pedidos.entidades.Product;

import java.util.ArrayList;

// TENGO QUE CAMBIAR ESTO PARA QUE MUESTRE LOS PRODUCTOS QUE GUARDE EN UN BD
// voy a nesecitar o un admin que cree esa bd
// o crear esa bd a manopla y q la app la recoja

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> listaProducts;
    Integer idUser, idPedido;

    public ProductAdapter(ArrayList<Product> listaProducts, Integer idUser, Integer idPedido){
        this.listaProducts = listaProducts;
        this.idUser = idUser;
        this.idPedido = idPedido;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_product, null, false);

        return new ProductViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        System.out.println(listaProducts.get(position).getNombre());
        if (listaProducts.get(position).getNombre().equals("Pan") || listaProducts.get(position).getNombre().equals("Bizcochos")) {
            holder.viewNombre.setText(listaProducts.get(position).getNombre());
            holder.viewPrecio.setText("$" + listaProducts.get(position).getPrecio() + " (/Kg)"); // MMM
        } else {
            holder.viewNombre.setText(listaProducts.get(position).getNombre());
            holder.viewPrecio.setText("$" + listaProducts.get(position).getPrecio() + " (/Doc)"); // MMM
        }

        //holder.viewCorreo.setText(listaProducts.get(position).getFoto());

        // setea foto en base a valor de la propiedad avatar en cada objeto cliente
        switch (listaProducts.get(position).getFoto()){
            case 1:
                holder.foto.setImageResource(R.drawable.pan);
                break;
            case 2:
                holder.foto.setImageResource(R.drawable.facturas);
                break;
            case 3:
                holder.foto.setImageResource(R.drawable.sandwichmiga);
                break;
            case 4:
                holder.foto.setImageResource(R.drawable.bizcochos);
                break;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listaProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewPrecio;
        ImageView foto;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewPrecio = itemView.findViewById(R.id.viewPrecio);
            foto = itemView.findViewById(R.id.item_foto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerProductActivity.class);

                    intent.putExtra("idUser", idUser);
                    intent.putExtra("idPedido", idPedido);
                    intent.putExtra("IdProduct", listaProducts.get(getAdapterPosition()).getId());

                    context.startActivity(intent);
                }
            });
        }
    }
}
