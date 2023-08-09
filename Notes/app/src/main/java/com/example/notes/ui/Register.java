package com.example.notes.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.BaseActivity;
import com.example.notes.R;

public class Register extends BaseActivity {
    private EditText username,password,pw_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        titleBar.setTitle("欢迎注册");

        username=findViewById(R.id.et_user_name);
        password=findViewById(R.id.et_psw);
        pw_again=findViewById(R.id.et_psw_again);

        //注册
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){
        String name =username.getText().toString();
        String pw   =password.getText().toString();
        String pwa =pw_again.getText().toString();

        if (name.equals("")||pw.equals("")||pwa.equals("")){
            Toast.makeText(Register.this,"请输入完整！",Toast.LENGTH_SHORT).show();
        }
        else {
            //查询用户
            Cursor cursor = db.rawQuery("select * from user where username=?", new String[]{name});
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                if (!pw.equals(pwa)) {
                    Toast.makeText(Register.this, "两次密码不相同！", Toast.LENGTH_SHORT).show();
                } else {
                    if (dbOpenHelper.insertUserData(name, pw)){
                        Toast.makeText(Register.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            } else {
                Toast.makeText(Register.this, "该用户已存在！", Toast.LENGTH_SHORT).show();
            }
        }
    }
}