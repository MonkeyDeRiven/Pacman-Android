package com.example.pacman_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class spielbildschirm extends AppCompatActivity implements RankingDialog.RankingDialogListener {
    final int arrayLength = 40; //80
    final int arrayHeight = 12; //24

   public int cookiesEaten=0,amountCookies=0;
   public int xPosArray=0,yPosArray=0;
    public int survivedmilliseconds=0;
    TextView startCounter,timeGone;
RelativeLayout rl;

boolean gamestart=false;
    private static final String filename = "highscore.txt";
    private ArrayList<bestenliste.player> arrBestenListe = new ArrayList<>();
    TextView txtScore;
    ImageView herz1;
    ImageView herz2;
    ImageView herz3;
    int score = 0;
    String userNameDone = "";
    Boolean intersectsWithRedGhost = false;

    public GraphNode startingBlockRedGhost = null;
    public block startingBlockPacman = null;

    int counter = 0;
    int skinauswahl = 0;

    AnimationDrawable pacmananimation;
    public block[][] gameField = new block[arrayHeight][arrayLength];
    Spieler pacman;
    Ghost redGhost;
    public static Handler h;
    boolean mapcreated=false;
    public GameActivity gameActivity = new GameActivity();

    ReentrantLock l = new ReentrantLock();


    public void moveEntity(ImageView entity, int direction,int acc)
    {
        if(mapcreated) {
            if (direction == 0) entity.setY(entity.getY() - (3*acc));   //OBEN
            else if (direction == 1) entity.setX(entity.getX() + (3*acc)); //RECHTS
            else if (direction == 2) entity.setY(entity.getY() + (3*acc)); //UNTEN
            else if (direction == 3) entity.setX(entity.getX() - (3*acc)); //LINKS
            else if (direction == -1) ;                            //STEHEN BLEIBEN
        }
    }
    public void checkCollision () {

            double pacmanX_1 = pacman.x;
            double pacmanY_1 = pacman.y;

            double pacmanX_2 = pacman.x + pacman.getWidth();
            double pacmanY_2 = pacman.y + pacman.getHeight();

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
                    if(gameField[i+1][j].getIsWall()){
                        if(gameField[i+1][j].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            fixCollisionPosition();
                            pacman.setDirection(-1);
                        }
                    }
                    if(gameField[i-1][j].getIsWall()){
                        if(gameField[i-1][j].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            fixCollisionPosition();
                            pacman.setDirection(-1);
                        }
                    }
                    if(gameField[i][j+1].getIsWall()){
                        if(gameField[i][j+1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            fixCollisionPosition();
                            pacman.setDirection(-1);
                        }
                    }
                    if(gameField[i][j-1].getIsWall()){
                        if(gameField[i][j-1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                            fixCollisionPosition();
                            pacman.setDirection(-1);
                        }
                    }
                    break;
                }

            }
           xPosArray=j;yPosArray=i;
   }

   public void fixCollisionPosition() {
       if (pacman.getDirection() == 0) pacman.getEntity().setY(pacman.getEntity().getY() + 4);
       else if (pacman.getDirection() == 1) pacman.getEntity().setX(pacman.getEntity().getX() - 4);
       else if (pacman.getDirection() == 2) pacman.getEntity().setY(pacman.getEntity().getY() - 4);
       else if (pacman.getDirection() == 3) pacman.getEntity().setX(pacman.getEntity().getX() + 4);
       pacman.updateCoordinates();
   }



   public void pauseView(){
      startCounter = findViewById(R.id.pauseCounter);
       rl = findViewById(R.id.pauseScreen);
       rl.bringToFront();
       RelativeLayout spiellayout = findViewById(R.id.spielScreen);


       Animation fadeoutFirst = AnimationUtils.loadAnimation(this,R.anim.fadeout);
       fadeoutFirst.setDuration(1000);
       Animation fadeoutSecond = AnimationUtils.loadAnimation(this,R.anim.fadeout);
       fadeoutSecond.setDuration(1000);
       Animation fadeoutThird = AnimationUtils.loadAnimation(this,R.anim.fadeout);
       fadeoutThird.setDuration(1000);
       Animation fadeoutFourth = AnimationUtils.loadAnimation(this,R.anim.fadeout);
       fadeoutFourth.setDuration(1000);
       startCounter.setVisibility(View.VISIBLE);
       fadeoutFirst.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
startCounter.setText("2");
startCounter.clearAnimation();
startCounter.startAnimation(fadeoutSecond);
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });

       fadeoutSecond.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
               startCounter.setText("1");
               startCounter.clearAnimation();
               startCounter.startAnimation(fadeoutThird);
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
       fadeoutThird.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {

               startCounter.setText("GO!");
               startCounter.clearAnimation();

               startCounter.startAnimation(fadeoutFourth);

           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
       fadeoutFourth.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
              startCounter.setVisibility(View.INVISIBLE);
                spiellayout.bringToFront();
               startCounter.setText("3");
                continueGame();
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
       startCounter.startAnimation(fadeoutFirst);
   }

    public void updateTimeGone()
    {timeGone = findViewById(R.id.timeGone);
    timeGone.setText("Time: "+ survivedmilliseconds/1000);

    }
    public void checkDots()
    {
        if(!gameField[yPosArray][xPosArray].isVisited())
        {
           gameField[yPosArray][xPosArray].image.setBackgroundColor(Color.parseColor("#a4c639"));
           gameField[yPosArray][xPosArray].setVisited(true);
           ++cookiesEaten;
        }

    }

    public void pauseGame()
    {
gamestart=false;
        pacman.setSpeed(0);
        redGhost.setSpeed(0);
        survivedmilliseconds -=20;
    }
    public void continueGame()
    {
        gamestart= true;
        pacman.setSpeed(1);
        redGhost.setSpeed(1);
    }



    public boolean checkWin()
    {
        if(cookiesEaten==amountCookies) return true;
        return false;
    }

    protected void onResume() {
      super.onResume();
        pauseView();
        Timer timer;
           {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                synchronized public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        synchronized public void run() {

                            if (mapcreated) {
                                if(!gamestart)pauseGame();
                                intersectsWithRedGhost = pacmanIntersectsWithGhost(redGhost);
                                if (intersectsWithRedGhost) {
                                        counter += 1;
                                        if(counter == 1)
                                        onHitWithGhost();
                                }
                                else {
                                    //Pacman gets moved first
                                    if(pacman.getNextBlock() != null) {
                                        if(pacman.reachedNextBlock()){
                                            block currentBlock = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2).getField();
                                            int currentBlockIndex = findBlockIndex(pacman.getNextBlock());
                                            if(pacman.getDirectionBuffer() == 0){
                                                if(gameField[(currentBlockIndex/40)-1][currentBlockIndex%40].getIsWall() == false){
                                                    pacman.getEntity().setRotation(0);
                                                    pacman.setDirection(0);
                                                    pacman.setDirectionBuffer(-1);
                                                    pacman.setNextBlock(null);
                                                }
                                                else{
                                                    pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
                                                }
                                            }
                                            if(pacman.getDirectionBuffer() == 1){
                                                if(gameField[currentBlockIndex/40][(currentBlockIndex%40)+1].getIsWall() == false){
                                                    pacman.getEntity().setRotation(90);
                                                    pacman.setDirection(1);
                                                    pacman.setDirectionBuffer(-1);
                                                    pacman.setNextBlock(null);
                                                }
                                                else{
                                                    pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
                                                }
                                            }
                                            if(pacman.getDirectionBuffer() == 2){
                                                if(gameField[(currentBlockIndex/40)+1][currentBlockIndex%40].getIsWall() == false){
                                                    pacman.getEntity().setRotation(180);
                                                    pacman.setDirection(2);
                                                    pacman.setDirectionBuffer(-1);
                                                    pacman.setNextBlock(null);
                                                }
                                                else{
                                                    pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
                                                }
                                            }
                                            if(pacman.getDirectionBuffer() == 3){
                                                if(gameField[currentBlockIndex/40][(currentBlockIndex%40)-1].getIsWall() == false){
                                                    pacman.getEntity().setRotation(-90);
                                                    pacman.setDirection(3);
                                                    pacman.setDirectionBuffer(-1);
                                                    pacman.setNextBlock(null);
                                                }
                                                else{
                                                    pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
                                                }
                                            }
                                        }
                                    }

                                    //Check if field is a cookie
                                    checkDots();
                                    moveEntity(pacman.getEntity(), pacman.getDirection(),pacman.getSpeed());
                                    pacman.updateCoordinates();

                                    //Red Ghost gets moved
                                 if(gamestart) {  setGhostDirection(new GraphNode(null), true);
                                    moveEntity(redGhost.getEntity(), redGhost.getDirection(),redGhost.getSpeed());
                                    redGhost.updateCoordinates();
                                    checkCollision();
                                    survivedmilliseconds+=20;
                                    updateTimeGone();}
                                }
                            }
                        }
                    });
                }
            }, 0, 20);
        }
  }
