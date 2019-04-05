package com.example.himani_k.greeting_card.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AddFavourites";
    private static final String TABLE_Name = "favourite";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Name + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
        Log.d("msg",""+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Name);
        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    public void addCards(Favourtites fav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, fav.getImage()); // Contact Name
        // Inserting Row
        db.insert(TABLE_Name, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get all cards in a recycler view
    public List<Favourtites> getAllCards() {
        List<Favourtites> Fav_List = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Name;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Favourtites fav=new Favourtites();
                fav.setId(Integer.parseInt(cursor.getString(0)));
                fav.setImage(cursor.getString(1));
                // Adding contact to list
                Fav_List.add(fav);
            } while (cursor.moveToNext());
        }

        // return contact list
        return Fav_List;
    }

    // Deleting single contact
    public void deleteCards(Favourtites fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Name, KEY_ID + " = ?",
                new String[] { String.valueOf(fav.getId()) });
        db.close();
    }
}