package com.example.krishnaveni.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.krishnaveni.androidlabs.ChatWindow;

/**
 * Created by KRISHNAVENI on 2017-11-07.
 */
public class MessageFragment extends Fragment implements View.OnClickListener{
    private long id;
    private String msg;
    private TextView idText;
    private TextView msgText;
    private ChatWindow chat=null;

    public void setChatWindow(ChatWindow chat){
        this.chat=chat;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg=getArguments().getString("MSG");
        id=getArguments().getLong("ID");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_message, container, false);
        Button b = (Button) v.findViewById(R.id.deleteButton);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        idText = (TextView) view.findViewById(R.id.dbIdText);
        msgText= (TextView) view.findViewById(R.id.msgText);

        // update view
        idText.setText(""+id);
        msgText.setText(msg);
    }

    // Activity is calling this to update view on Fragment
    public void updateView(int position){
        idText.setText(""+id);
        msgText.setText(msg);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent();
        intent.putExtra("ID",id);
        intent.putExtra("MSG",msg);
        if(chat!=null){
         chat.deleteMessage(intent);
        }
        else {
            getActivity().setResult(200, intent);
            getActivity().finish();
        }
    }
}



