package com.example.franck.candycrush_like;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by Franck on 06/02/2017.
 */

public  class Level_Controller {
    static public Random rand = new Random();
    static public int nb_colors = 6;
    public Game_Level level;
    public int nbcoupesjoues;
    static public int greater_Level;
    public int score;
    public Circle [][] config;
    public ArrayList<Circle> circles_to_remove  = new ArrayList<>();
    public ArrayList <Circle> moved_circles = new ArrayList<>() ;

    public boolean correct_move(Circle one, Circle two){
        boolean result = false;
        if ( checksidetoside(one,two)){
            one.setTarget(two);
            two.setTarget(one);
            execute_move(one);
            execute_move(two);
            Log.w("Move", "new Move" );
            if(check3aligned(one )|| check3aligned(two)) {
                moved_circles.add(one);
                moved_circles.add(two);
                result = exchange(one,two);
                oneMove();
            }
        }else {
            cancel_move(one);
            cancel_move(two);
        }
        //one.setLayoutPlacement();
        //two.setLayoutPlacement();
        return result;
    }

    public static boolean checksidetoside(Circle one,Circle two){
        return ( abs(one.getPlacementX()-two.getPlacementX()) + abs(one.getPlacementY()-two.getPlacementY()) <2 );
    }


    public boolean cancel_move(Circle one){
        config[one.getPlacementY()][one.getPlacementX()] = one;
        one.setTarget(one);
        return false;
    }
    public boolean check3aligned(Circle one){
        return check3alignedH(one) || check3alignedV(one);
    }

    public static boolean exchange(Circle one,Circle two){
        int tempo = one.getPlacementY();
        one.setPlacementY(two.getPlacementY());
        two.setPlacementY(tempo);
        tempo = one.getPlacementX();
        one.setPlacementX(two.getPlacementX());
        two.setPlacementX(tempo);
        one.setLayoutPlacement();
        two.setLayoutPlacement();
        return true;
    }



    public  boolean check3alignedV (Circle one){
        int row = one.getX_t();
        int column = one.getY_t();
        boolean aligned = false;
        int count = 0;
        for(int i = max(row-2,0); i <= min(row+2,level.nb_lines-1) ; i++){
            print_move(config[column][i]);
            if(one.getC()== config[column][i].getC() ){
               count++;
            }
        }
        for(int i = max(row-2,0); i <= min(row,(level.nb_lines-1)-2) ; i++){
            if(one.getC()== config[column][i].getC() &&  one.getC()== config[column][i+1].getC() && one.getC()== config[column][i+2].getC() ){
                aligned =true;
            }
        }
        Log.w("Salut Verticale ","aligned "+ aligned);
        /*if(count >=3)
            circles_to_remove.add(one);
        return count >=3;*/
        return aligned;
    }

    public boolean check3alignedH (Circle one){
        int row = one.getX_t();
        int column = one.getY_t();

        boolean aligned = false;

        int count = 0;
        for(int i = max(column-2,0);  i <= min (column+2,level.nb_columns-1) ; i++){
            print_move(config[i][row]);
            if(one.getC() == config[i][row].getC()){
                count++;
            }
        }
        for(int i = max(column-2,0);  i <= min (column,(level.nb_columns-1) -2) ; i++){
            if(one.getC() == config[i][row].getC() && one.getC() == config[i+1][row].getC() && one.getC() == config[i+2][row].getC()){
                aligned = true;
            }
        }
        Log.w("Salut Horizontal ","aligned "+ aligned);
        /*if(count >=3)
            circles_to_remove.add(one);
        return count >= 3;*/
        return aligned;
    }

    public boolean execute_move(Circle one){
        config[one.getY_t()][one.getX_t()] = one;
        return true;
    }

    public static void print_move( Circle one    ){
        Log.w("indice ", one.getCoordinates()+" -> " + one.getTargetCoordinates() + "    "+ one.getColor_s() );
    }

    public boolean column_fall(Circle c){
       // Circle c = config[column][index];
        int index = c.getPlacementX();
        int column = c.getPlacementY();
        Log.w("yo", "col : "+column+"   row : "+index);
        for (int i = index; i > 0 ;i--){
            config[column][i] = config[column][i-1];
            config[column][i].setPlacementX(i);
            config[column][i].setTarget(config[column][i]);
            config[column][i].setLayoutPlacement();
            moved_circles.add(config[column][i]);
            print_move(config[column][i]);
        }
        config[column][0] = c ;
        c.setPlacementX(0);
        c.setC(random());
        moved_circles.add(c);
        Log.w("color", c.getColor_s());
        c.setLayoutPlacement();
        return false;
    }

