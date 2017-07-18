package com.shuzhongchen.ecresume;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.shuzhongchen.ecresume.EditBaseActivity;
import com.shuzhongchen.ecresume.model.Custom;


import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by shuzhongchen on 7/18/17.
 */

public class CustomEditActivity extends EditBaseActivity<Custom> {

    public static final String KEY_CUSTOM = "custom";
    public static final String KEY_CUSTOM_ID = "custom_id";

    private ImageButton btnChangeStartDate;
    private ImageButton btnChangeEndDate;

    private int year;
    private int month;
    private int day;

    static final int START_DATE_DIALOG_ID = 999;
    static final int END_DATE_DIALOG_ID = 998;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_edit;
    }

    @Override
    protected void setupUIForCreate() {
        setTitle("Edit Customized Section");

        setupDatePicker();

        findViewById(R.id.custom_edit_delete).setVisibility(View.GONE);
    }

    @Override
    protected void setupUIForEdit(@NonNull final Custom data) {
        setTitle("New Customized Section");

        setupDatePicker();

        ((EditText) findViewById(R.id.custom_edit_main_title))
                .setText(data.title);
        ((EditText) findViewById(R.id.custom_edit_start_date))
                .setText(data.startDate);
        ((EditText) findViewById(R.id.custom_edit_end_date))
                .setText(data.endDate);
        ((EditText) findViewById(R.id.custom_edit_details))
                .setText(TextUtils.join("\n", data.details));

        findViewById(R.id.custom_edit_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_CUSTOM_ID, data.id);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void saveAndExit(@Nullable Custom data) {
        if (data == null) {
            data = new Custom();
        }

        data.title = ((EditText) findViewById(R.id.custom_edit_main_title)).getText().toString();
        data.startDate = ((EditText) findViewById(R.id.custom_edit_start_date)).getText().toString();
        data.endDate = ((EditText) findViewById(R.id.custom_edit_end_date)).getText().toString();
        data.details = Arrays.asList(TextUtils.split(((EditText) findViewById(R.id.custom_edit_details)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_CUSTOM, data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

    @Override
    protected Custom initializeData() {
        return getIntent().getParcelableExtra(KEY_CUSTOM);
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
            ((EditText) findViewById(R.id.custom_edit_start_date))
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
            ((EditText) findViewById(R.id.custom_edit_end_date))
                    .setText(new StringBuilder()
                            // Month is 0 based, just add 1
                            .append(month + 1).append("/").append(day).append("/")
                            .append(year));
        }
    };
}
