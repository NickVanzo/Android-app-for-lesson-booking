package com.example.bookinglessons.Data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class BookedLessonsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<BookedLesson>> bookedLessons = new MutableLiveData<>();

    public MutableLiveData<ArrayList<BookedLesson>> getBookedLessons() {
        return bookedLessons;
    }

    public void setBookedLessons(ArrayList<BookedLesson> bookedLessons) {
        this.bookedLessons.setValue(bookedLessons);
    }
}
