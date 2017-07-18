package com.shuzhongchen.ecresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by shuzhongchen on 7/17/17.
 */

public class Project implements Parcelable{

    public String id;

    public String name;

    public String startDate;

    public String endDate;

    public List<String> details;

    public Project() {
        id = UUID.randomUUID().toString();
    }

    protected Project(Parcel in) {
        id = in.readString();
        name = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        details = in.createStringArrayList();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeStringList(details);
    }
}
