package es.telefonica.talentum.noteapp;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


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

    private void loadAllDataFromDisk() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String noteTitle = preferences.getString("NOTE TITLE", "");
        String noteDescription = preferences.getString("NOTE_DESCRIPTION", "");

    }

    private void saveAllDataToDisk() {

    }
}
