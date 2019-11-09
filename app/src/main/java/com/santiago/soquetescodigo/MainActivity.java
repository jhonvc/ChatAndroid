package com.santiago.soquetescodigo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;


public class MainActivity extends AppCompatActivity {

    Socket mSocket;
    Button btnIngresar;
    TextInputLayout txtIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIngresar = findViewById(R.id.btn_ingresar);
        txtIngresar = findViewById(R.id.txt_ingresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("add user",
                        txtIngresar.getEditText().getText().toString() );
                Intent i =  new Intent(getApplicationContext(), Chat.class);
                startActivity(i);

            }
        });
        SoquetesCodigo app = (SoquetesCodigo) getApplication();
        mSocket = app.getSocket();
        mSocket.on("login", onLogin);
        mSocket.connect();

    }

    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int cantidad;

                    try {
                        cantidad = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    Toast.makeText(getApplicationContext(),
                            cantidad + " ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
