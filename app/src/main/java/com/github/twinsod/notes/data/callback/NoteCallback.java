package com.github.twinsod.notes.data.callback;

/**
 * Created by User on 09-Jun-17.
 */

public interface NoteCallback<T> {
    void onEmit(T data);

    void onCompleted();

    void onError(Throwable throwable);
}
