package com.example.pacman_android;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.Random;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myfirstapp.R;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class spielbildschirm extends AppCompatActivity implements RankingDialog.RankingDialogListener {
MediaPlayer Mediaplayer;
    int width = 0, height = 0,points=0,arrPosGx=0,arrPosGy=0,arrPosPx=0,arrPosPy=0;
double playerAcc=0.75;
private static final String filename = "highscore.txt";
public Rect []obstacles;
ImageView player,geist1;
public ArrayList<bestenliste.player> arrBestenListe;
TextView score;
dot[] dots;
boolean mundauf=false;
public arrayElement[][] spielfeld;
public Rect rect;
private int direction = 0,direction1=0;
public static Handler h;
boolean mapcreated=false,win=false,lose=false;

    public void move ()
    {
        checkCollision();
      player = findViewById(R.id.player);
      double accValue = 5*playerAcc;
        if(direction == 0) {player.setY(player.getY()-(int)accValue);} //OBEN
        else if(direction == 1) {player.setX(player.getX()+(int)accValue);;} //RECHTS
        else if(direction == 2) {player.setY(player.getY()+(int)accValue); }//UNTEN
        else if(direction == 3) {player.setX(player.getX()-(int)accValue); }//LINKS
        else if(direction == -1);
        //STEHEN BLEIBEN
    }

    public void checkCollisionGhost()
    {geist1 = findViewById(R.id.geist1);

        for (int a = 0; a < obstacles.length; a++) {
            if (obstacles[a].intersects((int)geist1.getX(),(int)geist1.getY(),(int)geist1.getX()+geist1.getWidth(),(int)geist1.getY()+(int)geist1.getHeight())) {
                if(direction1==0){geist1.setY(geist1.getY()+5);break;}
                else if(direction1==1){geist1.setX(geist1.getX()-5);break;}
                else if(direction1==2){geist1.setY(geist1.getY()-5);break;}
                else if(direction1==3){geist1.setX(geist1.getX()+5);break;}
            }
            }
    }






    public void ghostmove ()
    {

        geist1 = findViewById(R.id.geist1);
        if(direction1 == 0) geist1.setY(geist1.getY()-5);   //OBEN
        else if(direction1 == 1) geist1.setX(geist1.getX()+5); //RECHTS
        else if(direction1 == 2) geist1.setY(geist1.getY()+5); //UNTEN
        else if(direction1 == 3) geist1.setX(geist1.getX()-5); //LINKS
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

   public void checkDots()
   {player = findViewById(R.id.player);
   score = findViewById(R.id.txtPScore);
    int counter=0;
   for(int a=0;a<dots.length;a++)
   { Rect tempRect = new Rect();
   dots[a].dot.getHitRect(tempRect);
       if(tempRect.intersects((int)player.getX(),(int)player.getY(),(int)player.getX()+player.getWidth(),(int)player.getY()+player.getHeight()))
           if(!dots[a].picked) {dots[a].dot.setVisibility(View.INVISIBLE);dots[a].picked=true;points+=10;score.setText("Score: "+points);}

       if(dots[a].picked)++counter;
   }
   if(counter==dots.length)win=true;
   }



   public void checkGhostTouch()
   {geist1 = findViewById(R.id.geist1);
   player = findViewById(R.id.player);
   Rect rect= new Rect();
   geist1.getHitRect(rect);
   if(rect.intersects((int)player.getX(),(int)player.getY(),(int)player.getX()+player.getWidth(),(int)player.getY()+player.getHeight()))
       lose=true;

   }

   public void createArray()
   {player = findViewById(R.id.player);
   int breiteAnzahl = width/player.getWidth(),hoeheAnzahl = height/player.getHeight();
   int breiteBlock = player.getWidth(),hoeheBlock=player.getHeight();
   int x1=0,x2=breiteBlock,y1=0,y2=hoeheBlock;
   spielfeld = new arrayElement[breiteAnzahl][hoeheAnzahl];

   for(int a=0,b=0;b<hoeheAnzahl;a++,x1+=breiteBlock,x2+=breiteBlock)
   {
       arrayElement element = new arrayElement();
       element.x1=x1;
       element.x2=x2;
       element.y1=y1;
       element.y2=y2;
       for(int c=0;c<obstacles.length;c++)
       {
           if(rect.intersect(x1,y1,x2,y2)){element.inhalt=0;break;}
       }
       spielfeld[a][b] = element;
       if(a==breiteAnzahl-1){++b;a=-1;x1=-breiteBlock;x2=0;y1+=hoeheBlock;y2+=hoeheBlock;}
   }
   }

   public void checkPos()
   {player = findViewById(R.id.player);
   geist1 = findViewById(R.id.geist1);
   score = findViewById(R.id.txtPScore);
   ImageView dot = findViewById(R.id.dot1);
       int playerPosX= (int)player.getX()+(player.getWidth()/2),playerPosY=(int)player.getY()+(player.getHeight()/2);
   int geistPosX= (int)geist1.getX()+(geist1.getWidth()/2),geistPosY=(int)geist1.getY()+(geist1.getHeight()/2);
   int breiteAnzahl = width/player.getWidth(),hoeheAnzahl = height/player.getHeight();
   boolean checkGeist=false,checkPlayer=false;
   for(int a=0,b=0;b<hoeheAnzahl;a++)
   {
       if(playerPosX>spielfeld[a][b].x1&&playerPosX<spielfeld[a][b].x2
       &&playerPosY>spielfeld[a][b].y1&&playerPosY<spielfeld[a][b].y2){arrPosPx=a;arrPosPy=b;checkGeist=true;}
       if(geistPosX>spielfeld[a][b].x1&&geistPosX<spielfeld[a][b].x2
               &&geistPosY>spielfeld[a][b].y1&&geistPosY<spielfeld[a][b].y2){arrPosGx=a;arrPosGy=b;checkPlayer=true;}
       if(checkGeist&&checkPlayer)break;
       if(a==breiteAnzahl-1){a=-1;++b;}
   }

   }
   public void pathing()
   {Vector <position> pfade = new Vector<>();
       position start= new position();
           position ziel = new position();
           geist1 =findViewById(R.id.geist1);
          int x1=(int)geist1.getX()+(int)(geist1.getWidth()/2),x2=(int)geist1.getX()+(int)(geist1.getWidth()/2),y1=(int)geist1.getY()+(int)(geist1.getHeight()/2),y2=(int)geist1.getY()+(geist1.getHeight()/2);
           // int x1=(int)geist1.getX(),x2=(int)geist1.getX()+(int)geist1.getWidth(),y1=(int)geist1.getY(),y2=(int)geist1.getY()+(int)geist1.getHeight();
           ziel.xArray = arrPosPx;ziel.yArray=arrPosPy;
           start.xArray = arrPosGx;start.yArray=arrPosGy;
           start.x1=x1;start.x2=x2;start.y1=y1;start.y2=y2;
           start.visited[start.xArray][start.yArray]=true;
           int iterator=0;
           pfade.add(start);
           int attempts =0;



           while(pfade.size()!=0)
           {int least=1000;boolean walked=false;


          try{ for(int a=0;a<pfade.size();a++)
               {int value1=0,value2=0,value=0;
                       value1=pfade.elementAt(a).xArray-ziel.xArray;
                      value2=pfade.elementAt(a).yArray-ziel.yArray;
               if(value1<0)value1 = -value1;
               if(value2<0)value2 = -value2;
               value = value1+value2;
                if(value<least){least=value;iterator=a;}}

               }
          catch(Exception e){};

               if(pfade.elementAt(iterator).xArray>ziel.xArray&&!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray-1][pfade.elementAt(iterator).yArray])  //LINKS
               { walked=true;
                   position abbild = new position();
                 abbild.xArray=pfade.elementAt(iterator).xArray;
                 abbild.yArray=pfade.elementAt(iterator).yArray;
                 abbild.pfad = pfade.elementAt(iterator).pfad;
                 abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                   abbild.visited  =pfade.elementAt(iterator).visited;
                 abbild.visited[pfade.elementAt(iterator).xArray-1][pfade.elementAt(iterator).yArray]= true;

                 pfade.add(abbild);

                pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                 pfade.elementAt(iterator).xArray -=1;
                 pfade.elementAt(iterator).x1-=width/geist1.getWidth(); pfade.elementAt(iterator).x2-=width/geist1.getWidth();
                 pfade.elementAt(iterator).pfad.add(3);
                 if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                 /*  for(int a=0;a<obstacles.length;a++)
                   {
                       if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                       {
                           pfade.remove(iterator);break;
                       }
                   }*/
               }

               else  if(pfade.elementAt(iterator).yArray<ziel.yArray&&!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray+1])  //UNTEN
               { walked=true;
                   position abbild = new position();
                   abbild.xArray=pfade.elementAt(iterator).xArray;
                   abbild.yArray=pfade.elementAt(iterator).yArray;
                   abbild.pfad = pfade.elementAt(iterator).pfad;
                   abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                   abbild.visited  =pfade.elementAt(iterator).visited;
                   abbild.visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray+1]= true;

                   pfade.add(abbild);

                   pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                   pfade.elementAt(iterator).yArray +=1;
                   pfade.elementAt(iterator).y1+=height/geist1.getHeight(); pfade.elementAt(iterator).y2+=height/geist1.getHeight();
                   pfade.elementAt(iterator).pfad.add(2);
                   if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                  /* for(int a=0;a<obstacles.length;a++)
                   {
                       if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                       {
                           pfade.remove(iterator);break;
                       }
                   }*/
               }

               else if(pfade.elementAt(iterator).xArray<ziel.xArray&&!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray+1][pfade.elementAt(iterator).yArray])  //RECHTS
               { walked=true;
                   position abbild = new position();

                   abbild.xArray=pfade.elementAt(iterator).xArray;
                   abbild.yArray=pfade.elementAt(iterator).yArray;
                   abbild.pfad = pfade.elementAt(iterator).pfad;
                   abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;

                           abbild.visited  =pfade.elementAt(iterator).visited;

                   abbild.visited[pfade.elementAt(iterator).xArray+1][pfade.elementAt(iterator).yArray]= true;

                   pfade.add(abbild);


                   pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                   pfade.elementAt(iterator).xArray +=1;
                   pfade.elementAt(iterator).x1+=width/geist1.getWidth(); pfade.elementAt(iterator).x2+=width/geist1.getWidth();
                   pfade.elementAt(iterator).pfad.add(1);
                   if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
               /*    for(int a=0;a<obstacles.length;a++)
                   {
                       if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                       {
                           pfade.remove(iterator);break;
                       }
                   }*/
               }



               else if(pfade.elementAt(iterator).yArray>ziel.yArray&&!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray-1])  //OBEN
               { walked=true;
                   position abbild = new position();

                   abbild.xArray=pfade.elementAt(iterator).xArray;
                   abbild.yArray=pfade.elementAt(iterator).yArray;
                   abbild.pfad = pfade.elementAt(iterator).pfad;
                   abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                   abbild.visited  =pfade.elementAt(iterator).visited;
                   abbild.visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray-1]= true;

                   pfade.add(abbild);

                   pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                   pfade.elementAt(iterator).yArray -=1;
                   pfade.elementAt(iterator).y1-=height/geist1.getHeight(); pfade.elementAt(iterator).y2-=height/geist1.getHeight();
                   pfade.elementAt(iterator).pfad.add(0);
                   if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                 /*  for(int a=0;a<obstacles.length;a++)
                    {
                        if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                        {
                            pfade.remove(iterator);break;
                        }
                    }*/


               }



               if(!walked)
               {
                   if(!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray-1][pfade.elementAt(iterator).yArray])
                   {
                       position abbild = new position();
                       abbild.xArray=pfade.elementAt(iterator).xArray;
                       abbild.yArray=pfade.elementAt(iterator).yArray;
                       abbild.pfad = pfade.elementAt(iterator).pfad;
                       abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                       abbild.visited  =pfade.elementAt(iterator).visited;
                       abbild.visited[pfade.elementAt(iterator).xArray-1][pfade.elementAt(iterator).yArray]= true;

                       pfade.add(abbild);

                       pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                       pfade.elementAt(iterator).xArray -=1;
                       pfade.elementAt(iterator).x1-=width/geist1.getWidth(); pfade.elementAt(iterator).x2-=width/geist1.getWidth();
                       pfade.elementAt(iterator).pfad.add(3);
                       if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                    /*  for(int a=0;a<obstacles.length;a++)
                       {
                           if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                           {
                               pfade.remove(iterator);break;
                           }
                       }*/
                   }

                 else  if(!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray+1][pfade.elementAt(iterator).yArray])
                   {
                       position abbild = new position();
                       abbild.xArray=pfade.elementAt(iterator).xArray;
                       abbild.yArray=pfade.elementAt(iterator).yArray;
                       abbild.pfad = pfade.elementAt(iterator).pfad;
                       abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                       abbild.visited  =pfade.elementAt(iterator).visited;
                       abbild.visited[pfade.elementAt(iterator).xArray+1][pfade.elementAt(iterator).yArray]= true;

                       pfade.add(abbild);


                       pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                       pfade.elementAt(iterator).xArray +=1;
                       pfade.elementAt(iterator).x1+=width/geist1.getWidth(); pfade.elementAt(iterator).x2+=width/geist1.getWidth();
                       pfade.elementAt(iterator).pfad.add(1);
                       if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                    /* for(int a=0;a<obstacles.length;a++)
                       {
                           if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                           {
                               pfade.remove(iterator);break;
                           }
                       }*/
                   }

                 else  if(!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray-1])
                   {
                       position abbild = new position();

                       abbild.xArray=pfade.elementAt(iterator).xArray;
                       abbild.yArray=pfade.elementAt(iterator).yArray;
                       abbild.pfad = pfade.elementAt(iterator).pfad;
                       abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                       abbild.visited  =pfade.elementAt(iterator).visited;
                       abbild.visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray-1]= true;

                       pfade.add(abbild);


                       pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                       pfade.elementAt(iterator).yArray -=1;
                       pfade.elementAt(iterator).y1-=height/geist1.getHeight(); pfade.elementAt(iterator).y2-=height/geist1.getHeight();
                       pfade.elementAt(iterator).pfad.add(0);
                       if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                    /*  for(int a=0;a<obstacles.length;a++)
                       {
                           if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                           {
                               pfade.remove(iterator);break;
                           }*/
                       /*}*/


                   }
                 else  if(!pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray+1])
                   {
                       position abbild = new position();

                       abbild.xArray=pfade.elementAt(iterator).xArray;
                       abbild.yArray=pfade.elementAt(iterator).yArray;
                       abbild.pfad = pfade.elementAt(iterator).pfad;
                       abbild.x1= x1;abbild.x2=x2;abbild.y1=y1;abbild.y2=y2;
                       abbild.visited  =pfade.elementAt(iterator).visited;
                       abbild.visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray+1]= true;

                       pfade.add(abbild);

                       pfade.elementAt(iterator).visited[pfade.elementAt(iterator).xArray][pfade.elementAt(iterator).yArray]=true;
                       pfade.elementAt(iterator).yArray +=1;
                       pfade.elementAt(iterator).y1+=height/geist1.getHeight(); pfade.elementAt(iterator).y2+=height/geist1.getHeight();
                       pfade.elementAt(iterator).pfad.add(2);
                       if(pfade.elementAt(iterator).xArray==ziel.xArray && pfade.elementAt(iterator).yArray==ziel.yArray){direction1=pfade.elementAt(iterator).pfad.elementAt(0);break;}
                   /*   for(int a=0;a<obstacles.length;a++)
                       {
                           if(obstacles[a].intersects(pfade.elementAt(iterator).x1,pfade.elementAt(iterator).y1,pfade.elementAt(iterator).x2,pfade.elementAt(iterator).y2))
                           {
                               pfade.remove(iterator);break;
                           }
                       }*/
                   }
               //  else{pfade.remove(iterator);}



           }
               //if(iterator==pfade.size()-1)iterator=-1;
               //++iterator;



   }



   }


