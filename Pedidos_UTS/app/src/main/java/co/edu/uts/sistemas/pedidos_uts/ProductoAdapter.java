package co.edu.uts.sistemas.pedidos_uts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class ProductoAdapter extends BaseAdapter {

    private ArrayList<Producto> productos;
    private Context context;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.productos = productos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.vista_producto, null);
        }
        TextView lb_codigo = view.findViewById(R.id.lb_codigo);
        TextView lb_nombre = view.findViewById(R.id.lb_nombre);
        TextView lb_precio = view.findViewById(R.id.lb_precio);
        ImageView imagen = view.findViewById(R.id.imagen_lista);

        Producto producto = (Producto) getItem(i);
        //Producto producto = productos.get(i);
        lb_codigo.setText(producto.getCodigo());
        lb_nombre.setText(producto.getNombre());
        lb_precio.setText("$"+producto.getPrecio());

        Picasso.get().load(producto.getImagen()).resize(150,150).into(imagen);

        return view;
    }
}
