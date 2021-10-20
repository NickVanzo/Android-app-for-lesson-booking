package com.example.bookinglessons.ui.booklessons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.bookinglessons.Data.UserViewModel;
import com.example.bookinglessons.databinding.FragmentDashboardBinding;

public class BookLessonsFragment extends Fragment {

    private UserViewModel userViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        userViewModel.getUser().observe(getViewLifecycleOwner(), item -> {
            textView.setText(item);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}