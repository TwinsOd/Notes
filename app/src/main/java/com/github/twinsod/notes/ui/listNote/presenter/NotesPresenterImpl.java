package com.github.twinsod.notes.ui.listNote.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.github.twinsod.notes.OnPostSelectedListener;
import com.github.twinsod.notes.data.callback.NoteCallback;
import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.data.repository.NotesRepository;
import com.github.twinsod.notes.ui.base.BasePresenter;
import com.github.twinsod.notes.ui.listNote.view.NotesView;

import java.util.List;

/**
 * Created by Twins on 10.06.2017.
 */

public class NotesPresenterImpl extends BasePresenter<NotesView> implements NotesPresenter {
    private OnPostSelectedListener onPostSelectedListener;
    private NotesRepository notesRepository;

    public NotesPresenterImpl(@Nullable OnPostSelectedListener onPostSelectedListener, @Nullable NotesRepository notesRepository) {
        this.onPostSelectedListener = onPostSelectedListener;
        this.notesRepository = notesRepository;
    }

    @Override
    public void loadPosts() {
        notesRepository.getNotes(new NoteCallback<List<NoteModel>>() {
            @Override
            public void onEmit(List<NoteModel> data) {
                postsLoaded(data);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                showError(throwable);
            }
        });
    }

    @Override
    public void onPostClick(long postId) {
        if (onPostSelectedListener != null)
            onPostSelectedListener.onNoteSelected(postId);
    }

    @Override
    public void OnNewNoteClick() {
        if (onPostSelectedListener != null)
            onPostSelectedListener.onNewNoteSelected();
    }

    private void postsLoaded(List<NoteModel> data) {
        if (data != null) {
            if (getView() != null)
                getView().showPosts(data);
        }
    }

    private void showError(Throwable throwable) {
        Log.i("NotesPresenterImpl", throwable.getMessage());
    }
}
