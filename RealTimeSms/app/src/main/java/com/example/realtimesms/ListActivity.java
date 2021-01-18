package com.example.realtimesms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> list;
    String userName;
    RecyclerView userRecyView;
    UserAdapter userAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cikis_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.cikisYap:
                firebaseAuth.signOut();
                Intent intent = new Intent(ListActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        tanimla();
        listele();
    }



    public void tanimla(){

        userName= getIntent().getExtras().getString("kadi");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();


        list= new ArrayList<>();
        userRecyView =(RecyclerView)findViewById(R.id.userRecyView);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(ListActivity.this,3);
        userRecyView.setLayoutManager(layoutManager);
        userAdapter=new UserAdapter(ListActivity.this,list,ListActivity.this,userName);
        userRecyView.setAdapter(userAdapter);
    }

    public void listele(){

        reference.child("Kullanicilar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(!dataSnapshot.getKey().equals(userName)) {
                    list.add(dataSnapshot.getKey());
                    Log.i("Kullanicilar",dataSnapshot.getKey());
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}