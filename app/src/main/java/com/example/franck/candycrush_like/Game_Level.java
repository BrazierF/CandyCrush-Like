package com.example.franck.candycrush_like;

/** Niveau de jeu */

public class Game_Level{
    /** Le niveau  */
    public int num = 1;

    /** Le nombre de lignes présents dans le niveau */
    public int nb_lines = 5;
    /** Le nombre de colonnes présents dans le niveau */
    public int nb_columns = 8;
    /** Le score à atteindre pour réussir le niveau */
    public int atteinte = 1200;
    /** Le nombre de coups pour atteindre le score*/
    public int nb_coups = 10;

    /** Constructeur */
    public Game_Level (int level, int nb_col, int nb_row, int score, int nb_cps){
        num = level;
        nb_lines = nb_row;
        nb_columns = nb_col;
        atteinte = score;
        nb_coups = nb_cps;
    }
}