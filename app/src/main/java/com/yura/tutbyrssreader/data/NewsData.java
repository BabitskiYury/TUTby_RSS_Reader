package com.yura.tutbyrssreader.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.yura.tutbyrssreader.NewsState;
import com.yura.tutbyrssreader.room.StateConverter;

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
    public int id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "link")
    public String link;
    @ColumnInfo(name = "pubDate")
    public String pubDate;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "state")
    @TypeConverters({StateConverter.class})
    public NewsState state;

    public int getId() {
        return id;
    }

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

    public NewsState getState() {
        return state;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(NewsState state) {
        this.state = state;
    }
}
