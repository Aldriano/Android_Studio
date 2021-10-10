package br.com.aula.hotelaria.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

//https://www.tutorialspoint.com/android/android_sqlite_database.htm
public class DBCore extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName33.db";
    public static final String TABLE_NAME = "login";
    private static final int VERSAO_BANCO = 1;
    private HashMap hashMap;
    public static String databasePath = "";

    public DBCore(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
        databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        Log.v("minhaTAG","executando o método construtor DBCORE\n"+databasePath);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +" "+
                        "(_id integer primary key, email text, password text)"
        );
        Log.v("minhaTAG","executando: onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        Log.v("minhaTAG","executando: onUpgrade");
    }

    // O ideal é criar o padrão MVC - os métodos de insert, update e delete (manipulação)
    //deveria estar um uma classe ce Controller

    public boolean insertLogin (String email, String pwd) {
        Log.v("minhaTAG","insertLogin: email: "+email+" senha: "+pwd);

        //OBS - Só mostra o database no Device Explorer quando executar o primeiro insert
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", pwd);
        db.insert(TABLE_NAME, null, contentValues);
        //db.close();
        Log.v("minhaTAG","executando: insertLogin ("+email+")");

        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where _id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updatelogin (int id, String email, String pwd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", pwd);
        db.update(TABLE_NAME, contentValues, "_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                "_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllLogins() {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("email")));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor carregaDados(){
        Log.v("minhaTAG","Passou método carregaDados");
        Cursor cursor;
        String[] campos =  {"_id","email","password"};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.query(TABLE_NAME, campos, null, null, null, null, null, null);

//        String query = "SELECT * FROM "+TABLE_NAME;
//        SQLiteDatabase db = getWritableDatabase();
//        cursor = db.rawQuery(query,null);

        if(cursor!=null){
            cursor.moveToFirst();
            //lista
            /*while (cursor.moveToNext()){
                Log.v("minhaTAG",cursor.getString(1));
            }*/
        }
        db.close();
        return cursor;
    }

}
