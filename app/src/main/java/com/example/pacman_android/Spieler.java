package com.example.pacman_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.Image;
import android.widget.ImageView;

import com.example.myfirstapp.R;

public class Spieler {
    private ImageView entity;

    private int width;
    private int height;

    public int y;
    public int x;

    public Spieler(ImageView entity, int size){
        this.entity = entity;
        this.width = size;
        this.height = size;

        this.x = (int)entity.getX();
        this.y = (int)entity.getY();
    }

    public ImageView getEntity(){
        return entity;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}