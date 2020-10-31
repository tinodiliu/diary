package com.example.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class sqlite extends SQLiteOpenHelper {

    public static  final  String CREATE_DIARY="create table diary(" +
            "id integer primary key autoincrement," +
            "name TEXT NOT NULL," +
            "title TEXT NOT NULL," +
            "content TEXT NOT NULL," +
            "time TEXT NOT NULL)";
    private  Context mcontext;

    public sqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
        Toast.makeText(mcontext,"创建成功！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}