package com.jamiesajdak.calculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;


    //to hold current calculation on rotation
    private static final String STATE_CALCULATION = "CURRENT_CALCULATION";
    private static final String TAG = "MainActivity";



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
        Button buttonNeg = findViewById(R.id.buttonNeg);

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Button b = (Button) v;
                newNumber.append(b.getText().toString());
                String value = newNumber.getText().toString();
                try {
                    Log.d(TAG, "onClick: attempting");
                    result.setText(performOperation(value));
                } catch (NumberFormatException e) {
                    Log.d(TAG, "onClick: catch");
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
        buttonDecimal.setOnClickListener(Listener);


        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button b = (Button) v;
                String op = b.getText().toString();
                newNumber.append(op);
                String value = newNumber.getText().toString();
                try {
                    Log.d(TAG, "onClick: attempting");
                    result.setText(performOperation(value));
                } catch (NumberFormatException e) {
                    Log.d(TAG, "onClick: catch");
                }



            }
        };

        buttonDivide.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);


        buttonEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
            }
        });


        View.OnClickListener clear = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");
                result.setText("");

            }
        };

        buttonClear.setOnClickListener(clear);


        View.OnClickListener delListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNumber.setText("");

            }
        };

        buttonDel.setOnClickListener(delListener);



        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newNumber.getText().toString().equals("")){
                newNumber.setText("-");
                } else {
                String temp = newNumber.getText().toString();
                newNumber.setText("-");
                newNumber.append(temp);
                }

            }
        });





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


    private static String performOperation(String input) {
        StringBuilder tempNumber = new StringBuilder();
        ArrayList<Object> allElements = new ArrayList<>();

        for(char element : input.toCharArray()) {
            if(Character.isDigit(element) || String.valueOf(element).equals(".")) {
                tempNumber.append(element);
            } else {
                allElements.add(tempNumber);
                allElements.add(element);
                tempNumber = new StringBuilder();
            }
        }
        allElements.add(tempNumber);


//
//
        for( int i = 0; i < allElements.size(); i++) {
            if(allElements.get(i).toString().equals("/")) {
                Double divideEquals = Double.valueOf(allElements.get(i-1).toString())/Double.valueOf(allElements.get(i+1).toString());
                allElements.set(i, divideEquals);
                allElements.remove(i+1);
                allElements.remove(i-1);
            }
        }
        for( int i = 0; i < allElements.size(); i++) {
            if(allElements.get(i).toString().equals("*")) {
                Double multipyEquals = Double.valueOf(allElements.get(i-1).toString())*Double.valueOf(allElements.get(i+1).toString());
                allElements.set(i, multipyEquals);
                allElements.remove(i+1);
                allElements.remove(i-1);
            }
        }
        while(allElements.toString().contains("+")) {
            for (int i = 0; i < allElements.size(); i++){
                if(allElements.get(i).toString().equals("+")) {
                    Double plusEquals = Double.valueOf(allElements.get(i-1).toString())+Double.valueOf(allElements.get(i+1).toString());
                    allElements.remove(i+1);
                    allElements.set(i, plusEquals);
                    allElements.remove(i-1);

                }
            }
        }

        for(Object i : allElements){
            System.out.println(i.toString());
        }


        while(allElements.size() > 1) {
            for (int i = 0; i < allElements.size(); i++){
                if(allElements.get(i).toString().equals("-")) {
                    Double minusEquals = Double.valueOf(allElements.get(i-1).toString())-Double.valueOf(allElements.get(i+1).toString());
                    allElements.remove(i+1);
                    allElements.set(i, minusEquals);
                    allElements.remove(i-1);

                }
            }
        }

        return allElements.get(0).toString();

    }







}
