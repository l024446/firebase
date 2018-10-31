package br.unicamp.ft.firebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.zip.Checksum;

public class DataBaseController {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public DataBaseController(Context context){

        dbHelper = new DatabaseHelper(context);
        sqLiteDatabase = dbHelper.getReadableDatabase();

    }

    public void onInserir(String id, String nome, int acerto, int erro, float dist_mais, float dist_menos){

        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("nome", nome);
        contentValues.put("acertos", acerto);
        contentValues.put("erros", erro);
        contentValues.put("dist_mais", dist_mais);
        contentValues.put("dist_menos", dist_menos);


        sqLiteDatabase.insert("tabela", null, contentValues);
        dbHelper.close();
        sqLiteDatabase.close();
    }


    public boolean isRegistrado(String ra){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.
                rawQuery("select * from " + "tabela" + " where _id = ? ", new String[]{ra});

        if(cursor.getCount() <= 0){
            cursor.close();
            dbHelper.close();
            sqLiteDatabase.close();
            return false;
        }
        else{
            cursor.close();
            dbHelper.close();
            sqLiteDatabase.close();
            return true;
        }
    }

    public int onSelecionarCount(){
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT  * FROM " + "tabela";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        dbHelper.close();
        sqLiteDatabase.close();
        return count;
        }

    public void onSelecionar(TextView textView1, TextView textView2, TextView textView3, TextView textView4){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "Select * from tabela";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        int acertos = 0;
        int erros = 0;
        float dist_menos = 0;
        float dist_mais = 0;

        if (cursor.moveToFirst()){
            do {
                int acertoBD = cursor.getInt(cursor.getColumnIndex("acertos"));
                int erroBD = cursor.getInt(cursor.getColumnIndex("erros"));
                System.out.println("ACERTOBD: " + acertoBD);
                System.out.println("ERROBD: " + erroBD);
                int distancia_menos = cursor.getInt(cursor.getColumnIndex("dist_menos"));
                int distancia_mais = cursor.getInt(cursor.getColumnIndex("dist_mais"));


                acertos += acertoBD;
                erros += erroBD;
                dist_menos += distancia_menos;
                dist_mais += distancia_mais;

            }while(cursor.moveToNext());
            int total = acertos+erros;
            int count = onSelecionarCount();
            System.out.println("TOTAL:" + total);
            acertos = (acertos*100)/total;
            erros = (erros*100)/total;
            dist_mais = dist_mais/count;
            dist_menos = dist_menos/count;

            textView1.setText("Porcentagem de acertos: " + acertos + "%");
            textView2.setText("Porcentagem de erros: " + erros + "%");
            textView3.setText("Média de distancia para mais: " + dist_mais);
            textView4.setText("Média de distancia para menos: " + dist_menos);
        }
        cursor.close();
        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void onAtualizarAcerto(String ra){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int acertos = 0;
        Cursor cursor = sqLiteDatabase.
                rawQuery("select acertos from " + "tabela" + " where _id = ? ", new String[]{ra});
        if(cursor.moveToFirst()){
            acertos = cursor.getInt(cursor.getColumnIndex("acertos"));
        }
        acertos++;
        ContentValues contentValues = new ContentValues();
        contentValues.put("acertos", acertos);

        String   whereClause = "_id = ?";
        String[] whereArgs   = new String[]{ra};

        sqLiteDatabase.update("tabela", contentValues, whereClause, whereArgs);
        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void onAtualizarErroMais(String ra, int distancia){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int erros = 0;
        Cursor cursor = sqLiteDatabase.
                rawQuery("select erros from " + "tabela" + " where _id = ? ", new String[]{ra});
        if(cursor.moveToFirst()) {
            erros = cursor.getInt(cursor.getColumnIndex("erros"));
        }
        erros++;
        ContentValues contentValues = new ContentValues();
        contentValues.put("erros", erros);
        contentValues.put("dist_mais", distancia);

        String   whereClause = "_id = ?";
        String[] whereArgs   = new String[]{ra};

        sqLiteDatabase.update("tabela", contentValues, whereClause, whereArgs);
        dbHelper.close();
        sqLiteDatabase.close();
    }
    public void onAtualizarErroMenos(String ra, int distancia){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        int erros = 0;
        Cursor cursor = sqLiteDatabase.
                rawQuery("select erros from " + "tabela" + " where _id = ? ", new String[]{ra});
        if(cursor.moveToFirst()) {
            erros = cursor.getInt(cursor.getColumnIndex("erros"));
        }
        erros++;
        ContentValues contentValues = new ContentValues();
        contentValues.put("erros", erros);
        contentValues.put("dist_menos", distancia);

        String   whereClause = "_id = ?";
        String[] whereArgs   = new String[]{ra};

        sqLiteDatabase.update("tabela", contentValues, whereClause, whereArgs);
        dbHelper.close();
        sqLiteDatabase.close();
    }
    public void onSelecionarTop3Erros(TextView textView5){
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("tabela", null, null,
                null, null, null, "erros" + " DESC", null);
        if(cursor.moveToFirst()) {
            String str = "";
            do {
                String nome = cursor.getString(1);
                int erro = cursor.getInt(cursor.getColumnIndex("erros"));
                str = str + nome + " | " +"erros: "+ erro + "\n";

            }while(cursor.moveToNext());
            String[] parts = str.split("\n");
            int count = onSelecionarCount();
            if (count  == 1) {
                textView5.setText("Pessoas mais erradas: " + "\n" + "1-" +parts[0]);
            }
            else if (count == 2) {
                textView5.setText("Pessoas mais erradas: " + "\n" + "1-"+ parts[0] + "\n" + "2-" + parts[1]);
            }else if (count >= 3){
                    textView5.setText("Pessoas mais erradas: " + "\n" + "1-" + parts[0] + "\n"+ "2-" + parts[1] + "\n" + "3-"+ parts[2]);
            }

        }
        cursor.close();
        dbHelper.close();
        sqLiteDatabase.close();
    }

}
