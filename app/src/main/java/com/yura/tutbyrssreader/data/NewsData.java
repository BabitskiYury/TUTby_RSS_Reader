package com.yura.tutbyrssreader.data;

public class NewsData {

    public NewsData(String title, String link, String imgUrl, String pubDate) {
        this.title = title;
        this.link = link;
        this.imgUrl = imgUrl;
        this.pubDate = pubDate;
    }

    String title;
    String link;
    String imgUrl;
    String pubDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
