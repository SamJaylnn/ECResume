package com.shuzhongchen.ecresume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shuzhongchen.ecresume.model.Education;

import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by shuzhongchen on 7/15/17.
 */

public class EducationEditActivity extends EditBaseActivity<Education> {

    public static final String KEY_EDUCATION = "education";
    public static final String KEY_EDUCATION_ID = "education_id";

    private ImageButton btnChangeStartDate;
    private ImageButton btnChangeEndDate;

    private int year;
    private int month;
    private int day;

    static final int START_DATE_DIALOG_ID = 999;
    static final int END_DATE_DIALOG_ID = 998;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_education_edit;
    }

    @Override
    protected void setupUIForCreate() {
        setTitle("New Education");

        setupDatePicker();

        findViewById(R.id.education_edit_delete).setVisibility(View.GONE);
    }

    protected void setupUIForEdit(@NonNull final Education data) {
        setTitle("Edit Education");

        setupDatePicker();

        ((EditText) findViewById(R.id.education_edit_school))
                .setText(data.school);
        ((EditText) findViewById(R.id.education_edit_major))
                .setText(data.major);
        ((EditText) findViewById(R.id.education_edit_courses))
                .setText(TextUtils.join("\n", data.courses));

        ((EditText) findViewById(R.id.education_edit_start_date))
                .setText(data.startDate);
        ((EditText) findViewById(R.id.education_edit_end_date))
                .setText(data.endDate);

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
        education.startDate = ((EditText) findViewById(R.id.education_edit_start_date)).getText().toString();
        education.endDate = ((EditText) findViewById(R.id.education_edit_end_date)).getText().toString();
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


    // date picker
    public void setupDatePicker() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        btnChangeStartDate = (ImageButton) findViewById(R.id.btnChangeStartDate);
        btnChangeEndDate = (ImageButton) findViewById(R.id.btnChangeEndDate);

        btnChangeStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(START_DATE_DIALOG_ID);
            }
        });

        btnChangeEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(END_DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, startDatePickerListener,
                        year, month, day);

            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, endDatePickerListener,
                        year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener startDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into EditText
            ((EditText) findViewById(R.id.education_edit_start_date))
                    .setText(new StringBuilder()
                            // Month is 0 based, just add 1
                            .append(month + 1).append("/").append(day).append("/")
                            .append(year));
        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into EditText
            ((EditText) findViewById(R.id.education_edit_end_date))
                    .setText(new StringBuilder()
                            // Month is 0 based, just add 1
                            .append(month + 1).append("/").append(day).append("/")
                            .append(year));
        }
    };
}
