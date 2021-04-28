package co.edu.uts.sistemas.pedidos_uts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.PedidoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Pedido;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class VerDetallesPedidoActivity extends AppCompatActivity {
    private Pedido p;
    private PedidoDAO pedidoDAO;
    private boolean cambio=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalles_pedido);
        BaseDatos db = new BaseDatos(this);
        pedidoDAO=new PedidoDAO(db);
        int id = getIntent().getIntExtra("id",-1);
        if (id!=-1) {
            p=pedidoDAO.getPedido(id);
            mostrarDatos();
        }
        ListView lista = findViewById(R.id.lista_detalle_productos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos=i;
                AlertDialog.Builder msg = new AlertDialog.Builder(VerDetallesPedidoActivity.this);
                msg.setTitle("Eliminar Producto");
                msg.setMessage("Esta seguro que desea eliminar el producto del pedido?");
                msg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView lista = findViewById(R.id.lista_detalle_productos);
                        Producto producto = (Producto) lista.getItemAtPosition(pos);
                        pedidoDAO.deleteProductoPedido(p.get_id(), producto.get_id());
                        p=pedidoDAO.getPedido(p.get_id());
                        mostrarDatos();
                    }
                });
                msg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                msg.show();
            }
        });
    }

    private void mostrarDatos() {
        if (p!=null) {
            EditText num = findViewById(R.id.txtDetallesNumero);
            EditText fecha = findViewById(R.id.txtDetallesFecha);
            EditText cliente = findViewById(R.id.txtDetallesCliente);
            EditText valor = findViewById(R.id.txtDetallesValor);
            num.setText(""+p.getNumero());
            fecha.setText(p.getFecha());
            cliente.setText(p.getCliente().getName());
            valor.setText(""+p.getValor());
            actualizarLista();
        }
    }

    public void agregar(View view) {
        Intent intent=new Intent(VerDetallesPedidoActivity.this, AgregarProductoActivity.class);
        intent.putExtra("id_pedido", p.get_id());
        startActivityForResult(intent, 2300);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2300) {
            if (resultCode==RESULT_OK) {
                cambio=true;
                p=pedidoDAO.getPedido(p.get_id());
                mostrarDatos();
            }
        }
    }

    public void actualizarLista() {
        ArrayAdapter<Producto> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                p.getProductos());
        ListView lista = findViewById(R.id.lista_detalle_productos);
        lista.setAdapter(adapter);
    }

    public void volver(View view) {
        if (cambio) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}