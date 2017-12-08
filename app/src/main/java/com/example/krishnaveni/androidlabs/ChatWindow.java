package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ChatWindow extends Activity implements View.OnClickListener {
    private Cursor cursor;
    private ListView listView;
    private FrameLayout framelayout;
    private EditText textInput;
    private Button sendButton;
    private ChatAdapter messageAdapter;
   private boolean isTablet=false;
    ArrayList<String> al = new ArrayList();
    private static String ACTIVITY_NAME="ChatWindow";
    ChatDatabaseHelper chDbHelper=new ChatDatabaseHelper(this);
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView=findViewById(R.id.list);
        framelayout = findViewById(R.id.frameChat);
        if(framelayout!=null){
            isTablet=true;
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                long msgId = messageAdapter.getItemId(position);
                String msg=messageAdapter.getItemText(position);
                Bundle b = new Bundle();
                b.putLong("ID",msgId);
                b.putString("MSG",msg);
               if(!isTablet) {
                   Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                   intent.putExtras(b);
                   startActivityForResult(intent, 200, b);
               }
               else{
                   MessageFragment mFr=new MessageFragment();
                   mFr.setArguments(b);
                   FragmentTransaction transaction = getFragmentManager().beginTransaction();
                   transaction.add(R.id.frameChat,mFr);
                   transaction.commit();

               }
            }
        });
        textInput=findViewById(R.id.editText);
        sendButton=findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        // Reading from database
        SQLiteDatabase db=chDbHelper.getReadableDatabase();
         cursor=db.query(chDbHelper.TABLE_NAME,new String[]{chDbHelper.KEY_ID,chDbHelper.KEY_MESSAGE},null,null,null,null,null);
        Log.w(TAG, "Current db version is " + db.getVersion());
        while(cursor.moveToNext() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            al.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
        }
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
        for(int i=0;i<cursor.getColumnCount();i++) {
            Log.i(ACTIVITY_NAME, "Column name =" + cursor.getColumnName(i));
        }
        messageAdapter =new ChatAdapter( this,R.layout.activity_list_items,al );
        listView.setAdapter (messageAdapter);
    }

    @Override
    public void onClick(View view) {

        if(textInput.getText().toString().trim().length()>0) {
            al.add(textInput.getText().toString());
            // Gets the data repository in write mode
            db = chDbHelper.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(chDbHelper.KEY_MESSAGE, textInput.getText().toString());
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(chDbHelper.TABLE_NAME, null, values);
            messageAdapter.notifyDataSetChanged();
            //this restarts the process of getCount() getView()/
            textInput.setText("");
            }
        }
    private class ChatAdapter extends ArrayAdapter<ArrayList> {
        private ArrayList dataSet;
        public long getItemId(int position){
           cursor.moveToPosition(position);
           return cursor.getLong( cursor.getColumnIndex( ChatDatabaseHelper.KEY_ID));
        }

        public ChatAdapter(Context ctx,int layoutResourceId,ArrayList arrayList) {
            super(ctx,layoutResourceId,arrayList);
            dataSet=arrayList;
        }
        public int getCount() {
            return al.size();
        }

        public String getItemText(int position) {
            return (String)dataSet.get(position);
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItemText(position)  ); // get the string at position
                        return result;
        }
    }
    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy method");
        chDbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==200){
            db= chDbHelper.getWritableDatabase();
            long id=data.getExtras().getLong("ID");
            String message=data.getExtras().getString("MSG");
            if(message==null)
            {
                message="";
            }
            db.delete(chDbHelper.TABLE_NAME, chDbHelper.KEY_ID + " = ?",
                    new String[]{Long.toString(id)} );
            db.close();
            al.remove(message);
            messageAdapter=new ChatAdapter( this,R.layout.activity_list_items,al );
            listView.setAdapter(messageAdapter);
            messageAdapter.notifyDataSetChanged();
        }
    }
}

