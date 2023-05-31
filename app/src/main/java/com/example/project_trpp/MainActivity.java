package com.example.project_trpp;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**

 The MainActivity class is responsible for controlling the main activity of the application.
 It contains methods for calculating water goal, tracking water intake and displaying weight dialog.
 @author  Zharkova Svetlana, Skityova Anastasia
 @version 1.0
 @since 2023-04-05
 */
public class MainActivity extends AppCompatActivity implements WeightFragment.WeightDialogListener, WaterAddingFragment.WaterAddingDialogListener {

    private TextView date;

    private int waterGoal;
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


    private ImageButton ml100btn;
    private ImageButton ml200btn;
    private ImageButton ml300btn;
    private ImageButton ml500btn;
    private ImageButton deletebtn;
    /**

     The ImageButton that increments the amount of water consumed.
     */
    private ImageButton drinkButton;

    private SharedPreferences sharedPreferences;
    private CircularFillableLoaders circularFillableLoaders;
    private String savedDay;

    @Override
    protected void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        savedDay = sharedPreferences.getString("day", currentDate);
        // Если текущий день отличается от сохраненного дня, сбрасываем waterAmount
        if (!currentDate.equals(savedDay)) {
            waterAmount = 0;
            editor.putString("day", currentDate);
            editor.putInt("waterAmount", waterAmount);
            editor.apply();
        } else {
            // Иначе получаем сохраненное значение waterAmount
            waterAmount = sharedPreferences.getInt("waterAmount", 0);
        }
        date = findViewById(R.id.curr_date);
        date.setText(currentDate);

        circularFillableLoaders= (CircularFillableLoaders)findViewById(R.id.cfl);
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

    }

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
        setContentView(R.layout.activity1);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        String currentDate = dateFormat.format(calendar.getTime());
        // Инициализируем savedDay из SharedPreferences
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        savedDay = sharedPreferences.getString("day", currentDate);


        // Если текущий день отличается от сохраненного дня, сбрасываем waterAmount
        if (!currentDate.equals(savedDay)) {
            waterAmount = 0;
            editor.putString("day", currentDate);
            editor.putInt("waterAmount", waterAmount);
            editor.apply();
        } else {
            // Иначе получаем сохраненное значение waterAmount
            waterAmount = sharedPreferences.getInt("waterAmount", 0);
        }




        date = findViewById(R.id.curr_date);


        date.setText(currentDate);

       // waterGoalTextView = findViewById(R.id.water_goal_text_view);
        waterAmountTextView = findViewById(R.id.textView7);
        weightButton = findViewById(R.id.settings_btn);
        drinkButton = findViewById(R.id.drink_btn);
        ml100btn = findViewById(R.id.bnt_100ml);
        ml200btn = findViewById(R.id.btn_200ml);
        ml300btn = findViewById(R.id.btn_300ml);
        ml500btn = findViewById(R.id.btn_500ml);
        deletebtn = findViewById(R.id.deleteWater);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        weight = sharedPreferences.getInt("weight", 0);
        if (weight > 0) {
            calculateWaterGoal();
        } else {
            showWeightDialog();
        }
        circularFillableLoaders= (CircularFillableLoaders)findViewById(R.id.cfl);
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterAmount = 0;
                waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
                float div = (float)waterAmount/(float)waterGoal;
                float inPercent = div * 100;
                int percent = (int)Math.round(inPercent);
                percent = 100 - percent;
                circularFillableLoaders.setProgress(percent);
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
        drinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaterAddingDialog();
            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWeightDialog();
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
        ml100btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkWater100ml();
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
        ml200btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkWater200ml();
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
        ml300btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkWater300ml();
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
        ml500btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinkWater500ml();
                editor.putString("day", currentDate);
                editor.putInt("waterAmount", waterAmount);
                editor.apply();
            }
        });
    }

    private void showWaterAddingDialog() {
        WaterAddingFragment waterAddingFragment = new WaterAddingFragment();
        waterAddingFragment.show(getSupportFragmentManager(), "wateraddingdialog");
    }

    /**
     * Calculates the daily water goal based on the user's weight and updates the UI.
     */
    private void calculateWaterGoal() {
        waterGoal = weight * 25;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
    }

    /**
     * Increases the water amount by 250 mL and updates the UI.
     */
    private void drinkWater() {
        waterAmount += 250;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

    }
    private void drinkWater100ml() {
        waterAmount += 100;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

    }
    private void drinkWater200ml() {
        waterAmount += 200;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

    }
    private void drinkWater300ml() {
        waterAmount += 300;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

    }
    private void drinkWater500ml() {
        waterAmount += 500;
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);

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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentDate = dateFormat.format(calendar.getTime());
        editor.putInt("weight", weight);
        editor.apply();
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);
        editor.putString("day", currentDate);
        editor.putInt("waterAmount", waterAmount);
        editor.apply();
    }
    @Override
    public void onMlEntered(int ml) {
        waterAmount += ml;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String currentDate = dateFormat.format(calendar.getTime());
        waterAmountTextView.setText(waterAmount+"/"+waterGoal +" мл");
        float div = (float)waterAmount/(float)waterGoal;
        float inPercent = div * 100;
        int percent = (int)Math.round(inPercent);
        percent = 100 - percent;
        circularFillableLoaders.setProgress(percent);
        editor.putString("day", currentDate);
        editor.putInt("waterAmount", waterAmount);
        editor.apply();
    }

}