package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity implements View.OnClickListener {
    private ListView listView;
    private EditText textInput;
    private Button sendButton;
    private ChatAdapter messageAdapter;
    ArrayList<String> al = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView=findViewById(R.id.list);
        textInput=findViewById(R.id.editText);
        sendButton=findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);


    }

    @Override
    public void onClick(View view) {
        al.add(textInput.getText().toString());
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
}
