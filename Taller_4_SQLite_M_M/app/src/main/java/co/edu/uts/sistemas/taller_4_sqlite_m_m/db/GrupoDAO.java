package co.edu.uts.sistemas.taller_4_sqlite_m_m.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Cliente;
import co.edu.uts.sistemas.taller_4_sqlite_m_m.modelo.Grupo;

public class GrupoDAO {
    final static String _ID = "_id";
    private final static String NOMBRE = "nombre";
    private final static String NUMERO_CLIENTES = "numClientes";

    public final static String TABLE_NAME = "grupos";

    public final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            NOMBRE+" TEXT NOT NULL,"+
            NUMERO_CLIENTES+" INTEGER NOT NULL)";

    private final static String _ID_R = "_id";
    private final static String ID_GRUPO = "id_grupo";
    private final static String ID_CLIENTE = "id_cliente";

    public final static String TABLE_NAME_R = "clientes_grupos";

    public final static String CREATE_TABLE_R = "CREATE TABLE "+TABLE_NAME_R+" ("+
            _ID_R+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            ID_GRUPO+" INTEGER NOT NULL,"+
            ID_CLIENTE+" INTEGER NOT NULL,"+
            "CONSTRAINT FK1_grupos_relacion FOREIGN KEY ("+ID_GRUPO+") "+
            "REFERENCES "+TABLE_NAME+" ("+_ID+")," +
            "CONSTRAINT FK2_clientes_relacion FOREIGN KEY ("+ID_CLIENTE+") "+
            "REFERENCES "+ClienteDAO.TABLE_NAME+" ("+ClienteDAO._ID+") )";

    private BaseDatos baseDatos;

    public GrupoDAO(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public ArrayList<Grupo> getGrupos(){
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Grupo> grupos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
                int numClientes = cursor.getInt(cursor.getColumnIndex(NUMERO_CLIENTES));
                Grupo g = new Grupo(_id, nombre, numClientes);
                grupos.add(g);
            }while(cursor.moveToNext());
        }
        return grupos;
    }

    public boolean addGrupo(Grupo g){
        boolean flag = false;
        if (buscarGrupo(g.getNombre())!=null) {
            return flag;
        }
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(NOMBRE, g.getNombre());
        valores.put(NUMERO_CLIENTES, g.getNumClientes());
        if (db.insert(TABLE_NAME, null, valores)>0){
            flag = true;
        }
        return flag;
    }

    public Grupo getGrupo(int id){
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String where = _ID+"=?";
        Cursor cursor =
                db.query(TABLE_NAME, null, where, new String[]{""+id}, null, null, null);
        Grupo grupo = null;
        if (cursor.moveToFirst()) {
            int _id = cursor.getInt(cursor.getColumnIndex(_ID));
            String nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
            int numClientes = cursor.getInt(cursor.getColumnIndex(NUMERO_CLIENTES));
            grupo = new Grupo(_id, nombre, numClientes);
            grupo.setClientes(getClientesGrupo(id));
        }
        return grupo;
    }

    private ArrayList<Cliente> getClientesGrupo(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "select * from "+ClienteDAO.TABLE_NAME+" as c, "+TABLE_NAME_R+" as cg "+
                "where c."+ClienteDAO._ID+"="+"cg."+ this.ID_CLIENTE+" and "+
                "cg."+this.ID_GRUPO+"="+id;
        Cursor cursor =
                db.rawQuery(sql, null);
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
            } while(cursor.moveToNext());

        }
        return clientes;
    }

    public Grupo buscarGrupo(String name){
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String where = NOMBRE+"=?";
        Cursor cursor =
                db.query(TABLE_NAME, null, where, new String[]{name}, null, null, null);
        Grupo grupo = null;
        if (cursor.moveToFirst()) {
                int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                String nombre = cursor.getString(cursor.getColumnIndex(NOMBRE));
                int numClientes = cursor.getInt(cursor.getColumnIndex(NUMERO_CLIENTES));
                grupo = new Grupo(_id, nombre, numClientes);
        }
        return grupo;
    }


    public void deleteGrupo(int id) {
            SQLiteDatabase db = this.baseDatos.getWritableDatabase();
            db.delete(TABLE_NAME_R, ID_GRUPO+"=?", new String[]{""+id});
            db.delete(TABLE_NAME, _ID+"=?", new String[]{""+id});
    }

    public void deleteClienteGrupo(int id_grupo, int id_cliente) {
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();

        String where = ID_GRUPO+"="+id_grupo+" and "+ID_CLIENTE+"="+id_cliente;
        db.delete(TABLE_NAME_R, where, null);

        String sql = "update "+TABLE_NAME+" set "+NUMERO_CLIENTES+" = "+NUMERO_CLIENTES+" - 1"+
                " where "+_ID+"="+id_grupo;
        db.execSQL(sql);
    }

    public void addClienteGrupo(int id_grupo, int id_cliente) {
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ID_GRUPO, id_grupo);
        valores.put(ID_CLIENTE, id_cliente);
        db.insert(TABLE_NAME_R, null, valores);
        String sql = "update "+TABLE_NAME+" set "+NUMERO_CLIENTES+" = "+NUMERO_CLIENTES+" + 1"+
                " where "+_ID+"="+id_grupo;
        db.execSQL(sql);
    }

    public ArrayList<Cliente> getClientesNoGrupo(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "select * from "+ClienteDAO.TABLE_NAME+" "+
                "where "+ClienteDAO._ID+" NOT IN (select "+ this.ID_CLIENTE+" from "+
                TABLE_NAME_R+" where "+this.ID_GRUPO+"="+id+")";
        Cursor cursor =
                db.rawQuery(sql, null);
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
            } while(cursor.moveToNext());

        }
        return clientes;
    }


}
