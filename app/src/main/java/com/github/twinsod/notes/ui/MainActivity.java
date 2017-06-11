package com.github.twinsod.notes.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.twinsod.notes.App;
import com.github.twinsod.notes.OnPostSelectedListener;
import com.github.twinsod.notes.R;
import com.github.twinsod.notes.ui.detailsNote.presenter.NotePresenter;
import com.github.twinsod.notes.ui.detailsNote.presenter.NotePresenterImpl;
import com.github.twinsod.notes.ui.detailsNote.view.DetailsFragment;
import com.github.twinsod.notes.ui.listNote.presenter.NotesPresenter;
import com.github.twinsod.notes.ui.listNote.presenter.NotesPresenterImpl;
import com.github.twinsod.notes.ui.listNote.view.ListNotesFragment;

import static com.github.twinsod.notes.AppConst.ADD_NEW_NOTE;
import static com.github.twinsod.notes.AppConst.EDIT_NOTE;

public class MainActivity extends AppCompatActivity implements OnPostSelectedListener {
    private NotePresenter notePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runListFragment();
    }

    @Override
    public void onNoteSelected(long id) {
        runDetailsFragment(EDIT_NOTE, id);
    }

    @Override
    public void onNewNoteSelected() {
        runDetailsFragment(ADD_NEW_NOTE);
    }

    private void runDetailsFragment(int type) {
        runDetailsFragment(type, 0);
    }

    private void runDetailsFragment(int type, long id) {
        DetailsFragment fragment = DetailsFragment.getInstance(type);
        if (notePresenter == null)
            notePresenter = new NotePresenterImpl(App.getNodesRepository());
        notePresenter.setPostId(id);
        Log.i("Repository", "activity _ id = " + id);
        fragment.bindPresenter(notePresenter);
        notePresenter.bindView(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void runListFragment() {
        NotesPresenter notesPresenter = new NotesPresenterImpl(this, App.getNodesRepository());
        ListNotesFragment fragment = new ListNotesFragment();
        notesPresenter.bindView(fragment);
        fragment.bindPresenter(notesPresenter);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}
