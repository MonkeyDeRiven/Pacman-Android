package com.example.pacman_android;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Spielfeld {
    Bitmap hintergrund;
    Rect[][] spielfeldarray;
    int screenwidth=0;
    int screenheight=0;
    int anzahlBloeckeBreite=20;
    int anzahlBloeckeHoehe=15;
    int breiteBlock=0;
    int hoeheBlock=0;
    Canvas canvas;
    Paint paint;



  public  Spielfeld(Bitmap bm)
  {
      spielfeldarray = new Rect[20][15];
        paint = new Paint();
        paint.setColor(Color.RED);
        hintergrund = bm;
        screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenheight = Resources.getSystem().getDisplayMetrics().heightPixels;




    }
    public void draw(Canvas canvas) {

        screenwidth = canvas.getWidth();
        screenheight = canvas.getHeight();
        breiteBlock= screenwidth/20;
        hoeheBlock= screenheight/15;

        int left=0,right=breiteBlock,top=0,bottom=hoeheBlock;
        for(int a=0,b=0;b<anzahlBloeckeHoehe;a++,left+=breiteBlock,right+=breiteBlock)
        {Rect rect = new Rect();
        rect.set(left,top,right,bottom);
            canvas.drawRect(rect,paint);
            spielfeldarray[a][b] = rect;
        if(a==anzahlBloeckeBreite-1){a=-1;++b;left=-breiteBlock;right=0;top+=hoeheBlock;bottom+=hoeheBlock;}
        }


    }
}
