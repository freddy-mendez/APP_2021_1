package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.ClienteDAO;
import co.edu.uts.sistemas.pedidos_uts.bd.PedidoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Cliente;
import co.edu.uts.sistemas.pedidos_uts.model.Pedido;

public class CrearPedidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pedido);
        ClienteDAO clienteDAO=new ClienteDAO(new BaseDatos(this));
        Spinner spinner = findViewById(R.id.spCliente);
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1,
                clienteDAO.getClientes()
        );
        spinner.setAdapter(adapter);
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void guardar(View view) {
        EditText num = findViewById(R.id.txtNumeroPedido);
        EditText fecha = findViewById(R.id.txtFechaPedido);
        Spinner spinner = findViewById(R.id.spCliente);
        Cliente c = (Cliente) spinner.getSelectedItem();
        PedidoDAO pedidoDAO = new PedidoDAO(new BaseDatos(this));
        Pedido p = new Pedido(fecha.getText().toString(), c);
        p.setNumero(Integer.parseInt(num.getText().toString()));
        if (pedidoDAO.addPedido(p)) {
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Error al almacenar el pedido", Toast.LENGTH_SHORT).show();
        }
    }
}