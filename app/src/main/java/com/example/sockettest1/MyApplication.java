package com.example.sockettest1;

import android.app.Application;
import android.content.Context;

import java.net.Socket;

public class MyApplication extends Application{
    Socket socket = null;
    public Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
}
