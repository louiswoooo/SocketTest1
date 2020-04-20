package com.example.sockettest1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class SwitchActivity extends AppCompatActivity {

    private ImageView img_light1;
    private ImageView img_light2;
    private ImageView img_light3;
    private ImageView img_light4;

    private ImageView img_switch1;
    private ImageView img_switch2;
    private ImageView img_switch3;
    private ImageView img_switch4;

    private boolean switch1_flag;
    private boolean switch2_flag;
    private boolean switch3_flag;
    private boolean switch4_flag;

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        img_light1=(ImageView)findViewById(R.id.imgLight1);
        img_light2=(ImageView)findViewById(R.id.imgLight2);
        img_light3=(ImageView)findViewById(R.id.imgLight3);
        img_light4=(ImageView)findViewById(R.id.imgLight4);

        img_switch1=(ImageView)findViewById(R.id.imgSwitch1);
        img_switch2=(ImageView)findViewById(R.id.imgSwitch2);
        img_switch3=(ImageView)findViewById(R.id.imgSwitch3);
        img_switch4=(ImageView)findViewById(R.id.imgSwitch4);

        switch1_flag=false;
        switch2_flag=false;
        switch3_flag=false;
        switch4_flag=false;
        try {
            socket = ((MyApplication) getApplication()).getSocket();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

}



    private void SendThread1() throws IOException {
        try {
            if(switch1_flag==true)
                out.println("SWITCH 1 ON ##");
            else
                out.println("SWITCH 1 OFF ##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SendThread4() throws IOException {
        try {
            if(switch4_flag==true)
                out.println("SWITCH 4 ON ##");
            else
                out.println("SWITCH 4 OFF ##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void SendThread2() throws IOException {
        try {
            if(switch2_flag==true)
                out.println("SWITCH 2 ON ##");
            else
                out.println("SWITCH 2 OFF ##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void SendThread3() throws IOException {
        try {
            if(switch3_flag==true)
                out.println("SWITCH 3 ON ##");
            else
                out.println("SWITCH 3 OFF ##");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickSwitch1(View v)
    {
        if(switch1_flag == false)
        {
            img_switch1.setImageDrawable(getResources().getDrawable(R.drawable.on));
            img_light1.setImageDrawable(getResources().getDrawable(R.drawable.light11));
            switch1_flag=true;
        }
        else
        {
            img_switch1.setImageDrawable(getResources().getDrawable(R.drawable.off));
            img_light1.setImageDrawable(getResources().getDrawable(R.drawable.light22));
            switch1_flag=false;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    SendThread1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void onClickSwitch2(View v)
    {
        if(switch2_flag == false)
        {
            img_switch2.setImageDrawable(getResources().getDrawable(R.drawable.on));
            img_light2.setImageDrawable(getResources().getDrawable(R.drawable.light11));
            switch2_flag=true;
            try {
                out.println("SWITCH 2 ON ##");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            img_switch2.setImageDrawable(getResources().getDrawable(R.drawable.off));
            img_light2.setImageDrawable(getResources().getDrawable(R.drawable.light22));
            switch2_flag=false;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    SendThread2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void onClickSwitch3(View v)
    {
        if(switch3_flag == false)
        {
            img_switch3.setImageDrawable(getResources().getDrawable(R.drawable.on));
            img_light3.setImageDrawable(getResources().getDrawable(R.drawable.light11));
            switch3_flag=true;
        }
        else
        {
            img_switch3.setImageDrawable(getResources().getDrawable(R.drawable.off));
            img_light3.setImageDrawable(getResources().getDrawable(R.drawable.light22));
            switch3_flag=false;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    SendThread3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void onClickSwitch4(View v)
    {
        if(switch4_flag == false)
        {
            img_switch4.setImageDrawable(getResources().getDrawable(R.drawable.on));
            img_light4.setImageDrawable(getResources().getDrawable(R.drawable.light11));
            switch4_flag=true;
        }
        else
        {
            img_switch4.setImageDrawable(getResources().getDrawable(R.drawable.off));
            img_light4.setImageDrawable(getResources().getDrawable(R.drawable.light22));
            switch4_flag=false;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    SendThread4();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