public void animate()
{
    if(!mundauf){player.setImageResource(R.drawable.mundaufblau); mundauf=true;}  else {mundauf=false;player.setImageResource(R.drawable.mundzublau);}
}



    protected void onResume() {
      super.onResume();

  }

    protected void onPause() {
        super.onPause();
        direction = -1;
        gameEnd();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.spielbildschirm);
        Mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.app_src_main_res_raw_tetris_song);
        player = findViewById(R.id.player);
        Button up = findViewById(R.id.upb);
        Button left = findViewById(R.id.leftb);
        Button right = findViewById(R.id.rightb);
        Button down = findViewById(R.id.downb);
        player.bringToFront();
        arrBestenListe = new ArrayList<>();


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

    public void gameEnd(){
        loadRanking();
        addHighscore();

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

    public void addHighscore(){
        int size = arrBestenListe.size();
        Boolean playerIsBetter = false;
        points = 500;
        for(int i = 0; i<size; i++){
            if(points  >= arrBestenListe.get(i).score) {
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

    public void onUpMove(){

        direction = 0;}

        public void onLeftMove(){

            direction = 3;
        }
        public void onDownMove(){
            player.setRotation(90);
            direction = 2;
        }
        public void onRightMove(){
            player.setRotation(0);
            direction = 1;
        }

    public void openSpielmenueView(){
        Intent spielmenueView = new Intent(this, spielmenu.class);
        startActivity(spielmenueView);
    }
    public void openDialog(){
        RankingDialog rankingdialog = new RankingDialog();
        rankingdialog.show(getSupportFragmentManager(), "Top5");
    }

    void saveFile(){
        Collections.sort(arrBestenListe, (p1, p2) -> Integer.valueOf(p2.score).compareTo(p1.score));

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


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView obstacle1 = findViewById(R.id.obstacle1);ImageView obstacle2 = findViewById(R.id.obstacle2);ImageView obstacle3 = findViewById(R.id.obstacle3);
        ImageView obstacle4 = findViewById(R.id.obstacle4);ImageView obstacle5 = findViewById(R.id.obstacle5);ImageView obstacle6 = findViewById(R.id.obstacle6);
        ImageView obstacle7 = findViewById(R.id.obstacle7);ImageView obstacle8 = findViewById(R.id.obstacle8);ImageView obstacle9 = findViewById(R.id.obstacle9);
        ImageView obstacle10 = findViewById(R.id.obstacle10);ImageView obstacle11 = findViewById(R.id.obstacle11);ImageView obstacle12 = findViewById(R.id.obstacle12);
        ImageView obstacle13 = findViewById(R.id.obstacle13);

        ImageView dot1 = findViewById(R.id.dot1);ImageView dot2 = findViewById(R.id.dot2);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
         height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

        obstacles = new Rect[13];
        rect = new Rect();obstacle1.getHitRect(rect);obstacles[0] = rect;
        rect = new Rect();obstacle2.getHitRect(rect);obstacles[1] = rect;                           //RECT "ERZEUGT" EIN UNSICHTBARES QUADRAT UM DIE OBSTACLES,
        rect = new Rect();obstacle3.getHitRect(rect);obstacles[2] = rect;
        rect = new Rect();obstacle4.getHitRect(rect);obstacles[3] = rect;
        rect = new Rect();obstacle5.getHitRect(rect);obstacles[4] = rect;
        rect = new Rect();obstacle6.getHitRect(rect);obstacles[5] = rect;
        rect = new Rect();obstacle7.getHitRect(rect);obstacles[6] = rect;
        rect = new Rect();obstacle8.getHitRect(rect);obstacles[7] = rect;
        rect = new Rect();obstacle9.getHitRect(rect);obstacles[8] = rect;
        rect = new Rect();obstacle10.getHitRect(rect);obstacles[9] = rect;
        rect = new Rect();obstacle11.getHitRect(rect);obstacles[10] = rect;
        rect = new Rect();obstacle12.getHitRect(rect);obstacles[11] = rect;
        rect = new Rect();obstacle13.getHitRect(rect);obstacles[12] = rect;


        dots = new dot[2];
        dot dotobj = new dot(dot1);
        dots[0] = dotobj;
        dotobj = new dot(dot2);
        dots[1] = dotobj;



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
            }, 0, 10);
        }
        Timer timer2;
        {
            timer2 = new Timer();
            timer2.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {
                            ghostmove();

                        }
                    });
                }
            }, 0, 20);
        }


        Timer timer3;
        {
            timer3 = new Timer();
            timer3.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {
                            checkDots();
                            checkGhostTouch();
                            checkPos();
                            if(win) direction=-1;
                            if(lose) {onPause();} }
                    });
                }
            }, 0, 40);
        }

        Timer timer4;
        {
            timer4 = new Timer();
            timer4.scheduleAtFixedRate(new TimerTask() {
                public void run() { // wird periodisch im Timer thread aufgerufen
                    spielbildschirm.this.runOnUiThread(new Runnable() {
                        public void run() {
                            pathing();
                            animate();
                     }
                    });
                }
            }, 0, 50);
        }



        createArray();

    }



    @Override
    public void getUserName(String username) {
        String userNameDone = username;
        if(userNameDone.equals(""))
            userNameDone = "empty";
        arrBestenListe.add(new bestenliste.player(userNameDone, points));
        saveFile();
    }


    }





