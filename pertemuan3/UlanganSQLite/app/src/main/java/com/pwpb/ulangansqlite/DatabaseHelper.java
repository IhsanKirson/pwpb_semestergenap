package com.pwpb.ulangansqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION=1;
    private static final String DB_NAME="NoteData";
    private static final String TABLE_NAME="tbl_note";
    private static final String KEY_ID="id";
    private static final String KEY_TITLE="title";
    private static final String KEY_AGE="age";
    private static final String KEY_CREATED_AT="created_at";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "Create Table " + TABLE_NAME + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql=("drop table if exists " + TABLE_NAME);
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(NoteModel noteModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, noteModel.getId());
        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_AGE, noteModel.getDesc());
        values.put(KEY_CREATED_AT, noteModel.getCreated_at());
        db.insert(TABLE_NAME, null, values);
    }

    public List<NoteModel> selectUserData(){
        ArrayList<NoteModel> userList = new ArrayList<NoteModel>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID,KEY_TITLE, KEY_AGE, KEY_CREATED_AT};
        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);
        while (c.moveToNext()){
            String id = c.getString(0);
            String title = c.getString(1);
            String desc = c.getString(2);
            String created_at = c.getString(3);
            NoteModel noteModel = new NoteModel();
            noteModel.setId(id);
            noteModel.setTitle(title);
            noteModel.setDesc(desc);
            noteModel.setCreated_at(created_at);
            userList.add(noteModel);
        }
        return userList;
    }

    public void delete(String id){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + "='" + id + "'";
        db.delete(TABLE_NAME,whereClause,null);
    }

    public void update(NoteModel noteModel){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, noteModel.getId());
        values.put(KEY_TITLE, noteModel.getTitle());
        values.put(KEY_AGE, noteModel.getDesc());
        values.put(KEY_CREATED_AT, noteModel.getCreated_at());
        String whereClause = KEY_ID +"='" + noteModel.getId() + "'";
        db.update(TABLE_NAME,values,whereClause,null);
    }
}
