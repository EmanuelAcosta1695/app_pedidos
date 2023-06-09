package com.example.app_pedidos.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pedidos.R;
import com.example.app_pedidos.VerProductPedidoCarritoActivity;
import com.example.app_pedidos.entidades.Pedido;

import java.util.ArrayList;



public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ProductViewHolder> {

    ArrayList<Pedido> listaCarritoPedidos;
    Integer idUser, idPedido;
    int idPosition;

    public CarritoAdapter(ArrayList<Pedido> listaCarritoPedidos, Integer idUser, Integer idPedido){
        this.listaCarritoPedidos = listaCarritoPedidos;
        this.idUser = idUser;
        this.idPedido = idPedido;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_carrito, null, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {


        holder.viewNombre.setText(listaCarritoPedidos.get(position).getItem_name());
        holder.viewCantidad.setText(String.valueOf(listaCarritoPedidos.get(position).getCantidad()));

        switch (listaCarritoPedidos.get(position).getFoto()){
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
//        idPosition =  position;
        return position;
    }

    @Override
    public int getItemViewType(int position) {
//        idPosition =  position;
        return position;
    }

    @Override
    public int getItemCount() {
        return listaCarritoPedidos.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombre, viewCantidad;
        ImageView foto;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewCantidad = itemView.findViewById(R.id.viewCantidad);
            foto = itemView.findViewById(R.id.item_foto);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerProductPedidoCarritoActivity.class);

                    intent.putExtra("idUser", idUser);
                    intent.putExtra("idPedido", idPedido);

                    intent.putExtra("nameProductCarrito", listaCarritoPedidos.get(getAdapterPosition()).getItem_name());

                    context.startActivity(intent);
                }
            });
        }
    }

}
