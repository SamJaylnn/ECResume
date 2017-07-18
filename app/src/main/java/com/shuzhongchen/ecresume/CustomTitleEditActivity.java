package com.shuzhongchen.ecresume;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.shuzhongchen.ecresume.model.CustomTitle;
import com.shuzhongchen.ecresume.model.Education;

import java.util.Arrays;

/**
 * Created by shuzhongchen on 7/18/17.
 */

public class CustomTitleEditActivity extends EditBaseActivity<CustomTitle> {

    public static final String KEY_CUSTOM_TITLE = "custom_title";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_title_edit;
    }

    @Override
    protected void setupUIForCreate() {
        setTitle("Edit Section Title");
    }

    @Override
    protected void setupUIForEdit(@NonNull CustomTitle data) {
        setTitle("Edit Section Title");
        ((EditText) findViewById(R.id.custom_title_edit))
                .setText(data.title);
    }

    @Override
    protected void saveAndExit(@Nullable CustomTitle data) {
        if (data == null) {
            data = new CustomTitle();
        }

        data.title = ((EditText) findViewById(R.id.custom_title_edit)).getText().toString().toUpperCase();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_CUSTOM_TITLE, data);
        setResult(RESULT_OK, resultIntent); // activity's method
        finish();
    }

    @Override
    protected CustomTitle initializeData() {
        return getIntent().getParcelableExtra(KEY_CUSTOM_TITLE);
    }
}
