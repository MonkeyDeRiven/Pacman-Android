package com.example.pacman_android;

import android.widget.ImageView;

import java.util.ArrayList;

public class GraphNode {

    private block field;

    private int cost = -1;
    private int lengthIndex;
    private int heightIndex;

    private GraphNode prev;

    private boolean isVisited;

    private ArrayList<GraphNode> neighboursList = new ArrayList<GraphNode>();


    public int getCost(){
        return cost;
    }

    public GraphNode(block field){
        this.field = field;
        isVisited = false;
    }

    public GraphNode getPrev(){
        return prev;
    }

    public block getField(){
        return field;
    }

    public boolean getIsVisisted(){
        return isVisited;
    }

    public int getNeighbourListSize(){
        return neighboursList.size();
    }

    public GraphNode getNeighbour(int index){
        return neighboursList.get(index);
    }

    public void addNeighbour(GraphNode newNeighbour){
        neighboursList.add(newNeighbour);
    }

    public void setIsVisited(boolean newIsVisited){
        isVisited = newIsVisited;
    }

    public void setPrev(GraphNode newPrev){
        prev = newPrev;
    }

    public void setCost(int newCost){
        cost = newCost;
    }
}
