package com.example.teachersapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class StudentsViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link AndroidViewModel}
     */
    public StudentsViewModelFactory(@NonNull Application application) {
        super(application);
    }
}
