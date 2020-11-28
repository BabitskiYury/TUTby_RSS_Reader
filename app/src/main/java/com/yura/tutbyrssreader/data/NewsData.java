package com.yura.tutbyrssreader.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "news_table")
public class NewsData implements Serializable {

    public NewsData(String title, String link, String pubDate, String description) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
    }
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "link")
    String link;
    @ColumnInfo(name = "pubDate")
    String pubDate;
    @ColumnInfo(name = "description")
    String description;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }
}
