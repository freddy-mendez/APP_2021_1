package co.edu.uts.sistemas.pedidos_uts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.ProductoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class MenuBD_Activity extends AppCompatActivity {
    private ProductoDAO productoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_b_d_1);
        BaseDatos baseDatos=new BaseDatos(getApplicationContext());
        productoDAO = new ProductoDAO(baseDatos);
        /*Producto producto = new Producto("PROD001","Computador Escritorio", 1800000);
        productoDAO.addProducto(producto);
        producto = new Producto("PROD002","Portatil", 2500000);
        productoDAO.addProducto(producto);
        producto = new Producto("PROD003","Tableta", 1200000);
        productoDAO.addProducto(producto);*/
        actualizarLista();
    }

    public void ejecutar(View view) {
        if (view.getId()==R.id.button8) {
            EditText codigo = findViewById(R.id.txtCod);
            EditText nombre = findViewById(R.id.txtNom);
            EditText precio = findViewById(R.id.txtPrec);
            EditText imagen = findViewById(R.id.txtImg);
            codigo.setText("");
            nombre.setText("");
            precio.setText("");
            imagen.setText(Informacion.URL_IMG);
            LinearLayout linear = findViewById(R.id.linear_form);
            linear.setVisibility(View.VISIBLE);
            Button guardar = findViewById(R.id.button9);
            guardar.setVisibility(Button.VISIBLE);
            view.setVisibility(View.GONE);
        } else if (view.getId()==R.id.button9) {
            EditText codigo = findViewById(R.id.txtCod);
            EditText nombre = findViewById(R.id.txtNom);
            EditText precio = findViewById(R.id.txtPrec);
            EditText imagen = findViewById(R.id.txtImg);
            String txtCod = codigo.getText().toString();
            String txtNom = nombre.getText().toString();
            String txtPrec = precio.getText().toString();
            String txtImg = imagen.getText().toString();
            Producto p = new Producto(0, txtCod, txtNom, Integer.parseInt(txtPrec), txtImg);
            if (productoDAO.addProducto(p)) {
                codigo.setText("");
                nombre.setText("");
                precio.setText("");
                imagen.setText(Informacion.URL_IMG);
                Toast.makeText(this, "Producto Almacenado", Toast.LENGTH_SHORT).show();
                view.setVisibility(View.GONE);
                Button nuevo = findViewById(R.id.button8);
                nuevo.setVisibility(Button.VISIBLE);
                LinearLayout linear = findViewById(R.id.linear_form);
                linear.setVisibility(View.GONE);
                actualizarLista();
            } else {
                Toast.makeText(this, "Error al almacenar el Producto", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId()==R.id.button10) {
            Toast.makeText(this, "Opcion no implementada", Toast.LENGTH_SHORT).show();
        } else if (view.getId()==R.id.button11) {
            Toast.makeText(this, "Opcion no implementada", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarLista() {
        ArrayList<Producto> productos = productoDAO.getProductos();

        TableLayout stk = findViewById(R.id.tabla);

        stk.removeAllViews();

        TableRow tbrow0 = new TableRow(this);
        //tbrow0.setBackground();
        //tbrow0.setBackgroundResource(R.drawable.border);
        TextView tv0 = new TextView(this);
        tv0.setText(" No. ");
        tv0.setTextColor(Color.BLACK);
        tv0.setBackgroundResource(R.drawable.border);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Codigo ");
        tv1.setTextColor(Color.BLACK);
        tv1.setBackgroundResource(R.drawable.border);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Nombre ");
        tv2.setTextColor(Color.BLACK);
        tv2.setBackgroundResource(R.drawable.border);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Precio ");
        tv3.setTextColor(Color.BLACK);
        tv3.setBackgroundResource(R.drawable.border);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" Imagen ");
        tv4.setTextColor(Color.BLACK);
        tv4.setBackgroundResource(R.drawable.border);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(p.getCodigo());
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(p.getNombre());
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + p.getPrecio());
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            ImageView img = new ImageView(this);
            Picasso.get().load(p.getImagen()).resize(100,100).into(img);
            tbrow.addView(img);
            stk.addView(tbrow);
        }

    }
}