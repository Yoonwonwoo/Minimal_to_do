package com.example.minimaltodo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodosActivity extends Settings {
    FloatingActionButton enterButton;
    EditText titleEdit, desEdit;
    SwitchCompat dateSwitch;
    LinearLayout dateLinear;
    Button dateButton, timeButton;
    Calendar calendar = Calendar.getInstance();
    TextView resultText;
    ArrayList<TodoItem> list = new ArrayList<>();
    String titleStr, desStr, dateStr;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpletime = new SimpleDateFormat("hh:mm");
    String datenow = simpledate.format(date), timenow = simpletime.format(date);
    int y, m, d, h, mi, pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        resultText = findViewById(R.id.resultText);
        titleEdit = findViewById(R.id.titleEdit);
        desEdit = findViewById(R.id.desEdit);
        timeButton = findViewById(R.id.timeButton);
        dateButton = findViewById(R.id.dateButton);

        pos = getIntent().getIntExtra("pos", 0);

        dateButton.setText(datenow);
        timeButton.setText(timenow);

        enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (titleEdit.getText().toString().length() == 0 || desEdit.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "더 쓰자", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    TodoItem item = new TodoItem(titleEdit.getText().toString(), desEdit.getText().toString(), resultText.getText().toString());
                    intent.putExtra("title", titleEdit.getText().toString());
                    intent.putExtra("des", desEdit.getText().toString());
                    intent.putExtra("date", resultText.getText().toString());
                    intent.putExtra("position", pos);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        dateSwitch = findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dateLinear = findViewById(R.id.dateLinear);
                if (b) {
                    dateLinear.setVisibility(View.VISIBLE);
                } else {
                    dateLinear.setVisibility(View.INVISIBLE);
                }
            }
        });

        dateButton.setPaintFlags(dateButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TodosActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month+1;
                        d = dayOfMonth;
                        updateDialog();
                        updateButton();
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        timeButton.setPaintFlags(timeButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(TodosActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        h = hourOfDay;
                        mi = minute;
                        updateDialog();
                        updateButton();
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true);

                timePickerDialog.show();

            }

        });

        titleStr = getIntent().getStringExtra("title");
        desStr = getIntent().getStringExtra("des");
        dateStr = getIntent().getStringExtra("date");

        if(titleStr != null){
            titleEdit.setText(titleStr);
            desEdit.setText(desStr);
            resultText.setText(dateStr);
        }

    }

    private void updateDialog(){
        resultText = findViewById(R.id.resultText);
        resultText.setText(y + "년 " + m + "월 " + d +"일 " + h + "시 " + mi +"분");
    }

    private void updateButton(){
        dateButton.setText(y+"."+m+"."+d);
        timeButton.setText(h+":"+mi);
    }


}
