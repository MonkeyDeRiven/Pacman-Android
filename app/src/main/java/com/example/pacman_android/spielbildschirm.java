package com.example.pacman_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myfirstapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity {
    final int arrayLength = 40; //80
    final int arrayHeight = 12; //24

    int width = 0;
    int height = 0;
    public block[][] gameField = new block[arrayHeight][arrayLength];
    Spieler pacman;
    Ghost redGhost;
    private int direction = 1;
    public static Handler h;
    boolean mapcreated=false;

    public void move ()
    {
        if(mapcreated) {
            checkCollision();
            if (direction == 0) pacman.getEntity().setY(pacman.getEntity().getY() - 3);   //OBEN
            else if (direction == 1) pacman.getEntity().setX(pacman.getEntity().getX() + 3); //RECHTS
            else if (direction == 2) pacman.getEntity().setY(pacman.getEntity().getY() + 3); //UNTEN
            else if (direction == 3) pacman.getEntity().setX(pacman.getEntity().getX() - 3); //LINKS
            else if (direction == -1) ;                            //STEHEN BLEIBEN
        }
    }
    public void checkCollision () {
/*
            pacman = findViewById(R.id.player);

            double pacmanX_1 = pacman.getX();
            double pacmanY_1 = pacman.getY();

            double pacmanX_2 = pacman.getX() + pacman.getWidth();
            double pacmanY_2 = pacman.getY() + pacman.getHeight();

            double pacmanCenterX = pacmanX_1 + (double)pacman.getWidth() / 2;
            double pacmanCenterY = pacmanY_1 + (double)pacman.getHeight() / 2;

            int i = 0;
            int j = 0;
            while(true){
                if((pacmanCenterX > gameField[i][j].getX() && pacmanCenterX < gameField[i][j].getX() + gameField[i][j].getWidth()) == false){
                    j++;
                    continue;
                }
                else if((pacmanCenterY > gameField[i][j].getY() && pacmanCenterY < gameField[i][j].getY() + gameField[i][j].getHeight()) == false){
                    i++;
                    continue;
                }
                else{
                    if(i == 19 || gameField[i+1][j].getIsWall()){
                        if(i == 19 || gameField[i+1][j].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            direction = -1;
                        }
                    }
                    if(gameField[i-1][j].getIsWall()){
                        if(gameField[i-1][j].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            direction = -1;
                        }
                    }
                    if(gameField[i][j+1].getIsWall()){
                        if(gameField[i][j+1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            direction = -1;
                        }
                    }
                    if(gameField[i][j-1].getIsWall()){
                        if(gameField[i][j-1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            direction = -1;
                        }
                    }
                    break;
                }
            }

 */

        for (int a = 0; a < arrayLength; a++) {
            for(int j = 0; j < arrayHeight; j++) {
                if (gameField[j][a].getIsWall() == true && gameField[j][a].getCollisionArea().intersects((int) pacman.getEntity().getX(), (int) pacman.getEntity().getY(), (int) pacman.getEntity().getX() + pacman.getWidth(), (int) pacman.getEntity().getY() + (int) pacman.getHeight())) {
                    if (direction == 0) pacman.getEntity().setY(pacman.getEntity().getY() + 3);
                    else if (direction == 1) pacman.getEntity().setX(pacman.getEntity().getX() - 3);
                    else if (direction == 2) pacman.getEntity().setY(pacman.getEntity().getY() - 3);
                    else if (direction == 3) pacman.getEntity().setX(pacman.getEntity().getX() + 3);

                    direction = -1;
                }
            }
        }
   }


    protected void onResume() {
      super.onResume();
        Timer timer;
           {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        int counter = 0;
                        public void run() {
                            if(mapcreated){
                                redGhost.setPath(generateShortestPath());
                                counter++;
                            }
                            move();
                        }
                    });
                }
            }, 0, 20);
        }
  }

    protected void onPause() {
        super.onPause();
        direction = -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.spielbildschirm);
        Button up = findViewById(R.id.upb);
        Button left = findViewById(R.id.leftb);
        Button right = findViewById(R.id.rightb);
        Button down = findViewById(R.id.downb);

        ImageButton btnSpielmenue = (ImageButton) findViewById(R.id.btnSpielmenue);

        btnSpielmenue.setOnClickListener(view -> {
            openSpielmenueView();
        });

        up.setOnClickListener(view ->{
       onUpMove();
        })  ;
        down.setOnClickListener(view ->{
            onDownMove();
        })  ;

        right.setOnClickListener(view ->{
            onRightMove();
        })  ;

     left.setOnClickListener(view ->{
        onLeftMove();;
    })  ;


    h = new Handler() {
            public void handleMessage(Message msg) {
                finish();
            }
        };
    }

    public void onUpMove(){
    pacman.getEntity().setRotation(0);
        direction = 0;}

        public void onLeftMove(){
            pacman.getEntity().setRotation(-90);
            direction = 3;
        }
        public void onDownMove(){
            pacman.getEntity().setRotation(180);
            direction = 2;
        }
        public void onRightMove(){
            pacman.getEntity().setRotation(90);
            direction = 1;
        }

    public void openSpielmenueView(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        RelativeLayout gameDisplay = findViewById(R.id.spielScreen);

        if(mapcreated == false) {
            int screenWidth = gameDisplay.getWidth();
            int screenHeight = gameDisplay.getHeight();

            double blockWidthAccurate = (double) screenWidth / arrayLength;
            double blockHeightAccurate = (double) screenHeight / arrayHeight;

            int blockWidth = (int) (blockWidthAccurate);
            int blockHeight = (int) (blockHeightAccurate);

            double heightInaccuracyValue = (double) blockHeightAccurate - blockHeight;
            double widthInaccuracyValue = (double) blockWidthAccurate - blockWidth;

            int xPosition = (int) (widthInaccuracyValue * arrayLength / 2);
            int yPosition = (int) (heightInaccuracyValue * arrayHeight / 2);

            ImageView newImageView;
            block newBlock = null;
            Rect newRect;

            int startXpacman = 0;
            int startYpacman = 0;

            int startXredGhost = 0;
            int startYredGhost = 0;

            RelativeLayout.LayoutParams layoutParams;

            for (int i = 0; i < arrayHeight; i++) {
                for (int j = 0; j < arrayLength; j++) {

                    newImageView = new ImageView(this);
                    newRect = new Rect();

                    if (level1[i][j] == 0) {
                        newBlock = new block(false, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.WHITE);
                    }
                    if (level1[i][j] >= 1) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.BLACK);
                    }

                    if (i == 9 && j == 19) {
                        startXpacman = xPosition;
                        startYpacman = yPosition;
                    }

                    if(i == 5 && j == 17){
                        startXredGhost = xPosition;
                        startYredGhost = yPosition;
                    }

                    //new layout for the imageview
                    gameDisplay.addView(newImageView);
                    layoutParams = new RelativeLayout.LayoutParams(blockWidth, blockHeight);

                    newImageView.setLayoutParams(layoutParams);
                    newImageView.setX(xPosition);
                    newImageView.setY(yPosition);
                    newImageView.getHitRect(newRect);
                    newRect.set(xPosition, yPosition, xPosition + blockWidth, yPosition + blockHeight);
                    xPosition += blockWidth;
                    gameField[i][j] = newBlock;
                }
                yPosition += blockHeight;
                xPosition = (int) (widthInaccuracyValue * arrayLength / 2);
            }

            //Entity size defines the height and width of our player/ghosts
            int entitySize = 0;

            //Sets the entity size to 90% of the smaller edge
            if(blockHeight > blockWidth){
                entitySize = (int)(blockWidth * 0.9);
            }
            else{
                entitySize = (int)(blockHeight * 0.9);
            }

            //pacman gets created
            newImageView = new ImageView(this);
            newImageView.setBackgroundColor(Color.YELLOW);
            gameDisplay.addView(newImageView);

            //scales pacman
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);

            //sets pacmans starting position
            newImageView.setY(startYpacman);
            newImageView.setX(startXpacman);

            pacman = new Spieler(newImageView, entitySize);

            //Adds all Nodes to the Graph and sets their neighbours
            initializeGraph();
            setGraphNeighbours();

            //Red Ghost gets created
            newImageView = new ImageView(this);
            newImageView.setBackground(getDrawable(R.drawable.rotergeist_rechts));
            gameDisplay.addView(newImageView);
            newImageView.setLayoutParams(layoutParams);
            newImageView.setY(startYredGhost);
            newImageView.setX(startXredGhost);

            redGhost = new Ghost(newImageView, entitySize);
            redGhost.getEntity();
            mapcreated = true;
        }
    }

    public void moveGhostRandom(){

    }

    public void moveRedGhost(){

    }


    //Dijkstra
    public pathLinkedList generateShortestPath(){
        GraphNode start = findEntitysNode(redGhost.x + redGhost.getWidth()/2, redGhost.y + redGhost.getHeight()/2);
        GraphNode destination = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2);

        ArrayList<GraphNode> que = new ArrayList<GraphNode>();

        GraphNode currentNode = start;

        //visits the first node
        currentNode.setCost(0);
        currentNode.setPrev(null);
        currentNode.setIsVisited(true);
        que.add(start);

        while(currentNode != destination){
            for(int i = 0; i < currentNode.getNeighbourListSize(); i++){
                if(currentNode.getNeighbour(i).getIsVisisted() == false) {
                    //Neighbours of current get pushed into que
                    que.add(currentNode.getNeighbour(i));
                    //Neighbours cost and previous gets set
                    currentNode.getNeighbour(i).setCost(currentNode.getCost() + 1);
                    currentNode.getNeighbour(i).setPrev(currentNode);
                }
            }
            //Current gets removed from que
            que.remove(0);
            currentNode.setIsVisited(true);
            //current gets next node from que
            currentNode = que.get(0);
        }

        pathLinkedList route = new pathLinkedList();
        waypoint newWaypoint;
        waypoint prevWaypoint = null;
        while(currentNode.getPrev() != null){
            newWaypoint = new waypoint(currentNode);
            if(currentNode == destination){
                newWaypoint.setNext(null);
            }
            else {
                newWaypoint.setNext(prevWaypoint);
            }
            route.addWaypoint(newWaypoint);
            prevWaypoint = newWaypoint;
            currentNode = currentNode.getPrev();
        }
        resetGraph();
        return route;
    }

    public void resetGraph(){
        for(int i = 0; i < graph.size(); i++){
            graph.get(i).setIsVisited(false);
        }
    }

    public void initializeGraph(){
        GraphNode newNode;
        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){
                if(gameField[i][j].getIsWall()){
                    continue;
                }
                newNode = new GraphNode(gameField[i][j]);
                graph.add(newNode);
            }
        }
    }

    public void setGraphNeighbours(){
        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){
                if(gameField[i][j].getIsWall()){
                    continue;
                }
                GraphNode currentNode = findGraphNode(gameField[i][j]);

                if(i > 0 && gameField[i-1][j].getIsWall() == false){
                    currentNode.addNeighbour(findGraphNode(gameField[i-1][j]));
                }
                if(i < arrayHeight-1 && gameField[i+1][j].getIsWall() == false){
                    currentNode.addNeighbour(findGraphNode(gameField[i+1][j]));
                }
                if(j > 0 && gameField[i][j-1].getIsWall() == false){
                    currentNode.addNeighbour(findGraphNode(gameField[i][j-1]));
                }
                if(j < arrayLength-1 && gameField[i][j+1].getIsWall() == false){
                    currentNode.addNeighbour(findGraphNode(gameField[i][j+1]));
                }
            }
        }
    }

    public GraphNode findEntitysNode(int entityCenterX, int entityCenterY){
        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){
                if(gameField[i][j].containsPoint(entityCenterX, entityCenterY) && gameField[i][j].getIsWall() == false){
                    return findGraphNode(gameField[i][j]);
                }
            }
        }
        return null;
    }

    public GraphNode findGraphNode(block field){
        for(int i = 0; i < graph.size(); i++){
            if(graph.get(i).getField() == field){
                return graph.get(i);
            }
        }
        return null;
    }





    private int[][] level1 = new int[][]{
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1},
                        {1,0,1,1,1,0,1,0,1,0,1,1,0,0,0,0,1,1,0,1,1,1,1,0,1,1,0,0,0,1,0,1,0,1,1,1,0,1,0,1},
                        {1,0,0,0,1,0,1,0,1,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,1,1,0,1,0,0,0,1,0,1},
                        {1,0,1,1,1,0,1,0,1,0,1,1,1,1,1,0,1,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,1,0,1,0,0,0,1},
                        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1},
                        {1,0,1,1,0,1,1,0,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1},
                        {1,0,1,1,0,1,1,0,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0,1},
                        {1,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,1,1,1,0,1,1,0,1,0,1,0,0,0,1,0,1,0,0,0,0,1,1,0,1},
                        {1,0,1,1,0,0,0,0,1,1,1,1,1,0,0,0,1,1,0,0,0,1,0,1,0,1,0,1,1,1,0,1,0,1,1,0,1,1,0,1},
                        {1,0,0,0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                      };

    private ArrayList<GraphNode> graph = new ArrayList<GraphNode>();
}





