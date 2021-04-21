package co.edu.uts.sistemas.pedidos_uts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class ProductoRVAdapter extends RecyclerView.Adapter<ProductoRVAdapter.ItemHolder> {

    ArrayList<Producto> datos;

    public ProductoRVAdapter(ArrayList<Producto> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Producto p = datos.get(position);
        holder.lbCod.setText(p.getCodigo());
        holder.lbNom.setText(p.getNombre());
        holder.lbPrec.setText(""+p.getPrecio());
        Picasso.get().load(p.getImagen()).resize(150,150).into(holder.img_prod);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        CardView card_item;
        TextView lbCod;
        TextView lbNom;
        TextView lbPrec;
        ImageView img_prod;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            card_item=itemView.findViewById(R.id.card_item);
            lbCod=itemView.findViewById(R.id.lbCod);
            lbNom=itemView.findViewById(R.id.lbNom);
            lbPrec=itemView.findViewById(R.id.lbPrec);
            img_prod=itemView.findViewById(R.id.img_prod);
        }
    }
}
