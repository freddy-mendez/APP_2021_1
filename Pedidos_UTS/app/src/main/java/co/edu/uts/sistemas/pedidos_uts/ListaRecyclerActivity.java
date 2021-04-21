package co.edu.uts.sistemas.pedidos_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import co.edu.uts.sistemas.pedidos_uts.bd.BaseDatos;
import co.edu.uts.sistemas.pedidos_uts.bd.ProductoDAO;

public class ListaRecyclerActivity extends AppCompatActivity {
    private ProductoDAO productoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_recycler);
        RecyclerView lista_rv = findViewById(R.id.lista_rv);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lista_rv.setLayoutManager(manager);
        BaseDatos baseDatos=new BaseDatos(getApplicationContext());
        productoDAO = new ProductoDAO(baseDatos);
        ProductoRVAdapter adapter = new ProductoRVAdapter(productoDAO.getProductos());
        lista_rv.setAdapter(adapter);

    }
}