package com.example.project_trpp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class YourNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Проверьте флаг повторяющегося уведомления
        boolean isRepeating = intent.getBooleanExtra("repeating", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Создаем объект канала уведомлений
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            // Настройка параметров канала уведомлений
            channel.setDescription("Channel Description");
            // Добавляем канал в менеджер уведомлений
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Создайте и настройте уведомление
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable._423612_1)
                .setContentTitle("Water tracker")
                .setContentText("Пора пить воду!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Отправьте уведомление с помощью NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());

        // Если это повторяющееся уведомление, вы можете обновить время следующего уведомления
        if (isRepeating) {
            // Обновите время следующего уведомления
            long intervalMillis = AlarmManager.INTERVAL_HOUR;
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + intervalMillis, pendingIntent);
        }
    }
}
