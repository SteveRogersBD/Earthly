package com.example.earthly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.earthly.R;
import com.example.earthly.adapters.OnBoardAdapter;
import com.example.earthly.databinding.ActivityOnBoardBinding;
import com.example.earthly.models.OnBoardItem;

import java.util.ArrayList;
import java.util.List;

public class OnBoardActivity extends AppCompatActivity {

    OnBoardAdapter adapter;
    List<OnBoardItem>boardItems;
    ActivityOnBoardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String []titles = {getString(R.string.globe),
                getString(R.string.map),
                getString(R.string.reels),
                getString(R.string.scanner),
                getString(R.string.analyzer),
                getString(R.string.chatbot)};
        int []images = {R.drawable.globe,
                R.drawable.map,
                R.drawable.reels,
                R.drawable.barcode,
                R.drawable.analyze,
                R.drawable.bot};

        boardItems = new ArrayList<>();
        for(int i=0;i<6;i++)
        {
            OnBoardItem item = new OnBoardItem(titles[i],images[i]);
            boardItems.add(item);
        }

        adapter = new OnBoardAdapter(this,boardItems);
        binding.pagerOnboard.setAdapter(adapter);
        binding.dots.setViewPager2(binding.pagerOnboard);
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = binding.pagerOnboard.getCurrentItem();
                if (currentItem < binding.pagerOnboard.getAdapter().getItemCount() - 1) {
                    //do something later
                    binding.pagerOnboard.setCurrentItem(currentItem + 1);

                } else {
                    OnBoardActivity.this.startActivity(new Intent(OnBoardActivity.this, StartingActivity.class));
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                }
            }
        });

    }
}