package com.santiago.soquetescodigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class AdaptadorMensaje extends BaseAdapter {
    public List<String> mensajes;
    Context context;

    public AdaptadorMensaje(List<String> mensajes, Context context) {
        this.mensajes = mensajes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return mensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_mensaje,null);
        TextView tv = v.findViewById(R.id.textView);
        tv.setText(mensajes.get(position));
        return v;
    }
}
