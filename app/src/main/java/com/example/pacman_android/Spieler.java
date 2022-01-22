package com.example.pacman_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.myfirstapp.R;

public class Spieler {

    public Spielfeld spielfeld;
  public Bitmap obenauf,rechtsauf,untenauf,linksauf,obenzu,rechtszu,untenzu,linkszu,current;
  public int posX1 =0, posY1=0,posX2=0,posY2=0;
   public int direction=0;
   public int width=0;
   public int height=0;
  public int screenWidth;
  public int screenHeight;
  boolean mundzu= false;

   public Spieler(Bitmap bitmap,Bitmap bitmap2,Spielfeld spielfeld) {

       this.spielfeld = spielfeld;
        obenauf = bitmap;
        obenzu = bitmap2;

      screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
      screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

      width = screenWidth/10;
      height = screenWidth/10;
posX2 = width;
posY2 = height;

      obenauf = Bitmap.createScaledBitmap(obenauf, width, height, false);
      obenzu = Bitmap.createScaledBitmap(obenzu, width, height, false);
      rechtsauf=obenauf;
      linksauf=obenauf;
      untenauf=obenauf;         //ALLE BILDER GLEICHSETZEN MUNDAUF
      current=obenauf;

       rechtszu=obenzu;
       linkszu=obenzu;            //ALLE BILDER GLEICHSETZEN MUNDZU
       untenzu=obenzu;




      Matrix matrix = new Matrix();
      obenauf = Bitmap.createBitmap(obenauf, 0, 0, obenauf.getWidth(), obenauf.getHeight(),matrix, false);
      obenzu = Bitmap.createBitmap(obenzu, 0, 0, obenzu.getWidth(), obenzu.getHeight(),matrix, false);
      matrix.postRotate(90);
      rechtsauf= Bitmap.createBitmap(obenauf, 0, 0, obenauf.getWidth(), obenauf.getHeight(),matrix, false);
       rechtszu= Bitmap.createBitmap(rechtszu, 0, 0, rechtszu.getWidth(), rechtszu.getHeight(),matrix, false);
      matrix.postRotate(90);//SPIEGELUNG/ROTATION
      untenauf = Bitmap.createBitmap(obenauf, 0, 0, obenauf.getWidth(), obenauf.getHeight(),matrix, false);
       untenzu = Bitmap.createBitmap(untenzu, 0, 0, untenzu.getWidth(), untenzu.getHeight(),matrix, false);
       matrix.preScale(1,-1);
      linksauf =  Bitmap.createBitmap(rechtsauf, 0, 0, rechtsauf.getWidth(), rechtsauf.getHeight(),matrix, false);
       linkszu =  Bitmap.createBitmap(rechtszu, 0, 0, rechtsauf.getWidth(), rechtsauf.getHeight(),matrix, false);



   }

   public void move()
   {

      if(direction==0)
      {posY1-=5;posY2-=5;
      if(mundzu)
      {current=obenauf;mundzu=false;}
      else{current=obenzu;mundzu=true;}
      }
      else if(direction==1)
      {posX1+=5;posX2+=5;
      if(mundzu)
      {current=rechtsauf;mundzu=false;}
      else{current=rechtszu;mundzu=true;}}
      else if(direction==2)
      {posY1+=5;posY2+=5;
      if(mundzu)
      {current=untenauf;mundzu=false;}
      else{current=untenzu;mundzu=true;}}
      else if(direction==3)
   {posX1-=5;posX2-=5;
      if(mundzu)
      {current=linksauf;mundzu=false;}
      else{current=linkszu;mundzu=true;}}
      else if(direction==-1){};


   }

  /* public void  checkCollision()
   {boolean check=false;
       for(int a=0,c=0;c<spielfeld.anzahlBloeckeHoehe-10;a++)
       {
           if(spielfeld.spielfeldarray[a][c].intersects(posX,posY,posX+current.getWidth(),posY+current.getHeight()))
           {check=true;break;}
           if(a== spielfeld.anzahlBloeckeBreite-1){++c;a=-1;}

       }
       if(!check)direction=1;

   }*/




   public void draw(Canvas canvas) {
       canvas.drawBitmap(current, posX1, posY1, null);

   }

   public void update(double lastFrameDur){
       move();

         }




      }

