package br.unicamp.ft.firebase;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BEER";
    private static final int DB_VERSION = 1;


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE tabela " +
                    "(_id text PRIMARY KEY, nome text, "+
                    "acertos INTEGER, erros INTEGER, dist_mais INTEGER, " +
                    "dist_menos INTEGER);");
        }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
