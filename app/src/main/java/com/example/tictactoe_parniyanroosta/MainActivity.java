package com.example.tictactoe_parniyanroosta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int turn = 0;  //0 is for the leaf, 1 is for the flower and 2 is for empty cell

    private TextView playerFlowerScore, playerLeafScore;
    private int playerFlowerScoreCount, playerLeafScoreCount;
    private Button resetGame;

    boolean activeGame = true;   //for each round of the game

    // I've watched the following tutorials for this part ( lines 15-27-29 and the for loop in line 44 )
    // https://www.youtube.com/watch?v=LmOdIUp78FA&t=563s
    // https://www.youtube.com/watch?v=CCQTD7ptYqY


    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //based on the code that the instructor showed us
    int[][] winningStates = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},   //for rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},   //for columns
            {0, 4, 8}, {2, 4, 6}};             //for diagonal

    public void tapped(View view) {
        ImageView counts = (ImageView) view;
        int tappedAt = Integer.parseInt(counts.getTag().toString());

        if (activeGame && gameState[tappedAt] == 2) {
            //for giving the icons some animation when filling the board
            counts.setTranslationY(-1500);

            // to check if the tags are correct:
            // Toast.makeText(this, counts.getTag().toString(), Toast.LENGTH_SHORT).show();


            gameState[tappedAt] = turn;  //to store the value and records of the player and the position


            if (turn == 0) {
                counts.setImageResource(R.drawable.leaf);
                turn = 1;
            } else {
                counts.setImageResource(R.drawable.flower);
                turn = 0;
            }

            counts.animate().translationYBy(1500).rotation(3600).setDuration(350);
            for (int[] winningState : winningStates) {
                if (gameState[winningState[0]] == gameState[winningState[1]] && gameState[winningState[1]] == gameState[winningState[2]] && gameState[winningState[1]] != 2) {
                    String winner = "";
                    if (turn == 1) {
                        winner = "Leaf";
                        playerLeafScoreCount++;
                    } else {
                        winner = "Flower";
                        playerFlowerScoreCount++;
                    }
                    //updatePlayerScore();
                    activeGame = false;

                    TextView winnerAnnouncement = (TextView) findViewById(R.id.winner);
                    winnerAnnouncement.setText(winner + " has won!");
                    winnerAnnouncement.setVisibility(View.VISIBLE);

                    Button resetGame = (Button) findViewById(R.id.btnNewGame);
                    resetGame.setVisibility(View.VISIBLE);
                }
                updatePlayerScore();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerLeafScore = (TextView) findViewById(R.id.playerLeafScore);
        playerFlowerScore = (TextView) findViewById(R.id.playerFlowerScore);

        resetGame = (Button) findViewById(R.id.btnNewGame);

        playerLeafScoreCount = 0;
        playerFlowerScoreCount = 0;


        //setting onclick listener for the button
        resetGame.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                newGame(view);
                updatePlayerScore();
            }
        });
    }


    public void updatePlayerScore() {
        playerLeafScore.setText(Integer.toString(playerLeafScoreCount));
        playerFlowerScore.setText(Integer.toString(playerFlowerScoreCount));
    }


    //the function for re-starting the game and resetting all previous results
    public void newGame(View view) {

        TextView winnerAnnouncement = (TextView) findViewById(R.id.winner);
        winnerAnnouncement.setVisibility(View.INVISIBLE);

        Button newGame = (Button) findViewById(R.id.btnNewGame);
        newGame.setVisibility(View.INVISIBLE);

        //to empty all the grid cells
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < grid.getChildCount(); i++) {

            ImageView counts = (ImageView) grid.getChildAt(i);
            //to erase the board
            counts.setImageDrawable(null);
        }

        //to update the game
        turn = 0;
        activeGame = true;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

    }

}