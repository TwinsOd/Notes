package com.github.twinsod.notes.ui.listNote.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.twinsod.notes.R;
import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.listNote.adapter.NotesRecyclerViewAdapter;
import com.github.twinsod.notes.ui.listNote.presenter.NotesPresenter;

import java.util.ArrayList;
import java.util.List;


public class ListNotesFragment extends Fragment implements NotesView, NoteInteractors {

    private List<NoteModel> mNodeList = new ArrayList<>();
    private NotesPresenter notesPresenter;
    private NotesRecyclerViewAdapter mAdapter;

    public ListNotesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notesmodel_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNodeList.clear();

        mAdapter = new NotesRecyclerViewAdapter(mNodeList, this);
        recyclerView.setAdapter(mAdapter);

        view.findViewById(R.id.fab_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().OnNewNoteClick();
            }
        });

        getPresenter().loadPosts();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void bindPresenter(NotesPresenter presenter) {
        notesPresenter = presenter;
    }

    @Override
    public NotesPresenter getPresenter() {
        return notesPresenter;
    }

    @Override
    public void showPosts(@NonNull List<NoteModel> models) {
        mAdapter.update(models);
    }

    @Override
    public void onNoteClick(long postId) {
        getPresenter().onPostClick(postId);
    }
}
