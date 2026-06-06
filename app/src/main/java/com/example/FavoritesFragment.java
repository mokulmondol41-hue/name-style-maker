package com.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoritesFragment extends Fragment {

    private RecyclerView rvFavorites;
    private LinearLayout layoutEmpty;
    private StyleAdapter adapter;
    private List<StyledName> favoriteList = new ArrayList<>();
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        rvFavorites = view.findViewById(R.id.rv_favorites_list);
        layoutEmpty = view.findViewById(R.id.layout_empty_favorites);
        prefs = getContext().getSharedPreferences("StyleMakerPrefs", Context.MODE_PRIVATE);

        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new StyleAdapter(getContext(), favoriteList, () -> {
            // Callback fires when an item inside favorites list gets toggled off standard
            loadFavorites();
        });
        rvFavorites.setAdapter(adapter);

        return view;
    }

    private void loadFavorites() {
        if (prefs == null) return;
        favoriteList.clear();
        Set<String> favs = prefs.getStringSet("favorites_set", new HashSet<>());
        if (favs.isEmpty()) {
            rvFavorites.setVisibility(View.GONE);
            layoutEmpty.setVisibility(View.VISIBLE);
        } else {
            for (String text : favs) {
                favoriteList.add(new StyledName(text, true));
            }
            rvFavorites.setVisibility(View.VISIBLE);
            layoutEmpty.setVisibility(View.GONE);
        }
        if (adapter != null) {
            adapter.loadTextSizePref(); // Reload font size preference from SharedPreferences
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites();
    }
}
