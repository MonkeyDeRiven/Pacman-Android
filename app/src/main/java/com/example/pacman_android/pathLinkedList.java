package com.example.pacman_android;

public class pathLinkedList {
    waypoint root = null;
    waypoint end = null;

    public void addWaypoint(waypoint newWaypoint){
        if(root == null){
            root = newWaypoint;
            end = root;
            return;
        }
        root.setPrev(newWaypoint);
        root = root.getPrev();
    }

    public waypoint popStart(){
        waypoint toPop = root;
        root = root.getNext();
        return toPop;
    }
}
