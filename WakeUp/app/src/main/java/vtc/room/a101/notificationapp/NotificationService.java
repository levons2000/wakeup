package vtc.room.a101.notificationapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends Service {
    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    @SuppressLint("NewAPI")
    public int onStartCommand(Intent intent, int flags, int startId) {
        CharSequence name = "channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("id", name, importance);
        channel.setDescription("desc");
        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "id")
                .setContentTitle("WakeUp!")
                .setContentText("Time to waking up :)")
                .setSmallIcon(R.drawable.wakeup)
                .build();
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(0, notification);
        return START_NOT_STICKY;
    }
}
