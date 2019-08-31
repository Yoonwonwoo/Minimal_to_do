package com.example.minimaltodo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import  android.support.v7.widget.Toolbar;

public class Settings extends AppCompatActivity {
    CheckBox modeCheck;
    TextView nightMode;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivitySetTheme(this);
        setContentView(R.layout.activity_settings);

        nightMode = findViewById(R.id.nightMode);
        modeCheck = findViewById(R.id.modeCheck);

        modeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modeCheck.isChecked()){
                    ThemeUtils.changeToTheme(Settings.this, ThemeUtils.DARK);
                    setInSp("check", true);
                }else{
                    ThemeUtils.changeToTheme(Settings.this, ThemeUtils.LIGHT);
                    setInSp("check", false);
                }
            }
        });

        modeCheck.setChecked(getFromSp("check"));
        if (modeCheck.isChecked()){
            nightMode.setText("Night Mode ON");
        }else{
            nightMode.setText("Night Mode OFF");
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private boolean getFromSp(String key){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("CHECK", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    private void setInSp(String key, boolean value){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("CHECK", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                Intent intent = new Intent(Settings.this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
