package com.example;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StyleAdapter extends RecyclerView.Adapter<StyleAdapter.ViewHolder> {

    private final Context context;
    private final List<StyledName> items;
    private final SharedPreferences prefs;
    private float textSizeSp = 18f;
    private final OnFavoriteChangeListener favoriteChangeListener;

    public interface OnFavoriteChangeListener {
        void onFavoriteChanged();
    }

    public StyleAdapter(Context context, List<StyledName> items, OnFavoriteChangeListener listener) {
        this.context = context;
        this.items = items;
        this.favoriteChangeListener = listener;
        this.prefs = context.getSharedPreferences("StyleMakerPrefs", Context.MODE_PRIVATE);
        loadTextSizePref();
    }

    public void loadTextSizePref() {
        int sizePref = prefs.getInt("font_size_level", 1); // 0=Small, 1=Medium, 2=Large
        if (sizePref == 0) {
            textSizeSp = 14f;
        } else if (sizePref == 2) {
            textSizeSp = 24f;
        } else {
            textSizeSp = 18f;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_style, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StyledName item = items.get(position);
        
        holder.tvStyledName.setText(item.getStyledText());
        holder.tvStyledName.setTextSize(textSizeSp);
        holder.tvStyleIndex.setText("Style #" + (position + 1));

        // Sync local saved favorite state
        Set<String> favs = prefs.getStringSet("favorites_set", new HashSet<>());
        boolean isFav = favs.contains(item.getStyledText());
        item.setFavorite(isFav);

        if (isFav) {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);
        }

        // Action: Copy
        holder.btnCopy.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Styled Name", item.getStyledText());
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, context.getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show();
            }
        });

        // Action: Share
        holder.btnShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, item.getStyledText());
            context.startActivity(Intent.createChooser(intent, "Share Styled Name"));
        });

        // Action: Toggle Favorite
        holder.btnFavorite.setOnClickListener(v -> {
            Set<String> currentFavs = new HashSet<>(prefs.getStringSet("favorites_set", new HashSet<>()));
            boolean nextState = !item.isFavorite();
            item.setFavorite(nextState);

            if (nextState) {
                currentFavs.add(item.getStyledText());
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite);
            } else {
                currentFavs.remove(item.getStyledText());
                holder.btnFavorite.setImageResource(R.drawable.ic_favorite_border);
            }

            prefs.edit().putStringSet("favorites_set", currentFavs).apply();

            if (favoriteChangeListener != null) {
                favoriteChangeListener.onFavoriteChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStyledName;
        TextView tvStyleIndex;
        ImageButton btnFavorite;
        ImageButton btnShare;
        ImageButton btnCopy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStyledName = itemView.findViewById(R.id.tv_styled_name);
            tvStyleIndex = itemView.findViewById(R.id.tv_style_index);
            btnFavorite = itemView.findViewById(R.id.btn_style_favorite);
            btnShare = itemView.findViewById(R.id.btn_style_share);
            btnCopy = itemView.findViewById(R.id.btn_style_copy);
        }
    }
}
