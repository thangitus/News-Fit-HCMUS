package com.example.newsfithcmus.Activity;

public class News {
   String title;
   String link;
   String pubDate;
   String description;
   public News(String title, String link, String pubDate, String description) {
      this.title = title;
      this.link = link;
      this.pubDate = pubDate;
      this.description = description;
   }
   public String getTitle() {
      return title;
   }
   public void setTitle(String title) {
      this.title = title;
   }
   public String getPubDate() {
      return pubDate;
   }
   public void setPubDate(String pubDate) {
      this.pubDate = pubDate;
   }
   public String getDescription() {
      return description;
   }
   public void setDescription(String description) {
      this.description = description;
   }
   public String getLink() {
      return link;
   }
   public void setLink(String link) {
      this.link = link;
   }
}
