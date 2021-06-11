package com.example.loginregisterlistviewjsonretrofit.model;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.loginregisterlistviewjsonretrofit.model.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * FROM users where gmail=(:gmail) and password=(:password) ")
    UserEntity login (String gmail , String password);
}
