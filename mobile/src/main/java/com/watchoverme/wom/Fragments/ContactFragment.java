package com.watchoverme.wom.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.watchoverme.wom.Adapters.ContactListAdapter;
import com.watchoverme.wom.Models.Contact;
import com.watchoverme.wom.Models.IpClass;
import com.watchoverme.wom.Models.ServiceId;
import com.watchoverme.wom.Models.Watcher;
import com.watchoverme.wom.R;
import com.watchoverme.wom.Services.APIService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    ListView contactsListView;
    ContactListAdapter contactListAdapter;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        SharedPreferences loginData = getActivity().getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
        String sId = loginData.getString("service_id","");

        ServiceId serviceModel = new ServiceId(sId);

        String serviceId= serviceModel.getServiceId();

        contactsListView = (ListView)rootView.findViewById(R.id.contact_list);
        contactListAdapter = new ContactListAdapter(getActivity(),R.layout.contact_list_item);
        Toast.makeText(getActivity(),""+serviceId,Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpClass.ipAddress)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final APIService service = retrofit.create(APIService.class);

        Call<List<Watcher>> watcherData = service.processWatcher(serviceId);

        watcherData.enqueue(new Callback<List<Watcher>>() {
            @Override
            public void onResponse(Response<List<Watcher>> response, Retrofit retrofit) {
                final List<Watcher> watchers = response.body();
                contactListAdapter.addContactList(watchers);
                contactsListView.setAdapter(contactListAdapter);
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });

        return rootView;
    }

}
