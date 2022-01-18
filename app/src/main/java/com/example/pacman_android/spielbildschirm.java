package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myfirstapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity {
int breite = 0;
int hoehe = 0;
boolean check = false;

public block[][] spielfeldarray;


    private int direction = 0;
    final int anzahlBloeckeBreite=20,anzahlBloeckeHoehe=15;


private int posX=0;
private int posY=0;

  public static Handler h;

  public void fillPlayfield()
  {
      spielfeldarray = new block[breite/anzahlBloeckeBreite][hoehe/anzahlBloeckeHoehe];
      int breiteBlock = breite/anzahlBloeckeBreite,hoeheBlock=hoehe/anzahlBloeckeHoehe;
    ImageView spielfeld = findViewById(R.id.playfield);

      for(int a=0,b=0,x=(int)spielfeld.getX(),y=(int)spielfeld.getY();y<hoehe;a++,x+=breiteBlock)
      {block value = new block();
      value.x1 = x;
      value.x2 = x+breiteBlock;
      value.y1 = y;
      value.y2 = y+hoeheBlock;
      value.inhalt = 1;
      spielfeldarray[a][b]  = value;

          if(a==anzahlBloeckeBreite-1){++b;a=-1;y+=hoeheBlock;x=-breiteBlock;}
      }
  }

  public void playfield1()
  {
      for(int a=0;a<anzahlBloeckeBreite;a++)
      {
          spielfeldarray[a][0].inhalt =0 ;
      }
      for(int a=0;a<anzahlBloeckeBreite;a++)
      {
          spielfeldarray[a][anzahlBloeckeHoehe-1].inhalt =0 ;
      }
  }



    public void move ()
    {

        ImageView player = findViewById(R.id.player);
        if(direction == 0) player.setY(player.getY()-1);   //OBEN
        else if(direction == 1) player.setX(player.getX()+1); //RECHTS
        else if(direction == 2) player.setY(player.getY()+1); //UNTEN
        else if(direction == 3) player.setX(player.getX()-1); //LINKS
        else if(direction == -1);                            //STEHEN BLEIBEN
    }
public void checkPos() {

    ImageView player = findViewById(R.id.player);
    int x=(int)player.getX()+player.getWidth()/2;
    int y = (int)player.getY()+player.getHeight()/2;
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

    }

  public void checkCollision()
  {



               if(direction == 0 && posY-1<0 || spielfeldarray[posX][posY-1].inhalt==0)direction = -1;//OBEN

             else  if(direction == 1 &&  posX>=anzahlBloeckeBreite|| spielfeldarray[posX+1][posY].inhalt==0)direction = -1;//RECHTS
            //RECHTS
             else if(direction == 2 &&  posY>=anzahlBloeckeHoehe|| spielfeldarray[posX][posY+1].inhalt==0)direction = -1;//UNTEN
               //UNTEN
            else   if(direction == 3 &&  posX-1<0 || spielfeldarray[posX-1][posY].inhalt==0)direction = -1;}//LINKS












    protected void onResume() {
     ImageView player = findViewById(R.id.player);
      super.onResume();
        Timer timer;
           {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {

                            if(check)checkPos();
                            if(check)checkCollision();

                            move();

                        }
                    });
                }
            }, 0, 5);
        }





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

        h = new Handler() {
            public void handleMessage(Message msg) {
                finish();
            }
        };
    }

    public void openSpielmenueView(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView spielfeld = findViewById(R.id.playfield);
        breite = spielfeld.getWidth();
        hoehe = spielfeld.getHeight();
        fillPlayfield();
        playfield1();
        check = true;
    }
}


