package com.yura.tutbyrssreader.api;

import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;
import com.yura.tutbyrssreader.data.NewsData;
import com.yura.tutbyrssreader.data.XmlData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiController {

    private final Retrofit retrofit;
    private final TutByAPI tutByApi;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public ApiController(String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(TikXmlConverterFactory.create(new TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .build();

        tutByApi = retrofit.create(TutByAPI.class);
    }

    public List<NewsData> loadIndexRss(String link) {

        String regex = "[«А-ЯЁ][а-яёА-ЯЁ. ,a-zA-z\\n\\f\\r\\!\\.\\:\\?0-9“”«»—()−/]*[.](?![а-яёА-ЯЁa-zA-z])";

        Call<XmlData> call = tutByApi.loadIndexRss(link);

        ArrayList<NewsData> listItems = new ArrayList<>();
        try {
            Response<XmlData> response = call.execute();
            XmlData rss = response.body();

            Pattern pattern = Pattern.compile(regex);

            rss.getItems().forEach(
                    item -> {
                        String description = item.getDescription();
                        Matcher matcher = pattern.matcher(description);

                        if(matcher.find()){
                            int start = matcher.start();
                            int end = matcher.end();
                            description = description.substring(start, end);
                        }
                        listItems.add(new NewsData(item.getTitle(), item.getLink(),dateFormat.format(new Date(item.getPubDate())), description));

                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItems;
    }
}
