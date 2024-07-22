package com.example.collegeapp.ui.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.collegeapp.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private final List<teacherdata> list;
    private final Context context;

    public TeacherAdapter(List<teacherdata> list, Context context, String category) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent, false);

        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {

        teacherdata item = list.get(position);
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        holder.email.setText(item.getMail());
        try {
            Glide.with(context).load(item.getImage()).placeholder(R.drawable.person).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {


        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView post;
        private final TextView email;
        private final ImageView imageView;


        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.teachername);
            email = itemView.findViewById(R.id.teachermail);
            post = itemView.findViewById(R.id.teacherpost);
            imageView = itemView.findViewById(R.id.teacherimage);


        }
    }

}
