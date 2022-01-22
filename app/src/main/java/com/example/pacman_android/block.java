package com.example.pacman_android;

import android.graphics.Rect;
import android.media.Image;
import android.widget.ImageView;

public class block {
    private boolean isWall;

    private int height;
    private int width;

    private int x;
    private int y;

    private ImageView image;
    private Rect collisionArea = new Rect();

    public block(boolean isWall, int height, int width, int x, int y, ImageView image, Rect collisionArea){
        this.isWall = isWall;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.image = image;
        this.collisionArea = collisionArea;
    }

    public boolean getIsWall(){
        return isWall;
    }

    public Rect getCollisionArea(){
        return collisionArea;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public boolean intersects(ImageView player){
        if(player.getX() + player.getWidth() > x && player.getX() + player.getWidth() < x + width){
            if(player.getY() + player.getHeight() > y && player.getY() + player.getHeight() < y + height){
                return true;
            }
            if(player.getY() < y + height && player.getY() > y){
                return true;
            }
        }
        if(player.getX() < x + width && player.getX() > x){
            if(player.getY() + player.getHeight() > y && player.getY() + player.getHeight() < y + height){
                return true;
            }
            if(player.getY() < y + height && player.getY() > y){
                return true;
            }
        }
        return false;
    }
}
