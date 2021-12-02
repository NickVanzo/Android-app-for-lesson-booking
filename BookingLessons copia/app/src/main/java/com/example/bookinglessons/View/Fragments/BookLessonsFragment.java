package com.example.bookinglessons.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bookinglessons.Model.ViewModels.UserViewModel;
import com.example.bookinglessons.Network.MySingleton;
import com.example.bookinglessons.View.Activities.ListOfAvailableLessons;
import com.example.bookinglessons.R;
import com.example.bookinglessons.databinding.FragmentDashboardBinding;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BookLessonsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentDashboardBinding binding;
    Spinner spinner;
    String subjectSelected = "";
    Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spinner = root.findViewById(R.id.spinner_for_subject);
        button = root.findViewById(R.id.confirm_form_button);

        UserViewModel viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getUser().observe(getViewLifecycleOwner(), usernmae -> {
            Log.d("in onCreateView", "USEEEEEER"  + usernmae);
        });

        button.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ListOfAvailableLessons.class);
            i.putExtra("subject", subjectSelected);
            i.putExtra("day", binding.textDays.getText().toString());

            startActivity(i);
        });

        fetchSubjects();

        return root;
    }

    private void initSpinner(Spinner spinner, ArrayList<String> subjects) {
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setOnItemSelectedListener(this);

        spinner.setAdapter(adapter);
    }

    private void fetchSubjects() {
        String url = getResources().getString(R.string.servlet_url) + "subject/getSubjects";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url,
                null,
                response -> {
                    int i = 0;
                    try {
                        ArrayList<String> subjectsFetched = new ArrayList<>();
                        JSONArray subjects = response.getJSONArray("subject");
                        while(i < subjects.length()) {
                            subjectsFetched.add(subjects.getJSONObject(i).get("name").toString());
                            i++;
                        }
                        initSpinner(spinner, subjectsFetched);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }
        );
        MySingleton.getInstance(this.getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subjectSelected = parent.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}