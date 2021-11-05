package com.example.bookinglessons.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookinglessons.Adapters.AdapterListHome;
import com.example.bookinglessons.Model.BookedLesson;
import com.example.bookinglessons.Model.ViewModels.BookedLessonsViewModel;
import com.example.bookinglessons.Model.ViewModels.UserViewModel;
import com.example.bookinglessons.View.Activities.InfoOnLessonActivity;
import com.example.bookinglessons.R;
import com.example.bookinglessons.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private UserViewModel userViewModel;
    private BookedLessonsViewModel bookedLessonsViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
    Intent intent = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        bookedLessonsViewModel = new ViewModelProvider(requireActivity()).get(BookedLessonsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(listView == null) {
            listView = root.findViewById(R.id.list_view_home);
            listView.setOnItemClickListener((parent, view, position, id) -> {
                intent = new Intent(getActivity(), InfoOnLessonActivity.class);
                BookedLesson bl = (BookedLesson) listView.getItemAtPosition(position);

                intent.putExtra("subject", bl.getSubject());
                intent.putExtra("teacher", bl.getIdTeacher());
                intent.putExtra("student", bl.getIdUser());
                intent.putExtra("day", bl.getDay());
                intent.putExtra("slot", bl.getSlot());

                startActivity(intent);
            });
        }


        bookedLessonsViewModel.getBookedLessons().observe(getViewLifecycleOwner(), bookedLessons -> {
            AdapterListHome personalAdapter = new AdapterListHome(getContext(), R.layout.booked_lessons_list_item, bookedLessons);
            listView.setAdapter(personalAdapter);
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}