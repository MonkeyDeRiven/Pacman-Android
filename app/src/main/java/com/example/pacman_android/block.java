package com.example.pacman_android;

import android.graphics.Rect;
import android.media.Image;
import android.widget.ImageView;

public class block {
    private boolean isWall;

    private boolean isFruit= false;
    private int height;
    private int width;

    private int x;
    private int y;




    public ImageView image;
    private Rect collisionArea = new Rect();

    private boolean visited = false;

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

    public boolean isVisited() { return visited; }

    public ImageView getImage() { return image; }

    public void setImage(ImageView image) { this.image = image; }

    public void setVisited(boolean visited) { this.visited = visited; }

    public boolean containsEntity(ImageView entity){
        if(x <= entity.getX() && x + width >= entity.getX() + entity.getWidth()){
            if(y <= entity.getY() && y + height >= entity.getY() + entity.getHeight()){
                return true;
            }
        }
        return false;
    }

    public boolean containsPoint(int x, int y){

        if(x >= this.x && x <= this.x + this.width){
            if(y >= this.y && y <= this.y + this.height){
                return true;
            }
        }
        return false;
    }

    public boolean isFruit() {
        return isFruit;
    }

    public void setFruit(boolean fruit) {
        isFruit = fruit;
    }

}
