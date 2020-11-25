package com.yura.tutbyrssreader.Api;

import android.util.Log;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.data.XmlData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiController {

    static final String BASE_URL = "https://news.tut.by/";

    private final Retrofit retrofit;
    private final TutByAPI tutByApi;

    public ApiController() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(new TikXml.Builder().exceptionOnUnreadXml(false).build())).build();

        tutByApi = retrofit.create(TutByAPI.class);
    }

    public List<NewsData> loadIndexRss() {
        Call<XmlData> call = tutByApi.loadIndexRss();
        ArrayList<NewsData> listItems = new ArrayList<>();
        call.enqueue(new Callback<XmlData>() {
            @Override
            public void onResponse(Call<XmlData> call, Response<XmlData> response) {
                if (response.isSuccessful()) {
                    XmlData rss = response.body();

                    rss.getItems().forEach(
                            item -> {
                                listItems.add(new NewsData(item.getTitle(), item.getLink(), item.getImgUrl(), item.getPubDate()));
                                Log.d("item2412412", item.getTitle() + " " + item.getLink() + " " + item.getImgUrl() + " " + item.getPubDate());
                            });

                } else {
                    Log.d("item2412412", String.valueOf(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<XmlData> call, Throwable t) {
                Log.d("item2412412", t.getMessage());
            }
        });
        return listItems;
    }
}
