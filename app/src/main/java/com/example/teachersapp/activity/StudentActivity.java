package com.example.teachersapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teachersapp.R;
import com.example.teachersapp.model.Student;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class StudentActivity extends AppCompatActivity {
    private static final String TAG = "StudentActivity";
    private TextView firstName;
    private TextView lastName;
    private TextView score;
    private ImageView photo;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setTitle(R.string.student_info);

        firstName = findViewById(R.id.student_firstname);
        lastName = findViewById(R.id.student_lastname);
        score = findViewById(R.id.student_score);
        photo = findViewById(R.id.student_photo);
        Intent i = getIntent();
        student = i.getParcelableExtra(Student.class.getCanonicalName());
        firstName.setText(student.getFirstName());
        lastName.setText(student.getLastName());
        score.setText(String.valueOf(student.getScore()));
        photo.setImageBitmap(getImageBitmap(student.getPhotoUri()));
        Log.d(TAG, "loaded imageURI == " + student.getPhotoUri());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_student:
//                editStudent();
                return true;
            case R.id.action_delete_student:
//                deleteStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {

            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}
