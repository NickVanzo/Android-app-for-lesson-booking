package com.example.bookinglessons.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookinglessons.Adapters.AdapterListHome;
import com.example.bookinglessons.Model.BookedLesson;
import com.example.bookinglessons.Model.ViewModels.BookedLessonsViewModel;
import com.example.bookinglessons.Model.ViewModels.DeletedPastLessonsViewModel;
import com.example.bookinglessons.Model.ViewModels.UserViewModel;
import com.example.bookinglessons.R;
import com.example.bookinglessons.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class UserLessonsFragment extends Fragment {

    private UserViewModel userViewModel;
    private FragmentNotificationsBinding binding;
    private BookedLessonsViewModel bookedLessonsViewModel;
    private DeletedPastLessonsViewModel deletedPastLessonsViewModel;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        bookedLessonsViewModel = new ViewModelProvider(requireActivity()).get(BookedLessonsViewModel.class);
        deletedPastLessonsViewModel = new ViewModelProvider(requireActivity()).get(DeletedPastLessonsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(listView == null) {
            listView = root.findViewById(R.id.list_view_deleted_past_lessons);
        }

        deletedPastLessonsViewModel.getDeletedLessons().observe(getViewLifecycleOwner(), deletedLessons -> {
            deletedPastLessonsViewModel.getPastLessons().observe(getViewLifecycleOwner(), pastLessons -> {
                ArrayList<BookedLesson> lessons = new ArrayList<>(deletedLessons);
                lessons.addAll(pastLessons);
                AdapterListHome personalAdapter = new AdapterListHome(getContext(), R.layout.deleted_past_lessons_list_item, lessons);
                listView.setAdapter(personalAdapter);
            });
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("in onResume", "Hello from your userLessonsFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}