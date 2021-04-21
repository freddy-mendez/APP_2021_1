package co.edu.uts.sistemas.pedidos_uts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        int edad = getIntent().getIntExtra("edad", -1);
        if (nombre!=null && apellido!=null && edad!=-1) {
            TextView lbSaludo = findViewById(R.id.textView);
            lbSaludo.append("\n"+nombre+" "+apellido+"\n"+edad);
        }
        if (Informacion.productos.size()==0) {
            //TextView lbProducto = findViewById(R.id.txtProducto);
            //lbProducto.setText(Informacion.producto.toString());
            Informacion.cargarProductos();
            mostrarCantidadProductos();
        }
    }

    private void mostrarCantidadProductos() {
        Button btn3 = findViewById(R.id.button3);
        int cantidad = Informacion.productos.size();
        btn3.setText("Mostrar Productos ("+cantidad+")");
    }

    public void ejecutar(View view) {
        if (view.getId()==R.id.btnSalirMenu) {
            finish();
        } else if (view.getId()==R.id.btnDatos) {
            // Llamar a la actividad ProductoActivity
            Intent intent = new Intent(MenuActivity.this, ProductoActivity.class);
            //startActivity(intent);
            // code 100 Agregar    btnDatos
            // code 130 Listar     button3
            startActivityForResult(intent, 100);
        } else if (view.getId()==R.id.button3) {
            Intent intent = new Intent(MenuActivity.this, ListarProductosActivity.class);
            startActivityForResult(intent, 130);
        }  else if (view.getId()==R.id.btnBD) {
            Intent intent = new Intent(MenuActivity.this, MenuBDActivity.class);
            startActivity(intent);
        } else if (view.getId()==R.id.btnBD2) {
            Intent intent = new Intent(MenuActivity.this, MenuBD_Activity.class);
            startActivity(intent);
        } else if (view.getId()==R.id.btnPedido) {
            Intent intent = new Intent(MenuActivity.this, ListarPedidosActivity.class);
            startActivity(intent);
        } else if (view.getId()==R.id.button12) {
            Intent intent = new Intent(MenuActivity.this, ListaRecyclerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 || requestCode==130) {
            if (resultCode==RESULT_OK && data!=null) {
                /*String nombre = data.getStringExtra("nombre");
                String apellido = data.getStringExtra("apellido");
                int edad = data.getIntExtra("edad", -1);
                if (nombre!=null && apellido!=null && edad!=-1) {
                    TextView lbSaludo = findViewById(R.id.textView);
                    lbSaludo.setText("Bienvenido\n"+nombre+" "+apellido+"\n"+edad);
                }*/

            } else if (resultCode==RESULT_OK) {
                /*if (Informacion.producto!=null) {
                    TextView lbProducto = findViewById(R.id.txtProducto);
                    lbProducto.setText(Informacion.producto.toString());
                }*/
                mostrarCantidadProductos();
            }
        }
    }

    /*
    ProductoActivity debe contener 3 EdiText para solicitar la siguiente información:
    * nombre
    * apellido
    * edad
    Ademas debe contener un boton llamado enviar que captura los datos de los
    EditText, los agrega a un intent y hace el llamado a MenuActivity
    enviandole el intent en la petición
     */

}