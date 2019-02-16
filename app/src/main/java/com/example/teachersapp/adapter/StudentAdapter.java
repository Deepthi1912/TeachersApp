package com.example.teachersapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.R;
import com.example.teachersapp.Student;

import java.io.IOException;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;
    private Context context;

    public StudentAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.student_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.name.setText(students.get(position).getName());
        /*try {
            holder.photo.setImageBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(students.get(position).getPhotoUri())));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
            photo = itemView.findViewById(R.id.student_photo);
            name = itemView.findViewById(R.id.student_name);
        }
    }



}
