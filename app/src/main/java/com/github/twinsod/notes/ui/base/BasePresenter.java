package com.github.twinsod.notes.ui.base;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Twins on 10.06.2017.
 */

public abstract class BasePresenter<V extends View> implements Presenter<V> {

    private boolean isFirstAttach = true;

    @Nullable
    private V view;

    @Override
    public void bindView(@NonNull V view) {
        this.view = view;
        if (this.isFirstAttach) {
            this.isFirstAttach = false;
            this.onFirstBinding();
        }
    }

    @Override
    public void unbindView() {
        this.view = null;
    }

    @Nullable
    @Override
    public V getView() {
        return this.view;
    }

    @SuppressWarnings("WeakerAccess")
    protected void onFirstBinding() {
    }
}
