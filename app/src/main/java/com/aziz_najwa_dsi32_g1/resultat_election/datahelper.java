package com.aziz_najwa_dsi32_g1.resultat_election;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ALL")
public class datahelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "election";

    datahelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (cin text, nom text,prenom text,email text,login text primary key,password text)");
        db.execSQL("create table election (id integer Primary Key AUTOINCREMENT,nom text,terminer integer)");
        db.execSQL("create table choix (id integer Primary Key AUTOINCREMENT,nom text,election integer,nb integer)");
        db.execSQL("create table per (login text,election integer)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists election");
        db.execSQL("drop table if exists choix");
        db.execSQL("drop table if exists per");
        onCreate(db);
    }

    void insertuser(String cin, String nom, String prenom, String email, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cin", cin);
        contentValues.put("nom", nom);
        contentValues.put("prenom", prenom);
        contentValues.put("email", email);
        contentValues.put("login", login);
        contentValues.put("password", password);
        db.insert("user", null, contentValues);

    }

    void insertelection(String nom) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("terminer", 0);
        db.insert("election", null, contentValues);

    }

    void insertchoix(String nom, int election) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nom", nom);
        contentValues.put("election", election);
        contentValues.put("nb", 0);
        db.insert("choix", null, contentValues);

    }

    private void insertnp(String login, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", login);
        contentValues.put("election", id);
        db.insert("per", null, contentValues);
    }

    boolean chekemail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from user where email=?", new String[]{email});
        return cursor.getCount() <= 0;
    }

    boolean cheklog(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from user where login=?", new String[]{login});
        return cursor.getCount() <= 0;
    }

    boolean chekpass(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from user where login=? and password=?", new String[]{login, password});
        return cursor.getCount() > 0;

    }

    ArrayList<HashMap<String, String>> getEle() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,nom FROM election";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> ele = new HashMap<>();
            ele.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            ele.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            ele.put("img", String.valueOf(R.drawable.election));
            userList.add(ele);
        }
        return userList;
    }

    ArrayList<HashMap<String, String>> getchoix(String election) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,nom FROM choix where election=" + election;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> ele = new HashMap<>();
            ele.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            ele.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            ele.put("img", String.valueOf(R.drawable.election));
            userList.add(ele);
        }
        return userList;
    }

    ArrayList<HashMap<String, String>> getchoixres(String election) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT id,nom,nb FROM choix where election=" + election;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> ele = new HashMap<>();
            ele.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
            ele.put("nom", cursor.getString(cursor.getColumnIndex("nom")));
            ele.put("res", String.valueOf(cursor.getInt(cursor.getColumnIndex("nb"))));
            ele.put("img", String.valueOf(R.drawable.election));
            userList.add(ele);
        }
        return userList;
    }

    void deletelect(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("election", "id = ?", new String[]{id});
        db.close();
    }

    void arrelect(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE election SET terminer =1 WHERE id =" + id);
    }

    void deletchoix(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("choix", "id = ?", new String[]{id});
        db.close();
    }

    public void send(String id, String id1, String login) {
        SQLiteDatabase db = this.getWritableDatabase();

        insertnp(login, Integer.valueOf(id));

        String query = "SELECT nb FROM choix where id=" + id1;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        int nbr = 0;
        while (cursor.moveToNext()) {
            nbr = cursor.getInt(cursor.getColumnIndex("nb"));
        }
        nbr = nbr + 1;
        db.execSQL("UPDATE choix SET nb =" + nbr + " WHERE id =" + id1);
    }

    public boolean testelec(String id, String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM per where login='" + login + "' and election=" + id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public boolean testter(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String test = "0";
        String query = "SELECT terminer FROM election where id=" + id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            test = String.valueOf(cursor.getInt(cursor.getColumnIndex("terminer")));
        }
        if (test == "1") {
            return true;
        } else return false;
    }
}
