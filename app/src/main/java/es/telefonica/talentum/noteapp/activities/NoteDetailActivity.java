package es.telefonica.talentum.noteapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

import es.telefonica.talentum.noteapp.R;
import es.telefonica.talentum.noteapp.fragments.NoteDetailFragment;
import es.telefonica.talentum.noteapp.model.Note;

public class NoteDetailActivity extends AppCompatActivity {

    Button saveButton;
    NoteDetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        detailFragment = (NoteDetailFragment)getSupportFragmentManager().findFragmentById(R.id.activity_note_detail___detail_fragment);

        saveButton = (Button) findViewById(R.id.activity_note_detail___close_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent returnIntent = new Intent();
                Note note = detailFragment.getNote();
                returnIntent.putExtra("NewNote",(Serializable)note);

                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
