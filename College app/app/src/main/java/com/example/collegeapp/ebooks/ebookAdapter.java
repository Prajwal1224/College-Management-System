package com.example.collegeapp.ebooks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegeapp.R;

import java.util.List;

public class ebookAdapter extends RecyclerView.Adapter<ebookAdapter.ebookViewHolder> {

    private Context context;
    private List<ebookData> list;

    public ebookAdapter(Context context, List<ebookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ebookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout,parent,false);

        return new ebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ebookViewHolder holder,int position) {

        holder.pdftxt.setText(list.get(position).getPdfTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, pdfviewer.class);
                intent.putExtra("pdfUrl",list.get(holder.getAdapterPosition()).getPdfUrl());
                context.startActivity(intent);
            }
        });

        holder.pdfdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(holder.getAdapterPosition()).getPdfUrl()));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ebookViewHolder extends RecyclerView.ViewHolder {

        private TextView pdftxt;
        private ImageView pdfdownload;
        public ebookViewHolder(@NonNull View itemView) {
            super(itemView);

            pdftxt = itemView.findViewById(R.id.pdftxt);
            pdfdownload = itemView.findViewById(R.id.ebookdownload);

        }
    }

}
