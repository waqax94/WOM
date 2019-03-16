package com.watchoverme.wom.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.watchoverme.wom.Adapters.NotificationListAdapter;
import com.watchoverme.wom.Models.Notification;
import com.watchoverme.wom.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    ListView notificationList;
    NotificationListAdapter notificationListAdapter;
    List<Notification> notifications = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        notificationListAdapter = new NotificationListAdapter(getActivity(),R.layout.notification_list_item);


        notificationList = (ListView)rootView.findViewById(R.id.notification_list);
        notificationListAdapter.addNotificationList(notifications);
        notificationList.setAdapter(notificationListAdapter);

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

            notifications.add(0,new Notification(bundle.getString("text"),getCurrentTime(),getCurrentDate()));
            notificationListAdapter.addNotificationList(notifications);
            notificationListAdapter.notifyDataSetChanged();

            //notifications.setText(bundle.getString("text"));
        }
    };

    public String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm aa");
        String strTime = mdformat.format(calendar.getTime());
        return strTime;
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

}
