package co.edu.uts.sistemas.pedidos_uts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class VerProductoActivity extends AppCompatActivity {
    private int posicion;
    private Producto producto;
    private Uri img_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        posicion=getIntent().getIntExtra("posicion", -1);
        producto=Informacion.productos.get(posicion);
        verInformacionProducto();

        if (checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED ||
           checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 4000);
        }

        ImageView imagen = findViewById(R.id.imagen_producto);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(VerProductoActivity.this, "Abrir Imagen", Toast.LENGTH_SHORT).show();
                ContentValues valores = new ContentValues();
                valores.put(MediaStore.Images.Media.TITLE, "IMG_PROD_"+producto.getCodigo());
                valores.put(MediaStore.Images.Media.DESCRIPTION,
                        "Imagen del producto "+producto.getNombre());
                img_uri=getContentResolver().
                        insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, valores);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri);
                startActivityForResult(intent, 3000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3000) {
            ImageView imagen = findViewById(R.id.imagen_producto);
            imagen.setImageURI(img_uri);
        }
    }

    private void verInformacionProducto() {
        EditText cod = findViewById(R.id.txtCodigo);
        EditText nom = findViewById(R.id.txtNombre);
        EditText precio = findViewById(R.id.txtPrecio);
        ImageView imagen = findViewById(R.id.imagen_producto);

        cod.setText(producto.getCodigo());
        nom.setText(producto.getNombre());
        precio.setText(""+producto.getPrecio());

        Picasso.get().load(producto.getImagen()).resize(300,300).into(imagen);
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void eliminar(View view) {
        Informacion.productos.remove(posicion);
        setResult(RESULT_OK);
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
           && !Informacion.existeProducto(txtCodigo, posicion)) {
            int precio_ = Integer.parseInt(txtPrecio);

            producto.setCodigo(txtCodigo);
            producto.setNombre(txtNombre);
            producto.setPrecio(precio_);
            producto.setImagen(img_uri.toString());
            Log.i("IMG_URI:",img_uri.toString());

            Informacion.productos.set(posicion, producto);

            setResult(RESULT_OK);
            finish();
            //startActivity(intent);
        } else {
            Toast.makeText(this, "Todos los datos son obligatorios", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
        }
    }
}