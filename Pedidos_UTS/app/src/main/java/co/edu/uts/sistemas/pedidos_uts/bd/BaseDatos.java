package co.edu.uts.sistemas.pedidos_uts.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {
    private final static String NAME = "pedidos_uts.db";
    private final static int VERSION = 1;

    public BaseDatos(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "create table producto ( _id integer not null primary key, codigo text not null)";
        db.execSQL(ProductoDAO.CREATE_TABLE);
        db.execSQL(ClienteDAO.CREATE_TABLE);
        db.execSQL(PedidoDAO.CREATE_TABLE);
        db.execSQL(PedidoDAO.CREATE_TABLE_R);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+PedidoDAO.TABLE_NAME_R);
        db.execSQL("DROP TABLE "+PedidoDAO.TABLE_NAME);
        db.execSQL("DROP TABLE "+ProductoDAO.TABLE_NAME);
        db.execSQL("DROP TABLE "+ClienteDAO.TABLE_NAME);
        onCreate(db);
    }
}
