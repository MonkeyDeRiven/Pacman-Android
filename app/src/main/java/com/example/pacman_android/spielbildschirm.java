package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myfirstapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class spielbildschirm extends AppCompatActivity {
int width = 0;
int height = 0;
public Rect []obstacles;
ImageView player;
public Rect rect;
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


        public void checkCollision ()
        {player = findViewById(R.id.player);

            for (int a = 0; a < obstacles.length; a++) {
                if (obstacles[a].intersects((int)player.getX(),(int)player.getY(),(int)player.getX()+player.getWidth(),(int)player.getY()+(int)player.getHeight())) {
                    if(direction==0)player.setY(player.getY()+5);
                   else if(direction==1)player.setX(player.getX()-5);
                   else if(direction==2)player.setY(player.getY()-5);
                    else if(direction==3)player.setX(player.getX()+5);

                    direction = -1;

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
        player = findViewById(R.id.player);
        Button up = findViewById(R.id.upb);
        Button left = findViewById(R.id.leftb);
        Button right = findViewById(R.id.rightb);
        Button down = findViewById(R.id.downb);
        player.bringToFront();


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
    player.setRotation(0);
        direction = 0;}

        public void onLeftMove(){
            player.setRotation(-90);
            direction = 3;
        }
        public void onDownMove(){
            player.setRotation(180);
            direction = 2;
        }
        public void onRightMove(){
            player.setRotation(90);
            direction = 1;
        }

    public void openSpielmenueView(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView obstacle1 = findViewById(R.id.obstacle1);
        ImageView obstacle2 = findViewById(R.id.obstacle2);
        ImageView obstacle3 = findViewById(R.id.obstacle3);
        ImageView obstacle4 = findViewById(R.id.obstacle4);
        ImageView obstacle5 = findViewById(R.id.obstacle5);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        obstacles = new Rect[5];
        rect = new Rect();
        obstacle1.getHitRect(rect);
        obstacles[0] = rect;
        rect = new Rect();
        obstacle2.getHitRect(rect);
        obstacles[1] = rect;                //INITIALISIERUNG DER HINDERNISSE
        rect = new Rect();                  //RECT "ERZEUGT" EIN UNSICHTBARES QUADRAT UM DIE OBSTACLES,
                                            //DAMIT KANN MIT DER INTERSECT FUNKTION ÜBERPRÜFT WERDEN
                                            //OB DER SPIELER DAGEGEN LÄUFT
        obstacle3.getHitRect(rect);
        obstacles[2] = rect;
        rect = new Rect();
        obstacle4.getHitRect(rect);
        obstacles[3] = rect;
        rect = new Rect();
        obstacle5.getHitRect(rect);
        obstacles[4] = rect;
        mapcreated = true;


    }
}




