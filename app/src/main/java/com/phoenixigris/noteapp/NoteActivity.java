package com.phoenixigris.noteapp;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import de.hdodenhof.circleimageview.CircleImageView;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText textTitle, textDesc;
    private ImageView btnSaveNotes, btnback, backgroundPick, btndelete, btnEditNotes;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    public static String USER_ID = FirebaseAuth.getInstance().getUid();
    private final String NOTE_TITLE = "note_title";
    private final String NOTE_DESC = "note_desc";
    private final String NOTE_COLOR = "note_background";
    private RelativeLayout main_layout;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    private CircleImageView c1, c2, c3, c4, c5, c6, c7, c8;
    private DocumentReference df;
    private FirebaseFirestoreSettings firebaseFirestoreSettings;
    private Boolean editmode = null;
    private String noteBackground;


    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String note_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        db = FirebaseFirestore.getInstance();
        firebaseFirestoreSettings = new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build();
        db.setFirestoreSettings(firebaseFirestoreSettings);
        df = db.collection("Notes").document(USER_ID).collection("UserNotes").document();


        main_layout = findViewById(R.id.main_layout);
        textTitle = findViewById(R.id.text_title);
        textDesc = findViewById(R.id.text_desc);
        backgroundPick = findViewById(R.id.background);
        btnSaveNotes = findViewById(R.id.savenotes);
        btnback = findViewById(R.id.back);
        btnEditNotes = findViewById(R.id.edit_notes);
        btndelete = findViewById(R.id.delete);
        btnEditNotes.setOnClickListener(this);
        backgroundPick.setOnClickListener(this);
        btnSaveNotes.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btndelete.setOnClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = this.getIntent().getExtras();

        if (extras != null) {
            editmode = true;
            btnEditNotes.setVisibility(View.VISIBLE);
            btnSaveNotes.setVisibility(View.INVISIBLE);
            btnEditNotes.setVisibility(View.VISIBLE);
            setNote_id(extras.getString("NOTE_ID"));
            df = db.collection("Notes").document(USER_ID).collection("UserNotes").document(getNote_id());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {

                        //  Toast.makeText(NoteActivity.this, "NOte Exists", Toast.LENGTH_SHORT).show();
                        NoteContent noteContent = documentSnapshot.toObject(NoteContent.class);
                        String title = noteContent.getNote_title().toString();
                        String desc = noteContent.getNote_desc().toString();
                        String color = noteContent.getNote_background();
                        textTitle.setText(title);
                        textDesc.setText(desc);
                        if (color != null) {
                            // Toast.makeText(NoteActivity.this, "value of color isfrm" + color, Toast.LENGTH_SHORT).show();

                            setBackColor(color);
                        } else {
                            //  Toast.makeText(NoteActivity.this, "value of color is" + color, Toast.LENGTH_SHORT).show();
                            System.out.println("value of color is                      " + color);
                        }

                    }
                }
            });
        } else {
            btnEditNotes.setVisibility(View.INVISIBLE);
            btnSaveNotes.setVisibility(View.VISIBLE);
            btndelete.setVisibility(View.INVISIBLE);


        }
    }

    private void setBackColor(String color) {
        switch (color) {
            case "color_one":
                main_layout.setBackgroundResource(R.color.color_one);
                setNoteBackground("color_one");
                break;
            case "color_two":
                main_layout.setBackgroundResource(R.color.color_two);
                setNoteBackground("color_two");
                break;
            case "color_three":
                main_layout.setBackgroundResource(R.color.color_three);
                setNoteBackground("color_three");
                break;
            case "color_four":
                main_layout.setBackgroundResource(R.color.color_four);
                setNoteBackground("color_four");
                break;
            case "color_five":
                main_layout.setBackgroundResource(R.color.color_five);
                setNoteBackground("color_five");
                break;
            case "color_six":
                main_layout.setBackgroundResource(R.color.color_six);
                setNoteBackground("color_six");
                break;
            case "color_seven":
                main_layout.setBackgroundResource(R.color.color_seven);
                setNoteBackground("color_seven");
                break;
            case "color_eight":
                main_layout.setBackgroundResource(R.color.color_eight);
                setNoteBackground("color_eight");
                break;
            case "color_white":
                main_layout.setBackgroundResource(R.color.white);
                setNoteBackground("color_white");
                break;

        }
    }


    private void saveNotes() {
        String title = textTitle.getText().toString();
        String desc = textDesc.getText().toString();
        String color = getNoteBackground();
        documentReference = db.collection("Notes").document(USER_ID).collection("UserNotes").document();
        NoteContent noteContent = new NoteContent(title,
                desc, documentReference.getId(),
                USER_ID, color);
        documentReference.set(noteContent).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    //   Toast.makeText(NoteActivity.this, "Notes not Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        onBackPressed();
    }

    public void updateNotes() {
        String title = textTitle.getText().toString();
        String desc = textDesc.getText().toString();

        Toast.makeText(NoteActivity.this, "updated color" + getNoteBackground(), Toast.LENGTH_SHORT).show();
        df.update(NOTE_TITLE, title);
        df.update(NOTE_DESC, desc);
        df.update(NOTE_COLOR, getNoteBackground());
    }

    private long mLastClickTime = 0;

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 5000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        switch (v.getId()) {

            case R.id.delete:
                deleteNotes();

                //    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                break;
            case R.id.savenotes:
                //    Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

                saveNotes();
                break;
            case R.id.edit_notes:
                //    Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                updateNotes();
                break;
            case R.id.back:
                //      Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
            case R.id.background:
                //   Toast.makeText(this, "Choose a Color!", Toast.LENGTH_SHORT).show();
                colorPicker();
                break;
            case R.id.color_one:
                setNoteBackground("color_one");
                //   Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_one);
                break;
            case R.id.color_two:
                setNoteBackground("color_two");
                //   Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_two);
                break;
            case R.id.color_three:
                setNoteBackground("color_three");
                //   Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_three);
                break;
            case R.id.color_four:
                setNoteBackground("color_four");
                //   Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_four);
                break;
            case R.id.color_five:
                setNoteBackground("color_five");
                //    Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_five);
                break;
            case R.id.color_six:
                setNoteBackground("color_six");
                //   Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_six);
                break;
            case R.id.color_seven:
                setNoteBackground("color_seven");
                //    Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_seven);
                break;
            case R.id.color_eight:
                setNoteBackground("color_eight");
                //    Toast.makeText(NoteActivity.this, "color one chosen!" + getNoteBackground(), Toast.LENGTH_SHORT).show();
                main_layout.setBackgroundResource(R.color.color_eight);
                break;

        }
    }

    public void deleteNotes() {

        DocumentReference cf = db.collection("Notes").document(USER_ID).collection("UserNotes").document(getNote_id());
        cf.delete();
        onBackPressed();

    }


    @Override
    public void onBackPressed() {

        updateNotes();
        // After 5 seconds redirect to another intent
        Intent intent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(intent);


    }


    public void colorPicker() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View colorBox = this.getLayoutInflater().inflate(R.layout.color_pick_dialog, null);
        //colorpick
        c1 = colorBox.findViewById(R.id.color_one);
        c2 = colorBox.findViewById(R.id.color_two);
        c3 = colorBox.findViewById(R.id.color_three);
        c4 = colorBox.findViewById(R.id.color_four);
        c5 = colorBox.findViewById(R.id.color_five);
        c6 = colorBox.findViewById(R.id.color_six);
        c7 = colorBox.findViewById(R.id.color_seven);
        c8 = colorBox.findViewById(R.id.color_eight);
        dialogBuilder.setView(colorBox);

        dialog = dialogBuilder.create();

        dialog.show();
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);


    }

    public String getNoteBackground() {

        return noteBackground;
    }

    public void setNoteBackground(String noteBackground) {
        this.noteBackground = noteBackground;
    }

}