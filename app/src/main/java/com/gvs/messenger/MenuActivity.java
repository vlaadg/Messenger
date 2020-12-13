package com.gvs.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MenuActivity extends AppCompatActivity {

    private Button chatButton;
    private Button usersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        chatButton = findViewById(R.id.chat_btn1);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this , ChatActivity.class);
                startActivity(intent);
            }
        });

        usersButton = findViewById(R.id.users_btn1);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MenuActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
    }




}