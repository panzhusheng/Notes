package com.example.notes.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.notes.BaseActivity;
import com.example.notes.R;

public class Login extends BaseActivity {
    private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleBar.setTitle("登录");
        titleBar.setDisplayLeftView(false);

        username=findViewById(R.id.et_user_name);
        password=findViewById(R.id.et_psw);

        String name=SPUtils.getInstance().getString("username");
        String pw=SPUtils.getInstance().getString("password");
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(pw)){
            username.setText(name);
            password.setText(pw);
        }
        //点击登录
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //去注册
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this, Register.class);
                intent.putExtra("flag","login");
                startActivity(intent);
            }
        });
    }
    public void login(){
        String name =username.getText().toString();
        String pw   =password.getText().toString();

        if (name.equals("")||pw.equals("")){
            Toast.makeText(Login.this,"请输入账号密码！",Toast.LENGTH_SHORT).show();
        }
        else {
            //查询用户
            @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from user where username=?",new String[]{name});
            cursor.moveToFirst();
            if (cursor.getCount()==0){
                Toast.makeText(Login.this,"不存在该用户！",Toast.LENGTH_SHORT).show();
            }
            else{
                @SuppressLint("Range") String password=cursor.getString(cursor.getColumnIndex("password"));
                if (pw.equals(password)){
                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    //保存用户名到SharedPreferences
                    SPUtils.getInstance().put("username",name);
                    SPUtils.getInstance().put("password",pw);
                }else {
                    Toast.makeText(Login.this,"密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}