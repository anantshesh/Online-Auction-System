package com.example.apnabazaar.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.apnabazaar.auctionfolder.AuctioneerBiddingScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseMessaging extends FirebaseMessagingService {

    private static final String ADMIN_CHANNEL_ID = "admin_channel";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        String savedCurrentUser = sp.getString("Current_USERID", "None");

        String notificationType = remoteMessage.getData().get("notificationType");
        if (notificationType.equals("BidNotification")){

            String sender = remoteMessage.getData().get("sender");
            String pId = remoteMessage.getData().get("pid");
            String pTitle = remoteMessage.getData().get("pTitle");
            String buName = remoteMessage.getData().get("buName");

            if (!sender.equals(savedCurrentUser)){
                //showPostNotification("" +pId, "" + pTitle, "" + buName);
            }

        }

       /* else if (notificationType.equals("")){
String sent = remoteMessage.getData().get("sent");
        String user = remoteMessage.getData().get("user");
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (fuser != null && sent.equals(fuser.getUid())){
            if (!savedCurrentUser.equals(user)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    sendOAndAboveNotification(remoteMessage);
                }
                else {
                    sendNormalNotification(remoteMessage);
                }
            }
        }
    }
        }*/
        String sent = remoteMessage.getData().get("sent");
        String user = remoteMessage.getData().get("user");
        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (fuser != null && sent.equals(fuser.getUid())){
            if (!savedCurrentUser.equals(user)){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    sendOAndAboveNotification(remoteMessage);
                }
                else {
                    sendNormalNotification(remoteMessage);
                }
            }
        }
    }

    private void showPostNotification(String pId, String pTitle, String buName) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE );

        int notificationId = new Random().nextInt(3000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setupBidNotification(notificationManager);
        }

        Intent intent = new Intent(this, AuctioneerBiddingScreen.class);
        intent.putExtra("psotId", pId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

       // Bitmap largeIcon = BitmapFactory.decodeByteArray(getResources(), R.drawable.firebase_logo)

        //Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "" + ADMIN_CHANNEL_ID);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupBidNotification(NotificationManager notificationManager) {

        CharSequence channelName = "New Notification";
        String channelDescription = "Device to device Bid Notification";

        NotificationChannel adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        adminChannel.setDescription(channelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager!= null){
           notificationManager.createNotificationChannel(adminChannel);
        }


    }


    private void sendNormalNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, AuctioneerBiddingScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("postId", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this,  i ,intent ,PendingIntent.FLAG_ONE_SHOT);
        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defSoundUri)
                .setContentIntent(pIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int j = 0;
        if (j>0){
            j=i;

            notificationManager.notify(j, builder.build());
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOAndAboveNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int i = Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent = new Intent(this, AuctioneerBiddingScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("postId", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this,  i ,intent ,PendingIntent.FLAG_ONE_SHOT);
        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoAndAboveNotification notification1 = new OreoAndAboveNotification(this);
        Notification.Builder builder = notification1.getONotifications(title, body, pIntent, defSoundUri, icon);



        int j = 0;
        if (j>0){
            j=i;

        }
            notification1.getManager().notify(j, builder.build());





    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            updateToken(s);
        }
    }

    private void updateToken(String tokenRefresh) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token = new Token(tokenRefresh);
        ref.child(user.getUid()).setValue(token);

    }
}


