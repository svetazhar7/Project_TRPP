package com.example.project_trpp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
public class MainActivity extends AppCompatActivity implements WeightFragment.WeightDialogListener {

    private int waterAmount = 0;
    private int weight = 0;

    private TextView waterGoalTextView;
    private TextView waterAmountTextView;
    private Button weightButton;
    private Button drinkButton;

    private SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waterGoalTextView = findViewById(R.id.water_goal_text_view);
        waterAmountTextView = findViewById(R.id.water_amount_text_view);
        weightButton = findViewById(R.id.weight_button);
        drinkButton = findViewById(R.id.drink_button);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        weight = sharedPreferences.getInt("weight", 0);
        if (weight > 0) {
            calculateWaterGoal();
        } else {
            showWeightDialog();
        }

        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drinkWater();
            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightDialog();
            }
        });
    }

    private void calculateWaterGoal() {
        int waterGoal = weight * 50;
        waterGoalTextView.setText("Цель: " + waterGoal + " мл в день");
    }

    private void drinkWater() {
        waterAmount += 250;
        waterAmountTextView.setText("Выпито: " + waterAmount + " мл");
    }

    private void showWeightDialog() {
        WeightFragment weightFragment = new WeightFragment();
        weightFragment.show(getSupportFragmentManager(), "weightDialog");
    }

    @Override
    public void onWeightEntered(int weight) {
        this.weight = weight;
        calculateWaterGoal();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("weight", weight);
        editor.apply();
    }
}
