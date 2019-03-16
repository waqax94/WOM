package com.watchoverme.wom.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.watchoverme.wom.Models.Contact;
import com.watchoverme.wom.Models.Notification;
import com.watchoverme.wom.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends ArrayAdapter {

    List notificationList = new ArrayList();
    Context context;


    public NotificationListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    static class Handler{
        TextView text;
        TextView timeDate;
    }

    public void addNotificationList(List<Notification> obj){
        this.notificationList = obj;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.notificationList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.notificationList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list;
        list = convertView;
        final NotificationListAdapter.Handler handler;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = inflater.inflate(R.layout.notification_list_item,parent,false);
            handler = new NotificationListAdapter.Handler();
            handler.text = (TextView) list.findViewById(R.id.notification_text);
            handler.timeDate = (TextView) list.findViewById(R.id.notif_date_time);

            list.setTag(handler);
        }
        else{
            handler = (NotificationListAdapter.Handler)list.getTag();
        }

        final Notification notification;
        notification = (Notification) this.getItem(position);

        handler.text.setText(notification.getText());
        handler.timeDate.setText(notification.getTime() + " " + notification.getDate());

        return list;
    }
}
