package com.example.project_trpp;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

/**

 The MainActivity class is responsible for controlling the main activity of the application.
 It contains methods for calculating water goal, tracking water intake and displaying weight dialog.
 @author  Zharkova Svetlana, Skityova Anastasia
 @version 1.0
 @since 2023-04-05
 */
public class MainActivity extends AppCompatActivity implements WeightFragment.WeightDialogListener {
    /**

     The amount of water that has been consumed.
     */
    private int waterAmount = 0;
    /**

     The user's weight, used to calculate the recommended daily water intake.
     */
    private int weight = 0;
    /**

     The TextView that displays the user's recommended daily water intake.
     */
    private TextView waterGoalTextView;
    /**

     The TextView that displays the amount of water that has been consumed.
     */
    private TextView waterAmountTextView;
    /**

     The ImageButton that opens the weight input dialog.
     */
    private ImageButton weightButton;
    /**

     The ImageButton that increments the amount of water consumed.
     */
    private ImageButton drinkButton;

    private SharedPreferences sharedPreferences;

    /**
     * Called when the activity is starting. Responsible for initializing the activity.
     * Sets up the UI elements and retrieves the stored weight value from SharedPreferences.
     *
     * @param savedInstanceState the bundle containing the activity's previously saved state
     */
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

    /**
     * Calculates the daily water goal based on the user's weight and updates the UI.
     */
    private void calculateWaterGoal() {
        int waterGoal = weight * 50;
        waterGoalTextView.setText(waterGoal + " мл");
    }

    /**
     * Increases the water amount by 250 mL and updates the UI.
     */
    private void drinkWater() {
        waterAmount += 250;
        waterAmountTextView.setText(waterAmount + " мл");
    }

    /**
     * Shows the weight dialog to the user.
     */
    private void showWeightDialog() {
        WeightFragment weightFragment = new WeightFragment();
        weightFragment.show(getSupportFragmentManager(), "weightDialog");
    }

    /**
     * Called when the user enters their weight in the weight dialog.
     * Updates the weight value, calculates the water goal and stores the weight in SharedPreferences.
     *
     * @param weight the weight entered by the user
     */
    @Override
    public void onWeightEntered(int weight) {
        this.weight = weight;
        calculateWaterGoal();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("weight", weight);
        editor.apply();
    }

}