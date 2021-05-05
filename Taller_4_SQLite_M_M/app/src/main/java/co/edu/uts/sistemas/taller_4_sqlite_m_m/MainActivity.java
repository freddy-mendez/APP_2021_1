package co.edu.uts.sistemas.taller_4_sqlite_m_m;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.BaseDatos;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.GrupoDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Grupo;

public class MainActivity extends AppCompatActivity {
    private GrupoDAO grupoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grupoDAO=new GrupoDAO(new BaseDatos(getApplicationContext()));
        /*if (grupoDAO.getGrupos().size()==0) {
            Grupo g = new Grupo("Primer Semestre");
            grupoDAO.addGrupo(g);
            g = new Grupo("Segundo Semestre");
            grupoDAO.addGrupo(g);
            g = new Grupo("Tercer Semestre");
            grupoDAO.addGrupo(g);
        }*/
        actualizarLista();
        ListView lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Grupo g = (Grupo) lista.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, VerDetalleGrupoActivity.class);
                intent.putExtra("id_grupo", g.get_id());
                startActivityForResult(intent, 1200);
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Grupo g = (Grupo) lista.getItemAtPosition(pos);
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setTitle("Eliminar Grupo");
                alerta.setMessage("Esta seguro de eliminar el grupo "+g.getNombre());
                alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        grupoDAO.deleteGrupo(g.get_id());
                        actualizarLista();
                    }
                });
                alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alerta.show();
                return true;
            }
        });
    }

    private void actualizarLista() {
        ListView lista = findViewById(R.id.lista);
        ArrayAdapter<Grupo> adapter = new ArrayAdapter<>(
          getApplicationContext(),
          android.R.layout.simple_list_item_1,
          grupoDAO.getGrupos()
        );
        lista.setAdapter(adapter);
    }

    public void ejecutar(View view) {
        if (view.getId()==R.id.btnSalir) {
            finish();
        } else {
            Intent intent=new Intent(MainActivity.this, CrearGrupoActivity.class);
            startActivityForResult(intent, 1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 || requestCode==1200) {
            if (resultCode==RESULT_OK) {
                actualizarLista();
            }
        }
    }
}