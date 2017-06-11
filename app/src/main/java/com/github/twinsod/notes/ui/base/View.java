package com.github.twinsod.notes.ui.base;

/**
 * Created by Twins on 10.06.2017.
 */

public interface View<P extends Presenter> {

    void bindPresenter(P presenter);

    P getPresenter();
}
