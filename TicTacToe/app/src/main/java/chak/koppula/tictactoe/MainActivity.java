package chak.koppula.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //0: yellow, 1: red, -1: empty
    int[] gameState = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    int activePlayer = 0;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        TextView winnerTextView = findViewById(R.id.winnerTextView);

        winnerTextView.setText("Game in progress..");
        int counterTag = Integer.parseInt(counter.getTag().toString());

        if (gameState[counterTag] != -1 || !gameActive) return;

        gameState[counterTag] = activePlayer;
        counter.setTranslationY(-1500);

        setImageResource(counter);

        counter.animate().translationYBy(1500).rotationBy(360).setDuration(1000);
        checkForWin();
        checkForDraw();
    }

    public void checkForWin() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != -1) {
                gameWon();
            }
        }
    }

    public void checkForDraw() {
        boolean draw = true;
        for (int num : gameState) {
            if (num == -1) {
                draw = false;
                break;
            }
        }
        if (draw) {
            gameDrawn();
        }
    }

    public void setImageResource(ImageView counter) {
        if (activePlayer == 0) {
            counter.setImageResource(R.drawable.yellow);
            activePlayer = 1;
        } else {
            counter.setImageResource((R.drawable.red));
            activePlayer = 0;
        }
    }

    public void gameDrawn() {
        gameActive = false;
        Toast.makeText(this, "Game is a draw!", Toast.LENGTH_LONG).show();
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);

        winnerTextView.setText("Draw!!");
        winnerTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.VISIBLE);

    }

    public String findWinner() {
        if (activePlayer == 1) {
            return "Yellow";
        } else {
            return "Red";
        }
    }

    public void gameWon() {
        gameActive = false;
        String winner = findWinner();

        Toast.makeText(this, winner + " has won!", Toast.LENGTH_LONG).show();
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);

        winnerTextView.setText(winner + " has won!!");
        playAgainButton.setVisibility(View.VISIBLE);
    }

    public void playAgain(View view) {

        //sets button and textView to invisible
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        gameActive = true;

        resetCounters();
        resetGameState();
    }

    public void resetCounters() {
        GridLayout gridLayout = findViewById(R.id.gameGrid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    public void resetGameState() {
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
