package com.example.loginregisterlistviewjsonretrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyInterface {

    //this line is representing the URL line from where we will get the JSON data.
    //go to mocky.io and generate your json url
    String JSONURL = "https://run.mocky.io/v3/ee1b94ad-02f8-4d00-8c03-e2b2a889794c/";

    //holds the name of the php file
    @GET("json_parsing.php")
    Call<String> getString();
}
