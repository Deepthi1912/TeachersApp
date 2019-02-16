package com.example.teachersapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import com.example.teachersapp.activity.StudentsActivity;

import java.io.IOException;

@Entity
public class Student implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo (name = "photo_uri")
    private String photoUri;
    @ColumnInfo (name = "score")
    private int score;

    public Student(String name, String photoUri) {
        this.name = name;
        this.photoUri = photoUri;
    }

    protected Student(Parcel in) {
        name = in.readString();
        score = in.readInt();
        photoUri = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };


    public void addScore() {
        score++;
    }

    public boolean divideScore() {
        if (score >= 1) {
            score--;
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUri = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(score);
        dest.writeString(photoUri);
    }
}
