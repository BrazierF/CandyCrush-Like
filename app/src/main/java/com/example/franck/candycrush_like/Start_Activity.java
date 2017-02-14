package com.example.franck.candycrush_like;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button lvl1 = (Button) findViewById(R.id.playlvl1);
        lvl1. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(1);
            }
        });
        Button lvl2 = (Button) findViewById(R.id.playlvl2);
        lvl2. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(2);
            }
        });
        Button lvl3 = (Button) findViewById(R.id.playlvl3);
        lvl3. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(3);
            }
        });
        Button lvl4 = (Button) findViewById(R.id.playlvl4);
        lvl4. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(4);
            }
        });

    }

    private void start(int i) {
        if(i <= Level_Controller.greater_Level + 1) {
            Intent intent = new Intent(this, Game_Activity.class);
            intent.putExtra("com.example.franck.candycrush_like.level", i);
            startActivity(intent);
        }else{
            Toast.makeText(this,R.string.blocked_level,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
