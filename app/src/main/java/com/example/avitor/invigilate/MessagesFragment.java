package com.example.avitor.invigilate;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import android.text.format.DateFormat;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Enoch log";


    private static int SIGN_IN_REQUEST_CODE = 123;
    private FirebaseListAdapter<ChatMessage> adapter;
    private ViewGroup activityFragment;
    private FloatingActionButton sendButton;
    private View view;
    private EditText input;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG,"onActivityResult");
        if(requestCode == SIGN_IN_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Snackbar.make(activityFragment,"Successfully signed in. Welcome!",Snackbar.LENGTH_SHORT).show();
            }else{
                Snackbar.make(activityFragment,"We couldn't sign you in. Please try again later",Snackbar.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).finish();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_messages, container, false);

        activityFragment = getActivity().findViewById(android.R.id.content);

        //Edit text layout
        input = view.findViewById(R.id.input);

        sendButton = view.findViewById(R.id.sendButton);

        //Onclick listener for the send button
        sendButton.setOnClickListener(MessagesFragment.this);
        //Check if not signed in
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else{
            Log.i(TAG,"onActivityResult");
            Snackbar.make(activityFragment,"Welcome "+FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    Snackbar.LENGTH_SHORT).show();
            displayChatMessage();
        }
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.message_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.message_sign_out){
            Context context = getContext();
            assert context != null;
            AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Snackbar.make(activityFragment,"You have been signed out",Snackbar.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            });
        }
        return true;
    }


    //Display the chat
    public void displayChatMessage() {
        ListView listOfMessage = view.findViewById(R.id.list_of_message);
        Query query = FirebaseDatabase.getInstance()
                .getReference();
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>().setQuery(query,ChatMessage.class).setLayout(R.layout.list_item).setLifecycleOwner(this).build();
        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                //Get reference to the views of list_item.xml
                TextView messageText,messageUser,messageTime;
                messageText = v.findViewById(R.id.message_text);
                messageUser = v.findViewById(R.id.message_user);
                messageTime = v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };
        listOfMessage.setAdapter(adapter);

    }


    //Create menu option
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //Overriding onClick method in view.OnClickListener interface
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendButton:
                Log.i(TAG,"is send button working");

                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(
                        input.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getEmail()
                ));
                input.setText("");
        }

    }
}
