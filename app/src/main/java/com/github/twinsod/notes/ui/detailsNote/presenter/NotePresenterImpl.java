package com.github.twinsod.notes.ui.detailsNote.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.twinsod.notes.data.callback.NoteCallback;
import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.data.repository.NotesRepository;
import com.github.twinsod.notes.ui.base.BasePresenter;
import com.github.twinsod.notes.ui.detailsNote.view.NoteView;

/**
 * Created by Twins on 10.06.2017.
 */

public class NotePresenterImpl extends BasePresenter<NoteView> implements NotePresenter {
    private final NotesRepository notesRepository;
    private long currentId;

    public NotePresenterImpl(NotesRepository nodesRepository) {
        this.notesRepository = nodesRepository;
    }

    @Override
    public void setPostId(long id) {
        currentId = id;
    }

    @Override
    public void loadPost() {
        if (currentId != 0)
            notesRepository.getNote(currentId, new NoteCallback<NoteModel>() {
                @Override
                public void onEmit(NoteModel data) {
                    modelLoaded(data);
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
    public void updateNote(@NonNull NoteModel noteModel) {
        notesRepository.updateNote(noteModel.getId(), noteModel.getDescription(), new NoteCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {
                onDone();
            }

            @Override
            public void onError(Throwable throwable) {
                showError(throwable);
            }
        });
    }

    @Override
    public void saveNote(@NonNull String text) {
        notesRepository.saveNote(text, new NoteCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {
                onDone();
            }

            @Override
            public void onError(Throwable throwable) {
                showError(throwable);
            }
        });
    }

    @Override
    public void deleteNote(@NonNull long id) {
        notesRepository.deleteNote(id, new NoteCallback() {
            @Override
            public void onEmit(Object data) {

            }

            @Override
            public void onCompleted() {
                onDone();
            }

            @Override
            public void onError(Throwable throwable) {
                showError(throwable);
            }
        });
    }

    private void showError(Throwable throwable) {
        Log.d("NotePresenterImpl", throwable.getMessage());
    }

    private void onDone() {
        if (getView() != null)
            getView().onCompleted();
    }

    private void modelLoaded(NoteModel data) {
        if (data != null) {
            if (getView() != null)
                getView().showNote(data);
        }
    }
}
