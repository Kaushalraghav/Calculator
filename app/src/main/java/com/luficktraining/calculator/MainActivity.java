package com.luficktraining.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperations = "=";
    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button dot;
    private Button equal;
    private Button multiply;
    private Button divide;
    private Button add;
    private Button subtract;
    private Button neg;
    private Button clear;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = findViewById(R.id.ruselt);
        newNumber = findViewById(R.id.newNumber);
        displayOperation = findViewById(R.id.operation);
        zero = findViewById(R.id.b0);
        one = findViewById(R.id.b1);
        two = findViewById(R.id.b2);
        three = findViewById(R.id.b3);
        four = findViewById(R.id.b4);
        five = findViewById(R.id.b5);
        six = findViewById(R.id.b6);
        seven = findViewById(R.id.b7);
        eight = findViewById(R.id.b8);
        nine = findViewById(R.id.b9);
        dot = findViewById(R.id.bdot);
        divide = findViewById(R.id.bdivide);
        add = findViewById(R.id.bplus);
        multiply = findViewById(R.id.bmultiply);
        subtract = findViewById(R.id.bsubtract);
        equal = findViewById(R.id.bequals);
        neg = findViewById(R.id.bneg);
        clear = findViewById(R.id.bclear);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };
        zero.setOnClickListener(listener);
        one.setOnClickListener(listener);
        two.setOnClickListener(listener);
        three.setOnClickListener(listener);
        four.setOnClickListener(listener);
        five.setOnClickListener(listener);
        six.setOnClickListener(listener);
        seven.setOnClickListener(listener);
        eight.setOnClickListener(listener);
        nine.setOnClickListener(listener);
        dot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performeOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText(" ");
                }
                pendingOperations = op;
                displayOperation.setText(pendingOperations);
            }
        };
        add.setOnClickListener(opListener);
        subtract.setOnClickListener(opListener);
        divide.setOnClickListener(opListener);
        multiply.setOnClickListener(opListener);
        equal.setOnClickListener(opListener);

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if (value.length() == 0){
                    newNumber.setText("-");
                }
                else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }
                    catch (NumberFormatException e)
                    {
                        newNumber.setText("");
                    }
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingOperations="=";
                operand1=null;
                operand2=null;
                newNumber.setText("");
                result.setText("");
                displayOperation.setText("");
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperations = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperations);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION,pendingOperations);
        if (operand1 != null){
            outState.putDouble(STATE_OPERAND1,operand1);
        }
        super.onSaveInstanceState(outState);
    }

    private void performeOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            operand2 = value;

            if (pendingOperations.equals("=")) {
                pendingOperations = operation;
            }
            switch (pendingOperations) {
                case "=":
                    operand1 = operand2;
                    break;
                case "/":
                    if (operand2 == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "+":
                    operand1 += operand2;
                    break;
            }
        }
        result.setText(operand1.toString());
        newNumber.setText("");
    }
}