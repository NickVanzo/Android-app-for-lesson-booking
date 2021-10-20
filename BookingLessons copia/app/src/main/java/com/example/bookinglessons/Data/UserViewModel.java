package com.example.bookinglessons.Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bookinglessons.Data.User;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<String> user = new MutableLiveData<>();

    public void setUser(String user) {
        this.user.setValue(user);
    }

    public MutableLiveData<String> getUser() {
        return this.user;
    }
}
