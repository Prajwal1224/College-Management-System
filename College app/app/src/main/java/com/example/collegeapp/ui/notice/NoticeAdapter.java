package com.example.collegeapp.ui.notice;

import android.annotation.SuppressLint;
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
import com.example.collegeapp.Fullimageview;
import com.example.collegeapp.R;


import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {


    private final Context context;
    private final ArrayList<noticedata> list;

    public NoticeAdapter(Context context, ArrayList<noticedata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout, parent, false);


        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, @SuppressLint("RecyclerView") int position) {

        final noticedata currentitem = list.get(position);

        holder.disnoticetitle.setText(currentitem.getTitle());
        holder.date.setText(currentitem.getDate());
        holder.time.setText(currentitem.getTime());

        try {
            if (currentitem.getImage() != null)
                Glide.with(context).load(currentitem.getImage()).into(holder.disnoiceimg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.disnoiceimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Fullimageview.class);
                intent.putExtra( "image", currentitem.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private final TextView disnoticetitle;
        private final TextView date;
        private final TextView time;
        private final ImageView disnoiceimg;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);


            disnoticetitle = itemView.findViewById(R.id.disnoticetitle);
            disnoiceimg = itemView.findViewById(R.id.disnoticeimg);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);


        }
    }


}
