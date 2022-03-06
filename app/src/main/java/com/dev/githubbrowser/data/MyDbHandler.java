package com.dev.githubbrowser.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.dev.githubbrowser.model.Repo;
import com.dev.githubbrowser.params.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "(" + Params.KEY_ID +
                " INTEGER PRIMARY KEY," + Params.KEY_OWNER + " TEXT," + Params.KEY_REPOSITORY +
                " TEXT, " + Params.KEY_DESCRIPTION + " TEXT, " + Params.KEY_URL + " TEXT )";
        Log.d("query runned" , create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addRepo(Repo repo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_OWNER, repo.getRepoOwner());
        values.put(Params.KEY_REPOSITORY, repo.getRepoName());
        values.put(Params.KEY_DESCRIPTION, repo.getRepoDesc());
        values.put(Params.KEY_URL, repo.getRepoUrl());

        db.insert(Params.TABLE_NAME, null, values);
//        Log.d("msg", "id "+ repo.getId() + "    owner  "+ repo.getRepoOwner() +
//                "   name  " + repo.getRepoName() + "   desc  " +repo.getRepoDesc() + "   url  " + repo.getRepoUrl());
        db.close();
    }

    public List<Repo> getAllRepos(){
        List<Repo> repoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //generate query to read from database
        String select = "SELECT * FROM "+ Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);

        if (cursor.moveToFirst()){

            do {
                Repo repo = new Repo();
                repo.setId(Integer.parseInt(cursor.getString(0)));
                repo.setRepoOwner(cursor.getString(1));
                repo.setRepoName(cursor.getString(2));
                repo.setRepoDesc(cursor.getString(3));
                repo.setRepoUrl(cursor.getString(4));
                repoList.add(repo);
            }while(cursor.moveToNext());
        }
        return repoList;
    }

    public Repo getFullRepo(int i){
        Repo repo = new Repo();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM "+ Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        cursor.moveToFirst();

        for (int j = 0; j< i ; j++){
            cursor.moveToNext();
        }
        repo.setId(Integer.parseInt(cursor.getString(0)));
        repo.setRepoOwner(cursor.getString(1));
        repo.setRepoName(cursor.getString(2));
        repo.setRepoDesc(cursor.getString(3));
        repo.setRepoUrl(cursor.getString(4));
        return repo;
    }

    public void deleteRepoById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getCount();
    }


}
