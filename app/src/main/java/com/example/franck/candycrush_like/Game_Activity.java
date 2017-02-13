package com.example.franck.candycrush_like;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        Game_Level level_obj ;
        switch (level) {
            case 1:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
                level_obj = new Game_Level(level, 8, 5, 800, 6);
                game_c = new Level_Controller(level_obj);
                break;
            case 2:
                childV = getLayoutInflater().inflate(R.layout.lvl2, null);
                level_obj = new Game_Level(level, 8, 6, 1200, 10);
                game_c = new Level_Controller(level_obj);
                break;
            case 3:
                childV = getLayoutInflater().inflate(R.layout.lvl3, null);
                level_obj = new Game_Level(level, 7, 7, 1400, 10);
                game_c = new Level_Controller(level_obj);
                break;
            case 4:
                childV = getLayoutInflater().inflate(R.layout.lvl4, null);
                level_obj = new Game_Level(level, 7, 8, 1800, 10);
                game_c = new Level_Controller(level_obj);
                break;
            default:
                childV = getLayoutInflater().inflate(R.layout.lvl1, null);
                level_obj = new Game_Level(1, 8 , 5, 800, 6);
                game_c = new Level_Controller(level_obj);
        }

        item.addView(childV);
        //childV.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        GridLayout mlayout = (GridLayout) findViewById(R.id.grid);
        int count = mlayout.getChildCount();
        for(int i = 0 ; i <count ; i++) {
            Circle child = (Circle) mlayout.getChildAt(i);
            child.setOnDragListener(new MyDragListener());
            child.setOnTouchListener(new MyTouchListener());
            child.setLayoutPlacement();
            game_c.config[child.getPlacementY()][child.getPlacementX()] = child;
        }
        mlayout.setLayoutParams(new GridLayout.LayoutParams(

        ));
        mlayout.invalidate();
        childV.invalidate();
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

}
