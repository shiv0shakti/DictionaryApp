package algonquin.cst2335.dictionaryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {
    private TextView wordTextView;
    private TextView meaningTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        wordTextView = view.findViewById(R.id.detailsWordTextView);
        meaningTextView = view.findViewById(R.id.detailsMeaningTextView);

        // Retrieve data passed from the activity
        Bundle args = getArguments();
        if (args != null) {
            String word = args.getString("word");
            String meaning = args.getString("meaning");

            // Display details in the fragment
            wordTextView.setText(word);
            meaningTextView.setText(meaning);
        }

        return view;
    }
}