    public  boolean oneMove(){
        int multiplicateur = 1;
        int score_tempo ;
        do{
            score_tempo = refresh_grid() * multiplicateur;
            multiplicateur ++;
            addScore(score_tempo);
        }
        while (score_tempo>0);
        nbcoupesjoues++;
        return true;
    }

    public int  refresh_grid(){
        circles_to_remove.clear();
        int temp_score  = 0;
        for(Circle one : moved_circles) {
            int row = one.getX_t();
            int column = one.getY_t();
            //boolean horizontal = false;
            for (int i = max(column - 2, 0); i <= min(column, (level.nb_columns - 1) - 2); i++) {
                if (one.getC() == config[i][row].getC() && one.getC() == config[i + 1][row].getC() && one.getC() == config[i + 2][row].getC()) {
                    print_move(config[i][row]);
                    boolean aj_score[] = {addToRemove(config[i][row]), addToRemove(config[i + 1][row]),   addToRemove(config[i + 2][row])};
                    if(aj_score[0] || aj_score[1]|| aj_score[2])
                        temp_score +=100;
                }

            }
            /*if (horizontal) {
                addToRemove(config[circles_to_remove.get(circles_to_remove.size() - 1).getPlacementY() + 1][circles_to_remove.get(circles_to_remove.size() - 1).getPlacementX()]);
                addToRemove(config[circles_to_remove.get(circles_to_remove.size() - 2).getPlacementY() + 2][circles_to_remove.get(circles_to_remove.size() - 2).getPlacementX()]);
            }*/
            //boolean vertical = false;
            for (int i = max(row - 2, 0); i <= min(row, (level.nb_lines - 1) - 2); i++) {
                if (one.getC() == config[column][i].getC() && one.getC() == config[column][i + 1].getC() && one.getC() == config[column][i + 2].getC()) {
                    print_move(config[column][i]);
                    boolean aj_score[] = {addToRemove(config[column][i]), addToRemove(config[column][i+1]),   addToRemove(config[column][i+2])};
                    if(aj_score[0] || aj_score[1]|| aj_score[2])
                        temp_score +=100;
                }
            }
            /*if (vertical) {
                addToRemove(config[circles_to_remove.get(circles_to_remove.size() - 1).getPlacementY()][circles_to_remove.get(circles_to_remove.size() - 1).getPlacementX() + 1]);
                addToRemove(config[circles_to_remove.get(circles_to_remove.size() - 2).getPlacementY()][circles_to_remove.get(circles_to_remove.size() - 2).getPlacementX() + 2]);
            }*/
        }
        Log.w("Count", circles_to_remove.size()+" to remove");
        moved_circles.clear();
        removeCircles();

        //moved_circles.addAll(circles_to_remove);
        return (temp_score);
    }

    public  void removeCircles(){
        for (Circle c : circles_to_remove) {
            column_fall(c);
        }
    }

    public  boolean addToRemove(Circle c){
        if(!circles_to_remove.contains(c)) {
            circles_to_remove.add(c);
            return true;
        }else
            return false;
    }

    public static int  random(){
        return rand.nextInt(nb_colors);
    }

    public  Circle right_to(Circle a){
        if (a.getPlacementY() + 1 < level.nb_columns)
            return config[a.getPlacementY()+1][a.getPlacementX() ];
        else
            return null;
    }

    public  Circle left_to(Circle a){
        if (a.getPlacementY() - 1 > 0 )
            return config[a.getPlacementY()-1][a.getPlacementX() + 1];
        else
            return null;
    }

    public Circle above(Circle a){
        if (a.getPlacementX() + 1 < level.nb_lines)
            return config[a.getPlacementY()][a.getPlacementX() + 1];
        else
            return null;
    }

    public  Circle below(Circle a){
        if (a.getPlacementX() - 1 < 0)
            return config[a.getPlacementY()][a.getPlacementX() - 1];
        else
            return null;
    }

    public Level_Controller( Game_Level l){
        level = l;
        score = 0;
        nbcoupesjoues = 0;
        config = new Circle[l.nb_columns][l.nb_lines];
    }

    public void addScore(int ajout){
        score += ajout;
        if(level.atteinte<score )
            greater_Level = max (level.num, greater_Level);
    }

    public int getScore(){
        return score;
    }

}
