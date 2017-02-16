package com.example.franck.candycrush_like;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.TextView;

/** Element à aligner  */
public class Circle extends TextView {

    /** Ligne de l'élément */
    private int x;

    /**Colonne de l'élément*/
    private int y;

    /**Couleur de l'élément (int)*/
    private int c;


    /**Future ligne envisagée de l'élément*/
    private int x_t;


    /** Accesseur Future colonne envisagée de l'élément*/
    public int getY_t() {
        return y_t;
    }

    /** Modifieur Future colonne envisagée de l'élément*/
    public void setY_t(int y_t) {
        this.y_t = y_t;
    }


    /** Accesseur Future ligne envisagée de l'élément */
    public int getX_t() {
        return x_t;
    }


    /** Modifieur Future ligne envisagée de l'élément */
    public void setX_t(int x_t) {
        this.x_t = x_t;
    }

    /**Future colonne envisagée de l'élément*/
    private int y_t;

    /**Couleur de l'élément (String)*/
    private String color_s;


    /** Constructeur */
    public Circle(Context context, AttributeSet attrs){
        super(context,attrs);

        //Récupérer la couleur, la ligne et la colonne dans le fichier xml puis les assigner
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


    /** Modifier la couleur de l'élément (couleur visible à l'écran)*/
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
        transparent(false);
        setText("");
    }


    /** Accesseur de la couleur (int)*/
    public int getC(){
        return c;
    }


    /** Renvoie la position (couple) de l'élément (String)*/
    public String getCoordinates(){
        return "("+x+","+y+")";
    }


    /** Renvoie la future position envisagée (couple) de l'élément (String)*/
    public String getTargetCoordinates(){
        return "("+x_t+","+y_t+")";
    }


    /** Accesseur de la couleur (String)*/
    public String getColor_s(){
        return color_s;
    }

    /** Accesseur de la ligne de l'élément*/
    public int getPlacementX(){
        return x;
    }

    /** Accesseur de la colonne de l'élément*/
    public int getPlacementY(){
        return y;
    }


    /** Positionner l'élément à sa bonne place dans la grille (GridLayout)
     * Sans appel à cette fonction le déplacement de l'élément ne se verra pas.*/
    public void setLayoutPlacement(){
        GridLayout.Spec row = GridLayout.spec(x);
        GridLayout.Spec col = GridLayout.spec(y);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(row, col);
        setLayoutParams(params);
        setVisibility(VISIBLE);
    }

    /**Modifier la future position possible de l'élément par celle d'un autre élément */
    public void setTarget(Circle other){
        setX_t(other.getPlacementX());
        setY_t(other.getPlacementY());
    }


    /**Modifier la ligne de l'élément*/
    public void setPlacementX(int xi){
        x =xi;
    }

    /**Modifier la colonne de l'élément*/
    public void setPlacementY(int yi){
        y = yi;
    }

    /**Mettre la couleur en transparence*/
    public void transparent(boolean trans){
        if(trans)
            getBackground().setAlpha(120);
        else
            getBackground().setAlpha(255);
    }
}
