package com.example.newsfithcmus.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfithcmus.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemListener {
   private static final String TAG = "MainActivity";
   private static List<News> newsList;
   private RecyclerView recyclerView;
   private ProgressDialog progressDialog;
   public static List<News> getNewsList() {
      return newsList;
   }
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      recyclerView = findViewById(R.id.recycleListView);
      progressDialog = new ProgressDialog(this);
      progressDialog.setMessage("Loading news...");
      progressDialog.show();
      new DownloadFilesTask().execute();
   }
   private InputStream getInputStream(URL url) {
      try {
         return url.openConnection().getInputStream();
      } catch (IOException e) {
         return null;
      }
   }
   void createAdapter() {
      NewsAdapter adapter = new NewsAdapter(newsList, this, this);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
      recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
   }
   @Override
   public void onItemClickListener(int pos) {
      Intent intent = new Intent(this, NewsActivity.class);
      intent.putExtra("index", pos);
      startActivity(intent);
   }

   public class DownloadFilesTask extends AsyncTask<String, Integer, List<News>> {
      @Override
      protected List<News> doInBackground(String... urls) {
         List<News> news = new ArrayList<>();
         URL url = null;
         try {
            url = new URL("https://www.fit.hcmus.edu.vn/vn/feed.aspx");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(getInputStream(url), "UTF_8");
            boolean insideItem = false;
            // getEventType() Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = parser.getEventType();
            String title = null, link = null, date = null, des = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
               if (eventType == XmlPullParser.START_TAG) {

                  if (parser.getName().equalsIgnoreCase("item")) {
                     insideItem = true;
                  } else if (parser.getName().equals("title")) {
                     if (insideItem)
                        title = parser.nextText();
                  } else if (parser.getName().equals("link")) {
                     if (insideItem)
                        link = parser.nextText();
                  } else if (parser.getName().equals("pubDate")) {
                     if (insideItem)
                        date = parser.nextText();
                  } else if (parser.getName().equals("description")) {
                     if (insideItem)
                        des = parser.nextText();
                  }
               } else if (eventType == XmlPullParser.END_TAG && parser.getName().equals("item")) {
                  insideItem = false;

                  news.add(new News(title, link, date, des));
               }

               eventType = parser.next(); //move to next element
            }
            return news;
         } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
         }

         return null;
      }

      @Override
      protected void onProgressUpdate(Integer... values) {

      }

      @Override
      protected void onPostExecute(List<News> news) {
         progressDialog.dismiss();
         newsList = news;
         createAdapter();
      }
   }
}
