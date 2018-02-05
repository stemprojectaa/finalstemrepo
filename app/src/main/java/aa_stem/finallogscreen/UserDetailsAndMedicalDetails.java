package aa_stem.finallogscreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserDetailsAndMedicalDetails{


    //private variables
    String username;
    String password;
    String email;
    String dob;
    String gender;
    String medicinename;
    String dosageamount;
    String startdate;
    String starttime;
    String cellphone;
    String homephone;


    // Empty constructor
    public UserDetailsAndMedicalDetails(){

    }
    // constructor
    public UserDetailsAndMedicalDetails(String username, String password, String email,String dob,String gender){
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob  = dob;
        this.gender = gender;
    }

    // constructor
    public UserDetailsAndMedicalDetails(String medicinename, String dosageamount,String startdate, String starttime){
        this.medicinename = medicinename;
        this.dosageamount = dosageamount;
        this.startdate = startdate;
        this.starttime = starttime;
    }

    public UserDetailsAndMedicalDetails(String medicinename, String dosageamount,String startdate, String starttime, String cellphone, String homephone){
        this.medicinename = medicinename;
        this.dosageamount = dosageamount;
        this.startdate = startdate;
        this.starttime = starttime;
        this.cellphone = cellphone;
        this.homephone = homephone;
    }

    public UserDetailsAndMedicalDetails(String username, String password, String email,String medicinename, String dosageamount,String startdate, String starttime, String cellphone, String homephone){
        this.username = username;
        this.password = password;
        this.email = email;
        this.medicinename = medicinename;
        this.dosageamount = dosageamount;
        this.startdate = startdate;
        this.starttime = starttime;
        this.cellphone = cellphone;
        this.homephone = homephone;
    }


    // getting ID
    public String getUsername(){
        return this.username;
    }

    // setting id
    public void setUsername(String username){
        this.username = username;
    }

    // getting ID
    public String getPassword(){
        return this.password;
    }

    // setting id
    public void setPassword(String password){
        this.password = password;
    }

    // getting ID
    public String getEmail(){
        return this.email;
    }

    // setting id
    public void setEmail(String email){
        this.email = email;
    }

    // getting ID
    public String getDob(){
        return this.dob;
    }

    // setting id
    public void setDob(String dob){
        this.dob = dob;
    }

    // getting ID
    public String getGender(){
        return this.gender;
    }

    // setting id
    public void setGender(String gender){
        this.gender = gender;
    }

    // getting phone number
    public String getHomePhone(){
        return this.homephone;
    }

    // setting phone number
    public void setHomephone(String homephone){
        this.homephone = homephone;
    }

    // getting phone number
    public String getCellphone(){
        return this.cellphone;
    }

    // setting phone number
    public void setCellphone(String cellphone){
        this.cellphone = cellphone;
    }

    // getting phone number
    public String getMedicinename(){
        return this.medicinename;
    }

    // setting phone number
    public void setMedicinename(String medicinename){
        this.medicinename = medicinename;
    }

    // getting phone number
    public String getDosageamount(){
        return this.dosageamount;
    }

    // setting phone number
    public void setDosageamount(String dosageamount){
        this.dosageamount = dosageamount;
    }

    // getting phone number
    public String getStartdate(){
        return this.startdate;
    }

    // setting phone number
    public void setStartdate(String startdate){
        this.startdate = startdate;
    }

    // getting phone number
    public String getStarttime(){
        return this.starttime;
    }

    // setting phone number
    public void setStarttime(String starttime){
        this.starttime = starttime;
    }

}
