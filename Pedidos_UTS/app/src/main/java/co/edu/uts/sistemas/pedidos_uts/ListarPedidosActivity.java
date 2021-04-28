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
import android.widget.ListView;
import android.widget.Toast;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.ClienteDAO;
import co.edu.uts.sistemas.pedidos_uts.bd.PedidoDAO;
import co.edu.uts.sistemas.pedidos_uts.bd.ProductoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Cliente;
import co.edu.uts.sistemas.pedidos_uts.model.Pedido;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class ListarPedidosActivity extends AppCompatActivity {
    private PedidoDAO pedidoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);
        pedidoDAO=new PedidoDAO(new BaseDatos(this));
        ClienteDAO clienteDAO = new ClienteDAO(new BaseDatos(this));
        if (clienteDAO.getClientes().size()==0) {
            Cliente c = new Cliente("Freddy Mendez","Calle 32","fredymo",12345);
            clienteDAO.addCliente(c);
            c = new Cliente("Freddy ortiz","Calle 23","fredyOr",23456);
            clienteDAO.addCliente(c);
        }
        ListView lista = findViewById(R.id.lista_pedidos);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListView lista = findViewById(R.id.lista_pedidos);
                Pedido p = (Pedido) lista.getAdapter().getItem(i);
                Intent intent = new Intent(ListarPedidosActivity.this, VerDetallesPedidoActivity.class);
                intent.putExtra("id", p.get_id());
                startActivityForResult(intent, 2100);
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder msg = new AlertDialog.Builder(ListarPedidosActivity.this);
                msg.setTitle("Eliminar Pedido");
                msg.setMessage("Esta seguro que desea eliminar el pedido?");
                msg.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListView lista = findViewById(R.id.lista_pedidos);
                        Pedido pedido = (Pedido) lista.getItemAtPosition(pos);
                        pedidoDAO.deletePedido(pedido.get_id());
                        actualizarLista();
                    }
                });
                msg.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                msg.show();


                return true;
            }
        });

        actualizarLista();
    }

    public void volver(View view) {
        finish();
    }

    public void agregar(View view) {
        Intent intent = new Intent(ListarPedidosActivity.this, CrearPedidoActivity.class);
        startActivityForResult(intent, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000 || requestCode==2100) {
            if (resultCode==RESULT_OK) {
                actualizarLista();
            }
        }
    }

    private void actualizarLista() {
        ArrayAdapter<Pedido> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                pedidoDAO.getPedidos());
        ListView lista = findViewById(R.id.lista_pedidos);
        lista.setAdapter(adapter);
    }
}