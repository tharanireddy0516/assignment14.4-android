package com.example.tharani.runtimepermissions;
/*import is libraries imported for writing the code
* AppCompatActivity is base class for activities
* Bundle handles the orientation of the activity
*/

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
/*onCreate is the first method in the life cycle of an activity
     savedInstance passes data to super class,data is pull to store state of application
   * setContentView is used to set layout for the activity
   *R is a resource and it is auto generate file
   * activity_main assign an integer value*/
public class MainActivity extends AppCompatActivity {
    private static final int PERM_REQ_CODE=123;

    // Permission code used to check and ask for permission is Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //finding button in java
        Button button =  findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPermission()){//taking if statement to check whether has permission or not
                    makeFolder();//makes folder

                }else { requestPermission();//else is used when the statement in if statement is false

                }
            }
        });
    }
    //METHOD TO CHECK THE PERMISSION

    @SuppressLint("WrongConstant")
    //SuppressLint indicates that Lint should ignore the specified warnings for the annotated element.
    private boolean hasPermission(){//taking boolean for hasPermission to checks whether the permission false or true
        int res=0;//initializing res
        String[]permission= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        /* manifest acts as metadata,declares all attributes of activity tags,permissions,services,broadcast receivers
        tells a behavior of app
        taking permissions for WRITE_EXTERNAL_STORAGE*/
        for(String perm:permission){//taking for loop
            res=checkCallingOrSelfPermission(perm);
            //checkCallingOrSelfPermission determine whether the calling process of an IPC or you have been granted a particular permission
            if(!(res== PackageManager.PERMISSION_GRANTED)){//taking if statement to check whether permission granted or not
                return false;//returns false
            }
        }
        return true;//returns true
    }
    //Method to request for the permission
    private void requestPermission(){
        String[]permission= new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(permission,PERM_REQ_CODE);//taking if statement to check whether has permission or not
            /* manifest acts as metadata,declares all attributes of activity tags,permissions,services,broadcast receivers
        tells a behavior of app
        taking permissions for WRITE_EXTERNAL_STORAGE*/
        }
    }
     //taking method RequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;//by taking boolean allowing true
        switch (requestCode) {//switch is a multi way branch statement,dispatch execution to diff parts of code
            case PERM_REQ_CODE:
                for (int res : grantResults) {//taking for loop
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;//terminates
            default:
                //if user does not granted the permission
                allowed = false;//allows false
                break;
        }
        if (allowed) {//taking if statement to check whether has permission or not
            //user granted all the permission we can perform our work
            makeFolder();
        }
        else {//if statement is false in if statement then uses else
            //giving warning to the user that permission is not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//taking if statement whether this statement will execute or not
                if(shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
                Toast.makeText(getApplicationContext(),"User permission denied",Toast.LENGTH_LONG).show();
                /*A toast is a view containing a quick little message for the user.
                * LENGTH_LONG  display toast message in long period of time*/
            }
        }
    }
    //Method that would make folder if all permissions has been granted
    private void makeFolder(){
        File file= new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"MYFOLDER");
        if(!file.exists()){//taking if statement whether this statement will execute or not
            boolean ff=  file.mkdir();//booleans decides about files condition of true or false
            if(ff){//taking if statement whether this statement will execute or not
                Toast.makeText(getApplicationContext(),"Folder created successfully",Toast.LENGTH_LONG).show();
                /*A toast is a view containing a quick little message for the user.
                * LENGTH_LONG  display toast message in long period of time*/
            }else {//else is used when if statement condition is false
                Toast.makeText(getApplicationContext(), "Folder not created", Toast.LENGTH_LONG).show();
                /*A toast is a view containing a quick little message for the user.
                * LENGTH_LONG  display toast message in long period of time*/
            }

        }else {
            Toast.makeText(getApplicationContext(),"Folder already exist",Toast.LENGTH_LONG).show();
            /*A toast is a view containing a quick little message for the user.
                * LENGTH_LONG  display toast message in long period of time*/

        }
    }
}