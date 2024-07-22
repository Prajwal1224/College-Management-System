package com.example.admin2.notice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {


    private Context context;
    private ArrayList<noticedata> list;

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

        noticedata currentitem = list.get(position);

        holder.delenoticetitle.setText(currentitem.getTitle());

        try {

            if(currentitem.getImage() != null){
            Picasso.get().load(currentitem.getImage()).into(holder.delenoiceimg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.noticedelebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Notice");
                ref.child(currentitem.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(context, "Deleted Notice Successfully", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Something went wrong!!!", Toast.LENGTH_SHORT).show();

                    }
                });
                notifyItemRemoved(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private Button noticedelebtn;
        private TextView delenoticetitle;
        private ImageView delenoiceimg;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticedelebtn = itemView.findViewById(R.id.deletenoticebtn);
            delenoticetitle = itemView.findViewById(R.id.delenoticetitle);
            delenoiceimg = itemView.findViewById(R.id.delenoticeimg);


        }
    }


}
