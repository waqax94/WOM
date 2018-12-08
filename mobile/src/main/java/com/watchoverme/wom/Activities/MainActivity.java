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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.Log;
import com.watchoverme.wom.Models.User;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;
import com.watchoverme.wom.Services.FirebaseIdService;

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

    TextView batteryLevel;
    Button helpMe;
    RelativeLayout mainLayout;
    GestureDetector detector;
    LocationManager locationManager;
    LocationListener listener;
    double latitude = 0.0;
    double longitude = 0.0;
    int bLevel;
    ArrayList<Log> logs = new ArrayList<Log>();
    Timer timer;
    Timer locTimer;
    ArrayList<String> allNotifications = new ArrayList<String>();
    TextView notificationPanel;
    String registrationToken;
    Button check;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryLevel.setText(String.valueOf(bLevel) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String REG = "eUppQNG-AC8:APA91bHB5-_BlQlylM514ohDYiOErCDMIm1nfoIRsI33UC4pJL2ajT_ub2CoeY6VfM_i2jan9jrF2cYPQ-8Y3YFRiiF4dwbR8D9yt6uw0g4XEU-ai6at4lied3ggrtXyxYwop-xRWOHVKtb3nS7o270GtCTvQo2Ysw";

        //String RegS7 = "dSU2iZRfRwI:APA91bEv1jzb6YTDmJ39WbMdV7Dq-5kxFewWw6nl2BSXnGIxNROkCtETl4yeGuD6U0r-Rowl7z_FCF__KkjFWStyWaXhEg2gHKMhSC2UKbKXO0eTFunMqhdKj3nLbNId1DTbwXLl0jMFbA2MJRHbE0Vkfzb2Cg9-zg";

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            android.util.Log.d("REG_TOKEN_ERR", String.valueOf(task.getException()));
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();


                        //android.util.Log.d("REG_TOKEN", token);
                        //Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        registrationToken = token;
                    }

                });

        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        batteryLevel = (TextView) findViewById(R.id.battery_percent);
        helpMe = (Button) findViewById(R.id.help_me_btn);
        notificationPanel = (TextView) findViewById(R.id.notification_window);
        check = (Button) findViewById(R.id.check_btn);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences loginData = getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
                String phone = loginData.getString("wearerPhone","");
                String pw = loginData.getString("wearerPassword","");
                String sId = loginData.getString("serviceId","");


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);


                User user = new User (phone,pw);

                Call<String> userData = service.processLogin(user);

                userData.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {

                        if(response.body() == null || response.body().equals("")){
                            Toast.makeText(getApplicationContext(),"Login Again",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });

        this.registerReceiver(this.broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        detector = new GestureDetector(getApplicationContext(), new GestureListener());

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                locTimer = new Timer();
                locTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        LocationGetter();
                    }
                }, 0, 100);
            }

            @Override
            public void onProviderDisabled(String s) {
                locTimer.cancel();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        locTimer = new Timer();
        locTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                LocationGetter();
            }
        }, 0, 5000);


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 30000, 15000);

        helpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);

                if (configure_button()) {

                    Log log = new Log("SVC01", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), getCurrentTime(), "Wearer in trouble!",
                            "Alert Log", "" + latitude, "" + longitude, "" + bLevel,"" + registrationToken);

                    Call<Log> sendLog = service.processLogs(log);

                    sendLog.enqueue(new Callback<Log>() {
                        @Override
                        public void onResponse(Response<Log> response, Retrofit retrofit) {
                            if (logs.size() > 0) {
                                Call<String> sendHourlyLog = service.processInteractionLog(logs);

                                sendHourlyLog.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Response<String> response, Retrofit retrofit) {
                                        logs.clear();
                                        //Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Connection Error (Interactions)", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (broadcastNotificationReceiver != null){
            IntentFilter intentFilter = new  IntentFilter("ACTION_STRING_ACTIVITY");
            registerReceiver(broadcastNotificationReceiver, intentFilter);
        }
    }



    private BroadcastReceiver broadcastNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("alertNotices");
            notificationPanel.setText(bundle.getString("text"));
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    private void insertHourlyLogs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpClass.ipAddress)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final APIService service = retrofit.create(APIService.class);

        if (configure_button()) {

            Log log = new Log("SVC01", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), getCurrentTime(), "Wearer in trouble!",
                    "Hourly Log", "" + latitude, "" + longitude, "" + bLevel,"" + registrationToken);

            Call<String> sendHourlyLog = service.processHourlyLog(log);

            sendHourlyLog.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    Toast.makeText(getApplicationContext(), "Hourly Log Inserted", Toast.LENGTH_SHORT).show();
                    if (logs.size() > 0) {
                        Call<String> sendHourlyLog = service.processInteractionLog(logs);

                        sendHourlyLog.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Response<String> response, Retrofit retrofit) {
                                logs.clear();
                                Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getApplicationContext(), "Connection Error (Interactions)", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getApplicationContext(), "Hourly Logs not inserted", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
        }
    }

    void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            insertHourlyLogs();
        }
    };

    void LocationGetter() {
        this.runOnUiThread(Location_Tick);
    }

    private Runnable Location_Tick = new Runnable() {
        @Override
        public void run() {
            if(configure_button()){
            locationManager.requestLocationUpdates("gps", 0, 0, listener);
            }
        }
    };
    

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

    boolean configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
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

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(getApplicationContext(),"User Tap the Screen!",Toast.LENGTH_SHORT).show();
            Log log = new Log("SVC01",new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Tap the Screen!",
                    "Interaction log", "","", "" + bLevel,"" + registrationToken);
            logs.add(log);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(getApplicationContext(),"User Long press the Screen!",Toast.LENGTH_SHORT).show();
            Log log = new Log("SVC01",new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Long press the Screen!",
                    "Interaction log", "","", "","" + registrationToken);
            logs.add(log);
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(getApplicationContext(),"User Swipe the Screen!",Toast.LENGTH_SHORT).show();
            Log log = new Log("SVC01",new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Swipe the Screen!",
                    "Interaction log", "","", "" + bLevel,"" + registrationToken);
            logs.add(log);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(getApplicationContext(),"User Double tap the Screen!",Toast.LENGTH_SHORT).show();
            Log log = new Log("SVC01",new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Double tap the Screen!",
                    "Interaction log", "","", "" + bLevel,"" + registrationToken);
            logs.add(log);
            return true;
        }
    }

    public void updateNotificationList(String notificationText){
        allNotifications.add(notificationText);
        notificationPanel.setText(notificationText);
        //helpMe.setEnabled(false);
    }

}
