package com.yura.tutbyrssreader.data;

import java.io.Serializable;

public class NewsData implements Serializable {

    public NewsData(String title, String link, String pubDate, String description) {
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.description = description;
    }

    String title;
    String link;
    String pubDate;
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
