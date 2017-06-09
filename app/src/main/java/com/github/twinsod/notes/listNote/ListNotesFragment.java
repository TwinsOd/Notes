package com.github.twinsod.notes.listNote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.db.DatabaseConst;
import com.github.twinsod.notes.data.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListNotesFragment extends Fragment {
    private SQLiteDatabase database;
    private List<NoteModel> mNodeList = new ArrayList<>();
    private OnListFragmentInteractionListener mListener;
    private NotesRecyclerViewAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListNotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ListNotesFragment", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("ListNotesFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_notesmodel_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mNodeList.clear();
        loadListNotes();
        mAdapter = new NotesRecyclerViewAdapter(mNodeList, mListener);
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fab_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddNewNote();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        Log.i("ListNotesFragment", "onResume");
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        Log.i("ListNotesFragment", "onAttach");
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadListNotes() {
        Log.i("ListNotesFragment", "loadListNotes");
        openDB();
        Cursor cursor = database.query(DatabaseConst.TABLE.NOTES, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.ID));
                long date = cursor.getLong(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DATE));
                String description = cursor.getString(cursor.getColumnIndex(DatabaseConst.NOTES_FIELDS.DESCRIPTION));
                mNodeList.add(new NoteModel(description, date, id));
                cursor.moveToNext();
            }
            cursor.close();
            if (mAdapter != null)
                mAdapter.notifyDataSetChanged();
        }
        closeDB();
    }

    private void openDB() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        database = databaseHelper.getWritableDatabase();
    }

    private void closeDB() {
        database.close();
    }

    public interface OnListFragmentInteractionListener {
        void onShowNote(NoteModel item);

        void onAddNewNote();
    }
}
