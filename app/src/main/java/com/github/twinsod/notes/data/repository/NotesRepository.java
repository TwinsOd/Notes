package com.github.twinsod.notes.data.repository;

import android.support.annotation.NonNull;

import com.github.twinsod.notes.data.callback.NoteCallback;
import com.github.twinsod.notes.ui.listNote.NoteModel;

import java.util.List;

/**
 * Created by User on 09-Jun-17.
 */

public interface NotesRepository {
    void getNotes(@NonNull NoteCallback<List<NoteModel>> callback);

    void getNote(long idNote, @NonNull NoteCallback<NoteModel> callback);

    void saveNote(String text,@NonNull NoteCallback callback);

    void updateNote(long idNote, String text, @NonNull NoteCallback callback);
}
