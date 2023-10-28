package com.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //variable declaring
    EditText firstName, lastName, id;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find id
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        saveButton = findViewById(R.id.saveButton);
        id = findViewById(R.id.id);

        //save data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*//backgroundThread class object create
                BackgroundThread backgroundThread = new BackgroundThread();
                //thread start
                backgroundThread.start();
                firstName.setText("");
                lastName.setText("");*/

                //app database object create
                AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();

                //create interface object
                UserDao userDao = appDatabase.userDao();

                //uid exist or not
                Boolean check = userDao.is_exist(Integer.parseInt(id.getText().toString()));
                if (check == false){
                    userDao.insertRecord(new User(Integer.parseInt(id.getText().toString()), firstName.getText().toString(), lastName.getText().toString()));
                    id.setText("");
                    firstName.setText("");
                    lastName.setText("");
                    Toast.makeText(MainActivity.this, "Record save successfully...", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Record not save id already exist...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
    Room does not allow accessing the database on the main thread unless
     you called allowMainThreadQueries() on the builder because it might
     potentially lock the UI for a long period of time.
     Asynchronous queries (queries that return LiveData or RxJava Flowable)
     are exempt from this rule since they asynchronously run the query on a background
     thread when needed.


    //create thread class
    public class BackgroundThread extends Thread{
        public void run(){
            super.run();

            //app database object create
            AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "room_db").build();

            //create interface object
            UserDao userDao = appDatabase.userDao();

            //editText data get
            String firstname = firstName.getText().toString();
            String lastname = lastName.getText().toString();

            //calling method from the interface
            userDao.insertRecord(new User(10, firstname, lastname));

            //editText data clear
            //firstName.setText("");
            //lastName.setText("");
        }
    }*/
}