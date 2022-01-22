package com.example.pacman_android;

import android.widget.ImageView;

import java.util.ArrayList;

public class GraphNode {
    private GraphNode next;
    private GraphNode prev;

    private block intersection;

    private ArrayList<GraphNode> neighboursList = new ArrayList<GraphNode>();


    public GraphNode(){};

    public GraphNode getNext(){
        return next;
    }

    public GraphNode getPrev(){
        return prev;
    }

    public block getIntersection(){
        return intersection;
    }

    public int getNeighbourListSize(){
        return neighboursList.size();
    }

    public GraphNode getNeighbour(int index){
        return neighboursList.get(index);
    }
}
