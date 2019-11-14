package com.example.votez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main2Activity extends AppCompatActivity {
    String xyz;
    TextView profile_header_userName;
    Button contest_create,contest_vote,edit_profile;
    BottomNavigationView bottomNavigationView;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //Top Banner add
        MobileAds.initialize(this,"ca-app-pub-2490434932109238~1087339864");
        adView=findViewById(R.id.adView);
        //adView.setAdUnitId("ca-app-pub-2490434932109238/6419557593");
        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        //-----********
        bottomNavigationView=findViewById(R.id.bottom_nav);
        profile_header_userName=findViewById(R.id.profile_header_userName);
        contest_create=findViewById(R.id.contest_create);
        contest_vote=findViewById(R.id.contest_vote);
        edit_profile=findViewById(R.id.edit_profile);
        xyz=User.showUserName;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
//        edit_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity( new Intent(Main2Activity.this,Edit_profile.class));
//            }
//        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                        return true;

                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();
                        return true;

                    case R.id.nav_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AccountFragment()).commit();
                        return true;

                }
                return true;
            }
        });

}


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

}
