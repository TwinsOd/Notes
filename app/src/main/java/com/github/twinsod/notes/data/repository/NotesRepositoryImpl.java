package com.github.twinsod.notes.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import com.github.twinsod.notes.data.callback.NoteCallback;
import com.github.twinsod.notes.data.db.DatabaseConst;
import com.github.twinsod.notes.data.db.DatabaseHelper;
import com.github.twinsod.notes.data.models.NoteModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by User on 09-Jun-17.
 */

public class NotesRepositoryImpl implements NotesRepository {
    private SQLiteDatabase database;
    private Context mContext;

    public NotesRepositoryImpl(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void getNotes(@NonNull NoteCallback<List<NoteModel>> callback) {
        loadListNotes(callback);
    }

    @Override
    public void getNote(long idNote, @NonNull NoteCallback<NoteModel> callback) {
        getNoteById(idNote, callback);
    }

    @Override
    public void saveNote(String text, @NonNull NoteCallback callback) {
        saveNewNote(text, callback);
    }

    @Override
    public void updateNote(long idNote, String text, @NonNull NoteCallback callback) {
        update(idNote, text, callback);
    }

    @Override
    public void deleteNote(@NonNull long id, @NonNull NoteCallback callback) {
        delete(id, callback);
    }


    private void delete(long id, NoteCallback callback) {
        try {
            openDB();
            SQLiteStatement stmt = database.compileStatement(
                    String.format("DELETE FROM %s WHERE %s = ?1",
                            DatabaseConst.TABLE.NOTES, DatabaseConst.NOTES_FIELDS.ID));
            database.beginTransaction();

            stmt.clearBindings();
            stmt.bindLong(1, id);
            stmt.executeInsert();
            database.setTransactionSuccessful();
            database.endTransaction();
            closeDB();
            callback.onCompleted();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private void loadListNotes(NoteCallback<List<NoteModel>> callback) {
        try {
            List<NoteModel> noteModels = new ArrayList<>();

            openDB();
            Cursor cursor = database.query(DatabaseConst.TABLE.NOTES, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.ID));
                    long date = cursor.getLong(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DATE));
                    String description = cursor.getString(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DESCRIPTION));
                    noteModels.add(new NoteModel(description, date, id));
                    cursor.moveToNext();
                }
                cursor.close();
            }
            closeDB();
            callback.onEmit(noteModels);
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private void update(long idNotes, String text, NoteCallback callback) {
        try {
            openDB();
            SQLiteStatement stmt = database.compileStatement(
                    String.format("UPDATE %s SET %s = ?1 WHERE %s = ?2",
                            DatabaseConst.TABLE.NOTES, DatabaseConst.NOTES_FIELDS.DESCRIPTION, DatabaseConst.NOTES_FIELDS.ID));
            database.beginTransaction();

            stmt.clearBindings();
            stmt.bindString(1, text);
            stmt.bindLong(2, idNotes);
            stmt.executeInsert();
            database.setTransactionSuccessful();
            database.endTransaction();
            closeDB();
            callback.onCompleted();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private void saveNewNote(String text, NoteCallback callback) {
        try {
            openDB();
            SQLiteStatement stmt = database.compileStatement(
                    String.format("INSERT INTO %s ('%s', '%s', '%s') VALUES(?1, ?2, ?3)",
                            DatabaseConst.TABLE.NOTES,
                            DatabaseConst.NOTES_FIELDS.ID, DatabaseConst.NOTES_FIELDS.DESCRIPTION, DatabaseConst.NOTES_FIELDS.DATE));
            database.beginTransaction();

            stmt.clearBindings();
            stmt.bindString(2, text);
            stmt.bindLong(3, Calendar.getInstance().getTimeInMillis());
            stmt.executeInsert();
            database.setTransactionSuccessful();
            database.endTransaction();
            closeDB();
            callback.onCompleted();
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private void getNoteById(long id, NoteCallback<NoteModel> callback) {
        try {
            NoteModel model = new NoteModel();
            openDB();
            String where = DatabaseConst.TABLE.NOTES + "." + DatabaseConst.NOTES_FIELDS.ID + " = ?";
            String[] args = new String[]{String.valueOf(id)};
            Cursor cursor = database.query(DatabaseConst.TABLE.NOTES, null, where, args, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String description = cursor.getString(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DESCRIPTION));
                model.setDescription(description);
                long date = cursor.getLong(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DATE));
                model.setDate(date);
                model.setId(id);
                cursor.close();
            }
            closeDB();
            callback.onEmit(model);
        } catch (Exception e) {
            callback.onError(e);
        }
    }

    private void openDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
        database = databaseHelper.getWritableDatabase();
    }

    private void closeDB() {
        database.close();
    }
}
