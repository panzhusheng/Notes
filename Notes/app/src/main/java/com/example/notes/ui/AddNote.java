package com.example.notes.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.notes.BaseActivity;
import com.example.notes.bean.Notes;
import com.example.notes.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNote extends BaseActivity {
    private EditText title,content;
    private TextView number,time;
    private String flag;
    private Notes note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
        number=findViewById(R.id.number);
        time=findViewById(R.id.time);
        flag = getIntent().getStringExtra("flag");
        titleBar.getRightTextView().setText("保存");
        if (flag!=null){
            note = (Notes) getIntent().getSerializableExtra("entity");
            title.setText(note.getTitle());
            content.setText(note.getContent());
            time.setText("上次修改时间:"+ note.getTime());
            number.setText(note.getContent().length()+"字");
            titleBar.setTitle("修改记事本");
        }
        //设置时间
        else {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
            time.setText(simpleDateFormat.format(date));
            titleBar.setTitle("新增记事本");
        }

        //更新字数
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                number.setText(content.getText().toString().length()+"字");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        titleBar.setOnRightViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.CHINA);
                if (!"".equals(title.getText().toString())&&!"".equals(content.getText().toString())){
                    if (flag==null){
                        if (dbOpenHelper.insertNotes(title.getText().toString(),content.getText().toString(),simpleDateFormat.format(date), SPUtils.getInstance().getString("username"))){
                            Toast.makeText(AddNote.this, "保存成功！", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddNote.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        if (dbOpenHelper.updateNotes(String.valueOf(note.getId()),title.getText().toString(),content.getText().toString(),simpleDateFormat.format(date))){
                            Toast.makeText(AddNote.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(AddNote.this, "未知错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                    finish();
                }
                else {
                    Toast.makeText(AddNote.this, "请输入内容！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}