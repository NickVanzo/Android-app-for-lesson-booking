package com.example.bookinglessons.Model.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bookinglessons.Model.BookedLesson;

import java.util.ArrayList;

public class DeletedPastLessonsViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<BookedLesson>> deletedLessons = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<BookedLesson>> pastLessons = new MutableLiveData<>();

    public MutableLiveData<ArrayList<BookedLesson>> getDeletedLessons() {
        return deletedLessons;
    }
    public MutableLiveData<ArrayList<BookedLesson>> getPastLessons() {
        return pastLessons;
    }

    public void setDeletedLessons(ArrayList<BookedLesson> deletedLessons) {
        this.deletedLessons.setValue(deletedLessons);
    }

    public void setPastLessons(ArrayList<BookedLesson> pastLessons) {
        this.pastLessons.setValue(pastLessons);
    }
}
