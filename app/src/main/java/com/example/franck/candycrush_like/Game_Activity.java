package com.example.franck.candycrush_like;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/** Activité responsable de l'UI (similaire à la vue dans MVC) du jeu */
public class Game_Activity extends BaseActivity {

    /**
     * Controleur du jeu (similaire au modele+controleur dans MVC)
     */
    public Level_Controller game_c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    protected void initialize() {
        setContentView(R.layout.activity_game_);
        LinearLayout item = (LinearLayout) findViewById(R.id.fullscreen_content);
        View childV = null;

        // Récupérer le niveau auquel le joueur joue.
        int level = getIntent().getIntExtra("com.example.franck.candycrush_like.level", 1);
        Log.w("Level nb", "" + level);

        initialize_controller(level);
        initialize_grid(level);

        // Ajouter le listener sur le togglebutton
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    game_c.setAdvanced(true);
                    Button passbtn = (Button) findViewById(R.id.PassButton);
                    passbtn.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    game_c.setAdvanced(false);
                    Button passbtn = (Button) findViewById(R.id.PassButton);
                    passbtn.setVisibility(View.GONE);
                }
            }
        });

        // Ajouter le listener sur le bouton
        Button passbtn = (Button) findViewById(R.id.PassButton);
        passbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_c.advanced_click();
            }
        });

        //Ajouter le listener sur le bouton niveau suivant
        Button nextlvl = (Button) findViewById(R.id.NextLevel);
        nextlvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLevel();

            }
        });
    }

    /**
     * Rendre accessible ou non le bouton pour passer. (ie des alignements sont présents)
     */
    public void enableBtn(boolean enabled) {
        Button passbtn = (Button) findViewById(R.id.PassButton);
        passbtn.setEnabled(enabled);
    }

    /**
     * Notre propre DragListener
     * Fait une action lorsque l'on dépose un élément sur un autre
     */
    public class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //do nothing
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //do nothing
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    Circle item_dropped = (Circle) event.getLocalState();
                    if (game_c.correct_move(item_dropped, (Circle) v)) {
                        Toast.makeText(getBaseContext(), "" + game_c.getScore(), Toast.LENGTH_SHORT).show();
                        setScore(game_c.getScore());
                        setCoups(game_c.getCoupsJoues());
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // do nothing
                    break;
                default:
                    break;
            }
            return true;
        }

    }


    /**
     * Afficher le niveau courant dans le TextView
     */
    protected void setLevel(int level) {
        TextView level_t = (TextView) findViewById(R.id.level);
        level_t.setText(Integer.toString(level));
    }

    /**
     * Afficher le score et le nombre de points restants pour débloquer le niveau suivant dans les TextViews
     */
    protected void setScore(int score) {
        TextView score_text = (TextView) findViewById(R.id.score);
        score_text.setText(Integer.toString(score));
        score_text = (TextView) findViewById(R.id.remaining);
        score_text.setText((game_c.level.atteinte >= score ? Integer.toString(game_c.level.atteinte - score) : "0"));
    }

    /**
     * Afficher le nombre de coups restants dans le TextView
     */
    protected void setCoups(int coups) {
        TextView score_text = (TextView) findViewById(R.id.coups);
        score_text.setText(Integer.toString(game_c.level.nb_coups - game_c.getCoupsJoues()) + " / " + game_c.level.nb_coups);
    }

    /**
     * Pouvoir passer au niveau suivant si débloqué
     */
    protected void enableNextLvl(boolean enable) {
        Button nextLvl = (Button) findViewById(R.id.NextLevel);
        if (enable) {
            nextLvl.setVisibility(View.VISIBLE);
            nextLvl.setEnabled(enable);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reinitialize:
                reinit();
                //recreate();
                return true;
            // Respond to the action bar's Up/Home button
            case R.id.joker:
                reinitialize_grid();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /** Recommenencer la partie */
    public void reinit(){
        reinitialize_controller();
        reinitialize_grid();
    }

    /** Reinitialiser le controleur pour le même niveau*/
    public void reinitialize_controller(){
        game_c = new Level_Controller(this,game_c.level);
    }

    /** Reinitialiser la grille pour le même niveau*/
    public void reinitialize_grid(){
        initialize_grid(game_c.level.num);
    }

    /** Initialiser le controleur */
    public void initialize_controller(int level){
        //Afficher le score
        setLevel(level);
        Game_Level level_obj;
        switch (level) {
            case 1:
                level_obj = new Game_Level(level, 8, 5, 800, 6);
                break;
            case 2:
                level_obj = new Game_Level(level, 8, 6, 1200, 10);
                break;
            case 3:
                level_obj = new Game_Level(level, 7, 7, 1400, 10);
                break;
            case 4:
                level_obj = new Game_Level(level, 7, 8, 1800, 10);
                break;
            default:
                level_obj = new Game_Level(1, 8 , 5, 800, 6);
        }
        game_c = new Level_Controller(this , level_obj);
    }

    /** Reinitialiser la grille*/
    public void initialize_grid(int level) {
        LinearLayout item = (LinearLayout) findViewById(R.id.fullscreen_content);
        View childV = null;
        // Mettre la bonne grille en fonction du niveau
        switch (level) {
            case 1:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
                break;
            case 2:
                childV = getLayoutInflater().inflate(R.layout.lvl2, null);
                break;
            case 3:
                childV = getLayoutInflater().inflate(R.layout.lvl3, null);
                break;
            case 4:
                childV = getLayoutInflater().inflate(R.layout.lvl4, null);
                break;
            default:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
        }
        item.removeAllViews();
        item.addView(childV);
        GridLayout mlayout = (GridLayout) findViewById(R.id.grid);
        int count = mlayout.getChildCount();

        // Ajouter les listeners aux éléments de la grilles et les placer initialement dans la grille
        for (int i = 0; i < count; i++) {
            Circle child = (Circle) mlayout.getChildAt(i);
            child.setOnDragListener(new MyDragListener());
            child.setOnTouchListener(new MyTouchListener());
            child.setLayoutPlacement();
            game_c.config[child.getPlacementY()][child.getPlacementX()] = child;
        }
        mlayout.invalidate();
        childV.invalidate();

        //Initialiser le score et les coups joues dans le TextView
        setScore(game_c.getScore());
        setCoups(game_c.getCoupsJoues());
    }


    /**Passer au niveau suivant */
    public void nextLevel(){
        Intent intent = new Intent(this, Game_Activity.class);
        intent.putExtra("com.example.franck.candycrush_like.level", game_c.level.num +1);
        startActivity(intent);
        finish();
    }

}
