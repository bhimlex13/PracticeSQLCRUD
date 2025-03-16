package com.example.practicesqlcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    //USER TABLE = user_id (PK) | username | password
    private static final String USER_TABLE = "Users";
    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";


    //PRODUCT TABLE = product_id (PK) | user_id (FK) | product
    private static final String PRODUCT_TABLE = "Products";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT = "product";



    private static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " (" +
            USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERNAME  + " TEXT, " +
            PASSWORD + " TEXT)";

    private static final String CREATE_PRODUCT_TABLE = "CREATE TABLE " + PRODUCT_TABLE + " (" +
            PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_ID + " INTEGER, " +
            PRODUCT + " TEXT, " +
            "FOREIGN KEY (" + USER_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + "))";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        onCreate(db);
    }

    public boolean insertUser(String userName, String passWord){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, userName);
        cv.put(PASSWORD, passWord);
        long result = db.insert(USER_TABLE,null, cv);
        return result != -1;
    }

    public boolean insertProduct(int userId, String product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_ID, userId);
        cv.put(PRODUCT, product);
        long result = db.insert(PRODUCT_TABLE, null, cv);
        return result != -1;
    }

    public Cursor getAllProductsById(int userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Products.product FROM Users LEFT JOIN Products ON Users.user_id = Products.user_id WHERE Users.user_id =?",new String[]{String.valueOf(userId)});
    }

    public void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
