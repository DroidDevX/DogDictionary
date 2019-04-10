package com.droiddevsa.doganator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.droiddevsa.doganator.data.TestStatic;

public class AddDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        TestStatic.setDATA(AddDataActivity.class.getSimpleName(),"in AddData");
    }
}
