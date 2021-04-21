package co.edu.uts.sistemas.pedidos_uts.bd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import co.edu.uts.sistemas.pedidos_uts.model.Producto;

public class ProductoDAO {
    final static String _ID = "_id";
    private final static String CODIGO = "codigo";
    private final static String NOMBRE = "nombre";
    private final static String PRECIO = "precio";
    private final static String IMAGEN = "imagen";

    public final static String TABLE_NAME = "producto";

    public final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+
            _ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            CODIGO+" TEXT NOT NULL,"+
            NOMBRE+" TEXT NOT NULL,"+
            PRECIO+" INTEGER NOT NULL, "+
            IMAGEN+" TEXT NOT NULL)";

    private BaseDatos baseDatos;

    public ProductoDAO(BaseDatos baseDatos) {
        this.baseDatos=baseDatos;
    }

    public ArrayList<Producto> getProductos() {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_NAME, null, null, null, null, null, null);
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

    public Producto getProducto(int id) {
        SQLiteDatabase db = this.baseDatos.getReadableDatabase();
        String sql = "select * from "+TABLE_NAME+" where _id=?";
        Cursor cursor =
                db.rawQuery(sql, new String[]{""+id});
        Producto producto = null;
        if (cursor.moveToFirst()) {

                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String codigo = cursor.getString(cursor.getColumnIndex("codigo"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                int precio = cursor.getInt(cursor.getColumnIndex("precio"));
                String imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                producto = new Producto(_id, codigo, nombre, precio, imagen);

        }
        return producto;
    }

    public boolean updateProducto(Producto p) {
        boolean flag=false;
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        //"update producto set codigo=?, nombre=?, precio=?, imagen=? where _id=?";
        ContentValues valores = new ContentValues();
        valores.put("imagen", p.getImagen());
        valores.put("codigo", p.getCodigo());
        valores.put("nombre", p.getNombre());
        valores.put("precio", p.getPrecio());

        if (db.update(TABLE_NAME, valores, "_id=?", new String[]{""+p.get_id()})>0) {
            flag=true;
        }

        return flag;
    }

    public boolean deleteProducto(Producto p) {
        boolean flag=false;
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        if (db.delete(TABLE_NAME, "_id=?", new String[]{""+p.get_id()})>0) {
            flag=true;
        }
        return flag;
    }

    public boolean addProducto(Producto p) {
        boolean flag=false;
        SQLiteDatabase db = this.baseDatos.getWritableDatabase();
        //"insert into producto (_id, codigo, nombre, precio, imagen) values (NULL, '123', '123', 123, 'link.me')";
        ContentValues valores = new ContentValues();
        valores.put("imagen", p.getImagen());
        valores.put("codigo", p.getCodigo());
        valores.put("nombre", p.getNombre());
        valores.put("precio", p.getPrecio());

        if (db.insert(TABLE_NAME, null, valores)>0) {
            flag=true;
        }

        return flag;
    }

}
