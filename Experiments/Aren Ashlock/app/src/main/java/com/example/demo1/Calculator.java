package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {
    Button logoutInput;
    Button inputAdd;
    Button inputMinus;
    Button input0;
    Button input1;
    Button input2;
    Button input3;
    Button input4;
    Button input5;
    Button input6;
    Button input7;
    Button input8;
    Button input9;
    TextView calcDisplay;
    TextView nameDisplay;
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    int buttonPressed = -1;
    int calcNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        nameDisplay = findViewById(R.id.nameDisplay);

        Intent login = getIntent();
        String name = login.getStringExtra("usernameInput");
        nameDisplay.setText("Hello " + name +"!");

        star1 = findViewById(R.id.star1);
        star1.setVisibility(View.INVISIBLE);
        star2 = findViewById(R.id.star2);
        star2.setVisibility(View.INVISIBLE);
        star3 = findViewById(R.id.star3);
        star3.setVisibility(View.INVISIBLE);
        star4 = findViewById(R.id.star4);
        star4.setVisibility(View.INVISIBLE);
        star5 = findViewById(R.id.star5);
        star5.setVisibility(View.INVISIBLE);

        logoutInput = findViewById(R.id.logoutInput);

        inputAdd = findViewById(R.id.inputPlus);
        inputMinus = findViewById(R.id.inputMinus);

        input0 = findViewById(R.id.input0);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        input3 = findViewById(R.id.input3);
        input4 = findViewById(R.id.input4);
        input5 = findViewById(R.id.input5);
        input6 = findViewById(R.id.input6);
        input7 = findViewById(R.id.input7);
        input8 = findViewById(R.id.input8);
        input9 = findViewById(R.id.input9);

        calcDisplay = findViewById(R.id.calcDisplay);

        logoutInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(Calculator.this, MainActivity.class);
                startActivity(back);
            }
        });

        inputAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed = 10;
                setStarRating();
            }
        });

        inputMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPressed = 11;
                setStarRating();
            }
        });

        input0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 0));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 0));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(0));
                    calcNum = 0;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 1));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 1));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(1));
                    calcNum = 1;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 2));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 2));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(2));
                    calcNum = 2;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 3));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 3));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(3));
                    calcNum = 3;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 4));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 4));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(4));
                    calcNum = 4;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 5));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 5));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(5));
                    calcNum = 5;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 6));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 6));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(6));
                    calcNum = 6;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 7));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 7));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(1));
                    calcNum = 7;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 8));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 8));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(8));
                    calcNum = 8;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });

        input9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonPressed == 10){
                    calcDisplay.setText(String.valueOf(calcNum += 9));
                    buttonPressed = -1;
                }

                else if(buttonPressed == 11){
                    calcDisplay.setText(String.valueOf(calcNum -= 9));
                    buttonPressed = -1;
                }

                else{
                    calcDisplay.setText(String.valueOf(9));
                    calcNum = 9;
                    buttonPressed = -1;
                }

                setStarRating();
            }
        });
    }

    public void setStarRating(){
        if(calcNum <= 0){
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        else if(calcNum == 1){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        else if(calcNum == 2){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        else if(calcNum == 3){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        else if(calcNum == 4){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }

        else if(calcNum >= 5){
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
            star5.setVisibility(View.VISIBLE);
        }
    }
}