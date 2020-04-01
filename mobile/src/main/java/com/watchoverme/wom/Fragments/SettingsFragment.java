package com.watchoverme.wom.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import com.watchoverme.wom.Activities.LoginActivity;
import com.watchoverme.wom.Activities.PairedRABActivity;
import com.watchoverme.wom.R;


public class SettingsFragment extends Fragment {

    ImageButton RABDetails;
    ImageButton testWOMConnection;
    ImageButton PhoneSettings;
    ImageButton signOut;
    AlertDialog.Builder signoutDialog;
    AlertDialog signoutDialogShow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        RABDetails = (ImageButton) rootView.findViewById(R.id.paired_rab_details);
        testWOMConnection = (ImageButton) rootView.findViewById(R.id.test_wom_connection);
        PhoneSettings = (ImageButton) rootView.findViewById(R.id.find_my_phone_settings);
        signOut = (ImageButton) rootView.findViewById(R.id.sign_out_btn);

        signoutDialog = new AlertDialog.Builder(getActivity());
        signoutDialog.setTitle("Sign Out");
        signoutDialog.setMessage("Are you sure?");
        signoutDialog.setCancelable(true);
        signoutDialog.setPositiveButton("Yes, I'm Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences loginData = getActivity().getSharedPreferences("wearerInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginData.edit();
                editor.putString("user_name", "");
                editor.putString("password", "");
                editor.putString("wom_id", "");
                editor.putString("name", "");
                editor.putString("service_id", "");
                editor.apply();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                getActivity().finish();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        signoutDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        signoutDialogShow = signoutDialog.create();

        signoutDialogShow.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                signoutDialogShow.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.alertColor));
                signoutDialogShow.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        RABDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),PairedRABActivity.class);
                startActivity(i);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signoutDialogShow.show();
            }
        });


        return rootView;
    }


}
