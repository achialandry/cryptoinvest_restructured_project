package info.lansachia.cryptoinvest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 *
 * This Class manages and controls the logic to how notifications will be sent
 * the channels for notifications, text, tone, vibrations for notifications
 *
 * @author  Landry Achia
 */

public class NotificationHelper extends ContextWrapper {
    /**
     * Channel Id for different notification setting for this notification
     */
    private static final String CHANNEL_ID = "notification_channel_crypt_invest3_3_p";

    /**
     * notification manager variable
     */
    private NotificationManager mNotificationManager;

    /**
     * request code for notification when eventually hosted
     */
    public static final int REQUEST_CODE = 0;


    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param context The application context
     */
    public NotificationHelper(Context context) {
         super(context);
         createChannels();
    }

    /**
     * method to create different channels for notifications
     */
    public void createChannels(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            CharSequence channel_name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            // Create the channel object with the unique ID CHANNEL_ID
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channel_name,importance);
            // Configure the channel's initial settings

            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            channel.setShowBadge(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            // Submit the notification channel object to the notification manage
            getNotificationManager().createNotificationChannel(channel);
        }

    }

    /**
     * Build notification with desired configurations
     *
     */
    public NotificationCompat.Builder getNotificationBuilder(String title, String body) {

        //Pass the notification channel’s ID as the second argument//
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                //Set the notification’s icon, which we’ll be creating later
                .setSmallIcon(R.drawable.logo)

                //Set the notification’s title, which in this instance will be the contents be static like Crypto Invest
                .setContentTitle(title)
                //Set the notification’s body text
                .setContentText(body)

                .setAutoCancel(true);

    }

    /**
     * Send  notifications to the NotificationManager system service
     */

    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            // Register the channel with the system
            mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    /**
     * notify method to push notifications
     * @param id of the notification
     * @param notification notification builder
     */
    public void notify(int id, Notification.Builder notification) {
        getNotificationManager().notify(id, notification.build());
    }
}