Boolean gameEndDone = false;
    synchronized protected void onPause() {
        super.onPause();
       gamestart=false;

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

    herz1 = (ImageView) findViewById(R.id.herz1);
    herz2 = (ImageView) findViewById(R.id.herz4);
    herz3 = (ImageView) findViewById(R.id.herz5);


    }

    public void onUpMove(){
        block currentBlock = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2).getField();
        int currentBlockIndex = findBlockIndex(currentBlock);
        if(currentBlock.containsEntity(pacman.getEntity()) && gameField[(currentBlockIndex/40)-1][currentBlockIndex%40].getIsWall() == false){
            pacman.getEntity().setRotation(0);
            pacman.setDirection(0);
        }
        else{
            pacman.setDirectionBuffer(0);
            pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
        }
    }
    public void onLeftMove(){
        block currentBlock = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2).getField();
        int currentBlockIndex = findBlockIndex(currentBlock);
        if(currentBlock.containsEntity(pacman.getEntity()) && gameField[currentBlockIndex/40][(currentBlockIndex%40)-1].getIsWall() == false){
            pacman.getEntity().setRotation(-90);
            pacman.setDirection(3);
        }
        else{
            pacman.setDirectionBuffer(3);
            pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
        }
    }
    public void onDownMove() {
        block currentBlock = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2).getField();
        int currentBlockIndex = findBlockIndex(currentBlock);
        if(currentBlock.containsEntity(pacman.getEntity()) && gameField[(currentBlockIndex/40)+1][currentBlockIndex%40].getIsWall() == false){
            pacman.getEntity().setRotation(180);
            pacman.setDirection(2);
        }
        else{
            pacman.setDirectionBuffer(2);
            pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
        }
    }
    public void onRightMove(){
        block currentBlock = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2).getField();
        int currentBlockIndex = findBlockIndex(currentBlock);
        if(currentBlock.containsEntity(pacman.getEntity()) && gameField[currentBlockIndex/40][(currentBlockIndex%40)+1].getIsWall() == false){
            pacman.getEntity().setRotation(90);
            pacman.setDirection(1);
        }
        else{
            pacman.setDirectionBuffer(1);
            pacman.setNextBlock(getNexBlock(currentBlock, pacman.getDirection()));
        }
    }

    public block getNexBlock(block currentBlock, int direction){
        int currentBlockIndex = findBlockIndex(currentBlock);
        if(direction == 0 && gameField[(currentBlockIndex/40)-1][currentBlockIndex%40].getIsWall() == false){
            return gameField[(currentBlockIndex/40)-1][currentBlockIndex%40];
        }
        if(direction == 1 && gameField[currentBlockIndex/40][(currentBlockIndex%40)+1].getIsWall() == false){
            return gameField[currentBlockIndex/40][(currentBlockIndex%40)+1];
        }
        if(direction == 2 && gameField[(currentBlockIndex/40)+1][currentBlockIndex%40].getIsWall() == false){
            return gameField[(currentBlockIndex/40)+1][currentBlockIndex%40];
        }
        if(direction == 3 && gameField[currentBlockIndex/40][(currentBlockIndex%40)-1].getIsWall() == false){
            return gameField[currentBlockIndex/40][(currentBlockIndex%40)-1];
        }
        return null;
    }

    public void openSpielmenueView(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }

    public int findBlockIndex(block toFind){
        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){
                if (gameField[i][j] == toFind){
                    return i*40+j;
                }
            }
        }
        return 0;
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
                        newImageView.setBackgroundColor(Color.WHITE);++amountCookies;
                    }
                    if (level1[i][j] >= 1) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.BLACK);newBlock.setVisited(true);
                    }

                    if (i == 9 && j == 19) {
                        startingBlockPacman = newBlock;
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
            skinauswahl = Integer.valueOf(readFromFile(this));
            if(skinauswahl==0)newImageView.setBackgroundResource(R.drawable.pacmanmovementgelb);
            if(skinauswahl==1)newImageView.setBackgroundResource(R.drawable.pacmanmovementblau);
            if(skinauswahl==2)newImageView.setBackgroundResource(R.drawable.pacmanmovementgrun);
            if(skinauswahl==3)newImageView.setBackgroundResource(R.drawable.pacmanmovementrot);

            pacmananimation = (AnimationDrawable) newImageView.getBackground();
            pacmananimation.start();

            gameDisplay.addView(newImageView);

            //scales pacman
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);

            //sets pacmans starting position
            newImageView.setY(startingBlockPacman.getY());
            newImageView.setX(startingBlockPacman.getX());

            pacman = new Spieler(newImageView, entitySize);

            //Adds all Nodes to the Graph and sets their neighbours
            initializeGraph();
            setGraphNeighbours();

            //Red Ghost gets created
            newImageView = new ImageView(this);
            newImageView.setBackground(getDrawable(R.drawable.rotergeist_rechts));
            newImageView.setBackgroundColor(Color.BLUE);
            gameDisplay.addView(newImageView);
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);
            newImageView.setY(startYredGhost);
            newImageView.setX(startXredGhost);

            redGhost = new Ghost(newImageView, entitySize);
            redGhost.getEntity();
            mapcreated = true;

            startingBlockRedGhost = findGraphNode(gameField[5][17]);

        }
    }

    public void moveGhostRandom(){

    }

    public void setGhostDirection(GraphNode destination, boolean goToPacman){
        //Initialises path if not existent
        if(redGhost.path == null){
            redGhost.path = generateShortestPath(destination, goToPacman, redGhost);
            redGhost.setDirection(findDirection(redGhost));
        }

        //gets the next waypoint from path *Path.getFirst = current location*
        waypoint dest = redGhost.path.getSecond();

        if(redGhost.reachedNextWaypoint(dest)){
            redGhost.path = generateShortestPath(destination, goToPacman, redGhost);
            redGhost.setDirection(findDirection(redGhost));
        }
    }

    public int findDirection(Ghost ghost){
        int startX = ghost.path.getFirst().getNode().getField().getX();
        int startY = ghost.path.getFirst().getNode().getField().getY();

        int destX = ghost.path.getSecond().getNode().getField().getX();
        int destY = ghost.path.getSecond().getNode().getField().getY();

        if(startX - destX > 0){
            //left
            return 3;
        }
        else if(startX - destX < 0){
            //right
            return 1;
        }
        else if(startY - destY > 0){
            //up
            return 0;
        }
        else if(startY - destY < 0){
            //down
            return 2;
        }
        return -1;
    }

    //Dijkstra
    public pathLinkedList generateShortestPath(GraphNode dest, boolean goToPacman, Ghost ghost){
        GraphNode destination = null;
        GraphNode start = findEntitysNode(ghost.x + ghost.getWidth()/2, redGhost.y + redGhost.getHeight()/2);
        if(goToPacman)destination = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2);
        else destination = dest;
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

        newWaypoint = new waypoint(currentNode);
        newWaypoint.setNext(prevWaypoint);
        route.addWaypoint(newWaypoint);

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


    synchronized public boolean pacmanIntersectsWithGhost(Ghost ghost){
        GraphNode mNodeGhost = findEntitysNode(ghost.x + ghost.getWidth()/2, ghost.y + ghost.getHeight()/2);
        GraphNode mNodePacman = findEntitysNode(pacman.x + pacman.getWidth()/2, pacman.y + pacman.getHeight()/2);

        if(mNodePacman == mNodeGhost)
            intersectsWithRedGhost = true;

        return intersectsWithRedGhost;

    }

    public boolean ghostIsInStartingPos(Ghost ghost, GraphNode start){
        GraphNode mNodeGhost = findEntitysNode(ghost.x + ghost.getWidth()/2, ghost.y + ghost.getHeight()/2);

        if(mNodeGhost == start){
            return true;
        }
        else
            return false;
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




    void moveGhostToStartPos(Ghost ghost, GraphNode startingBlockGhost){
        ghost.path = null;
        while(!ghostIsInStartingPos(ghost, startingBlockGhost)) {
            setGhostDirection(startingBlockGhost, false);
            moveEntity(ghost.getEntity(), ghost.getDirection(),ghost.getSpeed());
            ghost.updateCoordinates();
            checkCollision();

        }
    }

    public void movePacmanToStartPos(){
        pacman.getEntity().setY(startingBlockPacman.getY());
        pacman.getEntity().setX(startingBlockPacman.getX());
        pacman.updateCoordinates();
    }


    void onHitWithGhost(){
        counter = 0;
        pacman.setDirection(-1);
        redGhost.setDirection(-1);
        if(intersectsWithRedGhost){ // || intersectsWithOtherGhosts

            pacman.life -= 1;
            if(pacman.life == 0)
                gameEnd();
            else{
                if(pacman.life == 1){
                    herz2.setVisibility(herz2.INVISIBLE);
                    moveGhostToStartPos(redGhost, startingBlockRedGhost);
                    movePacmanToStartPos();

                }
                if(pacman.life == 2){
                    herz3.setVisibility(herz3.INVISIBLE);
                    moveGhostToStartPos(redGhost, startingBlockRedGhost);
                    movePacmanToStartPos();
                }

            }
            intersectsWithRedGhost = false;
            //onResume();

        }
    }

    // ==================== Bestenliste Funktionen ====================

    synchronized public void gameEnd(){
        loadRanking();
        addHighscore();
    }

    public void addHighscore(){
        int size = arrBestenListe.size();
        Boolean playerIsBetter = false;
        score = 501;
        for(int i = 0; i<size; i++){
            if(score >= arrBestenListe.get(i).score) {
                playerIsBetter = true;
                break;
            }
        }

        if(size == 0)
            playerIsBetter = true;

        if(playerIsBetter){
            openDialog();
        }
    }

    public void openDialog(){
        RankingDialog rankingdialog = new RankingDialog();
        rankingdialog.show(getSupportFragmentManager(), "Top5");
    }

    void saveFile(){
       if(arrBestenListe.size() >= 1) Collections.sort(arrBestenListe, (p1, p2) -> Integer.valueOf(p2.score).compareTo(p1.score));

        FileOutputStream fos = null;
        String text = "";
        String name;
        String score;


        int size = arrBestenListe.size();

        for(int i = 0; i < size; i++){
            name = arrBestenListe.get(i).name;
            score = String.valueOf(arrBestenListe.get(i).score);
            text = text + name + ";" + score + "\n";
        }

        try {
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadRanking(){
        FileInputStream inputStream = null;

        String[] lineSplit;

        try{
            inputStream = openFileInput(filename);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(streamReader);
            String textLine;
            String name;
            int score;
            int counter = 0;

            while( (textLine = buffReader.readLine()) != null) {
                if (counter <= 4) {
                    lineSplit = textLine.split(";");
                    name = lineSplit[0];
                    score = Integer.parseInt(lineSplit[1]);
                    arrBestenListe.add(new bestenliste.player(name, score));
                    counter++;

                }
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserName(String username) {
        userNameDone = username;
        if(userNameDone.equals(""))
            userNameDone = "empty";
        arrBestenListe.add(new bestenliste.player(userNameDone, score));
        saveFile();
        gameEndDone = true;


        if(gameEndDone) {
            Intent newIntent = new Intent(this, hauptbildschirm.class);
            startActivity(newIntent);
        }
    }


    private String readFromFile(Context context) {

        String ret = "";

        try {
            try (InputStream inputStream = context.openFileInput("selectPacman.txt")) {

                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) != null) {
                        stringBuilder.append("\n").append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return String.valueOf(ret.charAt(1));
    }


}





