package com.phoenixigris.noteapp;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class NoteContent {

    private String note_title;
    private String note_id;
private String note_background;



    private String user_id;
    private String note_desc;




    public NoteContent(String note_title, String note_desc, String note_id, String user_id,String note_background) {
        this.note_title = note_title;
        this.note_desc = note_desc;
this.note_background = note_background;
        this.note_id= note_id;
        this.user_id = user_id;
    }
    public NoteContent(){

    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_desc() {
        return note_desc;
    }

    public void setNote_desc(String note_desc) {
        this.note_desc = note_desc;
    }


    public String getNote_background() {
        return note_background;
    }

    public void setNote_background(String note_background) {
        this.note_background = note_background;
    }

    public String getNote_id() {
        return note_id;
    }

    public void setNote_id(String note_id) {
        this.note_id = note_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "NoteContent{" +
                "note_title='" + note_title + '\'' +
                ", note_id='" + note_id + '\'' +
                ", note_background='" + note_background + '\'' +
                ", user_id='" + user_id + '\'' +
                ", note_desc='" + note_desc + '\'' +
                '}';
    }
}
