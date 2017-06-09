package com.github.twinsod.notes.data.db;

/**
 * Created by User on 08-Jun-17.
 */

public class DatabaseConst {

    static final class DATABASE {
        static final String NAME = "notes_db.db";
        static final int VERSION = 1;
    }

    public static final class TABLE {
        public static final String NOTES = "notes";
    }

    public static final class NOTES_FIELDS {
        public static final String ID = "id";
        public static final String DESCRIPTION = "description";
        public static final String DATE = "date";
    }

    static final class QUERY {

        static final String CREATE_TABLE_NOTES = "CREATE TABLE "
                + TABLE.NOTES
                + " ("
                + NOTES_FIELDS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " //" INTEGER PRIMARY KEY, "
                + NOTES_FIELDS.DESCRIPTION + " TEXT, "
                + NOTES_FIELDS.DATE + " TEXT "
                + ");";

        static final String DROP_TABLE_NOTES = "DROP TABLE IF EXISTS " + TABLE.NOTES + ";";
    }
}
