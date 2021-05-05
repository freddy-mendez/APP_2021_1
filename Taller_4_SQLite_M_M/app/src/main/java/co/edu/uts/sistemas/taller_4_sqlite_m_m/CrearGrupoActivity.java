package co.edu.uts.sistemas.taller_4_sqlite_m_m;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.BaseDatos;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.ClienteDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.GrupoDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Cliente;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Grupo;

public class CrearGrupoActivity extends AppCompatActivity {
    private GrupoDAO grupoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        BaseDatos db = new BaseDatos(getApplicationContext());
        grupoDAO=new GrupoDAO(db);
    }

    public void volver(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void guardar(View view) {
        EditText nombre = findViewById(R.id.txtNombreGrupo);
        String txtNombre = nombre.getText().toString();
        Grupo g = new Grupo(txtNombre);
        if (grupoDAO.addGrupo(g)) {
            setResult(RESULT_OK);
            finish();

        } else {
            Toast.makeText(this, "Error al crear el grupo", Toast.LENGTH_SHORT).show();
        }
    }


}