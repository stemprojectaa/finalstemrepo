package aa_stem.finallogscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class SetupAlarmService extends Service {

    String smsNumberToSend;
    String smsTextToSend;

    @Override
    public void onCreate() {
        Toast.makeText(this, "SertupAlarmService.onCreate()", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Toast.makeText(this, "SetupAlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "SetupAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Bundle bundle = intent.getExtras();
        smsNumberToSend = (String) bundle.getCharSequence("cellPhoneNo");
        smsTextToSend = (String) bundle.getCharSequence("smsText");

        Log.d("onstartmethod:",smsNumberToSend);

        Toast.makeText(this, "SetupAlarmService.onStart()", Toast.LENGTH_LONG).show();
        Toast.makeText(this,
                "SetupAlarmService.onStart() with \n" +
                        "smsNumberToSend = " + smsNumberToSend + "\n" +
                        "smsTextToSend = " + smsTextToSend,
                Toast.LENGTH_LONG).show();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsNumberToSend, null, smsTextToSend, null, null);

        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "SetupAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }




}
