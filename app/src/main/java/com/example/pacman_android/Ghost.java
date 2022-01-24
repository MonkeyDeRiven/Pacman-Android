package com.example.pacman_android;

import android.media.Image;
import android.widget.ImageView;

public class Ghost {

    private ImageView entity;
    private pathLinkedList path;

    private int speed;
    private int direction;

    private int width;
    private int height;

    public int x;
    public int y;

    public Ghost(ImageView entity, int size){
        this.entity = entity;
        this.direction = -1;
        this.width = size;
        this.height = size;

        this.x = (int)entity.getX();
        this.y = (int)entity.getY();
    }

    public void setPath(pathLinkedList newPath){
        path = newPath;
    }

    public ImageView getEntity(){
        return entity;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }
}
