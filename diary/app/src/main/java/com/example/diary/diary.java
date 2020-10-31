package com.example.diary;

import android.app.AppComponentFactory;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class diary extends AppCompatActivity {
    private SQLiteDatabase mysqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        final EditText title = (EditText)findViewById(R.id.titlenames);
        final EditText content = (EditText)findViewById(R.id.contents);
        TextView usname = (TextView)findViewById(R.id.username);
        TextView time = (TextView)findViewById(R.id.time);
        Button save = (Button)findViewById(R.id.save);
        Button cancel = (Button)findViewById(R.id.cancel);
        sqlite mydb = new sqlite(this, "sqlite.db", null, 1);
        mysqldb = mydb.getWritableDatabase();
        final Intent intent4 = new Intent();
        Intent intent1 = getIntent();
        final String usernames= intent1.getStringExtra("usernames");
        usname.setText("当前用户:"+usernames);

        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy年MM月dd日 HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        final String   str   =   formatter.format(curDate);
        time.setText(str);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues con = new ContentValues();
                con.put("name",usernames);
                con.put("time",str);
                con.put("title",title.getText().toString());
                con.put("content",content.getText().toString());
                mysqldb.insert("diary",null,con);
                Toast.makeText(getApplicationContext(), "数据插入成功",  Toast.LENGTH_LONG).show();
                intent4.setClass(diary.this, MainActivity.class);
                intent4.putExtra("username", usernames);
                startActivity(intent4);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }




}
