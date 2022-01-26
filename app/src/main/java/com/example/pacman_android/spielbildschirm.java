package com.example.pacman_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.View;

import android.util.Log;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public class spielbildschirm extends AppCompatActivity implements RankingDialog.RankingDialogListener {
    final String settingsFileName = "settings.txt";
    final int arrayLength = 40; //80
    final int arrayHeight = 12; //24

    public int cookiesEaten=0,amountCookies=0;
    public int xPosArray=0,yPosArray=0;
    public int survivedmilliseconds=0;
    TextView startCounter,timeGone;
    RelativeLayout rl;

    boolean gamestart=false;
    private static final String filename = "highscore.txt";
    private static final String filenameStats = "playerStats.txt";
    private ArrayList<bestenliste.player> arrBestenListe = new ArrayList<>();
    TextView txtScore;
    ImageView herz1;
    ImageView herz2;
    ImageView herz3;

    String userNameDone = "";
    TextView txtDialogScore;

    public block startingBlockPacman = null;
    public block startingBlockRedGhost = null;
    public block startingBlockOrangeGhost = null;
    public block startingBlockPinkGhost = null;

    public TextView textScoreAnzeige = null;

    int counter = 0;
    int skinauswahl = 0;

    AnimationDrawable pacmananimation;
    public block[][] gameField = new block[arrayHeight][arrayLength];

    Spieler pacman;

    Ghost redGhost;
    Ghost orangeGhost;
    Ghost pinkGhost;

    public static Handler h;

    boolean mapcreated=false;

    public GameActivity gameActivity = new GameActivity();

    ReentrantLock l = new ReentrantLock();

    public void moveEntity(ImageView entity, int direction, double acc) {
        if(mapcreated) {
            if(direction == 0) {
                entity.setY(entity.getY() - (int)(2*acc));   //OBEN
            }
            if(direction == 1){
                entity.setX(entity.getX() + (int)(2*acc)); //RECHTS
            }
            if(direction == 2){
                entity.setY(entity.getY() + (int)(2*acc)); //UNTEN
            }
            if(direction == 3){
                entity.setX(entity.getX() - (int)(2*acc)); //LINKS
            }
        }
    }
    public void checkCollision () {
        //check collision pacman
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
                        fixCollisionPositionPacman();
                        pacman.setDirection(-1);
                    }
                }
                if(gameField[i-1][j].getIsWall()){
                    if(gameField[i-1][j].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                        fixCollisionPositionPacman();
                        pacman.setDirection(-1);
                    }
                }
                if(gameField[i][j+1].getIsWall()){
                    if(gameField[i][j+1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                        fixCollisionPositionPacman();
                        pacman.setDirection(-1);
                    }
                }
                if(gameField[i][j-1].getIsWall()){
                    if(gameField[i][j-1].getCollisionArea().intersects((int)pacmanX_1,(int)pacmanY_1,(int)pacmanX_2,(int)pacmanY_2)){
                        fixCollisionPositionPacman();
                        pacman.setDirection(-1);
                    }
                }
                pacman.updateCoordinates();
                break;
            }
        }
        xPosArray=j;yPosArray=i;

        //check collison pinkGhost
        double pinkGhostX_1 = pinkGhost.x;
        double pinkGhostY_1 = pinkGhost.y;

        double pinkGhostX_2 = pinkGhost.x + pinkGhost.getWidth();
        double pinkGhostY_2 = pinkGhost.y + pinkGhost.getHeight();

        double pinkGhostCenterX = pinkGhostX_1 + (double)pinkGhost.getWidth() / 2;
        double pinkGhostCenterY = pinkGhostY_1 + (double)pinkGhost.getHeight() / 2;

        i = 0;
        j = 0;
        while(true){
            if((pinkGhostCenterX > gameField[i][j].getX() && pinkGhostCenterX < gameField[i][j].getX() + gameField[i][j].getWidth()) == false){
                j++;
                continue;
            }
            else if((pinkGhostCenterY > gameField[i][j].getY() && pinkGhostCenterY < gameField[i][j].getY() + gameField[i][j].getHeight()) == false){
                i++;
                continue;
            }
            else{
                if(gameField[i+1][j].getIsWall()){
                    if(gameField[i+1][j].getCollisionArea().intersects((int)pinkGhostX_1,(int)pinkGhostY_1,(int)pinkGhostX_2,(int)pinkGhostY_2)){
                        fixCollisionPositionGhost(pinkGhost);
                        pinkGhost.setDirection(-1);
                    }
                }
                if(gameField[i-1][j].getIsWall()){
                    if(gameField[i-1][j].getCollisionArea().intersects((int)pinkGhostX_1,(int)pinkGhostY_1,(int)pinkGhostX_2,(int)pinkGhostY_2)){
                        fixCollisionPositionGhost(pinkGhost);
                        pinkGhost.setDirection(-1);
                    }
                }
                if(gameField[i][j+1].getIsWall()){
                    if(gameField[i][j+1].getCollisionArea().intersects((int)pinkGhostX_1,(int)pinkGhostY_1,(int)pinkGhostX_2,(int)pinkGhostY_2)){
                        fixCollisionPositionGhost(pinkGhost);
                        pinkGhost.setDirection(-1);
                    }
                }
                if(gameField[i][j-1].getIsWall()){
                    if(gameField[i][j-1].getCollisionArea().intersects((int)pinkGhostX_1,(int)pinkGhostY_1,(int)pinkGhostX_2,(int)pinkGhostY_2)){
                        fixCollisionPositionGhost(pinkGhost);
                        pinkGhost.setDirection(-1);
                    }
                }
                pinkGhost.updateCoordinates();
                break;
            }
        }

        double orangeGhostX_1 = orangeGhost.x;
        double orangeGhostY_1 = orangeGhost.y;

        double orangeGhostX_2 = orangeGhost.x + orangeGhost.getWidth();
        double orangeGhostY_2 = orangeGhost.y + orangeGhost.getHeight();

        double orangeGhostCenterX = pinkGhostX_1 + (double)orangeGhost.getWidth() / 2;
        double orangeGhostCenterY = pinkGhostY_1 + (double)orangeGhost.getHeight() / 2;

        i = 0;
        j = 0;
        while(true){
            if((orangeGhostCenterX > gameField[i][j].getX() && orangeGhostCenterX < gameField[i][j].getX() + gameField[i][j].getWidth()) == false){
                j++;
                continue;
            }
            else if((orangeGhostCenterY > gameField[i][j].getY() && orangeGhostCenterY < gameField[i][j].getY() + gameField[i][j].getHeight()) == false){
                i++;
                continue;
            }
            else{
                if(gameField[i+1][j].getIsWall()){
                    if(gameField[i+1][j].getCollisionArea().intersects((int)orangeGhostX_1,(int)orangeGhostY_1,(int)orangeGhostX_2,(int)orangeGhostY_2)){
                        fixCollisionPositionGhost(orangeGhost);
                        orangeGhost.setDirection(-1);
                    }
                }
                if(gameField[i-1][j].getIsWall()){
                    if(gameField[i-1][j].getCollisionArea().intersects((int)orangeGhostX_1,(int)orangeGhostY_1,(int)orangeGhostX_2,(int)orangeGhostY_2)){
                        fixCollisionPositionGhost(orangeGhost);
                        orangeGhost.setDirection(-1);
                    }
                }
                if(gameField[i][j+1].getIsWall()){
                    if(gameField[i][j+1].getCollisionArea().intersects((int)orangeGhostX_1,(int)orangeGhostY_1,(int)orangeGhostX_2,(int)orangeGhostY_2)){
                        fixCollisionPositionGhost(orangeGhost);
                        pinkGhost.setDirection(-1);
                    }
                }
                if(gameField[i][j-1].getIsWall()){
                    if(gameField[i][j-1].getCollisionArea().intersects((int)orangeGhostX_1,(int)orangeGhostY_1,(int)orangeGhostX_2,(int)orangeGhostY_2)){
                        fixCollisionPositionGhost(orangeGhost);
                        orangeGhost.setDirection(-1);
                    }
                }
                orangeGhost.updateCoordinates();
                break;
            }
        }
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
           pacman.playerScore += 10;
           String scoreList = "Score: ";
           textScoreAnzeige.setText("Score: " + pacman.playerScore);
        }

    }

    public void setControllerLayout() {

        //Button for LEFT controller
        ImageButton btnRight_L = findViewById(R.id.rightb);
        ImageButton btnLeft_L = findViewById(R.id.leftb);
        ImageButton btnUp_L = findViewById(R.id.upb);
        ImageButton btnDown_L = findViewById(R.id.downb);

        //Button for RIGHT controller
        ImageButton btnRight_R = findViewById(R.id.rightb3);
        ImageButton btnLeft_R = findViewById(R.id.leftb3);
        ImageButton btnUp_R = findViewById(R.id.upb3);
        ImageButton btnDown_R = findViewById(R.id.downb3);

        //Pause Button
        ImageButton btnPause = findViewById(R.id.btnPause);

        String controllerLayout = "left";

        try {
            FileInputStream settingsOutput = openFileInput(settingsFileName);
            InputStreamReader reader = new InputStreamReader(settingsOutput);
            BufferedReader settingsReader = new BufferedReader(reader);

            //reads line from settings.txt
            controllerLayout = settingsReader.readLine();

            //Formats the String
            controllerLayout = controllerLayout.substring(controllerLayout.indexOf('=') + 1);
            controllerLayout = controllerLayout.trim();
            controllerLayout = controllerLayout.toLowerCase();

        } catch (IOException e) {
            Log.e("SETTINGS_ERROR", "Could not set controller layout, settings file may be corrupted");
        }

        if (controllerLayout.equals("left")) {
            //hide right controller
            btnRight_R.setVisibility(View.GONE);
            btnLeft_R.setVisibility(View.GONE);
            btnUp_R.setVisibility(View.GONE);
            btnDown_R.setVisibility(View.GONE);

            //show left controller
            btnRight_L.setVisibility(View.VISIBLE);
            btnLeft_L.setVisibility(View.VISIBLE);
            btnUp_L.setVisibility(View.VISIBLE);
            btnDown_L.setVisibility(View.VISIBLE);

        } else if (controllerLayout.equals("right")) {
            //hide rigth controller
            btnRight_R.setVisibility(View.VISIBLE);
            btnLeft_R.setVisibility(View.VISIBLE);
            btnUp_R.setVisibility(View.VISIBLE);
            btnDown_R.setVisibility(View.VISIBLE);

            //show left controller
            btnRight_L.setVisibility(View.GONE);
            btnLeft_L.setVisibility(View.GONE);
            btnUp_L.setVisibility(View.GONE);
            btnDown_L.setVisibility(View.GONE);
        }
    }

    public void pauseGame()
    {
        gamestart=false;
        pacman.setSpeed(0);
        redGhost.setSpeed(0);
        orangeGhost.setSpeed(0);
        pinkGhost.setSpeed(0);
        survivedmilliseconds -=20;
    }
    public void continueGame()
    {
        gamestart= true;
        pacman.setSpeed(2);
        redGhost.setSpeed(1);
        orangeGhost.setSpeed(2);
        pinkGhost.setSpeed(1);
    }

    public boolean checkWin()
    {
        if(cookiesEaten==amountCookies) return true;
        return false;
    }

    public void fixCollisionPositionPacman(){
       if(pacman.getDirection() == 0){
           pacman.getEntity().setY(pacman.getEntity().getY() + 5);
       }
       else if(pacman.getDirection() == 1){
           pacman.getEntity().setX(pacman.getEntity().getX() - 5);
       }
       else if(pacman.getDirection() == 2){
           pacman.getEntity().setY(pacman.getEntity().getY() - 5);
       }
       else if(pacman.getDirection() == 3){
           pacman.getEntity().setX(pacman.getEntity().getX() + 5);
       }

       pacman.updateCoordinates();
    }

    public void fixCollisionPositionGhost(Ghost ghost){
        if(ghost.getDirection() == 0){
            ghost.getEntity().setY(ghost.getEntity().getY() + (int)(2*ghost.getSpeed()));
        }
        else if(ghost.getDirection() == 1){
            ghost.getEntity().setX(ghost.getEntity().getX() - (int)(2*ghost.getSpeed()));
        }
        else if(ghost.getDirection() == 2){
            ghost.getEntity().setY(ghost.getEntity().getY() - (int)(2*ghost.getSpeed()));
        }
        else if(ghost.getDirection() == 3){
            ghost.getEntity().setX(ghost.getEntity().getX() + (int)(2*ghost.getSpeed()));
        }
        ghost.updateCoordinates();
    }

    protected void onResume() {
      super.onResume();
        pauseView();
        setControllerLayout();

  }
