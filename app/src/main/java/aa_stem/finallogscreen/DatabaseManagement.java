package aa_stem.finallogscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManagement extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MedicalManagement";

    // Contacts table name
    private static final String TABLE_MEDICAL_DETAILS = "UserMedicalDetails";
    private static final String TABLE_USER_DETAILS = "UserDetails";

    // Contacts Table Columns names
    private static final String KEY_USER = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MEDICINE_NAME = "medicinename";
    private static final String KEY_DOSAGE = "dosageamount";
    private static final String KEY_START_DATE = "startdate";
    private static final String KEY_START_TIME = "starttime";
    private static final String KEY_CELL_PHONE = "cellphone";
    private static final String KEY_HOME_PHONE = "homephone";
    private static final String KEY_DOB = "dateofbirth";
    private static final String KEY_GENDER = "gender";


    public DatabaseManagement(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //context.deleteDatabase(DATABASE_NAME);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("method called:",DATABASE_NAME);

        String CREATE_MEDICAL_TABLE = "CREATE TABLE " + TABLE_MEDICAL_DETAILS + "("
                + KEY_USER + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_MEDICINE_NAME + " TEXT,"
                + KEY_DOSAGE + " TEXT,"
                + KEY_START_DATE + " TEXT,"
                + KEY_START_TIME + " TEXT,"
                + KEY_CELL_PHONE + " TEXT,"
                + KEY_HOME_PHONE + " TEXT"
                + ")";
        Log.d("MEdTable:",CREATE_MEDICAL_TABLE);
        db.execSQL(CREATE_MEDICAL_TABLE);


        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER_DETAILS + "("
                + KEY_USER + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_DOB + " TEXT,"
                + KEY_GENDER + " TEXT"
                + ")";
        Log.d("UserTable:",CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICAL_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DETAILS);

        onCreate(db);

    }


    // Adding new medical record
    public void addUserDetails(UserDetailsAndMedicalDetails userDetailsAndMedicalDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("useradded:", userDetailsAndMedicalDetails.getUsername());
        ContentValues values = new ContentValues();
        values.put(KEY_USER, userDetailsAndMedicalDetails.getUsername()); // Contact Name
        values.put(KEY_PASSWORD, userDetailsAndMedicalDetails.getPassword());
        values.put(KEY_EMAIL, userDetailsAndMedicalDetails.getEmail());
        values.put(KEY_DOB, userDetailsAndMedicalDetails.getDob());
        values.put(KEY_GENDER, userDetailsAndMedicalDetails.getGender());

        // Inserting Row
        db.insert(TABLE_USER_DETAILS, null, values);
        //db.close(); // Closing database connection
    }

    // Adding new medical record
    public void addMedDetails(UserDetailsAndMedicalDetails userDetailsAndMedicalDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER, userDetailsAndMedicalDetails.getUsername()); // Contact Name
        values.put(KEY_PASSWORD, userDetailsAndMedicalDetails.getPassword());
        values.put(KEY_EMAIL, userDetailsAndMedicalDetails.getEmail());
        values.put(KEY_MEDICINE_NAME, userDetailsAndMedicalDetails.getMedicinename());
        values.put(KEY_DOSAGE, userDetailsAndMedicalDetails.getDosageamount());
        values.put(KEY_START_DATE, userDetailsAndMedicalDetails.getStartdate());
        values.put(KEY_START_TIME, userDetailsAndMedicalDetails.getStarttime());
        values.put(KEY_CELL_PHONE, userDetailsAndMedicalDetails.getCellphone());
        values.put(KEY_HOME_PHONE, userDetailsAndMedicalDetails.getHomePhone());

        // Inserting Row
        db.insert(TABLE_MEDICAL_DETAILS, null, values);
        //db.close(); // Closing database connection
    }


    public boolean isUserValid(String username){


        try
        {
            SQLiteDatabase db=this.getReadableDatabase();

            Log.d("username is: ",username);
            Cursor cursor=db.rawQuery("SELECT * "
                                                    +" FROM "
                                                    +TABLE_USER_DETAILS
                                                    +" WHERE "
                                                    +KEY_USER+"='"+username+"'",null);

            Log.d("Query is:",db.toString());
            if (cursor.moveToFirst())
            {
                db.close();
                Log.d("Record  Already Exists", "Table is:"+TABLE_USER_DETAILS+" ColumnName:"+username);
                return true;//record Exists

            }
            Log.d("User does not exist  ", "Table is:"+TABLE_USER_DETAILS+" ColumnName:"+KEY_USER+" Column Value:"+username);
            db.close();
        }
        catch(Exception errorException)
        {
            Log.d("Exception occured", "Exception occured "+errorException);
            // db.close();
        }
        return false;
    }


    // Getting single medical record
    public UserDetailsAndMedicalDetails getMedicalRecord(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MEDICAL_DETAILS, new String[]
                        { KEY_USER,
                        KEY_PASSWORD
                                , KEY_EMAIL
                                ,KEY_MEDICINE_NAME
                                ,KEY_DOSAGE
                                ,KEY_START_DATE
                                ,KEY_START_TIME
                                ,KEY_CELL_PHONE
                                ,KEY_HOME_PHONE}, KEY_USER + "=?",
                new String[] { String.valueOf(username) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        UserDetailsAndMedicalDetails userDetailsAndMedicalDetails = new UserDetailsAndMedicalDetails(
                cursor.getString(0)
                ,cursor.getString(1)
                ,cursor.getString(2)
                ,cursor.getString(3)
                ,cursor.getString(4)
                ,cursor.getString(5)
                ,cursor.getString(6)
                ,cursor.getString(7)
                ,cursor.getString(8));
        // return contact
        return userDetailsAndMedicalDetails;
    }


    // Getting All medical records
    public List<UserDetailsAndMedicalDetails> getAllMedicalHistory() {
        List<UserDetailsAndMedicalDetails> contactList = new ArrayList<UserDetailsAndMedicalDetails>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MEDICAL_DETAILS;
        Log.d("selectQuery is:",selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDetailsAndMedicalDetails userDetailsAndMedicalDetails = new UserDetailsAndMedicalDetails();
                userDetailsAndMedicalDetails.setUsername(cursor.getString(0));
                userDetailsAndMedicalDetails.setPassword(cursor.getString(1));
                userDetailsAndMedicalDetails.setEmail(cursor.getString(2));
                userDetailsAndMedicalDetails.setMedicinename(cursor.getString(3));
                userDetailsAndMedicalDetails.setDosageamount(cursor.getString(4));
                userDetailsAndMedicalDetails.setStartdate(cursor.getString(5));
                userDetailsAndMedicalDetails.setStarttime(cursor.getString(6));
                userDetailsAndMedicalDetails.setCellphone(cursor.getString(7));
                userDetailsAndMedicalDetails.setHomephone(cursor.getString(8));

                // Adding contact to list
                contactList.add(userDetailsAndMedicalDetails);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


}
