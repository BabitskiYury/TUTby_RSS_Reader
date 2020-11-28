package com.yura.tutbyrssreader.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yura.tutbyrssreader.data.NewsData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {NewsData.class}, version = 1, exportSchema = false)
public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();

    private static volatile NewsRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NewsRoomDatabase.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
