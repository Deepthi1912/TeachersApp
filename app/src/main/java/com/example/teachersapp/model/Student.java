package com.example.teachersapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
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
    @ColumnInfo (name = "photo")
    private byte[] photo;
    // TODO: fix image loading bug
    @ColumnInfo (name = "score")
    private int score;

    public Student(String firstName, String lastName, byte[] photo, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.score = score;
    }

    protected Student(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        photo = in.createByteArray();
        score = in.readInt();
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
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

    public byte[] getPhoto() {
        return photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeByteArray(photo);
        dest.writeInt(score);
    }
}
