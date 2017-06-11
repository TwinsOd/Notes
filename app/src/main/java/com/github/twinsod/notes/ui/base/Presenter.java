package com.github.twinsod.notes.ui.base;

/**
 * Created by Twins on 10.06.2017.
 */

public interface Presenter<V extends View> {

    void bindView(V view);

    void unbindView();

    V getView();
}