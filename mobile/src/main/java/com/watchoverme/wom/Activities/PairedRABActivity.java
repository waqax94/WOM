package com.watchoverme.wom.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.watchoverme.wom.R;

public class PairedRABActivity extends AppCompatActivity {

    Button unpairRAB;
    ImageButton findMyRAB;
    ImageButton findRABtoPair;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_rab);

        unpairRAB = (Button) findViewById(R.id.unpair_rab_btn);
        findRABtoPair = (ImageButton) findViewById(R.id.find_a_rab_to_pair);
        findMyRAB = (ImageButton) findViewById(R.id.find_my_rab);
        back = (ImageButton) findViewById(R.id.back_to_home);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
