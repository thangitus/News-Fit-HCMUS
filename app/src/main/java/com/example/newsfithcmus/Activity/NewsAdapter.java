package com.example.newsfithcmus.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfithcmus.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
   List<News> newsList;
   Context context;
   ItemListener itemListener;
   public NewsAdapter(List<News> news, Context context, ItemListener itemListener) {
      this.newsList = news;
      this.context = context;
      this.itemListener = itemListener;
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View itemView = inflater.inflate(R.layout.item, parent, false);
      NewsAdapter.MyViewHolder viewHolder = new NewsAdapter.MyViewHolder(itemView);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      News news = newsList.get(position);
      holder.title.setText(news.getTitle());
      holder.date.setText(news.getPubDate());
   }

   @Override
   public int getItemCount() {
      return newsList.size();
   }
   class MyViewHolder extends RecyclerView.ViewHolder {
      TextView title, date;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         title = itemView.findViewById(R.id.textViewTitle);
         date = itemView.findViewById(R.id.textViewDate);
         itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               itemListener.onItemClickListener(getLayoutPosition());
            }
         });
      }
   }
}
