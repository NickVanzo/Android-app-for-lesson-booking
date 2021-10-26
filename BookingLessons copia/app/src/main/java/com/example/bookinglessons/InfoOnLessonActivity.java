package com.example.bookinglessons;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class InfoOnLessonActivity extends AppCompatActivity {

    private String idUser;
    private String idTeacher;
    private String day;
    private String slot;
    private String subject;
    private String source;
    TextView teacherText;
    TextView subjectText;
    TextView slotText;
    TextView dayText;
    Button deleteButton;
    Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_on_lesson);
        getParametersPassedAsExtras();
        setUpUI();
    }

    private void setUpUI() {
        if(teacherText==null) {
            teacherText = findViewById(R.id.showTeacherID);
            teacherText.setText("Codice docente: " + idTeacher);
        }
        if(subjectText==null) {
            subjectText = findViewById(R.id.showSubject);
            subjectText.setText("Materia: " + subject);
        }
        if(dayText==null) {
            dayText = findViewById(R.id.showDay);
            switch (day) {
                case "0":
                    dayText.setText("Giorno: lunedi'");
                    break;
                case "2":
                    dayText.setText("Giorno: martedi'");
                    break;
                case "3":
                    dayText.setText("Giorno: mercoledi'");
                    break;
                case "4":
                    dayText.setText("Giorno: giovedi'");
                    break;
                case "5":
                    dayText.setText("Giorno: venerdi'");
                    break;
                default:
                    break;
            }
        }
        if(slotText==null) {
            slotText = findViewById(R.id.showSlot);
            slotText.setText("Orario: " + slot);
        }

        if(bookButton==null) {
            bookButton = findViewById(R.id.bookButton);
            bookButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("in onClick", "You pressed the book button!");
                }
            });
        }

        if(deleteButton==null) {
            deleteButton = findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.d("in onClick", "You pressed the delete button!");
                }
            });
        }
    }

    private void getParametersPassedAsExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            idUser = extras.getString("student");
            idTeacher = extras.getString("teacher");
            day = extras.getString("day");
            slot = extras.getString("slot");
            subject = extras.getString("subject");
            source = extras.getString("source");
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}