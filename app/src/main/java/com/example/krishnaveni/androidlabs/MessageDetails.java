package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageDetails extends Activity{


    private static int requestCode=200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        MessageFragment mFr=new MessageFragment();
        mFr.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.frameMessage,mFr);
        transaction.commit();
    }

}
