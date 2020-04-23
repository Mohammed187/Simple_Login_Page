package com.example.simple_login_page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME ="registeruser";
    public static final String COL_ID ="ID";
    public static final String COL_USER ="username";
    public static final String COL_PASS ="password";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table with 3 columns ( ID, Username, Password )
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER + "TEXT," + COL_PASS + "TEXT)";

        db.execSQL(CREATE_USER_TABLE);

        // YOU CAN CREATE IT ON THIS WAY
        //db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT)");
    }

    // UPGRADE DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //DROP USERS TABLE IF EXISTS
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);

        // CREATE THE TABLE AGAIN
        onCreate(db);
    }

    // ADD NEW USER
    long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COL_USER,user); // User name
        values.put(COL_PASS,password); // Contact password

        // Insert a row
        long res = db.insert(TABLE_NAME,null,values);
        db.close();
        return  res;
    }

    // Function to Check if the user Exist or no
    boolean checkUser(String username, String password){
        String[] columns = { COL_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_USER + "=?" + " and " + COL_PASS + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        int count = cursor.getCount();

        //Close the connection
        cursor.close();
        db.close();

        return count > 0;
    }
}