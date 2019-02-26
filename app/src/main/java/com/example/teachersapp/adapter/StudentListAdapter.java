package com.example.teachersapp.adapter;

import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.util.Arrays;

public class StudentListAdapter extends ListAdapter<Student, StudentListAdapter.StudentViewHolder>{

    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return oldItem.getFirstName().equals(newItem.getFirstName())
                    && oldItem.getLastName().equals(newItem.getLastName())
                    && Arrays.equals(oldItem.getPhoto(), newItem.getPhoto())
                    && oldItem.getScore() == newItem.getScore();
        }
    };
    private OnStudentClickListener listener;

    public StudentListAdapter() {
        super(DIFF_CALLBACK);
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
        Student currentStudent = getItem(position);
        holder.name.setText(new StringBuilder().append(currentStudent.getFirstName()).append(" ").append(currentStudent.getLastName()));
        holder.photo.setImageBitmap(BitmapFactory.decodeByteArray(currentStudent.getPhoto(), 0, currentStudent.getPhoto().length));
    }

    public Student getStudentAt(int position) {
        return getItem(position);
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
                    listener.onStudentClick(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }

    public interface OnStudentClickListener {
        void onStudentClick(Student student, int position);
    }

    public void setOnStudentClickListener(OnStudentClickListener listener) {
        this.listener = listener;
    }

}
