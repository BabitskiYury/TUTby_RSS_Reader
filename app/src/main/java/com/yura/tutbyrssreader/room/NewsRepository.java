package com.yura.tutbyrssreader.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

class NewsRepository {

    private NewsDao newsDao;
    private LiveData<List<NewsData>> allNews;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    NewsRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        newsDao = db.newsDao();
        allNews = newsDao.getAlphabetizedNewsLiveData();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<NewsData>> getAllNews() {
        return allNews;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(NewsData newsData) {
        NewsRoomDatabase.databaseWriteExecutor.execute(() -> {
            newsDao.insert(newsData);
        });
    }
}