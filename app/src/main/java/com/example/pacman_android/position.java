package com.example.pacman_android;

import android.graphics.Color;
import android.graphics.Rect;
import android.widget.ImageView;

import com.example.myfirstapp.R;

import java.util.Vector;

public class position {
  Vector<Integer> pfad;
  boolean rechts,links,unten,oben,wand,destination;
  int xArray,yArray;

  public position()
  {
    pfad = new Vector<>();
    rechts=true;
    links=true;
    unten=true;
    oben=true;
    wand=false;
    destination=false;
    x=0;
    y=0;
  }
}
