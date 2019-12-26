package com.example.minimaltodo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import  android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DeleteTodo.DeleteTodoListener {

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    FloatingActionButton addButton;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<TodoItem> list = new ArrayList<>();
    LinearLayout noItem;
    ConstraintLayout main;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivitySetTheme(this);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodosActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        init();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, TodosActivity.class);

                intent.putExtra("pos", position);
                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("des", list.get(position).getDescription());
                intent.putExtra("date", list.get(position).getDate());

                startActivityForResult(intent, 1);
            }
        });

    }


    private void init() {
        recyclerView = findViewById(R.id.mainRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapter(list);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelper = new DeleteTodo(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                list.add(new TodoItem(data.getStringExtra("title"), data.getStringExtra("des"), data.getStringExtra("date")));
                adapter.notifyDataSetChanged();
                SaveData(list);
                setNoItem();

            } else if (requestCode == 1) {
                list.set(data.getIntExtra("position", 0), new TodoItem(data.getStringExtra("title"), data.getStringExtra("des"), data.getStringExtra("date")));
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        main = findViewById(R.id.main);

        if (viewHolder instanceof RecyclerAdapter.ItemViewHolder) {
            final TodoItem deleteItem = list.get(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(main, "Deleted Todos", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deleteItem, deleteIndex);
                    setNoItem();
                }
            });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            setNoItem();
        }

    }

    public void setNoItem() {
        noItem = findViewById(R.id.noItem);
        if (adapter.getItemCount() == 0) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }
    }

    public void SaveData(ArrayList<TodoItem> items){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("MyItems", json);
        editor.commit();
    }

    public ArrayList<TodoItem> ReadData(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = pref.getString("MyItems", "EMPTY");
        Type type = new TypeToken<ArrayList<TodoItem>>(){}.getType();
        ArrayList<TodoItem> items = gson.fromJson(json, type);
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutItem :
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.settings :
                Intent intent2 = new Intent(MainActivity.this, Settings.class);
                startActivity(intent2);
                return true;

                default : return super.onOptionsItemSelected(item);
        }
    }
}
