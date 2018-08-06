package com.example.test.programmingmama.Utils.Service;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.test.programmingmama.MainActivity;
import com.example.test.programmingmama.R;
import com.example.test.programmingmama.Utils.PrefManager;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyJobService extends JobService {
    PrefManager prefManager;
    BackgroundJob backgroundJob;
    String getTime;
    private static final int NOTIFICATION_ID = 1;
    private NotificationManager alarmNotificationManager;



    @Override
    public boolean onStartJob(final JobParameters job) {
        prefManager = new PrefManager(getApplicationContext());


        // Do some work here
        backgroundJob = new BackgroundJob(){
            @Override
            protected void onPostExecute(Integer s) {


                if(s >2){
                   MediaPlayer
                           .create(getApplicationContext(), R.raw.right)
                           .start();
                    showNotification("Inactive for the First time: Journey of a programmer begins with small learning everyday. "+s);

                }else {
                    Toast.makeText(getApplicationContext(), "Time "+s, Toast.LENGTH_LONG).show();
                }
                //  showNotification();
                Toast.makeText(getApplicationContext(), "Message From Background Task"+s, Toast.LENGTH_LONG).show();
                jobFinished(job,false);
            }
        };
        backgroundJob.execute();
        return true; // Answers the question: "Is there still work going on?"
    }
    public void showNotification(String s){


        alarmNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        //get pending intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //Create notification
        NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                this)
                .setContentTitle("Alarm")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(s).setAutoCancel(true);
        alamNotificationBuilder.setContentIntent(contentIntent);

        //notiy notification manager about new notification
        alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());



//        Intent notificationIntent = new Intent(getApplicationContext(),MainActivity.class);
//        notificationIntent.setAction(Intent.ACTION_MAIN);
//        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        PendingIntent contentIntent = PendingIntent.getActivity(
//                getApplicationContext(),
//                0,
//                notificationIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        Notification notification  = new Notification.Builder(getApplicationContext())
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText("Notification Test")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(contentIntent)
//                .setAutoCancel(true)
//                .build();
//        startForeground(NOTIFICATION_ID,notification);
    }


    @Override
    public boolean onStopJob(JobParameters job) {
        return false; // Answers the question: "Should this job be retried?"
    }


    public class BackgroundJob extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            getTime=prefManager.getTime();

            SimpleDateFormat spimdata = new SimpleDateFormat("HH:mm:ss");
            String timeStamp =spimdata.format(Calendar.getInstance().getTime());

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = spimdata.parse(timeStamp);
                d2 = spimdata.parse(getTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = d1.getTime() -  d2.getTime();
            int diffMinutes = (int) (diff / (60 * 1000));

            return diffMinutes;

        }
    }
}