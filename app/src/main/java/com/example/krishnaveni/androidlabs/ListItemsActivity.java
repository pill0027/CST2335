package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    private static final int CAMERA_REQUEST = 1888;
    CheckBox cb;
    protected ImageButton photoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        photoButton = findViewById(R.id.imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(this);
        cb = findViewById(R.id.checkbox);
        cb.setOnCheckedChangeListener(this);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoButton.setImageBitmap(photo);
        }
    }

    @Override
    public void onCheckedChanged(final CompoundButton compoundButton, boolean isChecked) {

        if (compoundButton.getId() == R.id.switch1) {
            CharSequence text = getResources().getString(R.string.switchoff);
            int duration = Toast.LENGTH_LONG;
            if (isChecked) {
                text = getResources().getString(R.string.switchon);
                duration = Toast.LENGTH_SHORT;
            }
            Toast toast = Toast.makeText(this, text, duration);
            toast.show(); //display your message box
        }

        if (compoundButton.getId() == R.id.checkbox) {
            if (isChecked)
            {
              AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.dialog_message); //Add a dialog message to strings.xml
            builder.setTitle(R.string.dialog_title);

            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("Response",getResources().getString(R.string.response));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    compoundButton.toggle();

                }
            });
                builder.show();

            }
        }
    }
}

