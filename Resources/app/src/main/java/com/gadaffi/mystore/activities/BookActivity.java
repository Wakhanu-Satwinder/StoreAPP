package com.gadaffi.mystore.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gadaffi.mystore.R;

public class    BookActivity extends AppCompatActivity {

    //private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        //pref = getSharedPreferences("childcare",MODE_PRIVATE);
        initFragment();
    }

    private void initFragment() {
        Fragment fragment;

            fragment = new RoomsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }

    }
