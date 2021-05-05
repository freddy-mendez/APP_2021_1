package co.edu.uts.sistemas.taller_4_sqlite_m_m.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatos extends SQLiteOpenHelper {
    private static final String NAME = "taller_4.db";
    private static final int VERSION = 3;

    public BaseDatos(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClienteDAO.CREATE_TABLE);
        db.execSQL(GrupoDAO.CREATE_TABLE);
        db.execSQL(GrupoDAO.CREATE_TABLE_R);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+GrupoDAO.TABLE_NAME_R);
        db.execSQL("DROP TABLE "+GrupoDAO.TABLE_NAME);
        db.execSQL("DROP TABLE "+ClienteDAO.TABLE_NAME);
        onCreate(db);
    }
}
