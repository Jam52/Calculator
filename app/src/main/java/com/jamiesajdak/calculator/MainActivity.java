package com.jamiesajdak.calculator;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView operation;

    //variables to hold operands and types of calculations
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    //to hold pendingOperation on rotation
    private static final String STATE_PENDOP = "PEND_OP";
    private static final String STATE_OP1 = "OP1";

    private static final String TAG = "MainActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        newNumber = findViewById(R.id.newNumber);
        operation = findViewById(R.id.operation);

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


        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Button b = (Button) v;
                newNumber.append(b.getText().toString());

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
                String value = newNumber.getText().toString();
                try {
                    Double dValue = Double.valueOf(value);
                    performOperation(dValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                pendingOperation = op;
                operation.setText(pendingOperation);

            }
        };

        buttonDivide.setOnClickListener(operationListener);
        buttonMinus.setOnClickListener(operationListener);
        buttonMultiply.setOnClickListener(operationListener);
        buttonEquals.setOnClickListener(operationListener);
        buttonPlus.setOnClickListener(operationListener);


        View.OnClickListener clear = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operand1 = null;
                operand2 = null;
                pendingOperation = "=";
                newNumber.setText("");
                result.setText("");
                operation.setText("");
            }
        };

        buttonClear.setOnClickListener(clear);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        outState.putString(STATE_PENDOP, operation.getText().toString());
        if(operand1 != null) {
            outState.putDouble(STATE_OP1, operand1);
        }
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: out");
        pendingOperation = savedInstanceState.getString(STATE_PENDOP);
        operand1 = savedInstanceState.getDouble(STATE_OP1);
        operation.setText(pendingOperation);
    }


    private void performOperation(Double value, String op) {
        if (operand1 != null) {
            operand2 = value;

            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        operand1 = 0.0;
                    }
                    operand1 /= operand2;
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
            }
        } else {
            operand1 = value;
        }


        result.setText(operand1.toString());
        newNumber.setText("");

    }







}
