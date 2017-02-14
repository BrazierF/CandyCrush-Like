package com.example.franck.candycrush_like;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Game_Activity extends AppCompatActivity {

    public Level_Controller game_c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();

    }

    protected void initialize(){
        setContentView(R.layout.activity_game_);
        LinearLayout item = (LinearLayout)findViewById(R.id.fullscreen_content);
        View childV = null;
        int level = getIntent().getIntExtra("com.example.franck.candycrush_like.level",1);
        Log.w("Level nb",""+level);
        setLevel(level);
        Game_Level level_obj ;
        switch (level) {
            case 1:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
                level_obj = new Game_Level(level, 8, 5, 800, 6);
                break;
            case 2:
                childV = getLayoutInflater().inflate(R.layout.lvl2, null);
                level_obj = new Game_Level(level, 8, 6, 1200, 10);
                break;
            case 3:
                childV = getLayoutInflater().inflate(R.layout.lvl3, null);
                level_obj = new Game_Level(level, 7, 7, 1400, 10);

                break;
            case 4:
                childV = getLayoutInflater().inflate(R.layout.lvl4, null);
                level_obj = new Game_Level(level, 7, 8, 1800, 10);
                break;
            default:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
                level_obj = new Game_Level(1, 8 , 5, 800, 6);
        }
        game_c = new Level_Controller(this , level_obj);
        item.addView(childV);
        GridLayout mlayout = (GridLayout) findViewById(R.id.grid);
        int count = mlayout.getChildCount();
        for(int i = 0 ; i <count ; i++) {
            Circle child = (Circle) mlayout.getChildAt(i);
            child.setOnDragListener(new MyDragListener());
            child.setOnTouchListener(new MyTouchListener());
            child.setLayoutPlacement();
            game_c.config[child.getPlacementY()][child.getPlacementX()] = child;
        }
        mlayout.invalidate();
        childV.invalidate();
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
        Button passbtn = (Button) findViewById(R.id.PassButton);
        passbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game_c.advanced_click();
            }
        });
        setScore(game_c.getScore());
        setCoups(game_c.getCoupsJoues());
    }

    public void enableBtn(boolean enabled){
        Button passbtn = (Button) findViewById(R.id.PassButton);
        passbtn.setEnabled(enabled);
    }

    public class MyDragListener implements View.OnDragListener {

        /*Drawable enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);*/

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    Circle item_dropped = (Circle) event.getLocalState();
                    if(game_c.correct_move(item_dropped,(Circle)v)){
                        Toast.makeText(getBaseContext(),""+game_c.getScore(),Toast.LENGTH_SHORT).show();
                        setScore(game_c.getScore());
                        setCoups(game_c.getCoupsJoues());
                    }

                    /*View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                default:
                    break;
            }
            return true;
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

    protected void setLevel(int level){
        TextView level_t = (TextView) findViewById(R.id.level);
        level_t.setText(Integer.toString(level));
    }

    protected void setScore (int score){
        TextView score_text = (TextView) findViewById(R.id.score);
        score_text.setText(Integer.toString(score));
        score_text = (TextView) findViewById(R.id.remaining);
        score_text.setText((game_c.level.atteinte >= score ? Integer.toString(game_c.level.atteinte-score) : "0" ));
       }

    protected void setCoups (int coups){
        TextView score_text = (TextView) findViewById(R.id.coups);
        score_text.setText(Integer.toString(game_c.level.nb_coups-game_c.getCoupsJoues())+" / " +game_c.level.nb_coups);
    }

}
