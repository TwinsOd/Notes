package com.github.twinsod.notes.data.models;

/**
 * Created by User on 08-Jun-17.
 */

public class NoteModel {
    private String description;
    private long date;
    private long id;

    public NoteModel() {
    }

    public NoteModel(String description, long date, int id) {
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
