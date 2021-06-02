package com.example.loginregisterlistviewjsonretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvRegister;
    private EditText etLoginGmail,etLoginPassword;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegister = findViewById(R.id.tvRegister);
        etLoginGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        loginButton = findViewById(R.id.btnLogin);



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginGmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
                } else {
                    //Perform Query
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(email, password);
                            if(userEntity == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                //check internet connection
                                // get from https://www.tutlane.com/tutorial/android/android-internet-connection-status-with-examples#:~:text=In%20android%2C%20we%20can%20determine,connection%20is%20available%20or%20not.
                                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                                boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(connected) {
                                            startActivity(new Intent(MainActivity.this, ListActivity.class));
                                            Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "No Internet Connection" , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    }).start();
                }
            }
        });



        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if you don't have any account go to register page
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });

    }


}