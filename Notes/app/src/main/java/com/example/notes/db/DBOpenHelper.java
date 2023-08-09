package com.example.notes.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blankj.utilcode.util.SPUtils;
import com.example.notes.bean.Notes;

import java.util.ArrayList;
import java.util.List;


public class DBOpenHelper extends SQLiteOpenHelper {
     private DBOpenHelper dbOpenHelper;
     private SQLiteDatabase db;
     private static final String DBNAME="notes.db";
     private static final int VERSION=1;
     public DBOpenHelper(Context context) {
         super(context, DBNAME, null, VERSION);
        dbOpenHelper=this;
        db=dbOpenHelper.getWritableDatabase();
     }
    //创建数据库
     @Override
     public void onCreate(SQLiteDatabase db) {
    //创建数据表
     db.execSQL("create table if not exists user(id INTEGER primary key autoincrement,username varchar(25),password varchar(20))");
     db.execSQL("create table if not exists notes(id INTEGER primary key autoincrement,title varchar(20),content varchar(255),time varchar(20)," +
             "username varchar(20))");
     }
    //升级数据库
     @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
 
     }

    //插入用户数据
    public boolean insertUserData(String username,String password){
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        return db.insert("user",null,contentValues)>0;
    }

    public boolean insertNotes(String title,String content,String time,String username){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("time",time);
        contentValues.put("username",username);
        return db.insert("notes",null,contentValues)>0;
    }

    public boolean updateNotes(String id,String title,String content,String time){
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("content",content);
        contentValues.put("time",time);
        String sql="id=?";
        String[] strings=new String[]{id};
        return db.update("notes",contentValues,sql,strings)>0;
    }

    public boolean deleteNotes(String id){
        String sql="id=?";
        String[] contentValuesArray=new String[]{id};
        return db.delete("notes",sql,contentValuesArray)>0;
    }

    //获取笔记
    public List<Notes> getNotes(String query){
        List<Notes> list=new ArrayList<>();
        Cursor cursor;
        if (query==null){
            cursor=db.rawQuery("select * from notes where username =?",new String[]{SPUtils.getInstance().getString("username")});
        }
        else {
            cursor=db.rawQuery("select * from notes where username =? and title like ?",new String[]{SPUtils.getInstance().getString("username"),"%"+query+"%"});
        }
        if (cursor!=null){
            while (cursor.moveToNext()){
                @SuppressLint("Range") int id=cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title=cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String content=cursor.getString(cursor.getColumnIndex("content"));
                @SuppressLint("Range") String time=cursor.getString(cursor.getColumnIndex("time"));
                @SuppressLint("Range") String user=cursor.getString(cursor.getColumnIndex("username"));
                Notes note=new Notes(id,title,content,time,user);
                list.add(note);
            }
            cursor.close();
        }
        return list;
    }
}