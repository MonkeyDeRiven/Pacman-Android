package com.example.pacman_android;

import android.graphics.Color;
import android.graphics.Rect;
import android.widget.ImageView;

import com.example.myfirstapp.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

public class position {
  Vector<Integer> pfad;
  boolean wand,destination;
  int xArray,yArray,x1,x2,y1,y2;
  boolean visited[][];

  public position()
  {
    x1=0;x2=0;y1=0;y2=0;
    pfad = new Vector<>();
    wand=false;
    destination=false;
    xArray=0;
    yArray=0;
    visited= new boolean[140][140];
   for(int a=0;a<140;a++)
   {
     for(int b=0;b<140;b++)
     {
       visited[a][b] = false;
     }
   }
  }
}
