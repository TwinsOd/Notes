package com.github.twinsod.notes.ui.listNote.view;

import android.support.annotation.NonNull;

import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.base.View;
import com.github.twinsod.notes.ui.listNote.presenter.NotesPresenter;

import java.util.List;

/**
 * Created by Twins on 10.06.2017.
 */

public interface NotesView extends View<NotesPresenter> {
    void showPosts(@NonNull List<NoteModel> models);

}
