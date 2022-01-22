package com.example.pacman_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
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

import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity {
    final int arrayLength = 40; //80
    final int arrayHeight = 12; //24

    int width = 0;
    int height = 0;
    public block[][] gameField = new block[arrayHeight][arrayLength];
    ImageView pacman;
    private int direction = 1;
    public static Handler h;
    boolean mapcreated=false;

    public void move ()
    {
        if(mapcreated) {
            checkCollision();
            if (direction == 0) pacman.setY(pacman.getY() - 5);   //OBEN
            else if (direction == 1) pacman.setX(pacman.getX() + 5); //RECHTS
            else if (direction == 2) pacman.setY(pacman.getY() + 5); //UNTEN
            else if (direction == 3) pacman.setX(pacman.getX() - 5); //LINKS
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
                    if (gameField[j][a].getIsWall() == true && gameField[j][a].getCollisionArea().intersects((int) pacman.getX(), (int) pacman.getY(), (int) pacman.getX() + pacman.getWidth(), (int) pacman.getY() + (int) pacman.getHeight())) {
                        if (direction == 0) pacman.setY(pacman.getY() + 5);
                        else if (direction == 1) pacman.setX(pacman.getX() - 5);
                        else if (direction == 2) pacman.setY(pacman.getY() - 5);
                        else if (direction == 3) pacman.setX(pacman.getX() + 5);

                        direction = -1;
                    }
                }
            }


        /*
            for (int a = 0; a < arrayLength; a++) {
                for(int j = 0; j < arrayHeight; j++) {
                    if (gameField[j][a].intersects(pacman)){
                        if (direction == 0) pacman.setY(pacman.getY() + 5);
                        else if (direction == 1) pacman.setX(pacman.getX() - 5);
                        else if (direction == 2) pacman.setY(pacman.getY() - 5);
                        else if (direction == 3) pacman.setX(pacman.getX() + 5);
                        direction = -1;
                        break;
                    }

                }
            }

             */
   }


    protected void onResume() {
      super.onResume();
        Timer timer;
           {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {
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
    pacman.setRotation(0);
        direction = 0;}

        public void onLeftMove(){
            pacman.setRotation(-90);
            direction = 3;
        }
        public void onDownMove(){
            pacman.setRotation(180);
            direction = 2;
        }
        public void onRightMove(){
            pacman.setRotation(90);
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

        int screenWidth = gameDisplay.getWidth();
        int screenHeight = gameDisplay.getHeight();

        System.out.println(screenWidth);
        System.out.println(screenHeight);

        double blockWidthAccurate = (double)screenWidth / arrayLength;
        double blockHeightAccurate = (double)screenHeight / arrayHeight;

        int blockWidth = (int)(blockWidthAccurate);
        int blockHeight = (int)(blockHeightAccurate);

        double heightInaccuracyValue = (double)blockHeightAccurate - blockHeight;
        double widthInaccuracyValue = (double)blockWidthAccurate - blockWidth;

        int xPosition = (int)(widthInaccuracyValue * arrayLength / 2);
        int yPosition = (int)(heightInaccuracyValue * arrayHeight / 2);

        ImageView newImageView;
        block newBlock = null;
        Rect newRect;

        int startX = 0;
        int startY = 0;

        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){

                newImageView = new ImageView(this);

                newRect = new Rect();
                /*
                if(i == 0 || i == arrayHeight-1 || j == 0 || j == arrayLength-1){
                    newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                    newImageView.setBackgroundColor(Color.BLACK);
                }
                else{
                    newBlock = new block(false, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                    newImageView.setBackgroundColor(Color.WHITE);
                }
                 */

                if(level1[i][j] == 0){
                    newBlock = new block(false, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                    newImageView.setBackgroundColor(Color.WHITE);
                }
                if(level1[i][j] >= 1){
                    newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView, newRect);
                    newImageView.setBackgroundColor(Color.BLACK);
                }

                if(i == 9 && j == 18){
                    startX = xPosition;
                    startY = yPosition;
                }

                //new layout for the imageview
                gameDisplay.addView(newImageView);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(blockWidth, blockHeight);

                newImageView.setLayoutParams(layoutParams);
                newImageView.setX(xPosition);
                newImageView.setY(yPosition);
                newImageView.getHitRect(newRect);
                newRect.set(xPosition, yPosition, xPosition + blockWidth, yPosition + blockHeight);
                xPosition += blockWidth;
                gameField[i][j] = newBlock;
            }
            yPosition += blockHeight;
            xPosition = (int)(widthInaccuracyValue * arrayLength / 2);
        }
        newImageView = new ImageView(this);
        newImageView.setBackgroundColor(Color.YELLOW);
        gameDisplay.addView(newImageView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(blockHeight * 0.9), (int)(blockHeight * 0.9));
        newImageView.setLayoutParams(layoutParams);
        newImageView.setY(startY);
        newImageView.setX(startX);
        pacman = newImageView;
        mapcreated = true;
    }

    private void InitializeGraph(){

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
}





