package com.medicalcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutUs extends AppCompatActivity {

    ImageView instagram, twitter, github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        instagram = findViewById(R.id.instagram);
        github = findViewById(R.id.github);
        twitter = findViewById(R.id.x);
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl ("https://www.instagram.com/mostly_prima/");
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl("https://twitter.com/mostly_prima");
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl("https://github.com/primaogi");
            }
        });
    }

    private void gotourl(String url) {
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}