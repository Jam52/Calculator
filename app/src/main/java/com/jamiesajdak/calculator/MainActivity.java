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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //to hold current calculation on rotation
    private static final String STATE_CALCULATION = "CURRENT_CALCULATION";
    private static final String TAG = "MainActivity";
    private EditText result;
    private EditText newNumber;

    private static String performOperation(String input) {
        StringBuilder tempNumber = new StringBuilder();
        ArrayList<Object> allElements = new ArrayList<>();


        for (char element : input.toCharArray()) {

            if(String.valueOf(element).equals("-")){
                if(tempNumber.length()!=0){
                    allElements.add(tempNumber);
                    tempNumber = new StringBuilder();
                }
                allElements.add(element);
            }else
            if (Character.isDigit(element) || String.valueOf(element).equals(".")) {
                tempNumber.append(element);
            } else {
                allElements.add(tempNumber);
                allElements.add(element);
                tempNumber = new StringBuilder();
            }
        }
        allElements.add(tempNumber);

// if "-" first change to "-digit"
        if(allElements.get(0).toString().equals("-")){
            String addedMinus = "-"+allElements.get(1);
            allElements.set(0, addedMinus);
            allElements.remove(1);
        }


// changing "-"digits after operands into "-digit"
        for(int i=0; i < allElements.size(); i++) {
            String element = allElements.get(i).toString();
            if (element.equals("/")||element.equals("*")||element.equals("+")) {
                if(allElements.get(i+1).toString().equals("-")){
                    String addedMinus = "-"+allElements.get(i+2);
                    allElements.set(i+1, addedMinus);
                    allElements.remove(i+2);
                }
            }
        }

        System.out.println(allElements.toString());




        for (int i = 0; i < allElements.size(); i++) {
            String currentObj = allElements.get(i).toString();
            if (currentObj.equals("*") || currentObj.equals("/") || currentObj.equals("+") || currentObj.equals("-")) {
                if (allElements.get(i + 1).toString().equals("-")) {
                    String tempNum = "-" + (allElements.get(i + 2).toString());
                    allElements.set(i + 2, tempNum);
                    allElements.remove(i + 1);
                }
            }
        }

        System.out.println(allElements.toString());



        while (allElements.toString().contains("/"))
            for (int i = 0; i < allElements.size(); i++) {
                if (allElements.get(i).toString().equals("/")) {
                    Double divideEquals = Double.valueOf(allElements.get(i - 1).toString()) / Double.valueOf(allElements.get(i + 1).toString());
                    allElements.set(i, divideEquals);
                    allElements.remove(i + 1);
                    allElements.remove(i - 1);
                }
            }




        while (allElements.toString().contains("*")) {
            for (int i = 0; i < allElements.size(); i++) {
                if (allElements.get(i).toString().equals("*")) {
                    Double multipyEquals = Double.valueOf(allElements.get(i - 1).toString()) * Double.valueOf(allElements.get(i + 1).toString());
                    allElements.set(i, multipyEquals);
                    allElements.remove(i + 1);
                    allElements.remove(i - 1);
                }
            }
        }

        System.out.println(allElements);

        boolean minusTest = true;

        while(minusTest){
            minusTest = false;
            for (int i = 0; i < allElements.size(); i++) {
                if (allElements.get(i).toString().equals("-")) {
                    Double minusEquals = Double.valueOf(allElements.get(i - 1).toString()) - Double.valueOf(allElements.get(i + 1).toString());
                    allElements.set(i, minusEquals);
                    allElements.remove(i + 1);
                    allElements.remove(i - 1);
                    minusTest = true;
                }
            }
        }


        while (allElements.size() > 1) {
            for (int i = 0; i < allElements.size(); i++) {
                if (allElements.get(i).toString().equals("+")) {
                    Double plusEquals = Double.valueOf(allElements.get(i - 1).toString()) + Double.valueOf(allElements.get(i + 1).toString());
                    allElements.set(i, plusEquals);
                    allElements.remove(i + 1);
                    allElements.remove(i - 1);
                }
            }
        }


        String finalString = allElements.get(0).toString();
        String lastTwoDigits = "";
        if (finalString.length() >= 2) {
            lastTwoDigits = finalString.substring(finalString.length() - 2);
        }


        if (lastTwoDigits.equals(".0")) {
            return finalString.substring(0, finalString.length() - 2);

        } else {
            return finalString;
        }
    }

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

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Button b = (Button) v;
                String lastDigit = "";
                String lastNumber = "";

                if (newNumber.getText().length() > 0) {
                    lastNumber = findLastNumber(newNumber.getText().toString());
                    Log.d(TAG, "onClick: checking '.' "+ lastNumber);
                }
