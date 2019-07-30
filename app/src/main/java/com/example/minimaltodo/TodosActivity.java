package com.example.minimaltodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TodosActivity extends AppCompatActivity {
    FloatingActionButton enterButton;
    EditText titleEdit, desEdit;
    SwitchCompat dateSwitch;
    LinearLayout dateLinear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titleEdit.getText().toString().equals("") || desEdit.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"더 써 짜식아", Toast.LENGTH_SHORT ).show();
                }else{
                    Intent intent = new Intent();
                    TodoItem item = new TodoItem(titleEdit.getText().toString(), desEdit.getText().toString());
                    intent.putExtra("wonwoo is best",item);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

        dateSwitch = findViewById(R.id.dateSwitch);
        dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dateLinear.setVisibility(View.VISIBLE);
                }else{
                    dateLinear.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
