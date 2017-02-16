package com.example.franck.candycrush_like;

import android.os.Handler;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;


/** Contrôleur du jeu */
public  class Level_Controller {
    /** une instance de Random pour la génération de nouveaux éléments en haut de la grille */
    static public Random rand = new Random();

    /** Nombre de couleurs (commune à toutes)  dans la grille */
    static public int nb_colors = 6;

    /** Niveau du jeu */
    public Game_Level level;

    /** Nombre de coups joués */
    public int nbcoupesjoues = 0;

    /** Meilleur niveau (int) débloqué */
    static public int greater_Level;

    /** Etat du togglebutton  */
    public boolean advanced =false ;

    /** Score obtenu */
    public int score;

    /** Grille des éléments */
    public Circle [][] config;

    /** Elements alignés à enlever */
    public ArrayList<Circle> circles_to_remove  = new ArrayList<>();

    /** Elements qui ont changé de ma place */
    public ArrayList <Circle> moved_circles = new ArrayList<>() ;

    /** Multiplicateur de score */
    public int multiplicateur = 1;

    /** Activité lancant le jeu */
    public Game_Activity  activity = null;

    /** Echanger deux éléments */
    public boolean correct_move(final Circle one, Circle two){
        boolean result = false;
        // Verifier si c'est deux éléments sont bien adjacents
        if ( checksidetoside(one,two)){
            //Mettre comme cible à un élément l'autre élément
            one.setTarget(two);
            two.setTarget(one);
            //Changer la grille en fonction de ces déplacements
            execute_move(one);
            execute_move(two);
            Log.w("Move", "new Move" );

            //Verifier si ce déplacement crée des alignements
            if(check3aligned(one )|| check3aligned(two)) {
                // Ajouter les éléments dans liste des éléments déplacés
                moved_circles.add(one);
                moved_circles.add(two);
                // Echanger effectivement les éléments (i.e. visible à l'écran)
                result = exchange(one,two);
                if(advanced)
                    activity.enableBtn(true);
                else
                    oneMove();
            }
        }else {
            //Annuler le changement de la grille
            cancel_move(one);
            cancel_move(two);
        }
        //one.setLayoutPlacement();
        //two.setLayoutPlacement();
        return result;
    }


    /**  Verifier si c'est deux éléments sont bien adjacents */
    public static boolean checksidetoside(Circle one,Circle two){
        return ( abs(one.getPlacementX()-two.getPlacementX()) + abs(one.getPlacementY()-two.getPlacementY()) <2 );
    }


    /** Annuler le déplacement */
    public boolean cancel_move(Circle one){
        config[one.getPlacementY()][one.getPlacementX()] = one;
        // Remettre comme cible l'élément
        one.setTarget(one);
        return false;
    }

    /**  Verifier si l'élément fait partie d'un alignement */
    public boolean check3aligned(Circle one){
        return check3alignedH(one) || check3alignedV(one);
    }


    /** Echanger deux éléments et l'afficher*/
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


    /**  Verifier si l'élément fait partie d'un alignement vertical */
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

    /**  Verifier si l'élément fait partie d'un alignement horizontal */
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

    /** Executer le deplacement dans config*/
    public boolean execute_move(Circle one){
        config[one.getY_t()][one.getX_t()] = one;
        return true;
    }

    public static void print_move( Circle one    ){
        Log.w("indice ", one.getCoordinates()+" -> " + one.getTargetCoordinates() + "    "+ one.getColor_s() );
    }

