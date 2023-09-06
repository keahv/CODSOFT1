package com.example.myquotesapp.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myquotesapp.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "quotes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_QUOTES = "QUOTES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QUOTE = "TASK";

//    private static final String DATABASE_CREATE =
//            "CREATE TABLE " + TABLE_QUOTES + " (" +
//                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    COLUMN_QUOTE + " TEXT NOT NULL);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_QUOTES + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        onCreate(db);
    }

    public void insertTask(Model model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUOTE , model.getFavoriteQuote());
        db.insert(TABLE_QUOTES,null,values);
    }

    public void deleteQuote(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_QUOTES,"ID=?",new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Model> getAllQuotes(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<Model> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_QUOTES,null,null,null,null,null,null);
            if (cursor != null) {
                if (cursor.moveToFirst()){
                    do {
                        Model quote = new Model();
                        quote.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                        quote.setFavoriteQuote(cursor.getString(cursor.getColumnIndex(COLUMN_QUOTE)));
                        modelList.add(quote);
                    } while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
