package com.riotfallen.kamuspocket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.riotfallen.kamuspocket.R;
import com.riotfallen.kamuspocket.model.Dictionary;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String DICTIONARY_DATA = "dictionaryData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Dictionary dictionary = getIntent().getParcelableExtra(DICTIONARY_DATA);

        Toolbar toolbar = findViewById(R.id.detailActivityToolbar);
        TextView textViewWord = findViewById(R.id.detailActivityTextViewWord);
        TextView textViewMeaning = findViewById(R.id.detailActivityTextViewMeaning);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        textViewWord.setText(dictionary.getWord());
        textViewMeaning.setText(dictionary.getMeaning());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
