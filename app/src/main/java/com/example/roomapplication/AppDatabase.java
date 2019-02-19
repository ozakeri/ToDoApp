package com.example.roomapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.roomapplication.model.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
