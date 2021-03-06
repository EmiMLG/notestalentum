package es.telefonica.talentum.noteapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import es.telefonica.talentum.noteapp.R;
import es.telefonica.talentum.noteapp.model.Note;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailFragment extends Fragment {

    EditText titleText;
    EditText descriptionText;


    public NoteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        titleText = (EditText) view.findViewById(R.id.fragment_note_detail_title_text);
        descriptionText = (EditText) view.findViewById(R.id.fragment_note_detail_description_text);

        return view;


    }

    @Override
    public void onPause() {
        super.onPause();
        //save all from screen to disk: fragment is going to be destruyed
        saveAllDataToDisk();

    }

    @Override
    public void onResume() {
        super.onResume();
        // load data to show on screen (if any)
        loadAllDataFromDisk();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("NOTE_TITLE");
        editor.remove("NOTE_DESCRIPTION");
        editor.apply();
    }

    private void loadAllDataFromDisk() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String noteTitle = preferences.getString("NOTE TITLE", "");
        String noteDescription = preferences.getString("NOTE_DESCRIPTION", "");

        titleText.setText(noteTitle);
        descriptionText.setText(noteDescription);




    }

    private void saveAllDataToDisk() {
        // abro el fichero,
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();

        // leo lo que has escrito en pantalla
        String title = titleText.getText().toString();
        String description = descriptionText.getText().toString();


        // grabo eso en el fichero
        editor.putString("NOTE TITLE", title);
        editor.putString("NOTE_DESCRIPTION", description);

        editor.apply();

    }

    public Note getNote() {
        Note note = new Note();
        note.setTitle(titleText.getText().toString());
        note.setText(descriptionText.getText().toString());

        return note;
    }
}
