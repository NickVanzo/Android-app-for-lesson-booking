package com.example.bookinglessons.View.Activities;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Adapters.AdapterListAvailableLessons;
import com.example.bookinglessons.Model.ViewModels.UserViewModel;
import com.example.bookinglessons.Network.MySingleton;
import com.example.bookinglessons.Model.Lesson;
import com.example.bookinglessons.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOfAvailableLessons extends AppCompatActivity {

    private String subject = "";
    private String day = "";
    private UserViewModel viewModel;
    private String username = "";
    private ListView listView;
    private ArrayList<Lesson> availableLessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_available_lessons);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        Bundle extras = getIntent().getExtras();
        if(extras!= null) {
            subject = extras.getString("subject");
            day = extras.getString("day");
            username = extras.getString("username");
            Log.d("in onCreate", "USERNAME RECEIVED: " + username);
        }

        if(listView==null) {
            listView = findViewById(R.id.list_available_lessons);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(this, InfoOnLessonActivity.class);
                Lesson bl = (Lesson) listView.getItemAtPosition(position);

                intent.putExtra("subject", bl.getSubject());
                intent.putExtra("teacher", bl.getIdTeacher());
                intent.putExtra("student", bl.getIdUser());
                intent.putExtra("day", bl.getDay());
                intent.putExtra("slot", bl.getSlot());
                intent.putExtra("nameTeacher", bl.getNameTeacher());
                intent.putExtra("surnameTeacher", bl.getSurnameTeacher());

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

                        while(i < teachersFetched.length()) {
                            fetchAvailableSlots(teachersFetched.getJSONObject(i).getJSONObject("teacher"));
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

    private void fetchAvailableSlots(JSONObject teacher) {

        String url = null;
        try {
            url = getResources().getString(R.string.servlet_url) + "book/getFreeSlotsForTeacher?teacher="+teacher.getString("id")+"&day="+day;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonCustomReq = new JsonObjectRequest(
                url,
                null,
                response -> {
                    try {
                        JSONArray slotsFetched = response.getJSONArray("slots");
                        int i = 0;

                        while(i < slotsFetched.length()) {

//                            Log.d("in fetch", "USERNAME " + user);
                            Lesson bl = new Lesson("user", teacher.getString("id"), slotsFetched.get(i).toString(), subject, day, "Da Frequentare", teacher.getString("nome"), teacher.getString("cognome"));
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