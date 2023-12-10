package algonquin.cst2335.dictionaryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ViewSavedWordsActivity extends AppCompatActivity {

    private RecyclerView savedWordsRecyclerView;
    private SavedWordsAdapter savedWordsAdapter;
    private DictionaryDatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_saved_words);

        savedWordsRecyclerView = findViewById(R.id.savedWordsRecyclerView);
        databaseHelper = new DictionaryDatabaseHelper(this);

        savedWordsAdapter = new SavedWordsAdapter(getSavedWords(), this); // Pass the context
        savedWordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        savedWordsRecyclerView.setAdapter(savedWordsAdapter);


    }

    private List<DictionaryItem> getSavedWords() {
        List<DictionaryItem> savedWords = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DictionaryDatabaseHelper.TABLE_NAME,
                new String[]{DictionaryDatabaseHelper.COLUMN_WORD, DictionaryDatabaseHelper.COLUMN_MEANING},
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String word = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryDatabaseHelper.COLUMN_WORD));
            String meaning = cursor.getString(cursor.getColumnIndexOrThrow(DictionaryDatabaseHelper.COLUMN_MEANING));
            savedWords.add(new DictionaryItem(word, meaning));
        }

        cursor.close();
        db.close();
        return savedWords;
    }
}
