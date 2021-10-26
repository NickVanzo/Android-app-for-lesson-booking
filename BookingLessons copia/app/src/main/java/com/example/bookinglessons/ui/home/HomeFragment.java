package com.example.bookinglessons.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookinglessons.Controller.AdapterListHome;
import com.example.bookinglessons.Data.BookedLesson;
import com.example.bookinglessons.Data.BookedLessonsViewModel;
import com.example.bookinglessons.Data.UserViewModel;
import com.example.bookinglessons.InfoOnLessonActivity;
import com.example.bookinglessons.MainActivity;
import com.example.bookinglessons.R;
import com.example.bookinglessons.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private UserViewModel userViewModel;
    private BookedLessonsViewModel bookedLessonsViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
    Intent intent = null;
    private MutableLiveData<ArrayList<BookedLesson>> bookedLessons;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        bookedLessonsViewModel = new ViewModelProvider(requireActivity()).get(BookedLessonsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(listView == null) {
            listView = root.findViewById(R.id.list_view_home);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent = new Intent(getActivity(), InfoOnLessonActivity.class);
                    Log.d("In onItemClick", "Value: " + listView.getItemAtPosition(position));
                    startActivity(intent);
                }
            });
        }

        bookedLessonsViewModel.getBookedLessons().observe(getViewLifecycleOwner(), bookedLessons -> {
            AdapterListHome personalAdapter = new AdapterListHome(getContext(), R.layout.booked_lessons_list_item, bookedLessons);
            listView.setAdapter(personalAdapter);
        });

//        final TextView textView = binding.textHome;
//        userViewModel.getUser().observe(getViewLifecycleOwner(), item -> {
//            textView.setText(item);
//        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}