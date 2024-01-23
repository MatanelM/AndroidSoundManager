package com.example.androidsoundmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.soundmanager.SoundManager;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4;
    int mainSoundId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundManager.init();

        findViews();
        initViews();

    }

    private void findViews() {
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
    }

    private void initViews() {

        button1.setOnClickListener(l -> {
            SoundManager.getInstance().playOnce(this, R.raw.arcade);
        });


        button2.setOnClickListener(l -> {
            mainSoundId = SoundManager.getInstance().playInLoop(this, R.raw.main);

        });
        button3.setOnClickListener(l -> {
            SoundManager.getInstance().toggleMute();
        });

        button4.setOnClickListener(l -> {
            SoundManager.getInstance().rewind(mainSoundId, 4);
        });


    }

}