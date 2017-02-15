package com.example.franck.candycrush_like;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


/** Activité principale*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Ajouter les listeners pour chaque bouton
        Button start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game();
            }
        });

        Button rules = (Button) findViewById(R.id.rules_button);
        rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_rules();
            }
        });

        Button exit= (Button) findViewById(R.id.exit_button);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void start_game(){
        startActivity(new Intent(this,Start_Activity.class));
    }

    protected void start_rules(){
        startActivity(new Intent(this,Rules_Activity.class));
    }

}
