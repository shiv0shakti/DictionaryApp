package algonquin.cst2335.dictionaryapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "dictionary";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_MEANING = "meaning";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_WORD + " TEXT PRIMARY KEY," +
                    COLUMN_MEANING + " TEXT);";

    public DictionaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Recreate the database by calling onCreate
        onCreate(db);
    }
}