    /** Faire tomber les éléments de la colomne au dessus de l'élément*/
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
            // Ajouter ces éléments dans la liste des éléments déplacés
            moved_circles.add(config[column][i]);
            print_move(config[column][i]);
        }
        config[column][0] = c ;
        c.setPlacementX(0);
        // Random couleur pour l'élément en haut de la colomne
        c.setC(random());
        // Ajouter ces éléments dans la liste des éléments déplacés
        moved_circles.add(c);
        Log.w("color", c.getColor_s());
        c.setLayoutPlacement();
        return false;
    }


    public int nextStep(){
        int score_tempo = refresh_grid() * multiplicateur;
        addScore(score_tempo);
        multiplicateur++;
        return  score_tempo;
    }

    public void advanced_click(){
        if(circles_to_remove.size()>0)
            removeCircles();
        int score_tempo = refresh_grid() * multiplicateur;
        addScore(score_tempo);
        if(score_tempo == 0){
            nbcoupesjoues++;
            activity.setCoups(nbcoupesjoues);
            activity.enableBtn(false);
            multiplicateur = 1;
        }else{

        }
    }

    /** Boucle tant qu'il y a des alignements  */
    public  boolean oneMove(){
        multiplicateur = 1;
        int score_tempo ;
        do{
            score_tempo = nextStep();
            removeCircles();

        }
        while (score_tempo>0);
        nbcoupesjoues++;
        activity.setCoups(nbcoupesjoues);
        return true;
    }

    /** Calculer les alignements et leur score */
    public int  refresh_grid(){
        circles_to_remove.clear();
        int temp_score  = 0;
        for(Circle one : moved_circles) {
            int row = one.getX_t();
            int column = one.getY_t();
            for (int i = max(column - 2, 0); i <= min(column, (level.nb_columns - 1) - 2); i++) {
                if (one.getC() == config[i][row].getC() && one.getC() == config[i + 1][row].getC() && one.getC() == config[i + 2][row].getC()) {
                    print_move(config[i][row]);
                    boolean aj_score[] = {addToRemove(config[i][row]), addToRemove(config[i + 1][row]),   addToRemove(config[i + 2][row])};
                    if(aj_score[0] || aj_score[1]|| aj_score[2])
                        temp_score +=100;
                }

            }
            for (int i = max(row - 2, 0); i <= min(row, (level.nb_lines - 1) - 2); i++) {
                if (one.getC() == config[column][i].getC() && one.getC() == config[column][i + 1].getC() && one.getC() == config[column][i + 2].getC()) {
                    print_move(config[column][i]);
                    boolean aj_score[] = {addToRemove(config[column][i]), addToRemove(config[column][i+1]),   addToRemove(config[column][i+2])};
                    if(aj_score[0] || aj_score[1]|| aj_score[2])
                        temp_score +=100;
                }
            }
        }
        Log.w("Count", circles_to_remove.size()+" to remove");
        moved_circles.clear();

        blackCircles();

        //moved_circles.addAll(circles_to_remove);
        return (temp_score);
    }

    public  void blackCircles(){
        for (Circle c : circles_to_remove) {
            c.transparent(true);
        }
    }

    /** Enlever les éléments alignéq*/
    public  void removeCircles(){
        for (Circle c : circles_to_remove) {
            column_fall(c);
        }
    }

    /** Ajouter un élément dans la liste à enlever */
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

    /** Constructeur */
    public Level_Controller( Game_Activity act , Game_Level l){
        activity = act;
        level = l;
        score = 0;
        nbcoupesjoues = 0;
        config = new Circle[l.nb_columns][l.nb_lines];
    }


    /** Ajouter le score d'un mouvement */
    public void addScore(int ajout){
        score += ajout;
        // Verifie si on dépasse l'objectif
        if(level.atteinte<=score && level.nb_coups >= nbcoupesjoues )
            greater_Level = max (level.num, greater_Level);
        if(greater_Level >= level.num)
            activity.enableNextLvl(true);
        //Afficher le score
        activity.setScore(score);
    }

    /** Accesseur score de la partie */
    public int getScore(){
        return score;
    }

    /** Accesseur nombre de coups joues dans la partie */
    public int getCoupsJoues(){
        return nbcoupesjoues;
    }

    public void setAdvanced( boolean adv){
        advanced = adv;
    }
}
