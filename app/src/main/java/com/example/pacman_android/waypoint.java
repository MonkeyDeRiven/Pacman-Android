package com.example.pacman_android;

public class waypoint {
    private GraphNode node;
    private waypoint next;
    private waypoint prev;

    public waypoint(GraphNode newNode){
        node = newNode;
    }

    public GraphNode getNode(){
        return node;
    }

    public waypoint getNext(){
        return next;
    }

    public waypoint getPrev(){
        return prev;
    }

    public void setNode(GraphNode newNode){
        node = newNode;
    }

    public void setNext(waypoint newNext){
        next = newNext;
    }

    public void setPrev(waypoint newPrev){
        prev = newPrev;
    }
}
