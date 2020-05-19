package com.example.apnabazaar;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_1_ID = "Winner";
    public static final String CHANNEL_2_ID = "SELLER_PRODUCT";


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Check Winner",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Check if you are winner");

            NotificationChannel channe2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Your Product auction time ended",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channe2.setDescription("Check the winner for your product");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channe2);

        }
    }
}
