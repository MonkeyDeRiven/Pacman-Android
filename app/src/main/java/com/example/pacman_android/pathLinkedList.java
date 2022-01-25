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

    public void addWaypointEnd(waypoint newWaypoint){
        if(root == null){
            root = newWaypoint;
            end = root;
            return;
        }
        end.setNext(newWaypoint);
        end = end.getNext();

    }

    public waypoint getFirst(){
        return root;
    }

    public waypoint getSecond(){
        return root.getNext();
    }

    public void removeFirst(){
        if(end == root){
            root = null;
            end = null;
            return;
        }
        root = root.getNext();
        root.setPrev(null);
    }

    public boolean isEmpty(){
        if(root == null){
            return true;
        }
        return false;
    }
}
