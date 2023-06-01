package com.example.project_trpp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class NotificationFragment  extends DialogFragment{


        private Button hour1;
        private Button hour3;
        private Button hour5;
        private Button hour8;


        @SuppressLint("MissingInflatedId")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.notification, container, false);
            hour1 = view.findViewById(R.id.hour);
            hour3 = view.findViewById(R.id.hour2);
            hour5 = view.findViewById(R.id.hour3);
            hour8 = view.findViewById(R.id.hour8);

            hour1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNotificationInterval(1); // Устанавливаем период повторения 1 час
                    dismiss(); // Закрываем диалоговое окно
                }
            });

            hour3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNotificationInterval(3); // Устанавливаем период повторения 3 часа
                    dismiss(); // Закрываем диалоговое окно
                }
            });

            hour5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNotificationInterval(5); // Устанавливаем период повторения 5 часов
                    dismiss(); // Закрываем диалоговое окно
                }
            });

            hour8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNotificationInterval(8); // Устанавливаем период повторения 8 часов
                    dismiss(); // Закрываем диалоговое окно
                }
            });

            return view;
        }

        /**

         This method will be called when the fragment is visible and in focus.
         */
        @Override
        public void onResume() {

            super.onResume();
            int width = getResources().getDimensionPixelSize(R.dimen.dialog_weight_width);
            int height = getResources().getDimensionPixelSize(R.dimen.dialog_weight_height);
            getDialog().getWindow().setLayout(width, height);
        }
    private void setNotificationInterval(int hours) {
        // Создаем намерение для запуска уведомления
        Intent notificationIntent = new Intent(getActivity(), YourNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        // Получаем AlarmManager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        // Устанавливаем период повторения в выбранное количество часов
        if (alarmManager != null) {
            // Устанавливаем интервал повторения в hours * 1 час
            long repeatInterval = hours * AlarmManager.INTERVAL_HOUR;

            // Устанавливаем время первого срабатывания уведомления
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.HOUR, hours);

            // Устанавливаем направленное намерение на BroadcastReceiver
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatInterval, pendingIntent);

            Toast.makeText(getActivity(), "Уведомления будут приходить каждые " + hours + " часов.", Toast.LENGTH_SHORT).show();
        }
    }

}

