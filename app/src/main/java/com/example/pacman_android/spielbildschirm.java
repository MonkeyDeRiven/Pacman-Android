package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myfirstapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity   {


    int breite = 0;
int hoehe = 0;
boolean check = false;


public block[][] spielfeldarray;


    private int direction = 0;
    final int anzahlBloeckeBreite=20,anzahlBloeckeHoehe=15;


private int posX=0;
private int posY=0;






    public void move ()
    {

        ImageView player = findViewById(R.id.player);
        if(direction == 0) player.setY(player.getY()-5);   //OBEN
        else if(direction == 1) player.setX(player.getX()+5); //RECHTS
        else if(direction == 2) player.setY(player.getY()+5); //UNTEN
        else if(direction == 3) player.setX(player.getX()-5); //LINKS
        else if(direction == -1);                            //STEHEN BLEIBEN
    }
/*public void checkPos() {

     ImageView player = findViewById(R.id.player);
    int x=(int)player.getX()+player.getWidth();
    int y = (int)player.getY()+player.getHeight();
    for(int a =0,b=0;b!=anzahlBloeckeHoehe;a++)
    {
        if(x>=spielfeldarray[a][b].x1&&x<=spielfeldarray[a][b].x2&&
                y>=spielfeldarray[a][b].y1&&y<=spielfeldarray[a][b].y2)
        {
            posX = a;
            posY = b;
            break;
        }

            if(a==anzahlBloeckeBreite-1){++b;a=-1;}
    }

    }*/

  public void checkCollision() {
      ImageView player = findViewById(R.id.player);
      int x =(int) player.getX();
      int y =(int) player.getY();
  //    if(direction == 0 && posY-1<0 ||y-5 == grenzen[0].y2 )direction = -1;

      //    if(direction == 0 && posY-1<0 || spielfeldarray[posX][posY-1].inhalt==0)direction = -1;//OBEN

      //     else  if(direction == 1 &&  posX>=anzahlBloeckeBreite|| spielfeldarray[posX+1][posY].inhalt==0)direction = -1;//RECHTS
      //RECHTS
      //   else if(direction == 2 &&  posY>=anzahlBloeckeHoehe)direction = -1;//UNTEN
      //UNTEN
//            else   if(direction == 3 &&  posX-1<0)direction = -1;}//LINKS

  }










    protected void onResume() {
     ImageView player = findViewById(R.id.player);
      super.onResume();
        Timer timer2;
        {
            timer2 = new Timer();
            timer2.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {


                        }
                    });
                }
            }, 0, 5);



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
  }




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.spielbildschirm);
    //    hallo = findViewById(R.id.spielbildschirm2);

        Button up = findViewById(R.id.upb);
        Button left = findViewById(R.id.leftb);
        Button right = findViewById(R.id.rightb);
        Button down = findViewById(R.id.downb);
        ImageView player = findViewById(R.id.player);
        player.bringToFront();
        up.setOnTouchListener(new View.OnTouchListener()
    {public boolean onTouch(View v, MotionEvent event) {
        ImageView player = findViewById(R.id.player);
        player.setRotation(0);
        direction = 0;

        return true;
    }
    });
        right.setOnTouchListener(new View.OnTouchListener()
        {public boolean onTouch(View v, MotionEvent event) {
            ImageView player = findViewById(R.id.player);
            player.setRotation(90);
            direction = 1;
            return true;
        }
        });
        down.setOnTouchListener(new View.OnTouchListener()
        {public boolean onTouch(View v, MotionEvent event) {
            ImageView player = findViewById(R.id.player);
            player.setRotation(180);
            direction = 2;
            return true;
        }
        });
        left.setOnTouchListener(new View.OnTouchListener()
        {public boolean onTouch(View v, MotionEvent event) {
            ImageView player = findViewById(R.id.player);
            player.setRotation(-90);
            direction = 3;
            return true;
        }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

    }









}