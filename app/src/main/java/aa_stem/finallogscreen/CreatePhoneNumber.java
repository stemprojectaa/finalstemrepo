package aa_stem.finallogscreen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;

import java.util.Calendar;
import java.util.Date;
import aa_stem.finallogscreen.AlarmReceiver;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import android.view.View.OnClickListener;
import android.telephony.SmsManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;



public class CreatePhoneNumber extends AppCompatActivity {
    TextView viewpromptMedName;
    TextView viewpromptDoseAmt;
    TextView viewpromptStartDate;
    EditText inputhomehome;
    TextView viewpromptStartTime;
    EditText inputcellphone;
    public static  final String f_home_phone = "homephone";
    public static final String f_cell_phone = "cellphone";
    private static final String TAG = "CreatePhoneNumber";
    String medicine_name = "";
    String dose_amt = "";
    String start_Date = "";
    String start_Time = "";
    Date dateTime;
    String combineTime;
    String home_phone;
    String cell_phone;
    String phoneNo;
    String homePhoneNo;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    UserSessionManagement session;
    DatabaseManagement databaseManagement;
    UserDetailsAndMedicalDetails userDetailsAndMedicalDetails;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphonenumbers);
        inputhomehome = (EditText) findViewById(R.id.homephone) ;
        inputcellphone = (EditText) findViewById(R.id.cellphone) ;
        viewpromptMedName = (TextView) findViewById(R.id.medicineName);
        viewpromptDoseAmt = (TextView) findViewById(R.id.dosageAmount);
        viewpromptStartDate = (TextView) findViewById(R.id.startDate);
        viewpromptStartTime = (TextView) findViewById(R.id.startTime);

        session = new UserSessionManagement(getApplicationContext());
        databaseManagement = new DatabaseManagement(this);
        userDetailsAndMedicalDetails = new UserDetailsAndMedicalDetails();

        HashMap<String,String> medicalDetails = session.getMedicalDetails();
        medicine_name = medicalDetails.get(UserSessionManagement.KEY_MEDICINE_NAME);
        dose_amt = medicalDetails.get(UserSessionManagement.KEY_DOSAGE_AMOUNT);
        start_Date = medicalDetails.get((UserSessionManagement.KEY_START_DATE));
        start_Time = medicalDetails.get(UserSessionManagement.KEY_START_TIME);

        viewpromptMedName.setText("Medicine Name: "+ medicine_name);
        viewpromptDoseAmt.setText("Dosage Amount: "+ dose_amt);
        viewpromptStartDate.setText("Start Date: "+start_Date);
        viewpromptStartTime.setText("Start Time: "+start_Time);


        Button btnHome = (Button) findViewById(R.id.btnHome);
        Button btnImage = (Button) findViewById(R.id.btnTakeImage);
        Button btnMyScheudule = (Button) findViewById(R.id.btnMySchedule);

        /*
        Button btnSchedule = (Button) findViewById(R.id.btnSchedule);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        btnSchedule.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                phoneNo = inputcellphone.getText().toString();
                homePhoneNo = inputhomehome.getText().toString();
                Log.v("phone:",phoneNo);
                String sms = "Your schedule has now been set. You will be reminded when you are ready to start taking medication.";


                String[] fields = start_Time.split(":");
                String hour = fields[0];
                String min = fields[1];
                Log.v("Hour:",hour);
                Log.v("Minute:",min);
                combineTime = start_Date + " " + start_Time;
                Log.v("combineTime",combineTime);
                try{
                    dateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(combineTime.toString());
                    Log.v("DateTime:",dateTime.toString());
                }catch (ParseException e){
                    Log.v("Exception Parsing Time",e.getMessage());
                }

                Long time = dateTime.getTime();
                Log.v("TimeAlert: ",time.toString());


                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();

                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), notification);
                    mediaPlayer.start();

                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getAnchorMediaTimeUs()));
                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getAnchorSytemNanoTime()));
                    Log.d("Timestamp::::",String.valueOf(mediaPlayer.getTimestamp().getMediaClockRate()));



                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(5000);
                                if(mediaPlayer.isPlaying()){
                                    mediaPlayer.stop();
                                }
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    });
                    th.start();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreatePhoneNumber.this);
                alertDialog.setTitle("SMS Alert");
                alertDialog.setMessage("Alarm Set. Please continue..");

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int which) {
                            Intent i = new Intent(getApplicationContext(),SentSMSActivity.class);
                            startActivity(i);
                        }
                    });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"You Clicked NO, Please validate Input and Continue.",Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        });
                    alertDialog.show();


            }
        });
        */

        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreatePhoneNumber.this);

                alertDialog.setTitle("Create Alert");
                alertDialog.setMessage("Are you sure you want to continue..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),OptionsScreen.class);
                        startActivity(i);
                    }
                });

                alertDialog.show();
            }
        });


        btnMyScheudule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreatePhoneNumber.this);

                alertDialog.setTitle("Create Alert");
                alertDialog.setMessage("Please choose options in taking your medicines here..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),SentSMSActivity.class);
                        //Intent i = new Intent(getApplicationContext(),SetupAlarmAndSMS.class);
                        startActivity(i);
                    }
                });

                alertDialog.show();
            }
        });


       /*Changed this on 2-7-2018 to go directly to SentSMSActivity Screen
        //button click event
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreatePhoneNumber.this);

                alertDialog.setTitle("Create Alert");
                alertDialog.setMessage("Here, you will be taking an Image of the Medicine. Please Continue..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),CaptureImage.class);
                        //Intent i = new Intent(getApplicationContext(),SetupAlarmAndSMS.class);
                        startActivity(i);
                    }
                });

                alertDialog.show();
            }
        });
        */


    }

    public void Save(View view) {
        home_phone = inputhomehome.getText().toString();
        cell_phone = inputcellphone.getText().toString();
        session.savePhoneValues(home_phone,cell_phone);

        userDetailsAndMedicalDetails.setCellphone(cell_phone);
        userDetailsAndMedicalDetails.setStartdate(start_Date);
        userDetailsAndMedicalDetails.setStarttime(start_Time);

        databaseManagement.addMedDetails( new UserDetailsAndMedicalDetails(
                                        session.getKeyName()
                                        ,session.getKeyPassword()
                                        ,session.getKeyEmail()
                                        ,medicine_name
                                        ,dose_amt
                                        ,start_Date
                                        ,start_Time
                                        ,cell_phone
                                        ,home_phone
                                        ));
        Toast.makeText(getApplicationContext(),"Information is now saved.",Toast.LENGTH_LONG).show();
    }

    public void clear(View view) {
        inputhomehome = (EditText) findViewById(R.id.homephone);
        inputcellphone = (EditText) findViewById(R.id.cellphone);
        inputhomehome.setText("");
        inputcellphone.setText("");

    }


    public void scheduleAlarm(View V)
    {
        // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time,
        // we fetch  the current time in milliseconds and added 1 day time
        // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day



        combineTime = start_Date + " " + start_Time;
        Log.v("combineTime",combineTime);
        try{
            dateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm").parse(combineTime.toString());
            Log.v("DateTime:",dateTime.toString());
        }catch (ParseException e){
            Log.v("Exception Parsing Time",e.getMessage());
        }

        Long time = dateTime.getTime();
        Log.v("TimeAlert: ",time.toString());

        // create an Intent and set the class which will execute when Alarm triggers, here we have
        // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
        // alarm triggers and
        //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);

        // create the object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm is currently scheduled for:", Toast.LENGTH_LONG).show();

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}


// When IntakeCt = IntakeCt + 1 Then...
//     In a Scroll View, Make A Layout (Linear) with following Text Views:
//       MedName, DoseAmt, TimeTaken
