package com.yura.tutbyrssreader.data;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml
public class Item {
    @PropertyElement
    String title;
    @PropertyElement
    String link;
    @PropertyElement
    String pubDate;
    @PropertyElement
    String description;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getDescription() {
        return description;
    }
}
