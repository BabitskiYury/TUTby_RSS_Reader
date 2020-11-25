package com.yura.tutbyrssreader.data;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml
public class XmlData {
    @Path("channel")
    @Element
    List<Item> items;

    public List<Item> getItems() {
        return items;
    }
}

