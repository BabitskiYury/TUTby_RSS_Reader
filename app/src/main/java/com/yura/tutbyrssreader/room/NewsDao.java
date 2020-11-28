package com.yura.tutbyrssreader.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NewsData word);

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Query("SELECT * FROM news_table ORDER BY title ASC")
    List<NewsData> getAlphabetizedNews();

    @Query("SELECT * FROM news_table ORDER BY title ASC")
    LiveData<List<NewsData>> getAlphabetizedNewsLiveData();
}
