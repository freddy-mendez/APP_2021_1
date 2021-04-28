package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.PedidoDAO;
import co.edu.uts.sistemas.pedidos_uts.bd.ProductoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class AgregarProductoActivity extends AppCompatActivity {
    private PedidoDAO pedidoDAO;
    private ProductoDAO productoDAO;
    private int id_pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        id_pedido=getIntent().getIntExtra("id_pedido", -1);
        BaseDatos db = new BaseDatos(getApplicationContext());
        productoDAO=new ProductoDAO(db);
        pedidoDAO=new PedidoDAO(db);
        cargarDatos();
        Spinner spProductos = findViewById(R.id.spProductos);
        spProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EditText precio = findViewById(R.id.txtPrecioProducto);
                Producto p= (Producto) spProductos.getItemAtPosition(i);
                precio.setText(""+p.getPrecio());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void cargarDatos() {
        ArrayList<Producto> data = pedidoDAO.getProductosNoPedido(id_pedido);
        if (data.size()>0) {
            Spinner spProductos = findViewById(R.id.spProductos);
            ArrayAdapter<Producto> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    data);
            spProductos.setAdapter(adapter);
            EditText precio = findViewById(R.id.txtPrecioProducto);
            Producto p = data.get(0);
            precio.setText(""+p.getPrecio());
        } else {
            Button btnGuadar = findViewById(R.id.btnGuardarProducto);
            btnGuadar.setVisibility(View.GONE);
        }
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void guardar(View view) {
        Spinner spProductos = findViewById(R.id.spProductos);
        EditText precio = findViewById(R.id.txtPrecioProducto);
        EditText cantidad = findViewById(R.id.txtCantidad);
        int valorCantidad = Integer.parseInt(cantidad.getText().toString());
        pedidoDAO.addProductoPedido(id_pedido,
                ((Producto) spProductos.getSelectedItem()).get_id(),
                valorCantidad,
                Integer.parseInt(precio.getText().toString())*valorCantidad);
        setResult(RESULT_OK);
        finish();
    }

}