package com.yura.tutbyrssreader.data;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml
public class Item {
    @PropertyElement
    String title;
    @PropertyElement
    String link;
    @Path("enclosure")
    @Attribute(name = "url")
    String imgUrl;
    @PropertyElement
    String pubDate;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPubDate() {
        return pubDate;
    }
}
