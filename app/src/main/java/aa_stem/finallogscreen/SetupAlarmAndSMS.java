package aa_stem.finallogscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupAlarmAndSMS extends AppCompatActivity {

    String cellPhoneNo;
    String smsText;
    String start_Date;
    String start_Time;
    private PendingIntent pendingIntent;
    UserDetailsAndMedicalDetails userDetailsAndMedicalDetails;
    DatabaseManagement databaseManagement;
    UserSessionManagement userSessionManagement;
    long millisecondTime;
    Calendar calendar;
    String combineTime;
    int mYear,mMonth,mDay,mHour,mMinute;
    Intent myIntent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_alarm_and_sms);
        userDetailsAndMedicalDetails = new UserDetailsAndMedicalDetails();
        userSessionManagement = new UserSessionManagement(getApplicationContext());


        Button btnSchedule = (Button)findViewById(R.id.btnSchedule);
        Button btnTakeImage = (Button) findViewById(R.id.btnTakeImage);
        Button btnHome = (Button) findViewById(R.id.btnHome);

        userSessionManagement = new UserSessionManagement(getApplicationContext());

        HashMap<String,String> medicalDetails = userSessionManagement.getMedicalDetails();
        start_Date = medicalDetails.get((UserSessionManagement.KEY_START_DATE));
        start_Time = medicalDetails.get(UserSessionManagement.KEY_START_TIME);
        cellPhoneNo = medicalDetails.get(UserSessionManagement.KEY_CELL_PHONE);

        Log.d("Cell:",cellPhoneNo);
        combineTime = start_Date + " " + start_Time;
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");

        try {
            Date date = dateFormat.parse(combineTime);
            calendar.setTime(date);
            Log.d("date now:",date.toString());
            millisecondTime = calendar.getTimeInMillis();
            //Add 5 seconds
            millisecondTime = millisecondTime + 50*1000;
            Log.d("Milliseconds is :", Long.toString(millisecondTime));
        }catch (ParseException e){
            e.getMessage();
        }


        btnSchedule.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {

                smsText = "Sending Test Message";

                myIntent = new Intent(SetupAlarmAndSMS.this, SetupAlarmService.class);

                Bundle bundle = new Bundle();
                bundle.putCharSequence("cellPhoneNo", cellPhoneNo);
                bundle.putCharSequence("smsText", smsText);
                myIntent.putExtras(bundle);

                startService(myIntent);

                pendingIntent = PendingIntent.getService(SetupAlarmAndSMS.this, 0, myIntent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, millisecondTime, pendingIntent);

                Toast.makeText(SetupAlarmAndSMS.this,
                        "Start Alarm with \n" +
                                "smsNumber = " + cellPhoneNo + "\n" +
                                "smsText = " + smsText,
                        Toast.LENGTH_LONG).show();

                /*pendingIntent = PendingIntent.getService(SetupAlarmAndSMS.this, 0, myIntent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, millisecondTime, pendingIntent);
                Toast.makeText(SetupAlarmAndSMS.this,
                        "Start Alarm with \n" +
                                "smsNumber = " + cellPhoneNo + "\n" +
                                "smsText = " + smsText,
                        Toast.LENGTH_LONG).show();
                        */



            }});
    }

}
