package com.example.teachersapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.teachersapp.R;
import com.example.teachersapp.SurveyFragment;
import com.example.teachersapp.adapter.SurveyFragmentPagerAdapter;
import com.example.teachersapp.model.Student;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SurveyActivity extends AppCompatActivity implements SurveyFragment.SurveyFragmentListener {

    private StudentViewModel studentViewModel;
    private FragmentManager manager;
    private ViewPager pager;
    private SurveyFragmentPagerAdapter adapter;
    private int currentStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.survey);

        manager = getSupportFragmentManager();

        pager = findViewById(R.id.survey_pager);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        adapter = new SurveyFragmentPagerAdapter(manager);
        if (studentViewModel.getAllStudents().getValue() != null) {
            studentViewModel.getAllStudents().observe(this, students -> {
                adapter.setStudents(students);
                adapter.notifyDataSetChanged();
            });

            pager.setAdapter(adapter);
            pager.setCurrentItem(new Random().nextInt(adapter.getCount()));
        }







    }

    @Override
    public void onFragmentResult(boolean isTrue) {

    }
}
