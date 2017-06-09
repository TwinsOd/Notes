package com.github.twinsod.notes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.twinsod.notes.detailsNote.DetailsFragment;
import com.github.twinsod.notes.listNote.ListNotesFragment;
import com.github.twinsod.notes.listNote.NoteModel;

import static com.github.twinsod.notes.AppConst.ADD_NEW_NOTE;
import static com.github.twinsod.notes.AppConst.SHOW_NOTE;

public class MainActivity extends AppCompatActivity implements ListNotesFragment.OnListFragmentInteractionListener {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        runListFragment();
    }

    @Override
    public void onShowNote(NoteModel item) {
        runDetailsFragment(SHOW_NOTE, item.getId());
    }

    @Override
    public void onAddNewNote() {
        runDetailsFragment(ADD_NEW_NOTE);
    }

    private void runDetailsFragment(int type) {
        runDetailsFragment(type, 0);
    }

    private void runDetailsFragment(int type, long id) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        if (id == 0)
            mFragmentTransaction.replace(R.id.container, DetailsFragment.getInstance(type));
        else
            mFragmentTransaction.replace(R.id.container, DetailsFragment.getInstance(type, id));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void runListFragment() {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.container, new ListNotesFragment());
        mFragmentTransaction.commit();
    }
}
