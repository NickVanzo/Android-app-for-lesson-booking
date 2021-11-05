package com.example.bookinglessons.Model.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<String> user = new MutableLiveData<>();
    private final MutableLiveData<String> surname = new MutableLiveData<>();
    private final MutableLiveData<String> role = new MutableLiveData<>();

    public void setUser(String user) {
        this.user.setValue(user);
    }

    public void setSurname(String surname) {
        this.surname.setValue(surname);
    }

    public void setRole(String role) {
        this.role.setValue(role);
    }

    public MutableLiveData<String> getUser() {
        return this.user;
    }

    public MutableLiveData<String> getSurname() {
        return this.surname;
    }

    public MutableLiveData<String> getRole() {
        return this.role;
    }
}
