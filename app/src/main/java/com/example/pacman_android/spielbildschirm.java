package com.example.pacman_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myfirstapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity {
    final int arrayLength = 40; //80
    final int arrayHeight = 20; //24

    int width = 0;
    int height = 0;
    public block[][] gameField = new block[arrayHeight][arrayLength];
    ImageView pacman;
    private int direction = 0;
    public static Handler h;
    boolean mapcreated=false;

    public void move ()
    {
        if(mapcreated)checkCollision();
        ImageView player = findViewById(R.id.player);
        if(direction == 0) player.setY(player.getY()-5);   //OBEN
        else if(direction == 1) player.setX(player.getX()+5); //RECHTS
        else if(direction == 2) player.setY(player.getY()+5); //UNTEN
        else if(direction == 3) player.setX(player.getX()-5); //LINKS
        else if(direction == -1);                            //STEHEN BLEIBEN
    }

        public void checkCollision () {
            pacman = findViewById(R.id.player);

            double pacmanX_1 = pacman.getX();
            double pacmanY_1 = pacman.getY();

            double pacmanX_2 = pacman.getX() + pacman.getWidth();
            double pacmanY_2 = pacman.getY() + pacman.getHeight();

            double pacmanCenterX = pacmanX_1 + pacman.getWidth() / 2;
            double pacmanCenterY = pacmanY_1 + pacman.getHeight() / 2;

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

            /*
            for (int a = 0; a < obstacles.length; a++) {
                if (obstacles[a].intersects((int)player.getX(),(int)player.getY(),(int)player.getX()+player.getWidth(),(int)player.getY()+(int)player.getHeight())) {
                    if(direction==0)player.setY(player.getY()+5);
                   else if(direction==1)player.setX(player.getX()-5);
                   else if(direction==2)player.setY(player.getY()-5);
                    else if(direction==3)player.setX(player.getX()+5);

                    direction = -1;

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
        pacman = findViewById(R.id.player);
        Button up = findViewById(R.id.upb);
        Button left = findViewById(R.id.leftb);
        Button right = findViewById(R.id.rightb);
        Button down = findViewById(R.id.downb);
        pacman.bringToFront();


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
        block newBlock;
        Rect newRect;

        for(int i = 0; i < arrayHeight; i++){
            for(int j = 0; j < arrayLength; j++){

                newRect = new Rect();

                newImageView = new ImageView(this);

                if(i == 0 || i == arrayHeight-1 || j == 0 || j == arrayLength-1){
                    newBlock = new block(true, blockHeight, blockWidth, xPosition, yPosition, newImageView);
                    newImageView.setBackground(getDrawable(R.drawable.tempwall));
                }
                else{
                    newBlock = new block(false, blockHeight, blockWidth, xPosition, yPosition, newImageView);
                    newImageView.setBackgroundColor(Color.WHITE);
                }
                gameDisplay.addView(newImageView);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(blockWidth, blockHeight);
                newImageView.setLayoutParams(layoutParams);
                newImageView.setX(xPosition);
                newImageView.setY(yPosition);
                xPosition += blockWidth;
            }
            yPosition += blockHeight;
            xPosition = (int)(widthInaccuracyValue * arrayLength / 2);
        }
        int o = 0;
        o = o + o;
    }
}




