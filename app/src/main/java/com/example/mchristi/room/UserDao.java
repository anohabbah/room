package com.example.mchristi.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user_table ")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user_table WHERE uid IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);


    @Query("SELECT * FROM user_table WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Delete
    void deleteAll(User... users);

}


