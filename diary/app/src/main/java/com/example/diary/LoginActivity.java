package com.example.diary;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button signin = (Button)findViewById(R.id.signin);
        Button signup = (Button)findViewById(R.id.signup);
        Button skip = (Button)findViewById(R.id.skip);
        final CheckBox remember = (CheckBox)findViewById(R.id.remember);
        final CheckBox show = (CheckBox)findViewById(R.id.show);
        final EditText user = (EditText)findViewById(R.id.user);
        final EditText password = (EditText)findViewById(R.id.password);
        final Intent intent = new Intent();
        //准备写入对象
        final SharedPreferences mysp = getSharedPreferences("passport", MODE_PRIVATE);

        //根据自动填入设置填入账号和密码
        boolean flag = mysp.getBoolean("ischecked", true);
        if (flag) {
            System.out.println("22222");
            user.setText(mysp.getString("default_username", null));
            password.setText(mysp.getString("default_password", null));
            remember.setChecked(true);
        }

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show.isChecked()){
                    showOrHide(password,true);
                }else{
                    showOrHide(password,false);
                }
            }
        });

        //注册部分
        signup.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String in_usename = user.getText().toString();
                String in_password = password.getText().toString();
                if (in_password.equals("")||in_usename.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名或者密码未填写", Toast.LENGTH_LONG).show();
                } else if (mysp.getString("username"+in_usename, "").equals("")) {
                    SharedPreferences.Editor editor = mysp.edit();
                    editor.putString("username"+in_usename, in_usename);
                    editor.putString("password"+in_usename, in_password);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "注册成功",  Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名已经存在",  Toast.LENGTH_LONG).show();
                }
            }
        });
        // 登陆部分
        signin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String in_usename = user.getText().toString();
                String in_password = password.getText().toString();
                if (in_password.equals(mysp.getString("password"+in_usename, null))&&in_usename.equals(mysp.getString("username"+in_usename, ""))) {
                    SharedPreferences.Editor editor = mysp.edit();
                    //记住账号密码
                    if (remember.isChecked()) {
                        editor.putString("default_username",in_usename );
                        editor.putString("default_password", in_password);
                        editor.putBoolean("ischecked",true);
                    } else {
                        editor.putString("default_username",null );
                        editor.putString("default_password", null);
                        editor.putBoolean("ischecked", false);
                    }
                    editor.commit();
                    intent.putExtra("username", in_usename);
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "用户名或者密码错误",  Toast.LENGTH_LONG).show();
                }
            }
        });
        //跳过部分
        skip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String default_user = "default";
                Toast.makeText(getApplicationContext(), "以默认用户登录",  Toast.LENGTH_SHORT).show();
                intent.putExtra("username", default_user);
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showOrHide(EditText password,boolean isShow){
        //记住光标开始的位置
        int pos = password.getSelectionStart();
        if(isShow){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        password.setSelection(pos);
    }


}