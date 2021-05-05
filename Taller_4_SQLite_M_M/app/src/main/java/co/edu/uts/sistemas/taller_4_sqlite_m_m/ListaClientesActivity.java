package co.edu.uts.sistemas.taller_4_sqlite_m_m;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.BaseDatos;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.GrupoDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Cliente;

public class ListaClientesActivity extends AppCompatActivity {
    private GrupoDAO grupoDAO;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);
        grupoDAO=new GrupoDAO(new BaseDatos(getApplicationContext()));
        id = getIntent().getIntExtra("id_grupo",-1);
        ListView lista = findViewById(R.id.listaClientesAdd);
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_single_choice,
                grupoDAO.getClientesNoGrupo(id)
        );
        lista.setAdapter(adapter);
        lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

    }

    public void volver(View view) {

            setResult(RESULT_CANCELED);

        finish();
    }

    public void guardar(View view) {
        ListView lista = findViewById(R.id.listaClientesAdd);

        Cliente c = (Cliente) lista.getItemAtPosition(lista.getCheckedItemPosition());
        grupoDAO.addClienteGrupo (id,c.get_id());
        setResult(RESULT_OK);
        finish();
    }

}