package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherForecast extends Activity {

    private String urlString="http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private ImageView bpm;
    private  ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        pb=findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        currentTemp=(TextView)findViewById(R.id.textView);
        minTemp=(TextView)findViewById(R.id.textView2);
        maxTemp=(TextView)findViewById(R.id.textView3);
        bpm=(ImageView) findViewById(R.id.imageView3);
        currentTemp.setText("0"+ (char) 0x00B0 + "C");
        minTemp.setText("0"+ (char) 0x00B0 + "C");
        maxTemp.setText("0"+ (char) 0x00B0 + "C");

        new ForecastQuery().execute(urlString);
            }
    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        private String currentTempStr;
        private String minStr;
        private String maxStr;
        private String iconName;
        private Bitmap bp;
        protected static final String ACTIVITY_NAME = "WeatherForecast";

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    if (name!=null && name.equals("temperature")) {
                        if(currentTempStr==null) {
                            currentTempStr = parser.getAttributeValue(null, "value");
                        }
                      if(minStr==null)
                      {minStr=parser.getAttributeValue(null,"min");}
                        publishProgress(25);
                        if(maxStr==null) {
                            maxStr = parser.getAttributeValue(null, "max");
                        }
                        publishProgress(50);

                    }
                    else if (name!=null && name.equals("weather")) {
                        if(iconName==null)
                        iconName=parser.getAttributeValue(null,"icon");
                    }
                    }

                publishProgress(75);
                if(fileExists(iconName + ".png")) {
                    Log.i(ACTIVITY_NAME,"Found file  "+iconName+ " Locally");

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(iconName + ".png");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bp = BitmapFactory.decodeStream(fis);
                }
                else {
                    Log.i(ACTIVITY_NAME,"DId not find file  "+iconName+" Locally downloading it from internet");

                    String imageURL="http://openweathermap.org/img/w/" + iconName + ".png";
                    bp = HttpUtils.getImage(imageURL);
                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    bp.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                publishProgress(100);
            }
            catch(Exception exp){
                exp.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            currentTemp.setText(currentTempStr+ (char) 0x00B0 + "C");
            minTemp.setText(minStr+(char) 0x00B0+ "C");
            maxTemp.setText(maxStr+(char) 0x00B0+ "C");
            bpm.setImageBitmap(bp);
            pb.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(values[0]);

        }


        public boolean fileExists(String fname){
            Log.i(ACTIVITY_NAME,"Checking for file "+fname);
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }


    }
}
