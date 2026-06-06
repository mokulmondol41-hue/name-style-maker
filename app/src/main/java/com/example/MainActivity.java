package com.example;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = getString(R.string.app_name);

            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                title = getString(R.string.app_name);
            } else if (itemId == R.id.nav_favorites) {
                selectedFragment = new FavoritesFragment();
                title = getString(R.string.title_favorites);
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
                title = getString(R.string.title_settings);
            } else if (itemId == R.id.nav_developer) {
                selectedFragment = new DeveloperFragment();
                title = getString(R.string.setting_developer);
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                if (toolbar != null) {
                    toolbar.setTitle(title);
                }
                return true;
            }
            return false;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            if (toolbar != null) {
                toolbar.setTitle(getString(R.string.app_name));
            }
        }
    }
}
