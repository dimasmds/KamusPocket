package com.riotfallen.kamuspocket.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.riotfallen.kamuspocket.R;
import com.riotfallen.kamuspocket.db.DictionaryHelper;
import com.riotfallen.kamuspocket.model.Dictionary;
import com.riotfallen.kamuspocket.utils.PrefConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreLoadActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load);

        progressBar = findViewById(R.id.preLoadProgressBar);

        new LoadData().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        DictionaryHelper helper;
        PrefConfig prefConfig;
        double progress;
        double maxProgress = 100;

        @Override
        protected void onPreExecute() {
            helper = new DictionaryHelper(PreLoadActivity.this);
            prefConfig = new PrefConfig(PreLoadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean isFirstRun = prefConfig.getFirstRun();

            if (isFirstRun) {
                ArrayList<Dictionary> dictionariesEnglish = preLoadRaw(true);
                ArrayList<Dictionary> dictionariesIndonesian = preLoadRaw(false);

                try {
                    helper.open();
                    progress = 30;
                    publishProgress((int) progress);
                    double progressMaxInsert = 100;
                    double progressDiff = (progressMaxInsert - progress) / (dictionariesEnglish.size()
                            + dictionariesIndonesian.size());

                    helper.beginTransaction();
                    try {
                        for (Dictionary dictionaryEnglish : dictionariesEnglish) {
                            helper.insertTransaction(dictionaryEnglish, true);
                            progress += progressDiff;
                            publishProgress((int) progress);
                        }

                        helper.setTransactionSuccess();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        for (Dictionary dictionaryIndonesian : dictionariesIndonesian) {
                            helper.insertTransaction(dictionaryIndonesian, false);
                            progress += progressDiff;
                            publishProgress((int) progress);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    helper.endTransaction();

                    helper.close();
                    prefConfig.setFirstRun(false);
                    publishProgress((int) maxProgress);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxProgress);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(PreLoadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<Dictionary> preLoadRaw(boolean isEnglish) {
        ArrayList<Dictionary> dictionaries = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict;

            if (isEnglish) {
                raw_dict = res.openRawResource(R.raw.english_indonesia);
            } else {
                raw_dict = res.openRawResource(R.raw.indonesia_english);
            }

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            do {
                line = reader.readLine();
                String[] splitString = line.split("\t");

                Dictionary dictionary = new Dictionary();
                dictionary.setWord(splitString[0]);
                dictionary.setMeaning(splitString[1]);
                dictionaries.add(dictionary);
            } while (reader.readLine() != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaries;
    }
}
