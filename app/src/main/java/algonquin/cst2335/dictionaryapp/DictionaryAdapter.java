package algonquin.cst2335.dictionaryapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private List<DictionaryItem> dictionaryItems;

    public DictionaryAdapter(List<DictionaryItem> dictionaryItems) {
        this.dictionaryItems = dictionaryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DictionaryItem item = dictionaryItems.get(position);
        // Add the symbol (# or $) in front of the meaning
        String symbol = "#";
        String formattedMeaning = symbol + " " + item.getMeaning();
        holder.wordTextView.setText(item.getWord());
        holder.meaningTextView.setText(formattedMeaning);
    }

    @Override
    public int getItemCount() {
        return dictionaryItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView wordTextView;
        public TextView meaningTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            meaningTextView = itemView.findViewById(R.id.meaningTextView);
        }
    }
    public void setData(List<DictionaryItem> dictionaryItems) {
        this.dictionaryItems = dictionaryItems;
    }
}

