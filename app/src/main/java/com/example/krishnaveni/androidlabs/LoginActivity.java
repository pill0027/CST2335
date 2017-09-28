package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements View.OnClickListener{
    protected static final String ACTIVITY_NAME = "LoginActivity";
    protected static final String SETTINGS="com.example.krishnaveni.andriodlabs.loginactivity";
    protected String emailID="";
    protected EditText loginText;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        final Button b1= findViewById(R.id.button);
        sharedPref= getSharedPreferences(SETTINGS,MODE_PRIVATE);
        String defaultValue="default@domain.com";
        emailID= sharedPref.getString(getString(R.string.loginname), defaultValue);
        loginText= findViewById(R.id.loginbox);
        loginText.setText(emailID);
        b1.setOnClickListener(this);


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

    @Override
    public void onClick(View view) {
        emailID=loginText.getText().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.loginname), emailID);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(intent);

    }
}
