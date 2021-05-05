package co.edu.uts.sistemas.taller_4_sqlite_m_m;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.BaseDatos;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.ClienteDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.db.GrupoDAO;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Cliente;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Grupo;

public class VerDetalleGrupoActivity extends AppCompatActivity {
    private GrupoDAO grupoDAO;
    private ClienteDAO clienteDAO;
    private Grupo g;
    private boolean agrego=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_grupo);
        grupoDAO=new GrupoDAO(new BaseDatos(getApplicationContext()));
        clienteDAO=new ClienteDAO(new BaseDatos(getApplicationContext()));
        if (clienteDAO.getClientes().size()==0) {
            Cliente c = new Cliente("Freddy","Cra 32", "fmendezo", 123);
            clienteDAO.addCliente(c);
            c = new Cliente("Freddy2","Cra 32", "fmendezo", 123);
            clienteDAO.addCliente(c);
            c = new Cliente("Freddy3","Cra 32", "fmendezo", 123);
            clienteDAO.addCliente(c);
        }

        int id = getIntent().getIntExtra("id_grupo",-1);
        if (id!=-1) {
            g=grupoDAO.getGrupo(id);
            if (g!=null) {
                verDatos();
                ListView lista = findViewById(R.id.listaClientes);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Cliente c = (Cliente) lista.getItemAtPosition(i);
                        grupoDAO.deleteClienteGrupo(g.get_id(), c.get_id());
                        verDatos();
                    }
                });
            }
        }
    }

    private void verDatos() {
        g=grupoDAO.getGrupo(g.get_id());
        TextView id=findViewById(R.id.lbIdGrupo);
        TextView nombreGrupo=findViewById(R.id.lbNombreGrupo);
        TextView numClientes=findViewById(R.id.lbNumClientes);
        id.setText(""+g.get_id());
        nombreGrupo.setText(g.getNombre());
        numClientes.setText(""+g.getNumClientes());
        ListView lista = findViewById(R.id.listaClientes);
        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(
          getApplicationContext(),
                android.R.layout.simple_list_item_1,
                g.getClientes()
        );
        lista.setAdapter(adapter);
    }

    public void volver(View view) {
        if (!agrego) {
            setResult(RESULT_CANCELED);
        } else {
            setResult(RESULT_OK);
        }
        finish();
    }

    @Override
    public void finish() {
        //super.finish();
    }

    public void agregar(View view) {
        Intent intent = new Intent(getApplicationContext(), ListaClientesActivity.class);
        intent.putExtra("id_grupo", g.get_id());
        startActivityForResult(intent, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000 && resultCode==RESULT_OK) {
            agrego=true;

            verDatos();
        }
    }
}