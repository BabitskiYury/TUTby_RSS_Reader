package com.yura.tutbyrssreader.listeners;

import com.yura.tutbyrssreader.data.NewsData;

public interface PopupSelectItemListener {
    void onPopupItemSelect(String selectedAction, NewsData item);
}