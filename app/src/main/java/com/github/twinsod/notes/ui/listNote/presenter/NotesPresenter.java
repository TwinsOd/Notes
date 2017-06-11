package com.github.twinsod.notes.ui.listNote.presenter;

import com.github.twinsod.notes.ui.base.Presenter;
import com.github.twinsod.notes.ui.listNote.view.NotesView;

/**
 * Created by Twins on 10.06.2017.
 */

public interface NotesPresenter extends Presenter<NotesView> {
    void loadPosts();

    void onPostClick(long postId);

    void OnNewNoteClick();
}
