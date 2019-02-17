package vtc.room.a101.notificationapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(25, startWorking());
        int array[] = intent.getIntArrayExtra("intarray");
        int day = array[0];
        int hour = array[1];
        int minute = array[2];
        AlarmManager manager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent newIntent = new Intent(AlarmService.this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(AlarmService.this, 1, newIntent, 0);
        Time time = new Time();
        time.setToNow();
        long w = ((((day - time.monthDay) * 86400)+((hour - time.hour) * 3600) + ((minute - time.minute) * 60)) * 1000);
        manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + w, pendingIntent);
        return START_NOT_STICKY;
    }

    @SuppressLint("NewApi")
    private Notification startWorking() {
        CharSequence name = "name";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("bbb", name, importance);
        channel.setDescription("desc");
        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "bbb")
                .setContentTitle("WakeUp!")
                .setContentText("working")
                .setSmallIcon(R.drawable.wakeup)
                .build();
        notificationManager.createNotificationChannel(channel);
        return notification;
    }
}
