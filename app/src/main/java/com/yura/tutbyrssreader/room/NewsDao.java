package com.yura.tutbyrssreader.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yura.tutbyrssreader.data.NewsData;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NewsData word);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertList(List<NewsData> word);

    @Query("DELETE FROM news_table")
    void deleteAll();

    @Delete
    void delete(NewsData newsData);

    @Query("SELECT * FROM news_table")
    List<NewsData> getNews();

    @Query("SELECT * FROM news_table ")
    LiveData<List<NewsData>> getNewsLiveData();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(NewsData newsData);
}
