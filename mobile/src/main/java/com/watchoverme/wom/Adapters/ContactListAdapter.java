package com.watchoverme.wom.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    String serviceId;
    String womNumber;
    List contactList = new ArrayList();
    Context context;

    public ContactListAdapter(@NonNull Context context, int resource, String serviceId, String womNumber) {
        super(context, resource);
        this.context = context;
        this.serviceId = serviceId;
        this.womNumber = womNumber;
    }

    static class Handler{
        TextView watcherNum;
        TextView contactName;
        TextView contactNumber;
        ImageButton phoneMeBtn;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    public void addContactList(List<Contact> obj){
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

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            list = inflater.inflate(R.layout.contact_list_item,parent,false);
            handler = new ContactListAdapter.Handler();
            handler.watcherNum = (TextView) list.findViewById(R.id.watcher_no);
            handler.contactName = (TextView) list.findViewById(R.id.contact_name);
            handler.contactNumber = (TextView) list.findViewById(R.id.contact_number);
            handler.phoneMeBtn = (ImageButton) list.findViewById(R.id.call_me_btn);

            list.setTag(handler);
        }
        else{
            handler = (ContactListAdapter.Handler)list.getTag();
        }

        final Contact contact;
        contact = (Contact) this.getItem(position);

        handler.watcherNum.setText(contact.getPriority());
        handler.contactName.setText(contact.getWatcherName());
        handler.contactNumber.setText(contact.getWatcherPhone());

        handler.phoneMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpClass.ipAddress)
                        .addConverterFactory(GsonConverterFactory.create()).build();

                final APIService service = retrofit.create(APIService.class);

                final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Send phone me request to\n" + contact.getWatcherName());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String watcherNumber = "+61" + contact.getWatcherPhone().substring(1);
                        String wearerNumber = "+61" + womNumber.substring(1);

                        String message = "-\nYour wearer need your help! \nCall him/her on following number\n\n"+ wearerNumber
                                + "\n\nRegards \nWOM Team";

                        PhoneMeRequest phoneMeRequest = new PhoneMeRequest(watcherNumber,message,serviceId);

                        final Call<String> request = service.processPhoneMeRequest(phoneMeRequest);

                        request.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Response<String> response, Retrofit retrofit) {
                                Toast.makeText(context,response.body(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Toast.makeText(context,"Error while sending request!",Toast.LENGTH_SHORT).show();
                            }
                        });
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
