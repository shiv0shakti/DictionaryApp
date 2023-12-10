package algonquin.cst2335.dictionaryapp;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedWordsAdapter extends RecyclerView.Adapter<SavedWordsAdapter.SavedWordsViewHolder> {

    private List<DictionaryItem> savedWordsList;
    private ViewSavedWordsActivity context;

    public SavedWordsAdapter(List<DictionaryItem> savedWordsList, ViewSavedWordsActivity context) {
        this.savedWordsList = savedWordsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedWordsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_word, parent, false);
        return new SavedWordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedWordsViewHolder holder, int position) {
        DictionaryItem dictionaryItem = savedWordsList.get(position);
        holder.savedWordTextView.setText(dictionaryItem.getWord());
        holder.savedMeaningTextView.setText(dictionaryItem.getMeaning());

        // Set OnClickListener for the delete button
        holder.deleteButton.setOnClickListener(v -> {
            // Call a method to delete the word from the database
            deleteWordFromDatabase(dictionaryItem.getWord());
            // Remove the item from the list
            savedWordsList.remove(position);
            // Notify the adapter about the removal
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return savedWordsList.size();
    }

    public static class SavedWordsViewHolder extends RecyclerView.ViewHolder {
        TextView savedWordTextView;
        TextView savedMeaningTextView;
        Button deleteButton;

        public SavedWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            savedWordTextView = itemView.findViewById(R.id.savedWordTextView);
            savedMeaningTextView = itemView.findViewById(R.id.savedMeaningTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    // Method to delete the word from the database
    private void deleteWordFromDatabase(String word) {
        DictionaryDatabaseHelper databaseHelper = new DictionaryDatabaseHelper(context);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DictionaryDatabaseHelper.TABLE_NAME,
                DictionaryDatabaseHelper.COLUMN_WORD + " = ?",
                new String[]{word});
        db.close();
    }
}
