package com.shorturlxx;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StorageDb {
    private static final String TAG = "data";
    Context context;
    public StorageDb(Context context){
        this.context = context;

    }

    public void save(ArrayList<ListDetails> listDetails){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listDetails);
        editor.putString(TAG, json);
        editor.apply();
    }

    public ArrayList<ListDetails> retrieve(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        String json = sharedPrefs.getString(TAG, null);

        Type type = new TypeToken<ArrayList<ListDetails>>() {}.getType();
        ArrayList<ListDetails> list = gson.fromJson(json, type);
        if(list == null){
            list = new ArrayList<>();
        }
        return list;
    }
}
