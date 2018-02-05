package aa_stem.finallogscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    UserSessionManagement session;
    String username;
    String password;
    String email;
    DatabaseManagement databaseManagement;
    EditText editTextUser;
    EditText editTextPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //session = new UserSessionManagement(getApplicationContext());
        databaseManagement = new DatabaseManagement(this);
        editTextUser = (EditText) findViewById(R.id.username);
        editTextPwd = (EditText) findViewById(R.id.password);


        //HashMap<String,String> userInfo = session.getUserDetails();
        //username = userInfo.get(UserSessionManagement.KEY_NAME);
        //password = userInfo.get(UserSessionManagement.KEY_PASSWORD);
        //email = userInfo.get((UserSessionManagement.KEY_EMAIL));

        Button btnLogin = (Button) findViewById(R.id.login);
        Button btnHome = (Button) findViewById(R.id.btnHome);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                username = editTextUser.getText().toString();
                password = editTextPwd.getText().toString();

                Log.d("InsideLogin username:", username);
                Log.d("InsideLogin password:", password);

                boolean recordExists= databaseManagement.isUserValid(username);
                if(recordExists)
                {
                    Toast.makeText(getApplicationContext(), "User exists, logging in now..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), OptionsScreen.class));
                }else{
                    Toast.makeText(getApplicationContext(),"User not registered. Please register before login.",Toast.LENGTH_LONG).show();
                }


                /*if (username!=null && password!=null){
                    startActivity(new Intent(getApplicationContext(), OptionsScreen.class));
                }else {
                    Toast.makeText(getApplicationContext(),"User not registered. Please register before login.",Toast.LENGTH_LONG).show();
                }*/

            }
        });


        //button click event
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);

                alertDialog.setTitle("Home Alert");
                alertDialog.setMessage("Are you sure you want to continue..");


                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                });

                alertDialog.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(),"You Clicked NO, Please validate username before login.",Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        });

                alertDialog.show();
            }
        });


    }
}
