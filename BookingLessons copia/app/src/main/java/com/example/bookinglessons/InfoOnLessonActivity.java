package com.example.bookinglessons;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InfoOnLessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_on_lesson);
        Log.d("In onCreate", "Hello from InfoOnLessonActivity");
    }
}