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


    public ImageView entity;

    private int width;
    private int height;

    public int y;
    public int x;
    int life;

    private int direction;
    public int ateGhosts;
    public int playerScore;

    public Spieler(ImageView entity, int size){

        this.entity = entity;
        this.width = size;
        this.height = size;

        this.x = (int)entity.getX();
        this.y = (int)entity.getY();
        this.direction = -1;

        this.life = 1;

        this.ateGhosts = 5;
        this.playerScore = 501;
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

    public int getDirection(){
        return direction;
    }

    public void setDirection(int newDirection){
        direction = newDirection;
    }

    public void updateCoordinates(){
        x = (int)entity.getX();
        y = (int)entity.getY();
    }
}