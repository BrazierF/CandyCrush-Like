package com.example.franck.candycrush_like;

/**
 * Created by Franck on 07/02/2017.
 */

public class Game_Level{
    public int num = 1;
    public int nb_lines = 5;
    public int nb_columns = 8;
    public int atteinte = 1200;
    public int nb_coups = 10;

    public Game_Level (int level, int nb_col, int nb_row, int score, int nb_cps){
        num = level;
        nb_lines = nb_row;
        nb_columns = nb_col;
        atteinte = score;
        nb_coups = nb_cps;
    }
}