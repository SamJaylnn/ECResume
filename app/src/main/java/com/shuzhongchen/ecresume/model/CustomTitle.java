package com.shuzhongchen.ecresume.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shuzhongchen on 7/18/17.
 */

public class CustomTitle implements Parcelable {

    public String title;

    public CustomTitle() { }

    protected CustomTitle(Parcel in) {
        title = in.readString();
    }

    public static final Creator<CustomTitle> CREATOR = new Creator<CustomTitle>() {
        @Override
        public CustomTitle createFromParcel(Parcel in) {
            return new CustomTitle(in);
        }

        @Override
        public CustomTitle[] newArray(int size) {
            return new CustomTitle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
    }
}
