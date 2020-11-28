package com.yura.tutbyrssreader.api;

import com.yura.tutbyrssreader.data.XmlData;

import retrofit2.Call;
import retrofit2.http.GET;

interface TutByAPI {

    @GET("rss/index.rss")
    Call<XmlData> loadIndexRss();
}
