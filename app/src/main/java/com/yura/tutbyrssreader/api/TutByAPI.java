package com.yura.tutbyrssreader.api;

import com.yura.tutbyrssreader.data.XmlData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

interface TutByAPI {

    @GET()
    Call<XmlData> loadIndexRss(@Url String link);
}
