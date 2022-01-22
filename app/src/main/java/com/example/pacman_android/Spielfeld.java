package com.example.pacman_android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Spielfeld {
    Bitmap hintergrund;
    position[][] spielfeldarray;
    int screenwidth = 0;
    int screenheight = 0;
    int anzahlBloeckeBreite = 25;
    int anzahlBloeckeHoehe = 20;
    int breiteBlock = 0;
    int hoeheBlock = 0;
    Canvas canvas;
    Paint paint;


    public Spielfeld(Bitmap bm) {
        spielfeldarray = new position[20][15];
        paint = new Paint();
        paint.setColor(Color.BLUE);
        hintergrund = bm;
        screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenheight = Resources.getSystem().getDisplayMetrics().heightPixels;}}





