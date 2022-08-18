package com.spsh.crudtutorialtrial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.spsh.crudtutorialtrial.database.DBHelper;
import com.spsh.crudtutorialtrial.database.UsersMaster;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText et1_Username,et2_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1_Username = findViewById(R.id.et1_Username);
        et2_Password = findViewById(R.id.et2_Password);
    }

    public void saveUser(View view){
        //The values fetched by the ids above will be assigned to String variables of "name" and "password" respectively.
        String name = et1_Username.getText().toString();
        String password = et2_Password.getText().toString();
        DBHelper dbHelper = new DBHelper(this);
        if(name.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"Enter values",Toast.LENGTH_SHORT).show();
        }else{
            long inserted = dbHelper.addInfo(name,password);

            //Considers the affected rows.
            if(inserted>0){
                Toast.makeText(this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //view is passed as an object so that we can do a button click function.
    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAll();

        String [] infoArray = (String[]) info.toArray(new String[0]);
        //Create alert messages.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Details");

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            //OnClick method for elements
            //int i is what you are going to click --> the index of those elements.
            public void onClick(DialogInterface dialogInterface, int i) {

                //Using the infoArray --> this is splited using colons.
                //infoArray is converted to a String.

                //First element is taken --> available in the left hand side --> which is the username.
                //Will be displaying only the username, not the password.
                String userName = infoArray[i].split(":")[0];
                Toast.makeText(MainActivity.this,userName,Toast.LENGTH_SHORT).show();

                //set the values to the existing edit texts.
               //et1_username is the variable we assigned in the onCreate method

                //Can show the details in the text view rather than using the toast message, using the setText command below.
                et1_Username.setText(userName);

                //Shows that its been hidden ---> will not be displayed to the user,when updating or deleting it.
                et2_Password.setText("********");
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //Default dialog box
        //Similar to toast messages
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteUser(View view){
        DBHelper dbHelper = new DBHelper(this);
        String userName = et1_Username.getText().toString();

        if(userName.isEmpty()){
            Toast.makeText(this,"Select a user",Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.deleteInfo(userName);
            Toast.makeText(this,userName + "User is deleted",Toast.LENGTH_SHORT).show();
        }
    }
}