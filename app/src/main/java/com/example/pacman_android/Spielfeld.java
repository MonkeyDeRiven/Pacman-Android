package com.example.pacman_android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.widget.ImageView;

import com.example.myfirstapp.R;

public class Spielfeld {
    Bitmap hintergrund;
    block[][] spielfeldarray;
    int screenwidth = 0;
    int screenheight = 0;
    int anzahlBloeckeBreite = 25;
    int anzahlBloeckeHoehe = 20;
    int breiteBlock = 0;
    int hoeheBlock = 0;
    Canvas canvas;
    Paint paint;


    public Spielfeld(Bitmap bm) {
        spielfeldarray = new block[20][15];
        paint = new Paint();
        paint.setColor(Color.BLUE);
        hintergrund = bm;
        screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenheight = Resources.getSystem().getDisplayMetrics().heightPixels;
        fillPlayfield();




       // public void draw (Canvas canvas){
/*
            screenwidth = canvas.getWidth();
            screenheight = canvas.getHeight();
            breiteBlock = screenwidth / 20;
            hoeheBlock = screenheight / 15;

            int left = 0, right = breiteBlock, top = 200, bottom = 200 + hoeheBlock;
            for (int a = 0, b = 0; b < anzahlBloeckeHoehe; a++, left += breiteBlock, right += breiteBlock) {
                Rect rect = new Rect();
                rect.set(left, top, right, bottom);
                canvas.drawRect(rect, paint);
                spielfeldarray[a][b] = rect;
                if (a == anzahlBloeckeBreite - 1) {
                    a = -1;
                    ++b;
                    left = -breiteBlock;
                    right = 0;
                    top += hoeheBlock;
                    bottom += hoeheBlock;
                }*/
            //}
       // }
    }
    public void fillPlayfield ()
    {
        spielfeldarray = new block[25][20];
         breiteBlock = screenwidth / anzahlBloeckeBreite; hoeheBlock = screenheight / anzahlBloeckeHoehe;

        for (int a = 0, b = 0, x1 = 0, x2 = breiteBlock, y1 = 0, y2 = hoeheBlock; b < anzahlBloeckeHoehe; a++, x1 += breiteBlock, x2 += breiteBlock) {
            block value = new block();
            value.x1 = x1;
            value.x2 = x2;
            value.y1 = y1;
            value.y2 = y2;
            value.inhalt = 1;
            spielfeldarray[a][b] = value;

            if (a == anzahlBloeckeBreite- 1) {
                ++b;
                a = -1;
                y1 += hoeheBlock;
                x1 = -breiteBlock;
                x2 = 0;
                y2 += hoeheBlock;
            }
        }
    }
}