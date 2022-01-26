package com.example.pacman_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.myfirstapp.R;


public class geisterlexikon extends AppCompatActivity {
RecyclerView recyclerView;
String s1[] = {"Blinky","Pinky","Inky","Clyde"};
String s2[] ={"Blinky ist der Verfolger. Sieht er dich einmal ist es schwer ihn wieder los zuwerden.",
        "Pinky liebt den Hinterhalt. Vor diesem Geist solltest du dich in Acht nehmen.",
        "Inky ist sehr launisch. Man weiß nie wie seine aktuelle Laune gerade ist.",
        "Clyde ist … halt Clyde. Langsam und ein großer Dummkopf."};


int images[] = {R.drawable.rotergeist,R.drawable.pinkergeist,R.drawable.blauergeist,R.drawable.orangegeist};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geisterlexikon);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        RecyclerView rv= findViewById(R.id.recycleview);

        MyAdapter myAdapter = new MyAdapter(this,s1,s2,images);
        rv.setAdapter(myAdapter);
       rv.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnExit = (ImageButton) findViewById(R.id.btnExitGeisterlexikon);

        btnExit.setOnClickListener(view -> {
            finish();
        });


    }
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}