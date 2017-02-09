package com.example.franck.candycrush_like;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Franck on 07/02/2017.
 */

public class Circle extends Button {

    private int x;
    private int y;
    private int c;
    private int x_t;

    public int getY_t() {
        return y_t;
    }

    public void setY_t(int y_t) {
        this.y_t = y_t;
    }

    public int getX_t() {
        return x_t;
    }

    public void setX_t(int x_t) {
        this.x_t = x_t;
    }

    private int y_t;
    private String color_s;

    public Circle(Context context, AttributeSet attrs){
        super(context,attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Circle,
                0, 0);

        try {

            setC(a.getInteger(R.styleable.Circle_my_color, -1));
            x = a.getInteger(R.styleable.Circle_my_line, GridLayout.UNDEFINED);
            y = a.getInteger(R.styleable.Circle_my_column,GridLayout.UNDEFINED);
            setTarget(this);
        } finally {
            a.recycle();
        }

    }

    public void setC(int color){
        c= color;
        switch (c) {
            case 0:
                setBackgroundResource(R.drawable.red_circle);
                color_s = "red";
                break;
            case 1:
                setBackgroundResource(R.drawable.blue_circle);
                color_s = "blue";
                break;
            case 2:
                setBackgroundResource(R.drawable.green_circle);
                color_s = "green";
                break;
            case 3:
                setBackgroundResource(R.drawable.orange_circle);
                color_s = "orange";
                break;
            case 4:
                setBackgroundResource(R.drawable.yellow_circle);
                color_s = "yellow";
                break;
            case 5:
                setBackgroundResource(R.drawable.purple_circle);
                color_s = "purple";
                break;
            default:
                setBackgroundResource(R.drawable.round_button);
                color_s = "black";

        }

        //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(10000, 10000);
        //setLayoutParams(layoutParams);
        /*setWidth(10);
        setHeight(10);
        setTextSize(10);*/
    }

    public int getC(){
        return c;
    }


    public String getCoordinates(){
        return "("+x+","+y+")";
    }

    public String getTargetCoordinates(){
        return "("+x_t+","+y_t+")";
    }


    public String getColor_s(){
        return color_s;
    }

    public int getPlacementX(){
        return x;
    }

    public int getPlacementY(){
        return y;
    }

    public void setLayoutPlacement(){
        GridLayout.Spec row = GridLayout.spec(x);
        GridLayout.Spec col = GridLayout.spec(y);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(row, col);
        setLayoutParams(params);
        setVisibility(VISIBLE);
    }

    public void setTarget(Circle other){
        setX_t(other.getPlacementX());
        setY_t(other.getPlacementY());
    }

    public void setPlacementX(int xi){
        x =xi;
    }

    public void setPlacementY(int yi){
        y = yi;
    }
}
