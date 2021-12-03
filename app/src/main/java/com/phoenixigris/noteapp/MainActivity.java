package com.phoenixigris.noteapp;


import android.content.Intent;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter recycleViewAdapter;
    private FloatingActionButton btnAddNotes;
    private String USER_ID = FirebaseAuth.getInstance().getUid();
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    public ArrayList<NoteContent> noteContentArrayList = new ArrayList<>();
private Toolbar toolbar;

    private FirebaseFirestoreSettings firebaseFirestoreSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db.setFirestoreSettings(firebaseFirestoreSettings);
        collectionReference = db.collection("Notes").document(USER_ID).collection("UserNotes");
        recyclerView = findViewById(R.id.recycle_view);
        btnAddNotes = findViewById(R.id.addnotes);
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.getMenu().findItem(R.id.icon_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
            }
        });
        btnAddNotes.setOnClickListener(this);


        getNotes();
        recycleViewAdapter = new RecycleViewAdapter(MainActivity.this);
        recycleViewAdapter.setNoteContentArrayList(noteContentArrayList);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


    }

    private void getNotes() {
        if (collectionReference == null) {
            Toast.makeText(this, "collection reference is null", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "collection reference is not empty", Toast.LENGTH_SHORT).show();
        }
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    NoteContent note = document.toObject(NoteContent.class);
                    noteContentArrayList.add(note);
                }
                recycleViewAdapter.notifyDataSetChanged();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addnotes:
                Intent in = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(in);
        }

    }
}