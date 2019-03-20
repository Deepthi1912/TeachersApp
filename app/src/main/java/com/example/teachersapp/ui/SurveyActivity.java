package com.example.teachersapp.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.teachersapp.R;
import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.Objects;
import java.util.Random;

public class SurveyActivity extends AppCompatActivity implements SurveyFragment.SurveyFragmentListener {
    private StudentViewModel viewModel;
    private FragmentManager manager;
    private ViewPager pager;
    private SurveyFragmentPagerAdapter adapter;
    private StudentEntity student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.survey);
        viewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        manager = getSupportFragmentManager();
        if (viewModel.getAllStudents().getValue() != null) {
            int size = Objects.requireNonNull(viewModel.getAllStudents().getValue()).size();
            student = viewModel.getAllStudents().getValue().get(new Random().nextInt(size));
            manager.beginTransaction().add(R.id.survey_fragment, SurveyFragment.newInstance(student)).commit();
        }



    }

    @Override
    public void onFragmentResult(boolean isTrue) {

    }
}
