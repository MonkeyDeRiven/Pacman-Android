package com.example.pacman_android;

import android.graphics.Rect;
import android.widget.ImageView;

public class block {
    private boolean isWall;

    private int height;
    private int width;

    private int x;
    private int y;

    private ImageView image;
    private Rect collisionArea;

    public block(boolean isWall, int height, int width, int x, int y, ImageView image){
        this.isWall = isWall;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.image = image;

        Rect collisionArea = new Rect();
        image.getHitRect(collisionArea);
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
}
