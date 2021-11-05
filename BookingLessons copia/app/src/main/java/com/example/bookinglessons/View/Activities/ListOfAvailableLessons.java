package com.example.bookinglessons.View.Activities;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Adapters.AdapterListAvailableLessons;
import com.example.bookinglessons.Network.MySingleton;
import com.example.bookinglessons.Model.BookedLesson;
import com.example.bookinglessons.R;
import org.json.JSONArray;
import org.json.JSONException;

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
        String url = getResources().getString(R.string.servlet_url) + "subject/filterTeachers?subject="+subject+"&day="+day;

        JsonObjectRequest jsonCustomReq = new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONArray teachersFetched = response.getJSONArray("teachers");
                        int i = 0;
                        Log.d("in fetchTeachers", teachersFetched.toString());
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
        String url = getResources().getString(R.string.servlet_url) + "book/getFreeSlotsForTeacher?teacher="+teacher+"&day="+day;
        Log.d("in fetchAvailable", "TEACHER"+teacher);

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