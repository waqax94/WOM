package com.watchoverme.wom.Adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.watchoverme.wom.Models.Contact;
import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.PhoneMeRequest;
import com.watchoverme.wom.Models.Watcher;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ContactListAdapter extends ArrayAdapter {

    List contactList = new ArrayList();
    Context context;

    public ContactListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    static class Handler {
        TextView watcherNum;
        TextView contactName;
        TextView contactNumber;
        ImageButton phoneMeBtn;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    public void addContactList(List<Watcher> obj) {
        this.contactList = obj;
    }

    @Override
    public int getCount() {
        return this.contactList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.contactList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View list;
        list = convertView;
        final ContactListAdapter.Handler handler;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = inflater.inflate(R.layout.contact_list_item, parent, false);
            handler = new ContactListAdapter.Handler();
            handler.watcherNum = (TextView) list.findViewById(R.id.watcher_no);
            handler.contactName = (TextView) list.findViewById(R.id.contact_name);
            handler.contactNumber = (TextView) list.findViewById(R.id.contact_number);
            handler.phoneMeBtn = (ImageButton) list.findViewById(R.id.call_me_btn);

            list.setTag(handler);
        } else {
            handler = (ContactListAdapter.Handler) list.getTag();
        }

        final Watcher watcher;
        watcher = (Watcher) this.getItem(position);

        final String contactNumber = "+" + watcher.getMobileCode() + watcher.getMobile().substring(1);

        if (watcher.getMobileNumPrivacy().equals("Private")) {
            handler.contactNumber.setText("Private");
            handler.phoneMeBtn.setVisibility(View.GONE);
        } else {
            handler.contactNumber.setText(contactNumber);
        }
        handler.watcherNum.setText(watcher.getPriority());
        handler.contactName.setText(watcher.getName());

        handler.phoneMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Call " + watcher.getName());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+contactNumber));
                        context.startActivity(intent);

                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    }
                });

                alertDialog.show();
            }
        });

        return list;
    }
}
