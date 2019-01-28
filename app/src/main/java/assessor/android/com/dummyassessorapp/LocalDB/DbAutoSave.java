package assessor.android.com.dummyassessorapp.LocalDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbAutoSave extends SQLiteOpenHelper {
    Cursor cursor;
    boolean result = false;
    public DbAutoSave(Context context) {
        super(context, "DbAutoSave", null, 1);
    }
  String selectedop;
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table autosave (ID INTEGER PRIMARY KEY AUTOINCREMENT,STUID TEXT,QUE TEXT,SELECTEDOPTION TEXT)";
        String query1 = "create table attenstatus (ID INTEGER PRIMARY KEY AUTOINCREMENT,STUID TEXT,ATTSTATUS TEXT,FINALATT TEXT)";
        db.execSQL(query);
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists autosave");
        db.execSQL("drop table if exists attenstatus");
        onCreate(db);
    }

    public void insertData(String stuid, String queid,String queno ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STUID",queid );
        cv.put("QUE", stuid);
        cv.put("SELECTEDOPTION",queno);
        db.insert("autosave", null, cv);
    }
    public void insertddd(String stuid,String attensta,String finalatten){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("STUID",stuid);
        cv.put("ATTSTATUS",attensta);
        cv.put("FINALATT",finalatten);
        db.insert("attenstatus",null,cv);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+"attenstatus",null);
        return res;
    }

    public Cursor getData(String queryData) {
        String[] selection = {queryData};
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery("select * from autosave", null);
        cursor.close();
        String query = "select * from autosave where NAME =?";
        cursor = db.rawQuery(query, selection);
        if (cursor.getCount() != 0) {
            if (cursor.moveToLast()) {
                //result = true;
            }
        }
        cursor.close();
        return cursor;
    }

    public String getDataOfSingleClient(String Que){
        String selectQuery = "SELECT  * FROM " + "autosave" + " WHERE " + "QUE" + "='" + Que +  "'";

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(selectQuery,null);
        if (cursor.getCount()>0){
            cursor.moveToNext();
            selectedop=cursor.getString(2);
            return selectedop;
        }
        else {
            return  null;
        }

    }

    public void updateData(String stuid, String queid,String queno,String queiddd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("STUID",queid );
        cv.put("QUE", stuid);
        cv.put("SELECTEDOPTION",queno);
        db.update("autosave",cv, "QUE = ?",new String[]{queiddd});
        //db.update("autosave",cv, "_id = ?", new String[]{id});

    }

    public void updateD(String oldOption, String newOption){
      this.getWritableDatabase().execSQL("UPDATE autosave SET SELECTEDOPTION='"+newOption+"'WHERE SELECTEDOPTION='"+oldOption+"'");
    }

    private void onDelete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists autosave");
        onCreate(db);
    }
}
