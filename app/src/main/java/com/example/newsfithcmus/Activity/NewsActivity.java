package com.example.newsfithcmus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsfithcmus.R;

public class NewsActivity extends AppCompatActivity {
   Intent intent;
   private WebView webView;
   private TextView title, date;
   private News news;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_news);
      mapping();
      setupView();
   }
   private void setupView() {
      intent = getIntent();
      int index = intent.getIntExtra("index", 0);
      news = MainActivity.getNewsList().get(index);
      title.setText(news.getTitle());
      date.setText(news.getPubDate());
      webView.loadData(news.getDescription(), "text/html; charset=utf-8", "utf-8");
   }
   private void mapping() {
      webView = findViewById(R.id.webView);
      title = findViewById(R.id.textViewTitleNews);
      date = findViewById(R.id.textViewDateNews);
   }

}
