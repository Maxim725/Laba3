package com.example.laba3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Build;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private Context _context;
    private final int _notificationId = 1;
    private final String CHANNEL_ID = "CHANNEL_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = getApplicationContext();

        SubscribeToastButton();
        SubscribePushButton();
        SubscribeDialogButton();
    }

    private void SubscribeToastButton()
    {
        View layout = getLayoutInflater().inflate(R.layout.toast_lt, findViewById(R.id.toastLayout));

        Toast toast = new Toast(_context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);

        Button showToastButton = findViewById(R.id.toastButton);
        showToastButton.setOnClickListener(view -> toast.show());
    }

    private void SubscribePushButton()
    {
        CreateNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(_context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Уведомление в строке состояния")
                .setContentText("Как у вас дела?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Как у вас дела? У меня неплохо, спасибо что спросили"))
                .setAutoCancel(true);

        Button showPushButton = findViewById(R.id.pushButton);

        showPushButton.setOnClickListener(view ->
        {
            if (Build.VERSION.SDK_INT >= 33 &&
                    ContextCompat.checkSelfPermission(_context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(_context);
            notificationManager.notify(_notificationId, builder.build());
        });
    }

    private void SubscribeDialogButton()
    {
        Button showDialogButton = findViewById(R.id.dialogButton);
        showDialogButton.setOnClickListener(view -> new ToastDialogFragment().show(getSupportFragmentManager(), "Dialog"));
    }

    private void CreateNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}