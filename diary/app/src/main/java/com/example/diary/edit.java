package com.example.diary;

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

public class edit extends AppCompatActivity {

    private SQLiteDatabase mysqldb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        final EditText titles = (EditText)findViewById(R.id.titlenames);
        final EditText contents = (EditText)findViewById(R.id.contents);
        TextView usname = (TextView)findViewById(R.id.username);
        TextView time = (TextView)findViewById(R.id.time);
        Button update = (Button)findViewById(R.id.update);
        Button delete = (Button)findViewById(R.id.delete);
        Button cancel = (Button)findViewById(R.id.cancel);
        sqlite mydb = new sqlite(this, "sqlite.db", null, 1);
        mysqldb = mydb.getWritableDatabase();
        final Intent intent4 = new Intent();
        Intent intent3 = getIntent();
        usname.setText(getIntent().getStringExtra("name"));
        final String username = intent3.getStringExtra("name");
        contents.setText(getIntent().getStringExtra("content"));
        titles.setText(getIntent().getStringExtra("title"));
        time.setText(getIntent().getStringExtra("time"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra("id",0);
                String title=titles.getText().toString();
                String content=contents.getText().toString();
                mysqldb.execSQL("UPDATE diary SET title=?,content=?  WHERE id = ?",
                        new String[]{title,content,id+""});
                Toast.makeText(getApplicationContext(), "数据修改成功",  Toast.LENGTH_LONG).show();
                intent4.setClass(edit.this, MainActivity.class);
                intent4.putExtra("username", username);
                startActivity(intent4);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra("id",0);
                mysqldb.delete("diary","id = " + id,null);
                intent4.setClass(edit.this, MainActivity.class);
                intent4.putExtra("username", username);
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
