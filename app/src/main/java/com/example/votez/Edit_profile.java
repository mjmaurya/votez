package com.example.votez;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class Edit_profile extends AppCompatActivity {
    TextView user_name,user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_name.setText(User.showUserName);
        user_email.setText(User.showUserEmail);
    }
}
