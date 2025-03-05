package com.example.myeduapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class QuizReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Trigger the notification
        NotificationHelper.sendNotification(
                context,
                "Quiz Reminder",
                "Don’t forget to complete your daily quiz!"
        );
    }
}
