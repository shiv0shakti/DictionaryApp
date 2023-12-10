package algonquin.cst2335.dictionaryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DictionaryApi extends AppCompatActivity {

    private EditText editText;
    private Button searchButton;
    private RecyclerView recyclerView;
    private DictionaryAdapter dictionaryAdapter;
    private DictionaryDatabaseHelper databaseHelper;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_api);

        editText = findViewById(R.id.editText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView);


        databaseHelper = new DictionaryDatabaseHelper(this);

        dictionaryAdapter = new DictionaryAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dictionaryAdapter);

        searchButton.setOnClickListener(v -> {
            String searchTerm = editText.getText().toString().trim();
            getMeaningFromAPI(searchTerm);
        });

        FloatingActionButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            String wordToSave = editText.getText().toString().trim();
            String meaning = getFirstMeaning();
            saveToDatabase(wordToSave, meaning);
            updateRecyclerView(getDictionaryItems());
        });
        // Inside your DictionaryApi activity
        FloatingActionButton viewSavedButton = findViewById(R.id.viewSavedButton);
        viewSavedButton.setOnClickListener(v -> {
            startActivity(new Intent(DictionaryApi.this, ViewSavedWordsActivity.class));
        });

    }

    private List<DictionaryItem> getDictionaryItems() {
        List<DictionaryItem> items = new ArrayList<>();
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
            items.add(new DictionaryItem(word, meaning));
        }

        cursor.close();
        db.close();
        return items;
    }

    private void getMeaningFromAPI(String searchTerm) {
        String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchTerm;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateMeaning(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showAlertDialog("Error", "No Definition found.");
                    }
                });

        queue.add(stringRequest);
    }

    private void updateMeaning(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            List<DictionaryItem> dictionaryItems = new ArrayList<>();

            if (jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject wordObject = jsonArray.getJSONObject(i);
                    String word = wordObject.getString("word");

                    JSONArray meaningsArray = wordObject.getJSONArray("meanings");
                    if (meaningsArray.length() > 0) {
                        for (int j = 0; j < meaningsArray.length(); j++) {
                            JSONObject meaningObject = meaningsArray.getJSONObject(j);
                            String partOfSpeech = meaningObject.getString("partOfSpeech");

                            JSONArray definitionsArray = meaningObject.getJSONArray("definitions");
                            for (int k = 0; k < definitionsArray.length(); k++) {
                                JSONObject definitionObject = definitionsArray.getJSONObject(k);
                                String definition = definitionObject.getString("definition");

                                DictionaryItem dictionaryItem = new DictionaryItem(word, definition);
                                dictionaryItems.add(dictionaryItem);
                            }
                        }
                    }
                }
            }

            updateRecyclerView(dictionaryItems);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("API_RESPONSE_ERROR", "Failed to parse API response. Response: " + response);
            showAlertDialog("Error", "Failed to parse API response.");
        }
    }

    private void saveToDatabase(String word, String meaning) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DictionaryDatabaseHelper.COLUMN_WORD, word);
        values.put(DictionaryDatabaseHelper.COLUMN_MEANING, meaning);

        long result = db.insertWithOnConflict(
                DictionaryDatabaseHelper.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE
        );

        if (result != -1) {
            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void updateRecyclerView(List<DictionaryItem> dictionaryItems) {
        dictionaryAdapter.setData(dictionaryItems);
        dictionaryAdapter.notifyDataSetChanged();
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle OK button click
                })
                .show();
    }

    private String getFirstMeaning() {
        // Implement your logic to get the meaning
        return "";
    }
}
