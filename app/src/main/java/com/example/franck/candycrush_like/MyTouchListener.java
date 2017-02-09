package com.example.franck.candycrush_like;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by Franck on 06/02/2017.
 */

public class MyTouchListener implements OnTouchListener {

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}
