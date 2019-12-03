package com.lorenzorigato.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {


    // Static **************************************************************************************
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }


    // Class methods *******************************************************************************
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
}
