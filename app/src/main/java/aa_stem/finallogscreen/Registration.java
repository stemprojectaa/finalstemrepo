package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Registration extends AppCompatActivity {

    EditText inputusername;
    EditText inputpassword;
    EditText inputemail;
    EditText inputgender;
    EditText inputdateofbirth;

    String username;
    String password;
    String email;
    String dob;
    String gender;

    UserSessionManagement session;
    static final int READ_BLOCK_SIZE=100;
    DatabaseManagement databaseManagement;
    UserDetailsAndMedicalDetails userDetailsAndMedicalDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        session = new UserSessionManagement(getApplicationContext());
        databaseManagement = new DatabaseManagement(this);


        inputusername = (EditText) findViewById(R.id.username) ;
        inputpassword = (EditText) findViewById(R.id.password) ;
        inputemail = (EditText) findViewById(R.id.email) ;
        inputgender = (EditText) findViewById(R.id.gender) ;
        inputdateofbirth = (EditText) findViewById(R.id.dateofbirth) ;

        userDetailsAndMedicalDetails = new UserDetailsAndMedicalDetails(inputusername.getText().toString()
                                                                        ,inputpassword.getText().toString()
                                                                        ,inputemail.getText().toString()
                                                                        ,inputgender.getText().toString()
                                                                        ,inputgender.getText().toString());

        Button btnRegister = (Button) findViewById(R.id.registration);
        Button btnHome = (Button) findViewById(R.id.btnHome);

        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Registration.this);

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

    public void Save(View view) {

        username = inputusername.getText().toString();
        password = inputpassword.getText().toString();
        email = inputemail.getText().toString();
        dob = inputdateofbirth.getText().toString();
        gender = inputgender.getText().toString();

        session.createUserLoginSession(username,password,email);

        databaseManagement.addUserDetails(new UserDetailsAndMedicalDetails(username,password,email,dob,gender));


        File innerpath = getApplicationContext().getExternalFilesDir(null);
        File dir = new File(innerpath.getPath() + "/logfiles");
        Log.d("Directoryname:",dir.getPath());
        dir.mkdir();
        File file = new File(dir.getPath(),"trackinguser.txt");
        Log.d("Filepath:",file.getPath());

        try{
            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter outputwriter = new OutputStreamWriter(stream);
            outputwriter.write("This is a test.");
            outputwriter.flush();
            outputwriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        //writeToFile(view);

        Toast.makeText(getApplicationContext(),"User Login session created.",Toast.LENGTH_LONG).show();
    }

    public void writeToFile(View view){


        File innerpath = getApplicationContext().getFilesDir();
        //File outerpath = getApplicationContext().getExternalFilesDir(null);

        /*try {
            File root = Environment.getDataDirectory();
            File dir = new File(root.getPath() + "/logfiles");
            dir.mkdir();
            File file = new File(dir,"traceinfo.txt");

            Log.d("rootpath: ",root.getAbsolutePath().toString());
            Log.d("rootpath_1: ",root.getCanonicalPath().toString());
            Log.d("rootpath_2: ",root.getPath().toString());
            Log.d("rootpath_3: ",root.getCanonicalPath().toString());
            Log.d("rootpath_3: ",file.getCanonicalPath().toString());
            Log.d("filenameloc:",file.getAbsolutePath().toString());

        }catch (IOException e){
            e.printStackTrace();
        }*/




        Log.d("Innerpath: ",innerpath.getName().toString());
        //Log.d("Outer: ",outerpath.getName().toString());
        File file = new File(innerpath,"trackinguser2.txt");


        try{

            FileOutputStream stream = new FileOutputStream(file);
            OutputStreamWriter outputwriter = new OutputStreamWriter(stream);
            outputwriter.write("This is a test.");
            outputwriter.flush();
            outputwriter.close();

            //outputwriter.write("Username: "+ username);
            //outputwriter.write(("Password: "+ password));
            //outputwriter.write("Email: "+email);
            //Log.d("logwriting:",outputwriter.getEncoding().toString());
        }catch (Exception e){
            e.printStackTrace();
        }


        /*try{
            FileOutputStream fileout = openFileOutput("tracking.txt",MODE_PRIVATE);
            OutputStreamWriter outputwriter = new OutputStreamWriter(fileout);
            outputwriter.write("Username: "+ username);
            outputwriter.write(("Password: "+ password));
            outputwriter.write("Email: "+email);
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
