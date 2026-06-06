package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private SwitchMaterial switchDarkMode;
    private SeekBar sbFontSize;
    private TextView tvFontSizeLabel;
    private TextView tvFontPreview;
    private MaterialCardView cardRate;
    private MaterialCardView cardShare;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchDarkMode = view.findViewById(R.id.switch_dark_mode);
        sbFontSize = view.findViewById(R.id.sb_font_size);
        tvFontSizeLabel = view.findViewById(R.id.tv_font_size_label);
        tvFontPreview = view.findViewById(R.id.tv_font_preview);
        cardRate = view.findViewById(R.id.card_rate_app);
        cardShare = view.findViewById(R.id.card_share_app);

        prefs = getContext().getSharedPreferences("StyleMakerPrefs", Context.MODE_PRIVATE);

        // 1. Dark Mode setup
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        switchDarkMode.setChecked(isDarkMode);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("dark_mode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // 2. Font Size setup
        int initialLevel = prefs.getInt("font_size_level", 1); // 0=Small, 1=Medium, 2=Large
        sbFontSize.setProgress(initialLevel);
        updateFontSizeDisplay(initialLevel);

        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prefs.edit().putInt("font_size_level", progress).apply();
                updateFontSizeDisplay(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 3. Rate App click action
        cardRate.setOnClickListener(v -> {
            Toast.makeText(getContext(), getString(R.string.msg_rate_click), Toast.LENGTH_SHORT).show();
        });

        // 4. Share App click action
        cardShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            intent.putExtra(Intent.EXTRA_TEXT, "Hey! Design awesome gaming, clan, and bios styled names offline using " + getString(R.string.app_name) + " app!");
            startActivity(Intent.createChooser(intent, "Share " + getString(R.string.app_name)));
        });

        return view;
    }

    private void updateFontSizeDisplay(int level) {
        if (level == 0) {
            tvFontSizeLabel.setText("Size: Small");
            tvFontPreview.setTextSize(14f);
        } else if (level == 2) {
            tvFontSizeLabel.setText("Size: Large");
            tvFontPreview.setTextSize(24f);
        } else {
            tvFontSizeLabel.setText("Size: Medium");
            tvFontPreview.setTextSize(18f);
        }
    }
}
