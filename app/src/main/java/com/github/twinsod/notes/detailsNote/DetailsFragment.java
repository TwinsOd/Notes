package com.github.twinsod.notes.detailsNote;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.db.DatabaseConst;
import com.github.twinsod.notes.data.db.DatabaseHelper;
import com.github.twinsod.notes.listNote.NoteModel;

import java.lang.annotation.Retention;
import java.util.Calendar;

import static com.github.twinsod.notes.AppConst.ADD_NEW_NOTE;
import static com.github.twinsod.notes.AppConst.EDIT_NOTE;
import static com.github.twinsod.notes.AppConst.SHOW_NOTE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements View.OnClickListener {

    private EditText mEditView;

    @Retention(SOURCE)
    @IntDef({ADD_NEW_NOTE, EDIT_NOTE})
    public @interface DetailMode {
    }

    private static final String PARAM_TYPE = "extra_type";
    private static final String PARAM_ID = "extra_id";
    private int type;
    private long id;
    private SQLiteDatabase database;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment getInstance(@DetailMode int type) {
        DetailsFragment postFragment = new DetailsFragment();
        Bundle args = new Bundle(1);
        args.putInt(PARAM_TYPE, type);
        postFragment.setArguments(args);
        return postFragment;
    }

    public static DetailsFragment getInstance(@DetailMode int type, long id) {
        DetailsFragment postFragment = new DetailsFragment();
        Bundle args = new Bundle(2);
        args.putInt(PARAM_TYPE, type);
        args.putLong(PARAM_ID, id);
        postFragment.setArguments(args);
        return postFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(PARAM_TYPE);
            if (bundle.containsKey(PARAM_ID)) {
                id = bundle.getLong(PARAM_ID);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView titleView = (TextView) view.findViewById(R.id.title_note);
        mEditView = (EditText) view.findViewById(R.id.edit_view);
        mEditView.setOnClickListener(this);
        Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        switch (type) {
            case ADD_NEW_NOTE:
                titleView.setText(R.string.add_new_note);
                okButton.setText(R.string.save);
                break;
            case EDIT_NOTE:
            case SHOW_NOTE:
                titleView.setText(R.string.edit_note);
                mEditView.setText(getNoteById(id).getDescription());
                okButton.setText(R.string.save);
                break;
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_button:
                if (type == ADD_NEW_NOTE)
                    saveNewNote();
                else
                    updateNote(id);
                getActivity().onBackPressed();
                break;
            case R.id.edit_view:
                mEditView.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                mEditView.setClickable(true);
                mEditView.setFocusable(true);
                break;
        }
    }

    private void updateNote(long idNotes) {
        openDB();
        SQLiteStatement stmt = database.compileStatement(
                String.format("UPDATE %s SET %s = ?1 WHERE %s = ?2",
                        DatabaseConst.TABLE.NOTES,  DatabaseConst.NOTES_FIELDS.DESCRIPTION, DatabaseConst.NOTES_FIELDS.ID));
        database.beginTransaction();

        stmt.clearBindings();
        stmt.bindString(1, mEditView.getText().toString());
        stmt.bindLong(2, idNotes);
        stmt.executeInsert();
        database.setTransactionSuccessful();
        database.endTransaction();
        closeDB();
    }

    private void saveNewNote() {
        openDB();
        SQLiteStatement stmt = database.compileStatement(
                String.format("INSERT INTO %s ('%s', '%s', '%s') VALUES(?1, ?2, ?3)",
                        DatabaseConst.TABLE.NOTES,
                        DatabaseConst.NOTES_FIELDS.ID, DatabaseConst.NOTES_FIELDS.DESCRIPTION, DatabaseConst.NOTES_FIELDS.DATE));
        database.beginTransaction();

        stmt.clearBindings();
        stmt.bindString(2, mEditView.getText().toString());
        stmt.bindLong(3, Calendar.getInstance().getTimeInMillis());
        stmt.executeInsert();
        database.setTransactionSuccessful();
        database.endTransaction();
        closeDB();

    }

    private NoteModel getNoteById(long id) {
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
            cursor.close();
        }
        closeDB();
        return model;
    }

    private void openDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
    }

    private void closeDB() {
        database.close();
    }
}
