package com.santiago.soquetescodigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Chat extends AppCompatActivity {
    ListView list_mensajes;
    List<String> mensajes;
    AdaptadorMensaje adaptadorMensaje;
    Socket mSocket;
    Button button;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        list_mensajes = findViewById(R.id.mensajes);
        button = findViewById(R.id.btnMensaje);
        text = findViewById(R.id.editMensaje);
        mensajes = new LinkedList<String>();
        adaptadorMensaje = new AdaptadorMensaje(mensajes,this);
        list_mensajes.setAdapter(adaptadorMensaje);
        SoquetesCodigo app = (SoquetesCodigo) getApplication();
        mSocket = app.getSocket();
        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String mensaje;

                    try {
                        mensaje = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }
                    mensajes.add(mensaje);
                    adaptadorMensaje.notifyDataSetChanged();
                }
            });
        }
    };

    private void attemptSend() {

        String message = text.getText().toString().trim();


        text.setText("");
        addMessage("CharlieBrownie", message);

        // perform the sending message attempt.
        mSocket.emit("new message", message);
    }

    private void addMessage(String username, String message) {
        mensajes.add(message);
        adaptadorMensaje.notifyDataSetChanged();
    }
}
