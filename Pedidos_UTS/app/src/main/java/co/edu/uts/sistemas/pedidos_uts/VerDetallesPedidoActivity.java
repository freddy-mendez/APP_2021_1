package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.PedidoDAO;
import co.edu.uts.sistemas.pedidos_uts.model.Pedido;

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
        }
    }

    public void agregar(View view) {}

    public void volver(View view) {
        if (cambio) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}