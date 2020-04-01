package com.watchoverme.wom.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.Log;
import com.watchoverme.wom.Models.LoginResponse;
import com.watchoverme.wom.Models.User;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;
import com.watchoverme.wom.Services.FirebaseIdService;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MainActivity extends AppCompatActivity {

    ImageView loadingIcon;
    boolean loginCheck;
    Button tryAgainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIcon = (ImageView) findViewById(R.id.main_loading);
        tryAgainBtn = (Button) findViewById(R.id.tryagain_btn);

        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(loadingIcon);

        Glide.with(getApplicationContext())
                .load(R.drawable.loadinggif)
                .placeholder(R.drawable.loadinggif)
                .centerCrop()
                .crossFade()
                .into(loadingIcon);

        configure_button();
        loginCheck = loginMethod();


        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingIcon.setVisibility(View.VISIBLE);
                tryAgainBtn.setVisibility(View.INVISIBLE);
                loginMethod();
            }
        });



    }



    public boolean loginMethod(){
        SharedPreferences loginData = getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
        String userName = loginData.getString("user_name","");
        String password = loginData.getString("password","");

        final boolean[] check = new boolean[1];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpClass.ipAddress)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final APIService service = retrofit.create(APIService.class);


        Call<LoginResponse> userData = service.processLogin(userName,password);

        userData.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                if(response.body().getWomId().equals("-1")){
                    Toast.makeText(getApplicationContext(),"Some information has been changed \n" + response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    check[0] = true;
                    finish();
                    startActivity(i);
                }
                else{
                    SharedPreferences loginData = getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = loginData.edit();
                    editor.putString("wom_id", response.body().getWomId());
                    editor.putString("name", response.body().getName());
                    editor.putString("service_id", response.body().getServiceId());
                    editor.apply();
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    check[0] = true;
                    finish();
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Error!\nPlease make sure your internet is working!",Toast.LENGTH_SHORT).show();
                tryAgainBtn.setVisibility(View.VISIBLE);
                loadingIcon.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                //intent.setClassName("com.android.phone", "com.android.phone.Settings");
                //startActivity(intent);
                check[0] = false;
            }
        });


        /*userData.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {

                if(response.body() == null || response.body().equals("")){
                    Toast.makeText(getApplicationContext(),"Login Again",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    check[0] = true;
                    finish();
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                    check[0] = true;
                    finish();
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(),"Connection Error!\nPlease make sure your internet is working!",Toast.LENGTH_SHORT).show();
                tryAgainBtn.setVisibility(View.VISIBLE);
                loadingIcon.setVisibility(View.INVISIBLE);
                //Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                //intent.setClassName("com.android.phone", "com.android.phone.Settings");
                //startActivity(intent);
                check[0] = false;
            }
        });*/

        return  check[0];
    }

    boolean configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return false;
        }

        return true;
    }

}