Boolean gameEndDone = false;
    synchronized protected void onPause() {
        super.onPause();

        pacman.setDirection(-1);
        redGhost.setDirection(-1);

       gamestart=false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.spielbildschirm);

        ImageButton up = findViewById(R.id.upb);
        ImageButton left = findViewById(R.id.leftb);
        ImageButton right = findViewById(R.id.rightb);
        ImageButton down = findViewById(R.id.downb);

        ImageButton up2 = findViewById(R.id.upb3);
        ImageButton left2 = findViewById(R.id.leftb3);
        ImageButton right2 = findViewById(R.id.rightb3);
        ImageButton down2 = findViewById(R.id.downb3);

        ImageButton btnSpielmenue = (ImageButton) findViewById(R.id.btnSpielmenue);

        btnSpielmenue.setOnClickListener(view -> {
            openSpielmenueView();
        });

        up.setOnClickListener(view ->{
            onUpMove();
        });
        down.setOnClickListener(view ->{
            onDownMove();
        });
        right.setOnClickListener(view ->{
            onRightMove();
        });
        left.setOnClickListener(view ->{
            onLeftMove();;
        });

        up2.setOnClickListener(view ->{
            onUpMove();
        });
        down2.setOnClickListener(view ->{
            onDownMove();
        });
        right2.setOnClickListener(view ->{
            onRightMove();
        });
        left2.setOnClickListener(view ->{
            onLeftMove();;
        });

        h = new Handler() {
            public void handleMessage(Message msg) {
                finish();
            }
        };

        herz1 = (ImageView) findViewById(R.id.herz1);
        herz2 = (ImageView) findViewById(R.id.herz4);
        herz3 = (ImageView) findViewById(R.id.herz5);

        textScoreAnzeige = findViewById(R.id.txtPScoreAnzeige);

        setControllerLayout();

        Timer timer;
        {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                synchronized public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        synchronized public void run() {

                            if (mapcreated) {
                                if(!gamestart){
                                    pauseGame();
                                }
                                if (pacmanIntersectsWithGhost(redGhost) || pacmanIntersectsWithGhost(orangeGhost) || pacmanIntersectsWithGhost(pinkGhost)){
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

                                    if(gamestart){
                                        //Red Ghost gets moved
                                        setRedGhostDirection(new GraphNode(null), true);
                                        moveEntity(redGhost.getEntity(), redGhost.getDirection(),redGhost.getSpeed());
                                        redGhost.updateCoordinates();

                                        //Blue Ghost gets moved
                                        setOrangeGhostDirection();
                                        moveEntity(orangeGhost.getEntity(), orangeGhost.getDirection(), orangeGhost.getSpeed());
                                        orangeGhost.updateCoordinates();

                                        //Pink Ghost gets moved
                                        setPinkGhostDirection();
                                        moveEntity(pinkGhost.getEntity(), pinkGhost.getDirection(), pinkGhost.getSpeed());
                                        pinkGhost.updateCoordinates();

                                        checkCollision();
                                        survivedmilliseconds+=20;
                                        updateTimeGone();
                                    }
                                }
                            }
                        }
                    });
                }
            }, 0, 20);
        }
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


            RelativeLayout.LayoutParams layoutParams;

            for (int i = 0; i < arrayHeight; i++) {
                for (int j = 0; j < arrayLength; j++) {

                    newImageView = new ImageView(this);
                    newRect = new Rect();

                    if (level1[i][j] == 0) {
                        newBlock = new block(false, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.BLUE);++amountCookies;
                    }
                    if (level1[i][j] == 1) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newBlock.setVisited(true);
                        newImageView.setBackgroundColor(Color.BLACK);
                    }

                    if (level1[i][j] == 2) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_inner_top));
                    }
                    if (level1[i][j] == 3) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_inner_top_right));
                    }
                    if (level1[i][j] == 4) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_left));
                    }
                    if (level1[i][j] == 5) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_inner_bottom_left));
                    }
                    if (level1[i][j] == 6) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_inner_bottom));
                    }
                    if (level1[i][j] == 7) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_right));
                    }
                    if (level1[i][j] == 8) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_inner_bottom_right));
                    }
                    if (level1[i][j] == 9) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_right));
                    }
                    if (level1[i][j] == 10) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_block_left));
                    }
                    if (level1[i][j] == 11) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_block_middle));
                    }
                    if (level1[i][j] == 12) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_block_right));
                    }
                    if (level1[i][j] == 13) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_top_right));
                    }
                    if (level1[i][j] == 14) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_upper_end));
                    }
                    if (level1[i][j] == 15) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_left_right));
                    }
                    if (level1[i][j] == 16) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_top));
                    }
                    if (level1[i][j] == 17) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_lower_end));
                    }
                    if (level1[i][j] == 18) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_bottom_left));
                    }
                    if (level1[i][j] == 19) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_bottom));
                    }
                    if (level1[i][j] == 20) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_bottom_right));
                    }
                    if (level1[i][j] == 21) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_top_left));
                    }
                    if (level1[i][j] == 22) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackground(getDrawable(R.drawable.wall_outer_top_right));
                    }

                    if (level1[i][j] == 40) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                      newImageView.setBackgroundColor(Color.GREEN);
                        newBlock.setFruit(true);
                    }


                    if (level1[i][j] == 41) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.BLUE);
                        newBlock.setFruit(true);
                    }


                    if (level1[i][j] == 42) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.YELLOW);
                        newBlock.setFruit(true);
                    }


                    if (level1[i][j] == 43) {
                        newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                        newImageView.setBackgroundColor(Color.RED);
                        newBlock.setFruit(true);
                    }







                    if (i == 9 && j == 19) {
                        startingBlockPacman = newBlock;
                    }

                    if(i == 5 && j == 19){
                        startingBlockPinkGhost = newBlock;
                    }

                    if(i == 5 && j == 18) {
                        startingBlockOrangeGhost = newBlock;
                    }

                    if(i == 5 && j == 17){
                        startingBlockRedGhost = newBlock;
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
            gameDisplay.addView(newImageView);
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);
            newImageView.setY(startingBlockRedGhost.getY());
            newImageView.setX(startingBlockRedGhost.getX());

            redGhost = new Ghost(newImageView, entitySize);

            //Orange Ghost gets created
            newImageView = new ImageView(this);
            newImageView.setBackground(getDrawable(R.drawable.orangergeist_rechts));
            gameDisplay.addView(newImageView);
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);
            newImageView.setY(startingBlockOrangeGhost.getY());
            newImageView.setX(startingBlockOrangeGhost.getX());

            orangeGhost = new Ghost(newImageView, entitySize);

            //Pink Ghost gets ceated
            newImageView = new ImageView(this);
            newImageView.setBackground(getDrawable(R.drawable.pinkergeist_rechts));
            gameDisplay.addView(newImageView);
            layoutParams = new RelativeLayout.LayoutParams(entitySize, entitySize);
            newImageView.setLayoutParams(layoutParams);
            newImageView.setX(startingBlockPinkGhost.getX());
            newImageView.setY(startingBlockPinkGhost.getY());

            pinkGhost = new Ghost(newImageView, entitySize);

            mapcreated = true;
        }
    }

    public void setRandomPath(Ghost ghost){
        int ghostCenterX = (int)ghost.getEntity().getX() + ghost.getEntity().getWidth()/2;
        int ghostCenterY = (int)ghost.getEntity().getY() + ghost.getEntity().getHeight()/2;

        pathLinkedList path = new pathLinkedList();
        waypoint newStop;

        GraphNode currentNode = findEntitysNode(ghostCenterX, ghostCenterY);
        newStop = new waypoint(currentNode);
        path.addWaypointEnd(newStop);

        Random r1 = new Random();

        int index = r1.nextInt(currentNode.getNeighbourListSize());
        currentNode = currentNode.getNeighbour(index);
        newStop = new waypoint(currentNode);
        path.addWaypointEnd(newStop);

        index = r1.nextInt(currentNode.getNeighbourListSize());
        currentNode = currentNode.getNeighbour(index);
        while(currentNode == path.getFirst().getNode()){
            index = r1.nextInt(currentNode.getPrev().getNeighbourListSize());
            currentNode = currentNode.getPrev().getNeighbour(index);
        }
        newStop = new waypoint(currentNode);
        path.addWaypointEnd(newStop);

        ghost.path = path;
    }

    public void setOrangeGhostDirection(){
        if(orangeGhost.path == null){
            setRandomPath(orangeGhost);
            orangeGhost.setDirection(findDirection(orangeGhost));
        }

        waypoint dest = orangeGhost.path.getSecond();

        if(orangeGhost.reachedNextWaypoint(dest)){
            waypoint nextStop = null;
            orangeGhost.path.removeFirst();
            if(orangeGhost.path.getSecond().getNode().getNeighbourListSize() == 1){
                nextStop = new waypoint(orangeGhost.path.getFirst().getNode());
            }
            else {
                Random r1 = new Random();
                int index;

                GraphNode nextNode = orangeGhost.path.getFirst().getNode();
                while(nextNode == orangeGhost.path.getFirst().getNode()){
                    index = r1.nextInt(orangeGhost.path.getSecond().getNode().getNeighbourListSize());
                    nextNode = orangeGhost.path.getSecond().getNode().getNeighbour(index);
                }
                nextStop = new waypoint(nextNode);
            }
            orangeGhost.path.addWaypointEnd(nextStop);
            orangeGhost.setDirection(findDirection(orangeGhost));
        }
    }

    public void setPinkGhostDirection(){
        if(pinkGhost.isInReach(pacman.getEntity())){
            if(pinkGhost.getInReach()){
                pinkGhost.setDirection(findDirection(pinkGhost));
            }
            else{
                pinkGhost.setInReach(true);
                setShortestPath(null, true, pinkGhost);
                pinkGhost.setDirection(findDirection(pinkGhost));
            }
        }
        else{
            if(pinkGhost.getInReach()){
                pinkGhost.setInReach(false);
                setRandomPath(pinkGhost);
                pinkGhost.setDirection(findDirection(pinkGhost));
            }
            else{
                waypoint dest = pinkGhost.path.getSecond();
                if(pinkGhost.reachedNextWaypoint(dest)){
                    waypoint nextStop = null;
                    pinkGhost.path.removeFirst();
                    if(pinkGhost.path.getSecond().getNode().getNeighbourListSize() == 1){
                        nextStop = new waypoint(pinkGhost.path.getFirst().getNode());
                    }
                    else {
                        Random r1 = new Random();
                        int index;

                        GraphNode nextNode = pinkGhost.path.getFirst().getNode();
                        while(nextNode == pinkGhost.path.getFirst().getNode()){
                            index = r1.nextInt(pinkGhost.path.getSecond().getNode().getNeighbourListSize());
                            nextNode = pinkGhost.path.getSecond().getNode().getNeighbour(index);
                        }
                        nextStop = new waypoint(nextNode);
                    }
                    pinkGhost.path.addWaypointEnd(nextStop);
                    pinkGhost.setDirection(findDirection(pinkGhost));
                }

            }
        }
    }


    public void setRedGhostDirection(GraphNode destination, boolean goToPacman){
        //Initialises path if not existent
        if(redGhost.path == null){
            setShortestPath(destination, goToPacman, redGhost);
            redGhost.setDirection(findDirection(redGhost));
        }

        //gets the next waypoint from path *Path.getFirst = current location*
        waypoint dest = redGhost.path.getSecond();

        if(redGhost.reachedNextWaypoint(dest)){
            setShortestPath(destination, goToPacman, redGhost);
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
    public void setShortestPath(GraphNode dest, boolean goToPacman, Ghost ghost){
        GraphNode destination = null;
        GraphNode start = findEntitysNode(ghost.x + ghost.getWidth()/2, ghost.y + ghost.getHeight()/2);

        if(goToPacman) {
            destination = findEntitysNode(pacman.x + pacman.getWidth() / 2, pacman.y + pacman.getHeight() / 2);
        }
        else {
            destination = dest;
        }

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
        ghost.path = route;
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
            return true;

        return false;

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

// 40 Kirsche, 41 Eisfrucht, 42 Ananas,43 Feuerfrucht


    private ArrayList<GraphNode> graph = new ArrayList<GraphNode>();

   void moveGhostToStartPos(Ghost ghost, block startingBlock){
        ghost.path = null;

        ghost.getEntity().setY(startingBlock.getY());
        ghost.getEntity().setX(startingBlock.getX());
        ghost.updateCoordinates();

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
        orangeGhost.setDirection(-1);
        // || intersectsWithOtherGhosts

        pacman.life -= 1;
        if(pacman.life == 0)
            gameEnd();
        else{
            if(pacman.life == 1){
                herz2.setVisibility(herz2.INVISIBLE);
            }
            if(pacman.life == 2){
                herz3.setVisibility(herz3.INVISIBLE);
            }
            moveGhostToStartPos(redGhost, startingBlockRedGhost);
            moveGhostToStartPos(orangeGhost, startingBlockOrangeGhost);
            moveGhostToStartPos(pinkGhost, startingBlockPinkGhost);
            movePacmanToStartPos();
            pinkGhost.setInReach(true);
            //onResume();
        }
    }

    // ==================== Bestenliste Funktionen ====================

    synchronized public void gameEnd(){
        savePlayerStats();
        loadRanking();
        addHighscore();
    }

   public void savePlayerStats() {

        FileOutputStream fos = null;
        String text = "";

       try {
           fos = openFileOutput(filenameStats, MODE_PRIVATE);
           fos.write(text.getBytes());
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

       String score = String.valueOf(pacman.playerScore);
       String ateGhost = String.valueOf(pacman.ateGhosts);


       text = score + ";" + ateGhost;

        try {
            fos = openFileOutput(filenameStats, MODE_PRIVATE);
            fos.write(text.getBytes());
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addHighscore(){
        int size = arrBestenListe.size();
        Boolean playerIsBetter = false;

        for(int i = 0; i<size; i++){
            if(pacman.playerScore >= arrBestenListe.get(i).score) {
                playerIsBetter = true;
                break;
            }
        }

        if(size == 0)
            playerIsBetter = true;

        if(playerIsBetter){
            openDialog();
        }
        else
            openLooseDialog();
    }

    public void openLooseDialog(){
        EndScreen ende = new EndScreen();
        ende.show(getSupportFragmentManager(), "Runde Beendet");
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
        String scoreInto;

        int size = arrBestenListe.size();

        for(int i = 0; i < size; i++){
            name = arrBestenListe.get(i).name;
            scoreInto = String.valueOf(arrBestenListe.get(i).score);
            text = text + name + ";" + scoreInto + "\n";
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
        arrBestenListe.add(new bestenliste.player(userNameDone, pacman.playerScore));
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





