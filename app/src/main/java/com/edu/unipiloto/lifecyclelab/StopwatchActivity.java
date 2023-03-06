package com.edu.unipiloto.lifecyclelab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private int lap = 1;
    private int currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");

        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    public void onClickStart(View view) {
        running = true;
        if (lap == 1) {
            final TextView lapView = (TextView) findViewById(R.id.lap_view);
            lapView.setText("");
        }

    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        final TextView lapView = (TextView) findViewById(R.id.lap_view);
        lapView.setText("");
        running = false;
        seconds = 0;
        currentTime = 0;
        lap = 1;
    }

    public void resetLap() {
        currentTime = 0;
    }

    public void finishLapsCount() {
        running = false;
        seconds = 0;
        currentTime = 0;
        lap = 1;
    }

    public void onClickLap(View view) {
        if (!running)
            return;
        final TextView lapView = (TextView) findViewById(R.id.lap_view);

        int secs = currentTime % 60;
        int minutes = currentTime / 60 % 60;
        int hours = currentTime / 3600 % 24;
        String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
        String text = "Vuelta " + lap + " " + time;
        String previousText = (String) lapView.getText();
        lapView.setText(text + "\n" + previousText);

        lap++;
        resetLap();
        if (lap == 6) {
            finishLapsCount();
            return;
        }
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int secs = seconds % 60;
                int minutes = seconds / 60 % 60;
                int hours = seconds / 3600 % 24;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                    currentTime++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }
}
