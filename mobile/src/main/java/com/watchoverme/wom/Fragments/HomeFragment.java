package com.watchoverme.wom.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
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
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.watchoverme.wom.Activities.HelpMeCancelActivity;
import com.watchoverme.wom.Activities.HomeActivity;
import com.watchoverme.wom.Activities.MainActivity;
import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.Log;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;

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

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    TextView batteryLevel;
    TextView currentDate;
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
    String sId,phone;
    ProgressDialog dialog;


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            bLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryLevel.setText(String.valueOf(bLevel) + "%");
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait...");
        SharedPreferences loginData = getActivity().getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
        phone = loginData.getString("wearerPhone","");
        sId = loginData.getString("serviceId","");

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


                        registrationToken = token;
                    }

                });

        mainLayout = (RelativeLayout) rootView.findViewById(R.id.home_layout);
        batteryLevel = (TextView) rootView.findViewById(R.id.battery_percent);
        currentDate = (TextView) rootView.findViewById(R.id.date_display);
        helpMe = (Button) rootView.findViewById(R.id.help_me_btn);
        //checkConnection = (Button) rootView.findViewById(R.id.test_device_btn);


        currentDate.setText(getCurrentDate());
        getActivity().registerReceiver(this.broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                detector.onTouchEvent(motionEvent);
                return true;
            }
        });

        detector = new GestureDetector(getActivity(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {
                Log log = new Log(sId,new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Show Press the Screen!",
                        "Interaction log", "","", "" + bLevel,"" + registrationToken);
                logs.add(log);
            }


            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                Log log = new Log(sId,new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Tap the Screen!",
                        "Interaction log", "","", "" + bLevel,"" + registrationToken);
                logs.add(log);
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {
                Log log = new Log(sId,new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Long press the Screen!",
                        "Interaction log", "","", "","" + registrationToken);
                logs.add(log);
            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                Log log = new Log(sId,new SimpleDateFormat("dd/MM/yyyy").format(new Date()),getCurrentTime(),"User Swipe the Screen!",
                        "Interaction log", "","", "" + bLevel,"" + registrationToken);
                logs.add(log);
                return true;
            }
        });

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

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
                    public void run() { LocationGetter();
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


        /*timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 30000, 3600000);*/

        /*checkConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(),"Testing Connection",Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);

                Call<String> check = service.connectionCheck();

                check.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        Toast.makeText(getActivity(),response.body(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getActivity(),"Connection Error!\nPlease check your internet connection",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });*/

        helpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),HelpMeCancelActivity.class);
                startActivity(i);

                /*dialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);

                if (configure_button()) {

                    Log log = new Log(sId, new SimpleDateFormat("dd/MM/yyyy").format(new Date()), getCurrentTime(), "Wearer pressed help me button!",
                            "Alert Log", "" + latitude, "" + longitude, "" + bLevel,"" + registrationToken);

                    Call<String> sendLog = service.processHelpMe(log);

                    sendLog.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Response<String> response, Retrofit retrofit) {
                            Toast.makeText(getActivity(),response.body(),Toast.LENGTH_SHORT).show();
                            final Handler handler  = new Handler();
                            final Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            };
                            handler.postDelayed(runnable, 3000);
                            if (logs.size() > 0) {
                                Call<String> sendHourlyLog = service.processInteractionLog(logs);

                                sendHourlyLog.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Response<String> response, Retrofit retrofit) {
                                        logs.clear();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        Toast.makeText(getActivity(), "Connection Error (Interactions)", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        if (broadcastNotificationReceiver != null){
            IntentFilter intentFilter = new  IntentFilter("ACTION_STRING_ACTIVITY");
            getActivity().registerReceiver(broadcastNotificationReceiver, intentFilter);
        }

        return rootView;
    }

    private BroadcastReceiver broadcastNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getBundleExtra("alertNotices");
        }
    };

    /*private void insertHourlyLogs() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpClass.ipAddress)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final APIService service = retrofit.create(APIService.class);

        if (configure_button()) {

            Log log = new Log(sId, new SimpleDateFormat("dd/MM/yyyy").format(new Date()), getCurrentTime(), "Wearer in trouble!",
                    "Hourly Log", "" + latitude, "" + longitude, "" + bLevel,"" + registrationToken);

            Call<String> sendHourlyLog = service.processHourlyLog(log);

            sendHourlyLog.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Response<String> response, Retrofit retrofit) {
                    Toast.makeText(getActivity(), "Hourly Log Inserted", Toast.LENGTH_SHORT).show();
                    if (logs.size() > 0) {
                        Call<String> sendHourlyLog = service.processInteractionLog(logs);

                        sendHourlyLog.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Response<String> response, Retrofit retrofit) {
                                logs.clear();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(getActivity(), "Connection Error (Interactions)", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getActivity(), "Hourly Logs not inserted", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
        }
    }*/


    /*void TimerMethod() {
        getActivity().runOnUiThread(Timer_Tick);
    }

    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            //insertHourlyLogs();
        }
    };*/

    void LocationGetter() {
        if(getActivity() == null)
            return;

        getActivity().runOnUiThread(Location_Tick);
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

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMMM yyyy");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

    boolean configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
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
