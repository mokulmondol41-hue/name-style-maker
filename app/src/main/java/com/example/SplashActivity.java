package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.card.MaterialCardView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("StyleMakerPrefs", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MaterialCardView logoCard = findViewById(R.id.splash_card);
        if (logoCard != null) {
            ScaleAnimation scaleAnim = new ScaleAnimation(
                0.3f, 1.0f, 0.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
            );
            scaleAnim.setDuration(1200);
            scaleAnim.setFillAfter(true);
            logoCard.startAnimation(scaleAnim);
        }

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean hasShownPopup = prefs.getBoolean("join_popup_shown", false);
            if (!hasShownPopup) {
                showJoinPopup(prefs);
            } else {
                goToMain();
            }
        }, 1500);
    }

    private void showJoinPopup(SharedPreferences prefs) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        android.view.LayoutInflater inflater = getLayoutInflater();
        android.view.View popupView = inflater.inflate(R.layout.dialog_join_telegram, null);
        builder.setView(popupView);
        builder.setCancelable(false);

        android.app.AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        android.widget.Button btnJoin = popupView.findViewById(R.id.btn_join_telegram);
        android.widget.Button btnSkip = popupView.findViewById(R.id.btn_skip_popup);

        btnJoin.setOnClickListener(v -> {
            prefs.edit().putBoolean("join_popup_shown", true).apply();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/shuvo_bhai11"));
            startActivity(intent);
            dialog.dismiss();
            goToMain();
        });

        btnSkip.setOnClickListener(v -> {
            prefs.edit().putBoolean("join_popup_shown", true).apply();
            dialog.dismiss();
            goToMain();
        });

        dialog.show();
    }

    private void goToMain() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}
