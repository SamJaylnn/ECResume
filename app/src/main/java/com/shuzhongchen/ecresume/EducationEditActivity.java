package com.shuzhongchen.ecresume;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.shuzhongchen.ecresume.model.Education;
import com.shuzhongchen.ecresume.util.DateUtils;

import java.util.Arrays;

/**
 * Created by shuzhongchen on 7/15/17.
 */

public class EducationEditActivity extends  EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected void setupUIForCreate() {
        setTitle("New Education");

        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    protected void setupUIForEdit(@NonNull final Education data) {
        setTitle("Edit Education");

        ((EditText) findViewById(R.id.education_edit_school))
                .setText(data.school);
        ((EditText) findViewById(R.id.education_edit_major))
                .setText(data.major);
        ((EditText) findViewById(R.id.education_edit_courses))
                .setText(TextUtils.join("\n", data.courses));

        ((EditText) findViewById(R.id.education_edit_start_date))
                .setText(DateUtils.dateToString(data.startDate));
        ((EditText) findViewById(R.id.education_edit_end_date))
                .setText(DateUtils.dateToString(data.endDate));

        findViewById(R.id.education_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EDUCATION_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void saveAndExit(Education education) {
        if (education == null) {
            education = new Education();
        }

        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.major = ((EditText) findViewById(R.id.education_edit_major)).getText().toString();
        education.startDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_start_date)).getText().toString());
        education.endDate = DateUtils.stringToDate(
                ((EditText) findViewById(R.id.education_edit_end_date)).getText().toString());
        // can use date picker
        education.courses = Arrays.asList(TextUtils.split(
                ((EditText) findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, education);
        setResult(RESULT_OK, resultIntent); // activity's method
        finish();
    }

    @Override
    protected Education initializeData() {
        return getIntent().getParcelableExtra(KEY_EDUCATION);
    }

}
