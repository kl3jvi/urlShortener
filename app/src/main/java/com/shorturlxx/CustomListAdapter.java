package com.shorturlxx;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<ListDetails> {

    private final Context mcontext;
    private int res;

    public CustomListAdapter(Context context, int lan_list_scan_layout, ArrayList<ListDetails> details) {
        super(context,lan_list_scan_layout,details);
        mcontext=context;
        res = lan_list_scan_layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String first = getItem(position).getFirst();
        String sec = getItem(position).getSecond();
        String date = getItem(position).getDate();

        ListDetails lanDetails = new ListDetails(first,sec,date);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(res,parent,false);

        TextView dateTxt = convertView.findViewById(R.id.date);
        TextView firstTxt = convertView.findViewById(R.id.first);
        TextView secTxt = convertView.findViewById(R.id.sec);
        Button copy = convertView.findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mcontext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", sec);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mcontext,"Link copied to clipboard",Toast.LENGTH_SHORT).show();
            }
        });
        dateTxt.setText(date);
        firstTxt.setText(first);
        secTxt.setText(sec);

        return convertView;



    }

}
