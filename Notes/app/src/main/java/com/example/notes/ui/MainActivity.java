package com.example.notes.ui;

import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.example.notes.BaseActivity;
import com.example.notes.adapter.NoteAdapter;
import com.example.notes.bean.Notes;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Notes> noteList=new ArrayList<>();
    private ListView listView;
    private NoteAdapter noteAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleBar.setTitle("我的记事本");
        titleBar.getRightTextView().setText("新增");
        titleBar.setOnRightViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddNote.class));
            }
        });
        listView=findViewById(R.id.listView);
        searchView=findViewById(R.id.searchView);

        //初始化适配器
        noteAdapter=new NoteAdapter(this,R.layout.note_item, noteList);
        //设置适配器
        listView.setAdapter(noteAdapter);


        //搜索笔记
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)){
                    noteList.clear();
                    noteList.addAll(dbOpenHelper.getNotes(query));
                    noteAdapter.notifyDataSetChanged();
                    searchView.clearFocus();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    noteList.clear();
                    noteList.addAll(dbOpenHelper.getNotes(null));
                    noteAdapter.notifyDataSetChanged();
                    searchView.clearFocus();
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteList.clear();
        noteList.addAll(dbOpenHelper.getNotes(null));
        noteAdapter.notifyDataSetChanged();
    }
}