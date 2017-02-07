package es.telefonica.talentum.noteapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import es.telefonica.talentum.noteapp.R;
import es.telefonica.talentum.noteapp.adapters.NoteAdapter;
import es.telefonica.talentum.noteapp.fragments.NoteListFragment;
import es.telefonica.talentum.noteapp.model.Note;
import es.telefonica.talentum.noteapp.model.Notes;
import io.realm.Realm;
import io.realm.RealmResults;

public class NotesListActivity extends AppCompatActivity {

    private static final int NEW_NOTE = 69;
    Notes listOfNotes = new Notes();
    NoteListFragment noteListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        noteListFragment = (NoteListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_notes_list);

        /*
        for (int i = 0; i < 20; i++) {
            Note note = new Note("Note " + UUID.randomUUID() + i);
            listOfNotes.add(note);
            note.setText("Noticia super importante " + i);
            listOfNotes.add(note);

        }
        */

        loadFromRealm();

        NoteAdapter adapter = new NoteAdapter(listOfNotes, this);
        noteListFragment.setAdapter(adapter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        SaveToRealm();
    }

    private void SaveToRealm(){
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        for (int i = 0; i < listOfNotes.count(); i++){
            Note n = listOfNotes.get(i);
            realm.copyToRealmOrUpdate(n);
        }

        realm.commitTransaction();

    }

    private void loadFromRealm(){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> results = realm.where(Note.class).findAll();

        listOfNotes = new Notes();
        for (Note n: results){
            listOfNotes.add(n);
        }


        NoteAdapter adapter = new NoteAdapter(listOfNotes, this);
        noteListFragment.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_main_action_add_note) {

            Intent i = new Intent(NotesListActivity.this, NoteDetailActivity.class);
            startActivityForResult(i, NEW_NOTE);

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE && resultCode == RESULT_OK){
            final Note newNote = (Note) data.getSerializableExtra("NewNote");
            //add to database

            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(newNote);
                }
            });
            loadFromRealm();
        }
    }

}
