package com.example.bookinglessons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Controller.MySingleton;
import com.example.bookinglessons.Data.BookedLessonsViewModel;
import com.example.bookinglessons.Data.Costants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoOnLessonActivity extends AppCompatActivity {

    private String idUser;
    private String idTeacher;
    private String day;
    private String slot;
    private String subject;
    TextView teacherText;
    TextView subjectText;
    TextView slotText;
    TextView dayText;
    Button deleteButton;
    Button bookButton;
    BookedLessonsViewModel bookedLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_on_lesson);
        bookedLesson = new ViewModelProvider(this).get(BookedLessonsViewModel.class);
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
                case "1":
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
            bookButton.setOnClickListener(v -> {
                String url = Costants.URL + "book/addBookedLesson?teacher="+idTeacher+"&subject="+subject+"&day="+day+"&slot="+slot;
                JsonObjectRequest req = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        response -> {
                            try {
                                showToast((String) response.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {

                        }
                );
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
            });
        }

        if(deleteButton==null) {
            deleteButton = findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(v -> {

                String url = Costants.URL + "book/deleteBookedLesson?teacher="+idTeacher+"&subject="+subject+"&day="+day+"&slot="+slot;
                JsonObjectRequest req = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        null,
                        response -> {
                            try {
                                Log.d("in onClick deletion", "Reservation deleted!");
                                showToast((String) response.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> Log.d("in onErrorResponse", ""+error.getMessage())
                );
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
            });
        }
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT*2);
        toast.show();
    }

    private void getParametersPassedAsExtras() {
        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            idUser = extras.getString("student");
            idTeacher = extras.getString("teacher");
            day = extras.getString("day");
            slot = extras.getString("slot");
            subject = extras.getString("subject");
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }
}