package com.example.loginregisterlistviewjsonretrofit.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginregisterlistviewjsonretrofit.R;
import com.example.loginregisterlistviewjsonretrofit.model.UserDao;
import com.example.loginregisterlistviewjsonretrofit.model.UserDatabase;
import com.example.loginregisterlistviewjsonretrofit.model.UserEntity;

public class RegisterActivity extends AppCompatActivity {

    private Button registerBtn, gotoLoginBtn;
    private EditText regGmail, regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fGmail = regGmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                if (fPassword.isEmpty() || fGmail.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                    //create user Entity
                    UserEntity userEntity = new UserEntity();
                    userEntity.setGmail(regGmail.getText().toString());
                    userEntity.setPassword(regPassword.getText().toString());
                    //Do insert operation
                    Log.e("entity", userEntity.getGmail() + "," + userEntity.getPassword());
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //register user
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // after register go to login page
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}