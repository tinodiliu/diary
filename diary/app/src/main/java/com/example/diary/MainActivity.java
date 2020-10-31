package com.example.diary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<diarys> lists;
    private class diarys {
        Integer id;
        String name;
        String title;
        String content;
        String time;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent1 = new Intent();
        Button adds = (Button)findViewById(R.id.add);
        TextView uname = (TextView)findViewById(R.id.uname);
        list = (ListView)findViewById(R.id.list);
        lists= new ArrayList<diarys>();
        sqlite myDB = new sqlite(this, "sqlite.db", null, 1);
        SQLiteDatabase dbreader = myDB.getWritableDatabase();
        Intent intent = getIntent();
        String usernames= intent.getStringExtra("username");
        uname.setText("当前用户:"+usernames);
        intent1.putExtra("usernames", usernames);

        String names=intent.getStringExtra("username");;
        Cursor cursor = dbreader.query("diary",null,"name=?",new String[]{names},null,null,null);

        while(cursor.moveToNext()){
            diarys note = new diarys();
            note.id = cursor.getInt(cursor.getColumnIndex("id"));
            note.name = cursor.getString(cursor.getColumnIndex("name"));
            note.content = cursor.getString(cursor.getColumnIndex("content"));
            note.time = cursor.getString(cursor.getColumnIndex("time"));
            note.title = cursor.getString(cursor.getColumnIndex("title"));
            lists.add(note);
        }

        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1.setClass(MainActivity.this, diary.class);
                startActivity(intent1);
            }
        });

        List<HashMap<String,Object>> ls= new ArrayList<HashMap<String,Object>>();
        for(diarys d : lists){
            HashMap<String,Object> l = new HashMap<String,Object>();
            l.put("name",d.name);
            l.put("title",d.title);
            l.put("content",d.content);
            l.put("time",d.time);
            l.put("id",d.id);
            ls.add(l);
        }

        SimpleAdapter simple =new SimpleAdapter(this,ls,R.layout.list,new String[]{"id","name","title","content","time"},new int[]{R.id.list_id,R.id.list_name,R.id.list_title,R.id.list_content,R.id.list_time});
        list.setAdapter(simple);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent3 = new Intent(MainActivity.this, edit.class);
                intent3.putExtra("id", lists.get(i).id);
                intent3.putExtra("name", lists.get(i).name);
                intent3.putExtra("title", lists.get(i).title);
                intent3.putExtra("content", lists.get(i).content);
                intent3.putExtra("time", lists.get(i).time);
                startActivity(intent3);
            }
        });


    }



}