//                try{
//                    lastDigit = newNumber.getText().toString().substring(newNumber.getText().toString().length()-1);
//                } catch(Exception e) {
//                    Log.d(TAG, "onClick: som,thing");
//                }


                if(lastNumber.contains(".") && b.getText().toString().equals(".")){
                    Log.d(TAG, "onClick: duplicate .");
                } else {
                    newNumber.append(b.getText().toString());
                    String value = newNumber.getText().toString();
                    try {

                        Log.d(TAG, "onClick: attempting");
                        result.setText(performOperation(value));
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "onClick: catch");
                    }

                }



            }
        };

        View.OnClickListener zeroListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button b = (Button) v;

                String currentNewText = newNumber.getText().toString();
                String lastDigit = "";
                if(currentNewText.length() >0){
                    lastDigit = String.valueOf(currentNewText.charAt(currentNewText.length()-1));

                }

                if(currentNewText.equals("0") || currentNewText.equals("-0")){
                    Log.d(TAG, "onClick: singleZero/minusZero");
                } else if(lastDigit.equals("0") && !Character.isDigit(currentNewText.charAt(currentNewText.length()-2))){
                    Log.d(TAG, "onClick: zero after operand");
                    if(String.valueOf(currentNewText.charAt(currentNewText.length()-2)).equals(".")){
                        newNumber.append(b.getText().toString());
                    }
                } else {

                    newNumber.append(b.getText().toString());
                    String value = newNumber.getText().toString();
                    try {

                        Log.d(TAG, "onClick: attempting");
                        result.setText(performOperation(value));
                    } catch (NumberFormatException e) {
                        Log.d(TAG, "onClick: catch");
                    }
                }

            }
        };


        button0.setOnClickListener(zeroListener);
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

                if(newNumber.getText().toString().length() == 0 && result.getText().toString().length() != 0){
                    newNumber.setText(result.getText());
                }

                String lastDidgit = " ";

                if(newNumber.getText().toString().length()==0){
                    if (op.equals("-")){
                        newNumber.append(op);
                    }
                } else {
                    lastDidgit = String.valueOf(newNumber.getText().toString().charAt(newNumber.length()-1));
                    if (lastDidgit.equals("-") || lastDidgit.equals("+") || lastDidgit.equals("*") || lastDidgit.equals("/")) {
                        Log.d(TAG, "onClick: duplicate op");
                        if(op.equals("-")&& !lastDidgit.equals("-")){
                            newNumber.append(op);
                        }
                        } else {
                            newNumber.append(op);
                    }
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

                StringBuilder deleteLast = new StringBuilder(newNumber.getText().toString());
                if(deleteLast.length()>0){
                    deleteLast.deleteCharAt(deleteLast.length()-1);
                    newNumber.setText(deleteLast.toString());
                    try{
                        result.setText(performOperation(newNumber.getText().toString()));
                    } catch(Exception e) {
                        result.setText("");
                    }
                }

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

    private String findLastNumber(String number) {

        StringBuilder lastNumber = new StringBuilder();

        for(int i=number.length()-1; i >= 0; i--){
            if (Character.isDigit(number.charAt(i)) || String.valueOf(number.charAt(i)).equals(".")){
                lastNumber.append(number.charAt(i));
                Log.d(TAG, "findLastNumber: checking " + i + " " + lastNumber);

            }

        }
        return lastNumber.toString();
    }
}




