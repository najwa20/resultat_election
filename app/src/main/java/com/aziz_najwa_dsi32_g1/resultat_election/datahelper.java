package com.aziz_najwa_dsi32_g1.resultat_election;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class datahelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "election";

    public datahelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (cin text, nom text,prenom text,email text,login text primary key,password text)");
        db.execSQL("create table election (id integer Primary Key AUTOINCREMENT,nom text,terminer boolean)");
        db.execSQL("create table choix (id integer Primary Key AUTOINCREMENT,nom text,election integer,nb integer)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists election");
        db.execSQL("drop table if exists choix");
        onCreate(db);
    }

    public boolean insertuser(String cin, String nom, String prenom, String email, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cin", cin);
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("email", email);
        contentValues.put("login", login);
        contentValues.put("password", password);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;

    }

    public boolean insertelection(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("terminer", false);
        long ins = db.insert("election", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;

    }

    public boolean insertchoix(String nom,int election) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("election", election);
        contentValues.put("nb", 0);
        long ins = db.insert("choix", null, contentValues);
        if (ins == -1)
            return false;
        else
            return true;

    }

    public boolean chekemail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean cheklog(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where login=?", new String[]{login});
        if (cursor.getCount() > 0)
            return false;
        else
            return true;
    }

    public boolean chekpass(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where login=? and password=?", new String[]{login, password});
        return cursor.getCount() > 0;

    }

    public ArrayList<HashMap<String, String>> getEle() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,nom FROM election";
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> ele = new HashMap<>();
            ele.put("id",String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            ele.put("nom",cursor.getString(cursor.getColumnIndex("nom")));
            ele.put("img", String.valueOf(R.drawable.election));
            userList.add(ele);
        }
        return  userList;
    }

    public ArrayList<HashMap<String, String>> getchoix(String election) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,nom FROM choix where election="+election;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> ele = new HashMap<>();
            ele.put("id",String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            ele.put("nom",cursor.getString(cursor.getColumnIndex("nom")));
            ele.put("img", String.valueOf(R.drawable.election));
            userList.add(ele);
        }
        return  userList;
    }

    public void deletelect(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("election", "id = ?",new String[]{id});
        db.close();
    }

    public void arrelect(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("terminer",true);
        db.update("election",cv,"_id="+id,null);
        db.close();
    }

    public void deletchoix(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("choix", "id = ?",new String[]{id});
        db.close();
    }
}
