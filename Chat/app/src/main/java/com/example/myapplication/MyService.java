package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.MessagesByID;
import com.example.myapplication.entities.Sender;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    @Override
    public void onMessageSent(@NonNull String s) {
        super.onMessageSent(s);

    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        MutableLiveData<String> contacts = SingletonFirebase.getFirebaseContactInstance();
        MutableLiveData<MessagesByID> messages = SingletonFirebase.getFirebaseMessageInstance();
        if (message.getNotification() != null) {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
        Map<String, String> data = message.getData();
        if (data.get("action").equals("add_contact")) {
            contacts.postValue(message.getNotification().getBody());
        } else if (data.get("action").equals("send_message")) {
            String chatID = data.get("chatID");
            String senderUsername = data.get("senderUsername");
            String senderDisplayName = data.get("senderDisplayName");
            String receiver = data.get("receiver");
            String msgID = data.get("msgID");
            String date = data.get("date");
            messages.postValue(new MessagesByID(msgID, date, new Sender(senderUsername),
                    message.getNotification().getBody()));
        }
    }
    private String id;
    private String created;
    private Sender sender;
    private String content;

    private String chatID;
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "My channel", importance);
            channel.setDescription("Demo channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}

