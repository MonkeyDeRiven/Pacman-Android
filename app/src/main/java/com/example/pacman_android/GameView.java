package com.example.pacman_android;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.myfirstapp.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public Spieler spieler;
    public Spielfeld spielfeld;

    public GameThread thread;

    public GameView(Context context) {
        super(context);
        init(context);

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        spielfeld = new Spielfeld(BitmapFactory.decodeResource(getResources(), R.drawable.spielfelda));
        spieler = new Spieler(BitmapFactory.decodeResource(getResources(), R.drawable.pacman_mundauf_oben),BitmapFactory.decodeResource(getResources(), R.drawable.pacman_mundzu_oben),spielfeld);

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // update game logic!
    public void update(double lastFrameDur) {

        spieler.update(lastFrameDur);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            spielfeld.draw(canvas);
            spieler.draw(canvas);

        }
    }


    public void restart(){
        thread = new GameThread(getHolder(), this);
    }
}
