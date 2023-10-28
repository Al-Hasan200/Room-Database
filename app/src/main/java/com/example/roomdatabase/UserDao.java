package com.example.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void insertRecord(User users);

    @Query("SELECT EXISTS(SELECT * FROM User WHERE uid = :userId)")
    Boolean is_exist(int userId);

}
