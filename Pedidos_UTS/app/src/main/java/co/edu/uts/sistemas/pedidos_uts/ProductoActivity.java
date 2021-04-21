package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class ProductoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void enviar(View view) {
        EditText cod = findViewById(R.id.txtCodigo);
        EditText nom = findViewById(R.id.txtNombre);
        EditText precio = findViewById(R.id.txtPrecio);

        String txtCodigo = cod.getText().toString();
        String txtNombre = nom.getText().toString();
        String txtPrecio = precio.getText().toString();

        if (txtNombre.length()>0 && txtCodigo.length()>0 && txtPrecio.length()>0
           && !Informacion.existeProducto(txtCodigo)) {
            int precio_ = Integer.parseInt(txtPrecio);

            Informacion.productos.add(new Producto(txtCodigo, txtNombre, precio_));
            setResult(RESULT_OK);
            finish();
            //startActivity(intent);
        } else {
            Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
        }
    }
}