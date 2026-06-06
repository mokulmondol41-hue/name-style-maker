package com.example;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextInputEditText etNameInput;
    private Button btnGenerate;
    private RecyclerView rvStyles;
    private LinearLayout layoutEmpty;
    private StyleAdapter adapter;
    private List<StyledName> styleList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etNameInput = view.findViewById(R.id.et_name_input);
        btnGenerate = view.findViewById(R.id.btn_generate_styles);
        rvStyles = view.findViewById(R.id.rv_styles_list);
        layoutEmpty = view.findViewById(R.id.layout_empty_home);

        rvStyles.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StyleAdapter(getContext(), styleList, () -> {
            // Callback when favorite toggled
            // Home fragment doesn't need to rebuild the list, but it can refresh state if needed
        });
        rvStyles.setAdapter(adapter);

        btnGenerate.setOnClickListener(v -> generateStyles());

        return view;
    }

    private void generateStyles() {
        String input = etNameInput.getText() != null ? etNameInput.getText().toString().trim() : "";
        if (input.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.msg_empty_input), Toast.LENGTH_SHORT).show();
            return;
        }

        styleList.clear();
        List<String> generated = StyleGenerator.generateAllStyles(input);
        for (String text : generated) {
            styleList.add(new StyledName(text, false));
        }

        adapter.loadTextSizePref(); // Reload font size preference from SharedPreferences
        adapter.notifyDataSetChanged();

        layoutEmpty.setVisibility(View.GONE);
        rvStyles.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.loadTextSizePref(); // Re-apply font sizes if changed in Settings
            adapter.notifyDataSetChanged(); // Sync favorite states of current styles on resume
        }
    }
}
