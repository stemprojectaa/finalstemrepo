package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;
import android.widget.Toast;


public class EditMedicineData extends AppCompatActivity implements View.OnClickListener{
    EditText txtHomePhone;
    EditText inputmedicineName;
    EditText txtCellPhone;
    EditText inputdosageAmount;
    public  String mypreference = "project_contents";
    public  String f_medicine_name = "medicinename";
    public  String f_dosage_amount = "dosageamount";
    public  String f_start_date = "start_date";
    public  String f_start_time = "start_time";
    public  String f_home_phone = "home_phone";
    public  String f_cell_phone = "cell_phone";
    private  String TAG = "BasicSetup";
    Context context;
    String med_name = "";
    String dose_amt = "";
    SharedPreferences sharedPreferences;
    String medicine_name = "";
    String start_Date = "";
    String start_Time = "";
    String cell_phone = "";
    String home_phone = "";
    UserSessionManagement session;
    DatabaseManagement databaseManagement;

    //Date Picker
    Button btnStartDatePicker;
    Button btnStartTimePicker;
    EditText txtStartDate;
    EditText txtStartTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine_data);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        inputmedicineName = (EditText) findViewById(R.id.medicineName) ;
        inputdosageAmount = (EditText) findViewById(R.id.dosageAmount) ;
        btnStartDatePicker = (Button) findViewById(R.id.btn_start_date);
        btnStartTimePicker = (Button) findViewById(R.id.btn_start_time);
        txtStartDate = (EditText) findViewById(R.id.startDate);
        txtStartTime = (EditText) findViewById(R.id.startTime);
        txtHomePhone = (EditText) findViewById(R.id.homephone);
        txtCellPhone = (EditText) findViewById(R.id.cellphone);

        btnStartDatePicker.setOnClickListener(this);
        btnStartTimePicker.setOnClickListener(this);
        session = new UserSessionManagement(getApplicationContext());
        databaseManagement = new DatabaseManagement(this);

        HashMap<String,String> medicalDetails = session.getMedicalDetails();
        medicine_name = medicalDetails.get(UserSessionManagement.KEY_MEDICINE_NAME);
        dose_amt = medicalDetails.get(UserSessionManagement.KEY_DOSAGE_AMOUNT);
        start_Date = medicalDetails.get((UserSessionManagement.KEY_START_DATE));
        start_Time = medicalDetails.get(UserSessionManagement.KEY_START_TIME);
        home_phone = medicalDetails.get(UserSessionManagement.KEY_HOME_PHONE);
        cell_phone = medicalDetails.get(UserSessionManagement.KEY_CELL_PHONE);

        inputmedicineName.setText("Medicine Name: "+ medicine_name);
        inputdosageAmount.setText("Dosage Amount: "+ dose_amt);
        txtStartDate.setText("Start Date: "+start_Date);
        txtStartTime.setText("Start Time: "+start_Time);
        txtHomePhone.setText("Home Phone: "+home_phone);
        txtCellPhone.setText("Cell Phone: "+cell_phone);



        Button btnHome = (Button) findViewById(R.id.btnHome);
        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditMedicineData.this);


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

    }



    @Override
    public void onClick(View v) {

        if (v == btnStartDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnStartTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtStartTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void Save(View view) {

        med_name = inputmedicineName.getText().toString();
        dose_amt = inputdosageAmount.getText().toString();
        session.saveMedValues(med_name,dose_amt,txtStartDate.getText().toString(),txtStartTime.getText().toString());
        session.savePhoneValues(txtHomePhone.getText().toString(),txtCellPhone.getText().toString());

        databaseManagement.addMedDetails( new UserDetailsAndMedicalDetails(
                session.getKeyName()
                ,session.getKeyPassword()
                ,session.getKeyEmail()
                ,med_name
                ,dose_amt
                ,txtStartDate.getText().toString()
                ,txtStartTime.getText().toString()
                ,txtCellPhone.getText().toString()
                ,txtHomePhone.getText().toString()
        ));
        Toast.makeText(getApplicationContext(),"Information is now saved.",Toast.LENGTH_LONG).show();
    }

    public void clear(View view) {
        inputmedicineName = (EditText) findViewById(R.id.medicineName);
        inputdosageAmount = (EditText) findViewById(R.id.dosageAmount);
        inputmedicineName.setText("");
        inputdosageAmount.setText("");
        txtStartDate.setText("");
        txtStartTime.setText("");
        txtHomePhone.setText("");
        txtCellPhone.setText("");

    }


    public void Get(View view) {
        inputmedicineName = (EditText) findViewById(R.id.medicineName);
        inputdosageAmount = (EditText) findViewById(R.id.dosageAmount);
        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedPreferences.contains(f_medicine_name)) {
            inputmedicineName.setText(sharedPreferences.getString(f_medicine_name, ""));
        }
        if (sharedPreferences.contains(f_dosage_amount)) {
            inputdosageAmount.setText(sharedPreferences.getString(f_dosage_amount, ""));

        }
    }



    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
