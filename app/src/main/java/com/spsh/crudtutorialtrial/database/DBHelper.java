package com.spsh.crudtutorialtrial.database;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //Carries the database name.
    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //This method is only implemented, if you are creating the table for the first time.
        //If the table already exists---> this function will not be implemented.

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " (" +
                UsersMaster.Users._ID + "INTEGER PRIMARY KEY,"+
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";

        //PASS THE STRING TO BE EXECUTED IN THE EXEC COMMAND.
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public long addInfo(String username,String password){
       //Since its a writable database--> can add data to the database.
        SQLiteDatabase db = getWritableDatabase();

        //Create objects that will contain the username and the password. --> Content Values act as a row.
        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME, username);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD, password);

        //Second parameter passed is null.
        //Third parameter is values.
        return db.insert(UsersMaster.Users.TABLE_NAME,null,values);
    }

    public List readAll(){
        SQLiteDatabase db = getReadableDatabase();

        //ContentValues values = new ContentValues();
        String [] projection = {
                UsersMaster.Users._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UsersMaster.Users._ID + "DESC";
        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List info = new ArrayList();

        while(cursor.moveToNext()){
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));

            info.add(userName + ":" + password);

        }

        cursor.close();

        //just return the cursor --> if there are many columns.
        return info;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //DELETE CRUD
    public void deleteInfo(String userName){
        //Use Readable to check if data is available --> "getReadableDATABASE"
        SQLiteDatabase db = getReadableDatabase();

        //Where we check if the value is there.
        //Check if the username is equal or similar to the one that we are passing.
        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";

        //Pass the columns as an array.
        String[] stringArgs = {userName};

        //delete the  query
        db.delete((UsersMaster.Users.TABLE_NAME),selection,stringArgs);

    }

}
