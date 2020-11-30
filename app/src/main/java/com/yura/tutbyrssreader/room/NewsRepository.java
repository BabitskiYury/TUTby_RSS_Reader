package com.yura.tutbyrssreader.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.yura.tutbyrssreader.NewsState;
import com.yura.tutbyrssreader.api.ApiController;
import com.yura.tutbyrssreader.data.NewsData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsRepository {

    private NewsDao newsDao;
    private LiveData<List<NewsData>> allNews;

    public NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        newsDao = db.newsDao();
        allNews = newsDao.getNewsLiveData();
    }

    public LiveData<List<NewsData>> getAllNews() {
        return allNews;
    }

    public void insert(NewsData newsData) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> newsDao.insert(newsData));
    }

    public void loadDataFromNetwork(String baseUrl, String link) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {

            ApiController apiController = new ApiController(baseUrl);
            List<NewsData> data = apiController.loadIndexRss(link);

            if (!data.isEmpty()) {
                List<NewsData> oldItems = allNews.getValue();
                for (int i = 0; i < oldItems.size(); i++) {
                    for (int j = 0; j < data.size(); j++) {
                        if (oldItems.get(i).title.equals(data.get(j).title)) {
                            data.get(j).state = oldItems.get(i).state;
                            break;
                        }
                    }
                }
            }

            newsDao.deleteAll();
            newsDao.insertList(data);
        });
    }

    public void update(NewsData item) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> newsDao.update(item));
    }

    public void checkDates(int daysCount) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<NewsData> items = allNews.getValue();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            int days;
            long milliseconds;
            for (NewsData item: items) {
                try {
                    if(item.state == NewsState.DONE){
                        Date date1 = dateFormat.parse(item.getPubDate());
                        Date date2 = dateFormat.parse(dateFormat.format(new Date()));

                        milliseconds = date2.getTime() - date1.getTime();

                        days = (int) (milliseconds / (24 * 60 * 60 * 1000));

                        if(days>daysCount)
                            newsDao.delete(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}