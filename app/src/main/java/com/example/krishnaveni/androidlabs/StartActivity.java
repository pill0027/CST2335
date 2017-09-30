package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity implements View.OnClickListener {
    protected static final String ACTIVITY_NAME = "StartActivity";
    protected static int RESULT_CODE=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button b1=findViewById(R.id.button);
        b1.setOnClickListener(this);
       Button b2=findViewById(R.id.button2);
       b2.setOnClickListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");

    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }
    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == RESULT_CODE) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }

        if (resultCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG);
            toast.show(); //display your message box
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button:
                Intent intent = new Intent(this, ListItemsActivity.class);
                startActivityForResult(intent, RESULT_CODE);
                break;

            case R.id.button2:
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent chatintent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(chatintent);

                break;

            default:
                break;
        }

    }



}


