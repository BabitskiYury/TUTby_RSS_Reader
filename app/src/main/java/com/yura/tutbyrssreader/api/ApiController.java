package com.yura.tutbyrssreader.api;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.data.XmlData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiController {

    static final String BASE_URL = "https://news.tut.by/";

    private final Retrofit retrofit;
    private final TutByAPI tutByApi;

    public ApiController() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(TikXmlConverterFactory.create(new TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .build();

        tutByApi = retrofit.create(TutByAPI.class);
    }

    public List<NewsData> loadIndexRss() {
        Call<XmlData> call = tutByApi.loadIndexRss();
        ArrayList<NewsData> listItems = new ArrayList<>();
        try {
            Response<XmlData> response = call.execute();
            XmlData rss = response.body();

            rss.getItems().forEach(
                    item -> listItems.add(new NewsData(item.getTitle(), item.getLink(), item.getPubDate(), item.getDescription())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItems;
    }
}
