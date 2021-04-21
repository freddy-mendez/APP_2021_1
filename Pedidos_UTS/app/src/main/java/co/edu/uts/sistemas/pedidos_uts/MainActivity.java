package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.i("Estados", "Metodo OnCreate");
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        Log.i("Estados", "Metodo OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Estados", "Metodo OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Estados", "Metodo OnPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Estados", "Metodo OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Estados", "Metodo OnDestroy");
    }*/

    public void salir(View view) {
        finish();
    }

    public void ingresar(View view) {
        EditText user = findViewById(R.id.txtUser);
        EditText clave = findViewById(R.id.txtClave);

        String txtUser = user.getText().toString();
        String txtClave = clave.getText().toString();

        if (Informacion.user.equals(txtUser) && Informacion.passws.equals(txtClave)) {
            Toast.makeText(this, "Usuario/Contraseña validos!!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("nombre", "Freddy");
            intent.putExtra("apellido", "Mendez");
            intent.putExtra("edad", 42);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Usuario/Contraseña NO SON validos!!!", Toast.LENGTH_SHORT).show();
        }
    }
}