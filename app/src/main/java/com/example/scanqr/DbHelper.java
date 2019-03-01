package com.example.scanqr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper  extends SQLiteOpenHelper {
  public  static final String TABLE_NAME="mytable";
    public  static final String DATABASE_NAME="qrdb.db";

    public  static final String col_1="id";
    public  static final String col_2="code";
    public  static final String col_3="type";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null ,1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "code TEXT, type TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    onCreate(db);
    }

    public boolean insertData(String code, String type){
        ContentValues contentValues= new ContentValues();
        contentValues.put(col_2,code);
        contentValues.put(col_3,type);

        SQLiteDatabase db=this.getWritableDatabase();
        long result=db.insert(TABLE_NAME,null,contentValues);
        if (result==-1){
            return false;

        }else return true;
    }
    public ArrayList<data>getAllInformation(){
        ArrayList<data> arrayList=new ArrayList<>();
        SQLiteDatabase database=this.getReadableDatabase() ;
        Cursor cursor=database.rawQuery("Select* FROM "+TABLE_NAME,null );
        if(cursor!=null){
            while (cursor.moveToNext()){
                int id=cursor.getInt(0);
                String code= cursor.getString(1);
                String type=cursor.getString(2);
                data listitem=new data(id,code,type);
                arrayList.add(listitem);
            }
        }
cursor.close();
        return arrayList;
    }

    public void deleteRow(int value){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ "- WHERE "+ col_1+"-"+value);
    }
}



