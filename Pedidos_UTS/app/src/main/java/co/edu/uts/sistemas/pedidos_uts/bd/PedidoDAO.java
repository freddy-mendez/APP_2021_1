package co.edu.uts.sistemas.pedidos_uts.bd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Cliente;
import co.edu.uts.sistemas.pedidos_uts.model.Pedido;
import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class PedidoDAO {
    private final static String _ID = "id";
    private final static String NUMERO = "numero";
    private final static String FECHA = "fecha";
    private final static String VALOR = "valor";
    private final static String ID_CLIENTE = "id_cliente";

    public final static String TABLE_NAME = "pedidos";

    public final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            NUMERO+" INTEGER NOT NULL,"+
            FECHA+" TEXT NOT NULL,"+
            VALOR+" NUMERIC NOT NULL,"+
            ID_CLIENTE+" INTEGER NOT NULL, "+
        "CONSTRAINT FK1_Pedidos FOREIGN KEY ("+ID_CLIENTE+") "+
        "REFERENCES "+ClienteDAO.TABLE_NAME+" ("+ClienteDAO._ID+") )";

    private final static String _ID_R = "id";
    private final static String ID_PEDIDO = "id_pedido";
    private final static String ID_PRODUCTO = "id_producto";
    private final static String CANTIDAD = "cantidad";
    private final static String SUBTOTAL = "subtotal";

    public final static String TABLE_NAME_R = "pedidos_productos";

    public final static String CREATE_TABLE_R = "CREATE TABLE "+TABLE_NAME_R+" ("+
            _ID_R+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            ID_PEDIDO+" INTEGER NOT NULL,"+
            ID_PRODUCTO+" INTEGER NOT NULL,"+
            CANTIDAD+" INTEGER NOT NULL,"+
            SUBTOTAL+" NUMERIC NOT NULL, "+
            "CONSTRAINT FK1_Pedidos_relacion FOREIGN KEY ("+ID_PEDIDO+") "+
            "REFERENCES "+PedidoDAO.TABLE_NAME+" ("+PedidoDAO._ID+")," +
            "CONSTRAINT FK2_productos_relacion FOREIGN KEY ("+ID_PRODUCTO+") "+
            "REFERENCES "+ProductoDAO.TABLE_NAME+" ("+ProductoDAO._ID+") )";

    private BaseDatos baseDatos;

    public PedidoDAO(BaseDatos baseDatos) {
        this.baseDatos = baseDatos;
    }

    public ArrayList<Pedido> getPedidos(){
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Pedido> pedidos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                int numero = cursor.getInt(cursor.getColumnIndex("numero"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                float valor = cursor.getFloat(cursor.getColumnIndex("valor"));
                Pedido p = new Pedido(_id, numero, fecha, valor);
                int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));
                ClienteDAO clienteDAO = new ClienteDAO(baseDatos);
                Cliente c = clienteDAO.getCliente(id_cliente);
                p.setCliente(c);
                pedidos.add(p);
            }while(cursor.moveToNext());
        }
        return pedidos;
    }

    public boolean addPedido(Pedido p){
        boolean flag = false;

        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("numero", p.getNumero());
        valores.put("fecha", p.getFecha());
        valores.put("valor", p.getValor());
        valores.put("id_cliente", p.getCliente().get_id());
        if (db.insert(TABLE_NAME, null, valores)>0){
            flag = true;
        }
        return flag;
    }


    public Pedido getPedido(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, null, _ID+"="+id, null, null, null, null);
        Pedido pedido = null;
        if (cursor.moveToFirst()) {
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                int numero = cursor.getInt(cursor.getColumnIndex("numero"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                float valor = cursor.getFloat(cursor.getColumnIndex("valor"));
                pedido = new Pedido(_id, numero, fecha, valor);
                int id_cliente = cursor.getInt(cursor.getColumnIndex("id_cliente"));
                ClienteDAO clienteDAO = new ClienteDAO(baseDatos);
                Cliente c = clienteDAO.getCliente(id_cliente);
                pedido.setCliente(c);
                pedido.setProductos(getProductosPedido(id));
        }
        return pedido;
    }

    private ArrayList<Producto> getProductosPedido(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "select * from "+ProductoDAO.TABLE_NAME+" as p, "+TABLE_NAME_R+" as pp "+
                "where p."+ProductoDAO._ID+"="+"pp."+ this.ID_PRODUCTO+" and "+
                "pp."+this.ID_PEDIDO+"="+id;
        Cursor cursor =
                db.rawQuery(sql, null);
        ArrayList<Producto> productos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int precio = cursor.getInt(cursor.getColumnIndex("precio"));
                String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                Producto p = new Producto(_id, codigo, nombre, precio, imagen);
                productos.add(p);
            } while(cursor.moveToNext());

        }
        return productos;
    }

    public ArrayList<Producto> getProductosNoPedido(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "select * from "+ProductoDAO.TABLE_NAME+" "+
                "where "+ProductoDAO._ID+" NOT IN (select "+ this.ID_PRODUCTO+" from "+
                TABLE_NAME_R+" where "+this.ID_PEDIDO+"="+id+")";
        Cursor cursor =
                db.rawQuery(sql, null);
        ArrayList<Producto> productos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int precio = cursor.getInt(cursor.getColumnIndex("precio"));
                String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                Producto p = new Producto(_id, codigo, nombre, precio, imagen);
                productos.add(p);
            } while(cursor.moveToNext());

        }
        return productos;
    }


    public void addProductoPedido(int id_pedido, int id_producto, int cantidad, int subtotal) {
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(ID_PEDIDO, id_pedido);
        valores.put(ID_PRODUCTO, id_producto);
        valores.put(CANTIDAD, cantidad);
        valores.put(SUBTOTAL, subtotal);
        db.insert(TABLE_NAME_R, null, valores);
        String sql = "update "+TABLE_NAME+" set "+VALOR+" = "+VALOR+" + "+subtotal+
                " where "+_ID+"="+id_pedido;
        db.execSQL(sql);
    }

    public void deleteProductoPedido(int id_pedido, int id_producto) {
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();

        String sql = "select subtotal  from "+PedidoDAO.TABLE_NAME_R+" "+
                "where "+ this.ID_PEDIDO+"="+id_pedido+" and  "+
                this.ID_PRODUCTO+"="+id_producto;
        Cursor cursor =
                db.rawQuery(sql, null);
        int subtotal=0;
        if (cursor.moveToFirst()) {
            subtotal=cursor.getInt(cursor.getColumnIndex("subtotal"));
        }

        String where = ID_PEDIDO+"="+id_pedido+" and "+ID_PRODUCTO+"="+id_producto;
        db.delete(TABLE_NAME_R, where, null);

        sql = "update "+TABLE_NAME+" set "+VALOR+" = "+VALOR+" - "+subtotal+
                " where "+_ID+"="+id_pedido;
        db.execSQL(sql);
    }


    public void deletePedido(int id) {
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        db.delete(TABLE_NAME_R, ID_PEDIDO+"=?", new String[]{""+id});
        db.delete(TABLE_NAME, _ID+"=?", new String[]{""+id});
    }
}
