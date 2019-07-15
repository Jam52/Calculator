package com.jamiesajdak.calculator;

import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    //to hold current calculation on rotation
    private static final String STATE_CALCULATION = "CURRENT_CALCULATION";
    private static final String TAG = "MainActivity";
    private EditText result;
    private EditText newNumber;
    private static final int VIBE_LENGTH = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 =  findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDecimal = findViewById(R.id.buttonDecimal);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonDel = findViewById(R.id.buttonDel);
        Button buttonLbracket = findViewById(R.id.buttonLbracket);
        Button buttonRbracket = findViewById(R.id.buttonRbracket);


        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Button b = (Button) v;
                String calculation = "";

                if(Arrays.asList("1","2","3","4","5","6","7","8","9","0",".","(",")").contains(b.getText().toString())){
                    b.setBackgroundResource(R.drawable.pressed);
                } else {
                    b.setBackgroundResource(R.drawable.pressed_white);
                }

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VIBE_LENGTH);


                if(newNumber.getText().toString().length() > 0){
                    calculation = newNumber.getText().toString();
                    Log.d(TAG, "onClick: setting current calculation");
                }



                Boolean inputCheck = new testInput(b.getText().toString(), calculation).getInputCheck();
                Log.d(TAG, "onClick: checking input: "+ b.getText().toString() + ":" + inputCheck);


                if(new testInput(b.getText().toString(), newNumber.getText().toString()).getInputCheck()){
                    newNumber.append(b.getText().toString());
                    try{
                        result.setText(new MyCalc(newNumber.getText().toString()).getFinalCalc());
                    } catch(Exception e) {
                        Log.d(TAG, "onClick: catching calculation error");
                    }
                }

            }
        };

        button0.setOnClickListener(Listener);
        button1.setOnClickListener(Listener);
        button2.setOnClickListener(Listener);
        button3.setOnClickListener(Listener);
        button4.setOnClickListener(Listener);
        button5.setOnClickListener(Listener);
        button6.setOnClickListener(Listener);
        button7.setOnClickListener(Listener);
        button8.setOnClickListener(Listener);
        button9.setOnClickListener(Listener);
        buttonPlus.setOnClickListener(Listener);
        buttonMinus.setOnClickListener(Listener);
        buttonDivide.setOnClickListener(Listener);
        buttonMultiply.setOnClickListener(Listener);
        buttonDecimal.setOnClickListener(Listener);
        buttonLbracket.setOnClickListener(Listener);
        buttonRbracket.setOnClickListener(Listener);


        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                newNumber.setText("");

                Button b = (Button) v;
                b.setBackgroundResource(R.drawable.pressed_white);

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VIBE_LENGTH);
            }
        });


        View.OnClickListener clear = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newNumber.setText("");
                result.setText("");

                Button b = (Button) v;
                b.setBackgroundResource(R.drawable.pressed_orange);

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VIBE_LENGTH);


            }
        };

        buttonClear.setOnClickListener(clear);


        View.OnClickListener delListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder deleteLast = new StringBuilder(newNumber.getText().toString());
                if(deleteLast.length()>0){
                    deleteLast.deleteCharAt(deleteLast.length()-1);
                    newNumber.setText(deleteLast.toString());
                    try{
                        result.setText(new MyCalc(newNumber.getText().toString()).getFinalCalc());
                    } catch(Exception e) {
                        result.setText("");
                    }
                }

                Button b = (Button) v;
                b.setBackgroundResource(R.drawable.pressed_orange);

                Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(VIBE_LENGTH);

            }
        };

        buttonDel.setOnClickListener(delListener);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(STATE_CALCULATION, newNumber.getText().toString());
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: out");
        newNumber.setText(savedInstanceState.getString(STATE_CALCULATION));
    }

}




