package com.example.teachersapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "student")
public class Student implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
    @ColumnInfo (name = "photo_uri")
    private String photoUri;
    // TODO: fix image loading bug
    @ColumnInfo (name = "score")
    private int score;

    public Student(String firstName, String lastName, String photoUri) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUri = photoUri;
        this.score = 0;
    }

    protected Student(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(score);
        dest.writeString(photoUri);
    }
}
