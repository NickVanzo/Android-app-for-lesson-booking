package com.example.bookinglessons.View.Activities;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Network.MySingleton;
import com.example.bookinglessons.Model.ViewModels.BookedLessonsViewModel;
import com.example.bookinglessons.R;
import org.json.JSONException;

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
        ObjectAnimator animation_teacher_text = ObjectAnimator.ofFloat(findViewById(R.id.showTeacherID), "translationX", 300f);
        ObjectAnimator animation_subject_text = ObjectAnimator.ofFloat(findViewById(R.id.showSubject), "translationX", 300f);
        ObjectAnimator animation_day_text = ObjectAnimator.ofFloat(findViewById(R.id.showDay), "translationX", 300f);
        ObjectAnimator animation_slot_text = ObjectAnimator.ofFloat(findViewById(R.id.showSlot), "translationX", 300f);
        animation_day_text.setDuration(1000);
        animation_day_text.start();
        animation_slot_text.setDuration(1000);
        animation_slot_text.start();
        animation_subject_text.setDuration(1000);
        animation_subject_text.start();
        animation_teacher_text.setDuration(1000);
        animation_teacher_text.start();


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
                String url = getResources().getString(R.string.servlet_url) + "book/addBookedLesson?teacher="+idTeacher+"&subject="+subject+"&day="+day+"&slot="+slot;
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

                String url = getResources().getString(R.string.servlet_url) + "book/deleteBookedLesson?teacher="+idTeacher+"&subject="+subject+"&day="+day+"&slot="+slot;
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