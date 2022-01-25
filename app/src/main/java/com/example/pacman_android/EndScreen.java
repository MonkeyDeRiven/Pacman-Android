package com.example.pacman_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myfirstapp.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class EndScreen extends AppCompatDialogFragment {


    private TextView score;
    private TextView ateG;
    String playerScore;
    String ateGhosts;

    private static final String filenameStats = "playerStats.txt";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.verlorenlayout, null);


        builder.setView(view).setTitle("Verloren")
                .setPositiveButton("Beenden", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                            dismiss();
                            Intent newIntent = new Intent(getContext(),hauptbildschirm.class);
                            startActivity(newIntent);

                    }
                });
        setCancelable(false);

        loadRanking();

        score = view.findViewById(R.id.txtLooseScore);
        score.setText(score.getText().toString() + "         " + playerScore + " Punkte");

        ateG = view.findViewById(R.id.txtLooseAteGhosts);
        ateG.setText(ateG.getText().toString() + "\t"+  ateGhosts);

        return builder.create();
    }

    public void loadRanking(){
        FileInputStream inputStream = null;
        String textLine = "";
        String[] lineSplit;

        try{
            inputStream = getActivity().openFileInput(filenameStats);
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(streamReader);

            while( (textLine = buffReader.readLine()) != null) {
                lineSplit = textLine.split(";");
                playerScore = lineSplit[0];
                ateGhosts = lineSplit[1];
            }
        }

        catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}