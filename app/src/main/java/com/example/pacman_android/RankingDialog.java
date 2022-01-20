package com.example.pacman_android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myfirstapp.R;

public class RankingDialog extends AppCompatDialogFragment {
    private EditText userName;
    private RankingDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layouttophighscore, null);

        builder.setView(view).setTitle("Herzlichen Glückwunsch")
                .setPositiveButton("Abgeben", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String username = userName.getText().toString();
                        listener.getUserName(username);
                    }
                });

        userName = view.findViewById(R.id.userName);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (RankingDialogListener) context;
    }

    public interface RankingDialogListener{
        void getUserName(String username);
    }
}
