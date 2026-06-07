package com.example;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DeveloperFragment extends Fragment {

    private static final String AVATAR_URL = "https://i.ibb.co/CpTsxVRz/image.png";
    private static final String TELEGRAM_URL = "https://t.me/shuvo_bhai11";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer, container, false);

        // Load profile picture with Glide
        ImageView imgAvatar = view.findViewById(R.id.img_dev_avatar);
        Glide.with(this)
            .load(AVATAR_URL)
            .apply(new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.ic_developer)
                .error(R.drawable.ic_developer))
            .into(imgAvatar);

        // Telegram icon click -> open channel
        ImageView imgTelegram = view.findViewById(R.id.img_telegram_icon);
        imgTelegram.setOnClickListener(v -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_URL)));
        });

        return view;
    }
}
