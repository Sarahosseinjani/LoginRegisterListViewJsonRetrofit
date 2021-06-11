package com.example.loginregisterlistviewjsonretrofit.view;


import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.loginregisterlistviewjsonretrofit.ConnectionLiveData;
import com.example.loginregisterlistviewjsonretrofit.viewModel.ConnectionModel;
import com.example.loginregisterlistviewjsonretrofit.adaptor.ListAdaptor;
import com.example.loginregisterlistviewjsonretrofit.viewModel.ModelListView;
import com.example.loginregisterlistviewjsonretrofit.MyInterface;
import com.example.loginregisterlistviewjsonretrofit.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//get help from https://demonuts.com/android-retrofit/#list
public class ListActivity extends AppCompatActivity  {

    public static final int MobileData = 2;
    public static final int WifiData = 1;

    private ListView listView;
    private ListAdaptor listAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.lv);
        //Live data object and setting an observer on it
        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, new Observer<ConnectionModel>() {
            @Override
            public void onChanged(@Nullable ConnectionModel connection) {
                //every time connection state changes, we'll be notified and can perform action accordingly
                if (connection.getIsConnected()) {
                    switch (connection.getType()) {
                        case WifiData:
                            Toast.makeText(getApplicationContext(), String.format("Wifi turned ON"), Toast.LENGTH_SHORT).show();
                            getJSONResponse();
                            break;
                        case MobileData:
                            Toast.makeText(getApplicationContext(), String.format("Mobile data turned ON"), Toast.LENGTH_SHORT).show();
                            getJSONResponse();
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Connection turned OFF"), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //getJSONResponse();

    }

    private void getJSONResponse(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyInterface.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MyInterface api = retrofit.create(MyInterface.class);

        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            //get the JSON response in the string format
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());

                        String jsonresponse = response.body().toString();
                        writeListView(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void writeListView(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            ArrayList<ModelListView> modelListViewArrayList = new ArrayList<>();
            //get the json array from the field “data“
            JSONArray dataArray  = obj.getJSONArray("data");

            //In the every iteration, fetch the information and set it in the object of the ModelListView class
               for (int i = 0; i < dataArray.length(); i++) {

                    ModelListView modelListView = new ModelListView();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    modelListView.setImgURL(dataobj.getString("avatar"));
                    modelListView.setName(dataobj.getString("first_name"));
                    modelListView.setEmail(dataobj.getString("email"));

                    modelListViewArrayList.add(modelListView);

                }

                listAdapter = new ListAdaptor(this, modelListViewArrayList);
                listView.setAdapter(listAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}