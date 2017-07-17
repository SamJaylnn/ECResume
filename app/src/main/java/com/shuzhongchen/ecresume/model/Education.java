package com.shuzhongchen.ecresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.shuzhongchen.ecresume.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by shuzhongchen on 7/15/17.
 */

public class Education implements Parcelable{

    public String id;

    public String school;

    public String major;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education() { id = UUID.randomUUID().toString(); }

    protected Education(Parcel in) {
        id = in.readString();
        school = in.readString();
        major = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        courses = in.createStringArrayList();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(school);
        parcel.writeString(major);
        parcel.writeString(DateUtils.dateToString(startDate));
        parcel.writeString(DateUtils.dateToString(endDate));
        parcel.writeStringList(courses);
    }
}
