package com.example.teachersapp.adapter;

import com.example.teachersapp.SurveyFragment;
import com.example.teachersapp.model.Student;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;

public class SurveyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Student> students;

    public SurveyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return SurveyFragment.newInstance(students.get(position));
    }

    @Override
    public int getCount() {
        return students.size();
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
