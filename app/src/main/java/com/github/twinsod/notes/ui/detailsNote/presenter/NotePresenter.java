package com.github.twinsod.notes.ui.detailsNote.presenter;

import android.support.annotation.NonNull;

import com.github.twinsod.notes.data.models.NoteModel;
import com.github.twinsod.notes.ui.base.Presenter;
import com.github.twinsod.notes.ui.detailsNote.view.NoteView;

/**
 * Created by Twins on 10.06.2017.
 */

public interface NotePresenter extends Presenter<NoteView> {
    void setPostId(long postId);

    void loadPost();

    void updateNote(@NonNull NoteModel noteModel);

    void saveNote(@NonNull String text);
}
