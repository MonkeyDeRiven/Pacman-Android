package com.example.pacman_android;

import android.media.Image;
import android.provider.ContactsContract;
import android.widget.ImageView;

public class Ghost {

    public ImageView entity;

    public boolean isFrozen=false;
    private int speed=3;
    private int direction;
    private boolean inReach = true;




    private int width;
    private int height;

    public boolean isEatable = false;

    public int x;
    public int y;

    private waypoint currentDestination;

    public pathLinkedList path;

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

    public int getDirection(){
        return direction;
    }

    public void setDirection(int newDirection){
        direction = newDirection;
    }

    public void setCurrentDestination(waypoint newCurrentDestination){
        currentDestination = newCurrentDestination;
    }

    public waypoint getCurrentDestination(){
        return currentDestination;
    }

    public void updateCoordinates(){
        x = (int)entity.getX();
        y = (int)entity.getY();
    }

    public boolean reachedNextWaypoint(waypoint next){
        //Area of the block via X1,2 and Y1,2
        int dest_X1 = next.getNode().getField().getX();
        int dest_X2 = next.getNode().getField().getX() + next.getNode().getField().getWidth();

        int dest_Y1 = next.getNode().getField().getY();
        int dest_Y2 = next.getNode().getField().getY() + next.getNode().getField().getHeight();

        if((dest_X1 <= x && dest_X2 >= x + width) && (dest_Y1 <= y && dest_Y2 >= y + height)){
            System.out.println("TRUE");
            return true;
        }
        System.out.println("FALSE");
        return false;
    }

    public boolean isInReach(ImageView entity){
        int ghostCenterX = x + width/2;
        int ghostCenterY = y + height/2;

        int pacmanCenterX = (int)entity.getX() + entity.getWidth()/2;
        int pacmanCenterY = (int)entity.getY() + entity.getHeight()/2;

        int distance = (int) Math.sqrt((ghostCenterX - pacmanCenterX) * (ghostCenterX - pacmanCenterX) + (pacmanCenterX - pacmanCenterY) * (pacmanCenterX - pacmanCenterY));
        if(distance < 300){
            System.out.println("TRUE");
            return true;
        }
        else{
            return false;
        }
    }

    public boolean getInReach(){
        return inReach;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setInReach(boolean newInReach){
        inReach = newInReach;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }
}
