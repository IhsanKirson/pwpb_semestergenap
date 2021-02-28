package com.pwpb.sqlitetugas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="UserInfo";
    private static final String TABLE_NAME="tbl_user";
    private static final String KEY_NIM="nim";
    private static final String KEY_NAME="name";
    private static final String KEY_TGLLAHIR="tglLahir";
    private static final String KEY_GENDER="gender";
    private static final String KEY_ALAMAT="alamat";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "Create Table "+TABLE_NAME+ "("
                + KEY_NIM + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_TGLLAHIR + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_ALAMAT + " TEXT" + ")";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists " +TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Mahasiswa mahasiswa){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NIM,mahasiswa.getNim());
        values.put(KEY_NAME,mahasiswa.getName());
        values.put(KEY_TGLLAHIR,mahasiswa.getTglLahir());
        values.put(KEY_GENDER,mahasiswa.getGender());
        values.put(KEY_ALAMAT,mahasiswa.getAlamat());
        db.insert(TABLE_NAME,null,values);
    }

    public List<Mahasiswa> selectUserData(){
        ArrayList<Mahasiswa> userList = new ArrayList<Mahasiswa>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_NIM,KEY_NAME, KEY_TGLLAHIR, KEY_GENDER, KEY_ALAMAT};
        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            String nim = c.getString(0);
            String name = c.getString(1);
            String tglLahir = c.getString(2);
            String gender = c.getString(3);
            String alamat = c.getString(4);
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(nim);
            mahasiswa.setName(name);
            mahasiswa.setTglLahir(tglLahir);
            mahasiswa.setGender(gender);
            mahasiswa.setAlamat(alamat);
            userList.add(mahasiswa);
        }
        return userList;
    }

    public void delete(String nim){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_NIM+"='"+nim+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }

    public void update(Mahasiswa mahasiswa){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,mahasiswa.getName());
        values.put(KEY_TGLLAHIR,mahasiswa.getTglLahir());
        values.put(KEY_GENDER,mahasiswa.getGender());
        values.put(KEY_ALAMAT,mahasiswa.getAlamat());
        String whereClause=KEY_NIM+"='"+mahasiswa.getNim()+"'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}
