package com.example.teachersapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.model.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kotlin.jvm.Throws;

public class SurveyFragment extends Fragment {

    private ImageView photo;
    private TextView name;
    private TextView score;
    private Student student;
    private SurveyFragmentListener listener;

    public SurveyFragment() {
    }

    public static SurveyFragment newInstance(Student student) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle args = new Bundle();
        args.putParcelable(Student.class.getCanonicalName(), student);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = getArguments().getParcelable(Student.class.getCanonicalName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photo = view.findViewById(R.id.survey_photo);
        name = view.findViewById(R.id.survey_name);
        score = view.findViewById(R.id.survey_score);
        photo.setImageBitmap(BitmapFactory.decodeByteArray(student.getPhoto(), 0, student.getPhoto().length));
        name.setText(new StringBuilder(student.getFirstName()).append(" ").append(student.getLastName()));
        score.setText(String.valueOf(student.getScore()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SurveyFragmentListener) {
            this.listener = (SurveyFragmentListener) context;
        } else {
            throw new RuntimeException("Activity doesn't implement OnFragmentListener!");

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface SurveyFragmentListener {
        void onFragmentResult(boolean isTrue);
    }
}
