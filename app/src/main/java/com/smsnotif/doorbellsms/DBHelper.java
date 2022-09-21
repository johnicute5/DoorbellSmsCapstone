package com.smsnotif.doorbellsms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Smsdoorbell.db";
    public DBHelper(Context context) {
        super(context, "Smsdoorbell.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table users(username TEXT primary key, password TEXT)");
        DB.execSQL("create Table doorbelldetails(dbmodel TEXT primary key, dbname TEXT NOT NULL, dbnumber TEXT NOT NULL UNIQUE, dbmessage TEXT NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists users");
        DB.execSQL("drop Table if exists doorbelldeatails");
    }
    public Boolean insertdetialsData(String dbmodel , String dbname,String dbnumber, String dbmessage){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("db_model", dbmodel );
        contentValues.put("dbname", dbname);
        contentValues.put("dbnumber", dbnumber);
        contentValues.put("dbmessage", dbmessage);
        long result=DB.insert("doorbelldetails", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("dbmodel", username);
        contentValues.put("dbname", password);
        long result=DB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }



}