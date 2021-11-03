package com.example.bookinglessons;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Controller.AdapterListAvailableLessons;
import com.example.bookinglessons.Controller.MySingleton;
import com.example.bookinglessons.Data.BookedLesson;
import com.example.bookinglessons.Data.Costants;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfAvailableLessons extends AppCompatActivity {

    private String subject = "";
    private String day = "";
    private ListView listView;
    private ArrayList<BookedLesson> availableLessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_available_lessons);

        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            subject = extras.getString("subject");
            day = extras.getString("day");
        }

        if(listView==null) {
            listView = findViewById(R.id.list_available_lessons);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(this, InfoOnLessonActivity.class);
                BookedLesson bl = (BookedLesson) listView.getItemAtPosition(position);

                intent.putExtra("subject", bl.getSubject());
                intent.putExtra("teacher", bl.getIdTeacher());
                intent.putExtra("student", bl.getIdUser());
                intent.putExtra("day", bl.getDay());
                intent.putExtra("slot", bl.getSlot());

                startActivity(intent);
            });
        }
        fetchTeachersForSubject(subject);

    }

    private void fetchTeachersForSubject(String subject) {
        String url = Costants.URL + "subject/filterTeachers?subject="+subject+"&day="+day;

        JsonObjectRequest jsonCustomReq = new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONArray teachersFetched = response.getJSONArray("teachers");
                        int i = 0;
                        while(i < teachersFetched.length()) {
                            fetchAvailableSlots(teachersFetched.getJSONObject(i).get("teacherID").toString());
                            i++;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonCustomReq);
    }

    private void fetchAvailableSlots(String teacher) {
        String url = Costants.URL + "book/getFreeSlotsForTeacher?teacher="+teacher+"&day="+day;

        JsonObjectRequest jsonCustomReq = new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONArray slotsFetched = response.getJSONArray("slots");
                        int i = 0;

                        while(i < slotsFetched.length()) {
                            BookedLesson bl = new BookedLesson("912759", teacher, slotsFetched.get(i).toString(), subject, day, "Da Frequentare");
                            availableLessons.add(bl);
                            i++;
                        }
                        AdapterListAvailableLessons adapter = new AdapterListAvailableLessons(this, R.layout.available_lessons_item, availableLessons);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(jsonCustomReq);
    }
}