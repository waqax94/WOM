package com.watchoverme.wom.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.User;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

    EditText phone,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText) findViewById(R.id.login_phone);
        password = (EditText) findViewById(R.id.login_pw);
        login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);


                User user = new User (phone.getText().toString(),password.getText().toString());

                Call<String> userData = service.processLogin(user);

                userData.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {

                        if(response.body() == null || response.body().equals("")){
                            Toast.makeText(getApplicationContext(),"Incorrect Credentials",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                            SharedPreferences loginData = getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = loginData.edit();
                            editor.putString("wearerPhone", phone.getText().toString());
                            editor.putString("wearerPassword", password.getText().toString());
                            editor.putString("serviceId",response.body().substring(0,12));
                            editor.putString("wearerName",response.body().substring(13));
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}
