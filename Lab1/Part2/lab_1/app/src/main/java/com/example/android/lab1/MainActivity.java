package com.example.android.lab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Integer firstValue;
    Integer secondValue;
    String operation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Calculator");
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText1);
    }


    public void buttonClick(View view) {
        Button button = (Button) view;
        switch (button.getText().toString()) {
            case "C":
                firstValue = null;
                secondValue = null;
                operation = null;
                editText.setText("");
                break;
            case "<-":

                break;
            case "+":
                firstValue =
                        Integer.valueOf(editText.getText().toString());
                operation = "+";
                editText.setText(editText.getText().toString() + button.getText());
                break;
            case "-":
                firstValue =
                        Integer.valueOf(editText.getText().toString());
                operation = "-";
                editText.setText(editText.getText().toString() + button.getText());
                break;
            case "*":
                firstValue =
                        Integer.valueOf(editText.getText().toString());
                operation = "*";
                editText.setText(editText.getText().toString() + button.getText());
                break;
            case "/":
                firstValue =
                        Integer.valueOf(editText.getText().toString());
                operation = "/";
                editText.setText(editText.getText().toString() + button.getText());
                break;
            case "sqrt(x)":
                firstValue = Integer.valueOf(editText.getText().toString());
                editText.setText(String.valueOf((Math.sqrt(firstValue))));
                break;
            case "^(x)":
                firstValue = Integer.valueOf(editText.getText().toString());
                editText.setText(String.valueOf(firstValue * firstValue));
                break;
            case "=":
                switch (operation) {
                    case "+":
                        editText.setText(String.valueOf((firstValue + secondValue)));
                        break;
                    case "-":
                        editText.setText(String.valueOf((firstValue - secondValue)));
                        break;
                    case "*":
                        editText.setText(String.valueOf((firstValue * secondValue)));
                        break;
                    case "/":
                        editText.setText(String.valueOf((firstValue / secondValue)));
                        break;
                    default:
                        break;
                }
                firstValue = null;
                secondValue = null;
                operation = null;
                break;
            default:
                if (operation != null) {
                    if (secondValue != null)
                        secondValue =
                                Integer.valueOf((String.valueOf(secondValue) + button.getText().toString()));
                    else
                        secondValue =
                                Integer.valueOf(button.getText().toString());
                }
                editText.setText(editText.getText().toString() + button.getText());
                break;
        }

    }
}