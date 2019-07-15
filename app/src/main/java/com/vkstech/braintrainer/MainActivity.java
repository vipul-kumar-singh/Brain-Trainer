package com.vkstech.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button goButton, button0, button1, button2, button3, playAgainButton;

    private TextView sumTextView, resultTextView, scoreTextView, timerTextView;

    private static int locationOfCorrectAnswer, score = 0, numberOfQuestions = 0;

    private ArrayList<Integer> answerOptions = new ArrayList<>();

    private ConstraintLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = findViewById(R.id.goButton);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.INVISIBLE);

        sumTextView = findViewById(R.id.sumTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextVIew);

        gameLayout = findViewById(R.id.gameLayout);

        goButton.setVisibility(View.VISIBLE);
        gameLayout.setVisibility(View.INVISIBLE);
    }

    public void newQuestion() {
        Random random = new Random();

        int a = random.nextInt(21); // lowest = 0, highest = 20
        int b = random.nextInt(21); // lowest = 0, highest = 20

        sumTextView.setText(a + " + " + b);

        locationOfCorrectAnswer = random.nextInt(4);

        answerOptions.clear();

        for (int i = 0; i < 4; i++) {
            int answerOption;

            if (i == locationOfCorrectAnswer) {
                answerOption = a + b;

            } else {
                answerOption = random.nextInt(41);

                while (answerOption == a + b) {
                    answerOption = random.nextInt(41);
                }
                answerOptions.add(answerOption);

            }
            answerOptions.add(i, answerOption);
        }

        button0.setText(Integer.toString(answerOptions.get(0)));
        button1.setText(Integer.toString(answerOptions.get(1)));
        button2.setText(Integer.toString(answerOptions.get(2)));
        button3.setText(Integer.toString(answerOptions.get(3)));
    }

    public void start(View view) {
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        playAgain(timerTextView);
    }

    public void chooseAnswer(View view) {
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            resultTextView.setText(R.string.correct);
            resultTextView.setTextColor(getResources().getColor(R.color.correctColor));
            score++;
        } else {
            resultTextView.setText(R.string.wrong);
            resultTextView.setTextColor(getResources().getColor(R.color.wrongColor));
        }
        numberOfQuestions++;
        scoreTextView.setText(score + "/" + numberOfQuestions);
        newQuestion();
    }


    public void playAgain(View view) {
        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText(R.string.thirtySeconds);
        scoreTextView.setText(R.string.initialScore);
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setText("");

        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        newQuestion();

        new CountDownTimer(30100, 1000) {

            @Override
            public void onTick(long l) {
                timerTextView.setText((l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                resultTextView.setText(R.string.done);
                resultTextView.setTextColor(getResources().getColor(R.color.doneColor));
                playAgainButton.setVisibility(View.VISIBLE);
                button0.setEnabled(false);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        }.start();
    }
}
