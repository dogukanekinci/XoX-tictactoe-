package com.example.xoxoyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playeronescore, PlayerTwoScore, PlayerStatus;
    private Button []buttons= new Button[9];
    private Button resetgame;
    private int playeronescorecount, PlayerTwoScorecount, rountcount;
    boolean activeplayer;
    //p1 => 0
    //p2 => 1
    //empty => 2
    int[] gamestate= {2,2,2,2,2,2,2,2,2};

    int [][] winningpositions = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, //columns
            {0,4,8},{2,4,6} //cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playeronescore = (TextView) findViewById(R.id.playeronescore);
        PlayerTwoScore = (TextView) findViewById(R.id.PlayerTwoScore);
        PlayerStatus = (TextView) findViewById(R.id.PlayerStatus);
        resetgame= (Button)findViewById(R.id.resetgame);
        for (int i=0; i < buttons.length; i++) {
            String buttonID="btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);

        }
        rountcount = 0;
        playeronescorecount =0;
        PlayerTwoScorecount = 0;
        activeplayer = true;
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId()); // btn_2
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length())); //2
        if (activeplayer) {
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#10ad3a"));
            gamestate[gameStatePointer] = 0;
        } else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#ad1093"));
            gamestate[gameStatePointer] = 1;
        }
        rountcount++;
        if (checkWinner()) {
            if (activeplayer) {
                playeronescorecount++;
                updatePlayerScore();
                Toast.makeText(this, "Birinci Oyuncu Kazandı!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                PlayerTwoScorecount++;
                updatePlayerScore();
                Toast.makeText(this, "İkinci Oyuncu Kazandı!", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        } else if (rountcount == 9) {
            playAgain();
            Toast.makeText(this, "Berabere", Toast.LENGTH_SHORT).show();

        } else {
            activeplayer = !activeplayer;
        }
        if (playeronescorecount > PlayerTwoScorecount) {
            PlayerStatus.setText("Birinci Oyuncu Kazandı");
        } else if (PlayerTwoScorecount > playeronescorecount) {
            PlayerStatus.setText("İkinci Oyuncu Kazandı");
        } else {
            PlayerStatus.setText("Durum Berabere");
        }
        resetgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playeronescorecount = 0;
                PlayerTwoScorecount = 0;
                PlayerStatus.setText("");
                updatePlayerScore();
            }
        });
    }
    public boolean checkWinner () {
        boolean winnerResult= false;

        for(int []winningposition : winningpositions){
            if (gamestate[winningposition[0]] == gamestate[winningposition[1]] &&
                    gamestate[winningposition[1]] == gamestate[winningposition[2]] &&
                        gamestate [winningposition[0]] !=2) {
                winnerResult = true;

                }
            }
        return winnerResult;
        }
    public void updatePlayerScore (){
        playeronescore.setText(Integer.toString(playeronescorecount));
        PlayerTwoScore .setText(Integer.toString(PlayerTwoScorecount));
    }
    public void playAgain(){
        rountcount = 0;
        activeplayer = true;
        for (int i = 0; i < buttons.length; i++){
            gamestate[i]= 2;
            buttons [i] .setText("");
        }
    }
}