package co.edu.uts.sistemas.pedidos_uts.bd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Cliente;


public class ClienteDAO {
     final static String _ID = "id";
    private final static String NAME = "name";
    private final static String DIRECCION = "direccion";
    private final static String EMAIL = "email";
    private final static String CELULAR = "celular";

    public final static String TABLE_NAME = "cliente";

    public final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            NAME+" TEXT NOT NULL,"+
            DIRECCION+" TEXT NOT NULL,"+
            EMAIL+" TEXT NOT NULL,"+
            CELULAR+" INTEGER NOT NULL)";

    private BaseDatos baseDatos;

    public ClienteDAO(BaseDatos baseDatos){
        this.baseDatos=baseDatos;
    }

    public ArrayList<Cliente> getClientes(){
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Cliente> clientes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                int celular = cursor.getInt(cursor.getColumnIndex("celular"));
                Cliente c = new Cliente(_id, name, direccion, email, celular);
                clientes.add(c);
            }while(cursor.moveToNext());
        }
        return clientes;
    }

    public Cliente getCliente(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=? ";
        Cursor cursor =
                db.rawQuery(sql, new String[]{""+id});
        Cliente cliente = null;
        if (cursor.moveToFirst()){
            //do {
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String direccion = cursor.getString(cursor.getColumnIndex("direccion"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                int celular = cursor.getInt(cursor.getColumnIndex("celular"));
                cliente = new Cliente(_id, name, direccion, email, celular);
            //}while(cursor.moveToNext());
        }
        return cliente;
    }

    public boolean updateCliente(Cliente c){
        boolean flag = false;

        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", c.getName());
        valores.put("direccion", c.getDireccion());
        valores.put("email", c.getEmail());
        valores.put("celular", c.getCelular());

        if (db.update(TABLE_NAME, valores,"id=? ", new String[]{""+c.get_id()})>0){
            flag = true;
        }

        return flag;
    }

    public boolean deleteCliente(Cliente c) {
        boolean flag=false;
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        if (db.delete(TABLE_NAME, "id=? ", new String[]{""+c.get_id()})>0) {
            flag=true;
        }
        return flag;
    }

    public boolean addCliente(Cliente c){
        boolean flag = false;

        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("name", c.getName());
        valores.put("direccion", c.getDireccion());
        valores.put("email", c.getEmail());
        valores.put("celular", c.getCelular());

        if (db.insert(TABLE_NAME, null, valores)>0){
            flag = true;
        }

        return flag;
    }
}

