package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ChatWindow extends Activity implements View.OnClickListener {
    private ListView listView;
    private EditText textInput;
    private Button sendButton;
    private ChatAdapter messageAdapter;
    ArrayList<String> al = new ArrayList();
    private static String ACTIVITY_NAME="ChatWindow";
    ChatDatabaseHelper chDbHelper=new ChatDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView=findViewById(R.id.list);
        textInput=findViewById(R.id.editText);
        sendButton=findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        // Reading from database
        SQLiteDatabase db=chDbHelper.getReadableDatabase();
        Cursor cursor=db.query(chDbHelper.TABLE_NAME,new String[]{chDbHelper.KEY_ID,chDbHelper.KEY_MESSAGE},null,null,null,null,null);
        Log.w(TAG, "Current db version is " + db.getVersion());
        while(cursor.moveToNext() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            al.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
        for(int i=0;i<cursor.getColumnCount();i++) {
            Log.i(ACTIVITY_NAME, "Column name =" + cursor.getColumnName(i));
        }
    }

    @Override
    public void onClick(View view) {
        al.add(textInput.getText().toString());
        // Gets the data repository in write mode
        SQLiteDatabase db = chDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(chDbHelper.KEY_MESSAGE,textInput.getText().toString() );
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(chDbHelper.TABLE_NAME, null, values);
        messageAdapter.notifyDataSetChanged();
        //this restarts the process of getCount() getView()/
        textInput.setText("");
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return al.size();
        }

        public String getItem(int position) {
            return al.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
                        return result;
        }
    }
    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy method");
        chDbHelper.close();
        super.onDestroy();
    }
}
