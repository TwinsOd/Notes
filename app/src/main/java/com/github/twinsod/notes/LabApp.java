package com.github.twinsod.notes;

import android.app.Application;

import com.github.twinsod.notes.data.repository.NotesRepository;
import com.github.twinsod.notes.data.repository.NotesRepositoryImpl;

/**
 * Created by User on 09-Jun-17.
 */

public class LabApp extends Application {
    private static NotesRepository notesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        notesRepository = new NotesRepositoryImpl(this);
    }

    public static NotesRepository getNodesRepository() {
        return notesRepository;
    }
}
