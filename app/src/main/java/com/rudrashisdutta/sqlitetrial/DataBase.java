package com.rudrashisdutta.sqlitetrial;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.LinkedHashMap;

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "SQLiteTrial";
    private static final int DB_VER = 1;
    private static final String DB_TABLE = "trial";

    public static final String DESCENDING = "DESC";
    public static final String ASCENDING = "ASC";

    private SQLiteDatabase database;

    DataBase(Context context){
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL("create table " + DB_TABLE + " (_key text primary key, value text, date INTEGER unique, num REAL);");
            Log.i("Created Table", DB_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("drop table if exists " + DB_TABLE);
            onCreate(sqLiteDatabase);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean delete(String key){
        boolean success = false;
        try{
            database = getWritableDatabase();
            database.execSQL("delete from " + DB_TABLE + " where _key = '" + key + "'");
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
    public boolean empty(){
        boolean success = false;
        try{
            database = getWritableDatabase();
            database.execSQL("delete from " + DB_TABLE);
            success = true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean save(String key, String value){
        boolean success = false;
        try{
            database = getWritableDatabase();
            Date dateTimeNow = new Date();
            database.execSQL("insert into " + DB_TABLE + " (_key, value, date, num) values('" + key + "','" + value + "'," + dateTimeNow.getTime() + ",122233.221112)");
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
    public LinkedHashMap<String, String> get(String ORDER){
        database = getWritableDatabase();
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        try{
            try (Cursor cursor = database.rawQuery("select * from " + DB_TABLE + " ORDER BY date " + ORDER + ";", null)) {
                while (cursor.moveToNext()) {
                    data.put(cursor.getString(0), cursor.getString(1));
                    Log.e("t", cursor.getLong(2) + "  " + cursor.getDouble(3) + "");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public static long howMany(Context context){
        long count = -1;
        SQLiteDatabase database = new DataBase(context).getReadableDatabase();
        try(Cursor cursor = database.rawQuery("select count(*) from " + DB_TABLE + ";", null)){
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        return count;
    }
}
