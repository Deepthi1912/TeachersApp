package com.example.teachersapp.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.R;
import com.example.teachersapp.db.entity.StudentEntity;
import com.example.teachersapp.model.Student;
import com.example.teachersapp.viewmodel.StudentViewModel;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class SurveyFragment extends Fragment {

    private ImageView photo;
    private TextView name;
    private TextView score;
    private StudentEntity student;
    private SurveyFragmentListener listener;
    private StudentViewModel viewModel;

    public SurveyFragment() {
    }

    public static SurveyFragment newInstance(StudentEntity student) {
        SurveyFragment fragment = new SurveyFragment();
        if (student != null) {
            Bundle args = new Bundle();
            args.putParcelable(StudentEntity.class.getCanonicalName(), student);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = getArguments().getParcelable(StudentEntity.class.getCanonicalName());
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
        if (student != null) {
            photo.setImageBitmap(BitmapFactory.decodeByteArray(student.getPhoto(), 0, student.getPhoto().length));
            name.setText(new StringBuilder(student.getFirstName()).append(" ").append(student.getLastName()));
            score.setText(String.valueOf(student.getScore()));
        }

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
