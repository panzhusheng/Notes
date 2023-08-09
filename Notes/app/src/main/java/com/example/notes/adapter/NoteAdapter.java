package com.example.notes.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.db.DBOpenHelper;
import com.example.notes.bean.Notes;
import com.example.notes.R;
import com.example.notes.ui.AddNote;

import java.util.List;

//listview适配器
public class NoteAdapter extends ArrayAdapter<Notes> {
    private Context context;
    private DBOpenHelper dbOpenHelper;

    //构造方法
    public NoteAdapter(@NonNull Context context, int resource, List<Notes> diaryList) {
        super(context, resource,diaryList);
        this.context=context;
        dbOpenHelper=new DBOpenHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notes note=getItem(position);
        @SuppressLint("ViewHolder") View view= LayoutInflater.from(getContext()).inflate(R.layout.note_item,parent,false);
        TextView title = view.findViewById(R.id.title);
        TextView create_time = view.findViewById(R.id.create_time);
        TextView content=view.findViewById(R.id.content);
        ImageView del = view.findViewById(R.id.del);
        LinearLayout constraintLayout=view.findViewById(R.id.con);

        //设置item内容
        title.setText(note.getTitle());
        content.setText(note.getContent());
        create_time.setText(note.getTime());
        //删除item
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbOpenHelper.deleteNotes(String.valueOf(note.getId()))){
                    Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();
                    remove(note);
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //单击item
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AddNote.class);
                //设置flag
                intent.putExtra("flag","update");
                //将笔记内容传递过去
                intent.putExtra("entity",note);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
