package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.ProductoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class MenuBDActivity extends AppCompatActivity {
    private ProductoDAO productoDAO;
    private boolean editar=false;
    private boolean eliminar=false;
    private Producto p=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_b_d);
        BaseDatos baseDatos=new BaseDatos(getApplicationContext());
        productoDAO = new ProductoDAO(baseDatos);
        /*Producto producto = new Producto("PROD001","Computador Escritorio", 1800000);
        productoDAO.addProducto(producto);
        producto = new Producto("PROD002","Portatil", 2500000);
        productoDAO.addProducto(producto);
        producto = new Producto("PROD003","Tableta", 1200000);
        productoDAO.addProducto(producto);*/
        actualizarLista(0);
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
                actualizarLista(0);
            } else {
                Toast.makeText(this, "Error al almacenar el Producto", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId()==R.id.button10) {
            LinearLayout linear = findViewById(R.id.linear_form);
            if (!editar) {
                linear.setVisibility(View.VISIBLE);
                EditText codigo = findViewById(R.id.txtCod);
                EditText nombre = findViewById(R.id.txtNom);
                EditText precio = findViewById(R.id.txtPrec);
                EditText imagen = findViewById(R.id.txtImg);
                ListView lista = findViewById(R.id.lista_productos_bd);
                p=null;
                //Toast.makeText(this, ""+lista.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
                if (lista.getCheckedItemPosition()>=0) {
                    p = (Producto) lista.getItemAtPosition(lista.getCheckedItemPosition());
                    codigo.setText(p.getCodigo());
                    nombre.setText(p.getNombre());
                    precio.setText(""+p.getPrecio());
                    imagen.setText(p.getImagen());
                    editar=true;
                    Button btn = findViewById(R.id.button10);
                    btn.setText("Actualizar");
                } else {
                    codigo.setText("");
                    nombre.setText("");
                    precio.setText("");
                    imagen.setText(Informacion.URL_IMG);
                    linear.setVisibility(View.GONE);
                }
            } else {
                EditText codigo = findViewById(R.id.txtCod);
                EditText nombre = findViewById(R.id.txtNom);
                EditText precio = findViewById(R.id.txtPrec);
                EditText imagen = findViewById(R.id.txtImg);
                String txtCod = codigo.getText().toString();
                String txtNom = nombre.getText().toString();
                String txtPrec = precio.getText().toString();
                String txtImg = imagen.getText().toString();
                p.setCodigo(txtCod);
                p.setNombre(txtNom);
                p.setPrecio(Integer.parseInt(txtPrec));
                p.setImagen(txtImg);
                //Toast.makeText(this, "Update", Toast.LENGTH_SHORT).show();
                productoDAO.updateProducto(p);
                p=null;
                editar=false;
                Button btn = findViewById(R.id.button10);
                btn.setText("EDITAR");
                linear.setVisibility(View.GONE);
            }
        } else if (view.getId()==R.id.button11) {
            //Toast.makeText(this, "Opcion no implementada", Toast.LENGTH_SHORT).show();
            if (!eliminar) {
                actualizarLista(1);
                Button btn = findViewById(R.id.button11);
                btn.setText("BORRAR");
                eliminar=true;
            } else {
                ListView lista = findViewById(R.id.lista_productos_bd);
                SparseBooleanArray valores = lista.getCheckedItemPositions();
                for (int i=0; i<valores.size(); i++) {
                    int pos = valores.keyAt(i);
                    if (valores.get(pos)) {
                        p = (Producto) lista.getAdapter().getItem(pos);
                        productoDAO.deleteProducto(p);
                        p=null;
                    }
                }
                actualizarLista(0);
                Button btn = findViewById(R.id.button11);
                btn.setText("ELIMINAR");
                eliminar=false;
            }
        }
    }

    private void actualizarLista(int vista) {
        ArrayList<Producto> productos = productoDAO.getProductos();
        if (vista==0) {
            ArrayAdapter<Producto> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_single_choice,
                    productos);
            ListView lista = findViewById(R.id.lista_productos_bd);
            lista.setAdapter(adapter);
            lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        } else {
            ArrayAdapter<Producto> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_multiple_choice,
                    productos);
            ListView lista = findViewById(R.id.lista_productos_bd);
            lista.setAdapter(adapter);
            lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }

    }
}