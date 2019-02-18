package com.example.teachersapp.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students = new ArrayList<>();
    private OnStudentClickListener listener;

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_cardview, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.name.setText(new StringBuilder().append(students.get(position).getFirstName()).append(" ").append(students.get(position).getLastName()));
        holder.photo.setImageURI(Uri.parse(students.get(position).getPhotoUri()));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;

        public StudentViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.student_card_photo);
            name = itemView.findViewById(R.id.student_card_name);
            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onStudentClick(students.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnStudentClickListener {
        void onStudentClick(Student student);
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        this.listener = listener;
    }

}
