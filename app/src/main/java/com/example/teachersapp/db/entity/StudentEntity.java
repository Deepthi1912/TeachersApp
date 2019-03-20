package com.example.teachersapp.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.teachersapp.model.Student;

@Entity(tableName = "student")
public class StudentEntity implements Student, Parcelable {
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

    public StudentEntity(int id, String firstName, String lastName, byte[] photo, int score) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.score = score;
    }

    @Ignore
    public StudentEntity(String firstName, String lastName, byte[] photo, int score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
        this.score = score;
    }

    protected StudentEntity(Parcel in) {
        id = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        photo = in.createByteArray();
        score = in.readInt();
    }

    public static final Creator<StudentEntity> CREATOR = new Creator<StudentEntity>() {
        @Override
        public StudentEntity createFromParcel(Parcel in) {
            return new StudentEntity(in);
        }

        @Override
        public StudentEntity[] newArray(int size) {
            return new StudentEntity[size];
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

    public void setId(int id) {
        this.id = id;
    }

    public void setScore(int score) {
        this.score = score;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public byte[] getPhoto() {
        return photo;
    }
}
