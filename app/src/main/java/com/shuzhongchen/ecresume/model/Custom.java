package com.shuzhongchen.ecresume.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.UUID;

/**
 * Created by shuzhongchen on 7/18/17.
 */

public class Custom implements Parcelable{

    public String id;

    public String title;

    public String startDate;

    public String endDate;

    public List<String> details;

    public Custom() {
        id = UUID.randomUUID().toString();
    }

    protected Custom(Parcel in) {
        id = in.readString();
        title = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        details = in.createStringArrayList();
    }

    public static final Creator<Custom> CREATOR = new Creator<Custom>() {
        @Override
        public Custom createFromParcel(Parcel in) {
            return new Custom(in);
        }

        @Override
        public Custom[] newArray(int size) {
            return new Custom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeStringList(details);
    }
}
