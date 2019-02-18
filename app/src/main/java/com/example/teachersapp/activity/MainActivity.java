package com.example.teachersapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.teachersapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.open_student_list_button).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, StudentListActivity.class);
            startActivity(i);
        });
    }
}
