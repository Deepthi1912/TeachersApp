package com.example.teachersapp.ui;

import com.example.teachersapp.db.entity.StudentEntity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SurveyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<StudentEntity> students;

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

    public void setStudents(List<StudentEntity> students) {
        this.students = students;
    }
}
