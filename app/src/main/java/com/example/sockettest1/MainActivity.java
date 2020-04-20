package com.example.sockettest1;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_server_ip;
    private EditText et_server_port;
    private TextView tv_log;
    private TextView tv_local_ip;
    private EditText editsend;
    private Button btnsend;
    private Button btn_connect;
    private Button btn_disconnect;
    private Button btn_clear;
    private static String HOST = "192.168.4.1";
    private static int PORT = 8000;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private String local_ip;

    private Button btn_switch_panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        tv_log = (TextView) findViewById(R.id.tvLog);
        tv_local_ip = (TextView) findViewById(R.id.tvLocalIP);
        editsend = (EditText) findViewById(R.id.msg_send);
        et_server_ip = (EditText) findViewById(R.id.etServerIP);
        btnsend = (Button) findViewById(R.id.btn_send);
        btn_clear = (Button) findViewById(R.id.btnClear);
        btn_connect = (Button) findViewById(R.id.btnConnect);
        btn_disconnect = (Button) findViewById(R.id.btnDisconnect);
        et_server_ip.setEnabled(true);
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    tv_log.append(content);
                    break;

                case 0x124:
                    tv_local_ip.append(local_ip);
                    break;
                case 0x125:
                    editsend.setEnabled(true);
                    btnsend.setEnabled(true);
                    btn_clear.setEnabled(true);
                    btn_disconnect.setEnabled(true);
                    btn_connect.setEnabled(false);
                    et_server_ip.setEnabled(false);
                    Toast.makeText(MainActivity.this, "连接服务端成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x126:
                    Toast.makeText(MainActivity.this, "连接服务端失败", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };

    public void onConnect(View v) {
        Log.i("点击", "onConnect");

        new Thread() {
            @Override
            public void run() {
                try {
                    acceptServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void onDisconnect(View v) {
        Log.i("点击", "onDisconnect");
        editsend.setEnabled(false);
        btnsend.setEnabled(false);
        btn_clear.setEnabled(false);
        btn_disconnect.setEnabled(false);
        btn_connect.setEnabled(true);
        et_server_ip.setEnabled(true);
    }

    public void onClick_Clear(View v){
        tv_log.setText("");
    }
    public void onClickSwitchPanel(View v){
        Intent intent = new Intent(MainActivity.this, SwitchActivity.class);
        startActivity(intent);
    }

    public void onSend(View v) {
        Log.i("点击", "onSend");
        new Thread() {
            @Override
            public void run() {
                try {
                    SendThread();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void SendThread() throws IOException {
        try {
            out.println("Client:"+ editsend.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void acceptServer() throws IOException {
        try {
            HOST=et_server_ip.getText().toString();
            PORT=Integer.parseInt(et_server_port.getText().toString());
            socket = new Socket("192.168.1.101", 8000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
            InetAddress address = InetAddress.getLocalHost();
            local_ip = address.getHostAddress();
            handler.sendEmptyMessage(0x124);
            handler.sendEmptyMessage(0x125);
            ((MyApplication)getApplication()).setSocket(socket);

            while (true) {
                if (socket.isConnected()) {
                    if (!socket.isInputShutdown()) {
                        if((content = in.readLine()) != null){
                            content += "\n";
                            handler.sendEmptyMessage(0x123);
                        }
                        }
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
            handler.sendEmptyMessage(0x126);
        }
    }
}
