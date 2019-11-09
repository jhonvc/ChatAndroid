package com.santiago.soquetescodigo;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SoquetesCodigo extends Application {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://172.23.8.44:3000/");
        } catch (URISyntaxException e) {}
    }

    public Socket getSocket()
    {
        return mSocket;
    }
}
