package com.example.bookinglessons.Model.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bookinglessons.Model.Lesson;

import java.util.ArrayList;

public class BookedLessonsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Lesson>> bookedLessons = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Lesson>> getBookedLessons() {
        return bookedLessons;
    }

    public void setBookedLessons(ArrayList<Lesson> bookedLessons) {
        this.bookedLessons.setValue(bookedLessons);
    }

    private final MutableLiveData<ArrayList<Lesson>> deletedLessons = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Lesson>> pastLessons = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Lesson>> getDeletedLessons() {
        return deletedLessons;
    }
    public MutableLiveData<ArrayList<Lesson>> getPastLessons() {
        return pastLessons;
    }

    public void setDeletedLessons(ArrayList<Lesson> deletedLessons) {
        this.deletedLessons.setValue(deletedLessons);
    }

    public void setPastLessons(ArrayList<Lesson> pastLessons) {
        this.pastLessons.setValue(pastLessons);
    }
}
