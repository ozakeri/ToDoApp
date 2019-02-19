package com.example.roomapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.roomapplication.adapter.TasksAdapter;
import com.example.roomapplication.model.Task;
import com.example.roomapplication.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton floating_button_add;
    RecyclerView recyclerView;
    List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floating_button_add = findViewById(R.id.floating_button_add);
        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getList();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("task", taskList.get(position));
                Intent intent = new Intent(getApplicationContext(), UpdateTaskActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));

        floating_button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
            }
        });
    }

    public void getList() {
        class TaskList extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                taskList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TasksAdapter tasksAdapter = new TasksAdapter(getApplicationContext(), tasks);
                recyclerView.setAdapter(tasksAdapter);
            }
        }

        TaskList taskList = new TaskList();
        taskList.execute();
    }
}
