package com.watchoverme.wom.Activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.watchoverme.wom.R;

public class HelpMeCancelActivity extends AppCompatActivity {

    Button cancel;
    TextView countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me_cancel);

        cancel = findViewById(R.id.cancel_help_me);
        countDown = findViewById(R.id.count_down);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {
                countDown.setText("" + l / 1000);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();

    }

}
