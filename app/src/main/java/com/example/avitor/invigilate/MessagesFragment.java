package com.example.avitor.invigilate;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.text.DateFormat;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {


    private static int SIGN_IN_REQUEST_CODE = 123;
    private FirebaseListAdapter<ChatMessage> adapter;
    private RelativeLayout activityFragment;
    private FloatingActionButton fab;
    private View view;

    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        setMenuVisibility(true);
        super.onCreateView(inflater,container,savedInstanceState);
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        activityFragment = view.findViewById(R.id.activityFragment);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = view.findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                input.setText("");
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else{
            Snackbar.make(container,"Welcome "+
                            FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                    Snackbar.LENGTH_SHORT).show();

        }
        //Load content
        displayChatMessage();
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
                    Objects.requireNonNull(getActivity()).finish();
                }
            });
        }
        return true;
    }

    private void displayChatMessage() {
        ListView listOfMessage = view.findViewById(R.id.list_of_message);
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chats")
                .limitToLast(50);
        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>().setQuery(query,ChatMessage.class).setLayout(R.layout.list_item).build();
        adapter = new FirebaseListAdapter<ChatMessage>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull ChatMessage model, int position) {
                //Get reference to the views of list_item.xml
                TextView messageText,messageUser,messageTime;
                messageText = view.findViewById(R.id.message_text);
                messageUser = view.findViewById(R.id.message_user);
                messageTime = view.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
            }
        };
        listOfMessage.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}
