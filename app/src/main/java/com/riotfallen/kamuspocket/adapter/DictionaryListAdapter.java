package com.riotfallen.kamuspocket.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.riotfallen.kamuspocket.R;
import com.riotfallen.kamuspocket.activity.DetailActivity;
import com.riotfallen.kamuspocket.model.Dictionary;

import java.util.ArrayList;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.DictionaryListViewHolder> {

    private ArrayList<Dictionary> kamusArrayList;
    private Activity activity;

    public DictionaryListAdapter(Activity activity) {
        this.activity = activity;
    }

    private ArrayList<Dictionary> getDictionaryArrayList() {
        return kamusArrayList;
    }

    public void addItem(ArrayList<Dictionary> dictionaries){
        this.kamusArrayList = dictionaries;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DictionaryListAdapter.DictionaryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DictionaryListViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_item_kamus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DictionaryListAdapter.DictionaryListViewHolder holder, int position) {
        holder.textViewMeaning.setText(getDictionaryArrayList().get(position).getMeaning());
        holder.textViewWord.setText(getDictionaryArrayList().get(position).getWord());
        holder.cardView.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, DetailActivity.class);
                intent.putExtra(DetailActivity.DICTIONARY_DATA, getDictionaryArrayList().get(position));
                activity.startActivity(intent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return kamusArrayList.size();
    }

    class DictionaryListViewHolder extends RecyclerView.ViewHolder {

        TextView textViewWord;
        TextView textViewMeaning;
        CardView cardView;

        DictionaryListViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewWord = itemView.findViewById(R.id.vikWord);
            textViewMeaning = itemView.findViewById(R.id.vikMeaning);
            cardView = itemView.findViewById(R.id.vikCardView);
        }

    }
}
