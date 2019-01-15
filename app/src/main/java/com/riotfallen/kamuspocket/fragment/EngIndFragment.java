package com.riotfallen.kamuspocket.fragment;


import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riotfallen.kamuspocket.R;
import com.riotfallen.kamuspocket.adapter.DictionaryListAdapter;
import com.riotfallen.kamuspocket.db.DictionaryHelper;
import com.riotfallen.kamuspocket.model.Dictionary;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class EngIndFragment extends Fragment {

    private EditText editTextSearch;
    private DictionaryListAdapter adapter;
    private DictionaryHelper helper;
    ArrayList<Dictionary> dictionaries;

    public EngIndFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_eng_ind, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).
                getSupportActionBar()).setTitle(R.string.english_indonesia);

        RecyclerView recyclerView = view.findViewById(R.id.engIndFragmentRecyclerView);
        editTextSearch = view.findViewById(R.id.engIndFragmentEditTextSearch);
        Button buttonClear = view.findViewById(R.id.engIndFragmentButtonClear);

        dictionaries = new ArrayList<>();

        helper = new DictionaryHelper(getContext());
        adapter = new DictionaryListAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        loadDictionary("");


        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   loadDictionary(editTextSearch.getText().toString());
                }
                return false;
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextSearch.setText("", TextView.BufferType.EDITABLE);
                loadDictionary("");
            }
        });
    }

    private void loadDictionary(String query){
        dictionaries.clear();
        try{
            helper.open();
            dictionaries = helper.getWordByName(query, true);
            helper.close();
            adapter.addItem(dictionaries);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
