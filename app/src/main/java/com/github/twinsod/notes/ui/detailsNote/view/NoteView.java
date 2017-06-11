package com.github.twinsod.notes.ui.detailsNote.view;

import android.support.annotation.NonNull;

import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.base.View;
import com.github.twinsod.notes.ui.detailsNote.presenter.NotePresenter;

/**
 * Created by Twins on 10.06.2017.
 */

public interface NoteView extends View<NotePresenter> {
    void showNote(@NonNull NoteModel noteModel);

    void onCompleted();
}