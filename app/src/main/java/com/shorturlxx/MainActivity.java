package com.shorturlxx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class    MainActivity extends AppCompatActivity {

    public class ShortenerAssync extends AsyncTask<String,String,String>{
        int number;
        String shortenedUrl = "";
        final Context context;
        public ShortenerAssync(Context context,int number) {
            this.number = number;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... url) {
            switch (number) {
                case 0:
                    try {
                        OkHttpClient client = new OkHttpClient();
                        Request request1 = new Request.Builder().url("https://tinyurl.com/api-create.php?url="+url[0])
                                .get()
                                .build();
                        Response response1 = client.newCall(request1).execute();
                        shortenedUrl = response1.body().string();
                        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                        String date = df.format(Calendar.getInstance().getTime());
                        if(!shortenedUrl.equals("Error")){
                            details.add(0,new ListDetails(url[0],shortenedUrl,date));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
                case 1:
                    try {
                        java.util.Scanner s = new java.util.Scanner(
                                new java.net.URL(
                                        "https://is.gd/create.php?format=simple&url=" + url[0])
                                        .openStream(), "UTF-8")
                                .useDelimiter("\\A");
                        shortenedUrl = s.next();
                        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                        String date = df.format(Calendar.getInstance().getTime());
                        if(!shortenedUrl.equals("Error")){
                            details.add(0,new ListDetails(url[0],shortenedUrl,date));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        java.util.Scanner s = new java.util.Scanner(
                                new java.net.URL(
                                        "https://4h.net/api/?url=" + url[0])
                                        .openStream(), "UTF-8")
                                .useDelimiter("\\A");
                        shortenedUrl = s.next();
                        DateFormat df = new SimpleDateFormat("d MMM yyyy, HH:mm");
                        String date = df.format(Calendar.getInstance().getTime());
                        if(!shortenedUrl.equals("Invalid URL")){
                            details.add(0,new ListDetails(url[0],shortenedUrl,date));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;




            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            storageDb.save(details);
            customListAdapter = new CustomListAdapter(MainActivity.this,R.layout.custom_link,details);
            listView.setAdapter(customListAdapter);
        }
    }












    private Spinner spinner;
    private int chosen;
    private Button button;
    private String url;
    EditText linkUrl;
    CustomListAdapter customListAdapter;
    ArrayList<ListDetails> details = new ArrayList<>();
    ListView listView;
    StorageDb storageDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storageDb = new StorageDb(this);
        details = storageDb.retrieve();

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button);
        linkUrl = findViewById(R.id.url);

        customListAdapter = new CustomListAdapter(MainActivity.this,R.layout.custom_link,details);
        listView = findViewById(R.id.lista);
        

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.url_shorteners, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        chosen = 0;
                        break;
                    case 1:
                        chosen = 1;
                        break;
                    case 2:
                        chosen = 2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             chosen=0;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (chosen){
                    case 0:
                        url = linkUrl.getText().toString();
                        ShortenerAssync tinyUrl = new ShortenerAssync(MainActivity.this,0);
                        tinyUrl.execute(url);
                        break;
                    case 1:
                        url = linkUrl.getText().toString();
                        ShortenerAssync isgd = new ShortenerAssync(MainActivity.this,1);
                        isgd.execute(url);
                        break;
                    case 2:
                        url = linkUrl.getText().toString();
                        ShortenerAssync fourh = new ShortenerAssync(MainActivity.this,2);
                        fourh.execute(url);
                        break;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    protected void onResume() {
        super.onResume();
        customListAdapter = new CustomListAdapter(MainActivity.this,R.layout.custom_link,storageDb.retrieve());
        listView.setAdapter(customListAdapter);
    }


}