package com.example.sakta;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class SaktaDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sakta.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "_id";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASS = "password";
    public static final String COL_USER_NAME = "name";

    public SaktaDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COL_USER_PASS + " TEXT NOT NULL, " +
                COL_USER_NAME + " TEXT" +
                ");";
        db.execSQL(sqlUsers);

        // Тестовый пользователь (можно потом убрать)
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, "test@sakta.kg");
        cv.put(COL_USER_PASS, "123456");
        cv.put(COL_USER_NAME, "Тестовый");
        db.insert(TABLE_USERS, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // На будущее
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Регистрация пользователя
    public boolean registerUser(String email, String pass) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USER_EMAIL, email);
        cv.put(COL_USER_PASS, pass);

        long id = -1;
        try {
            id = db.insertOrThrow(TABLE_USERS, null, cv);
        } catch (Exception ignored) { }

        return id != -1;
    }

    // Проверка логина/пароля
    public boolean checkUser(String email, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        String[] cols = { COL_USER_ID };
        String sel = COL_USER_EMAIL + " = ? AND " + COL_USER_PASS + " = ?";
        String[] args = { email, pass };

        Cursor c = db.query(TABLE_USERS, cols, sel, args, null, null, null);
        boolean ok = c.moveToFirst();
        c.close();
        return ok;
    }
}
