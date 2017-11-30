package com.pplus.android.androidchatpplusv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "socket";
    Socket socket = new Socket();
    TextView txtName;
    EditText edtMess;
    Button btnSubmit;
    BufferedReader is;
    BufferedWriter os;
    String name = "Default user";
    ListView lvMess;
    ArrayList<String> lsMess = new ArrayList<>();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        initDisplay();
        initEvents();

    }

    private void initEvents() {
        btnSubmit.setOnClickListener(this);
        txtName.setOnClickListener(this);
    }

    private void initDisplay() {
        txtName.setText(name);
    }

    private void initControls() {
        mapped();

//        new connect().execute();

        new connectServer().start();
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, lsMess);
        lvMess.setAdapter(adapter);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");

    }

    private void mapped() {
        txtName = (TextView) findViewById(R.id.txtName);
        edtMess = (EditText) findViewById(R.id.edtInputMess);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        lvMess = (ListView) findViewById(R.id.listViewMess);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsubmit:
                new putmess().start();
                break;
            case R.id.txtName:
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new connect().execute();
                Toast.makeText(this, "Reconnect Server", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class putmess extends Thread {
        @Override
        public void run() {
            try {
                Log.d(TAG, "putting mess");
                String putMess = edtMess.getText().toString().trim();
                os.write(name + ": " + putMess);
                os.newLine();
                os.flush();
                Log.d(TAG, "put mess conplete");
                adapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "read mess\n" + e);
            }

        }
    }

    private class connect extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket("192.168.1.10", 3000);
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                os.write("Userconnect: " + name);
                os.newLine();
                os.flush();
                Log.d(TAG, "connect sucess");
                String inMess;
                while (true) {
                    inMess = is.readLine();
                    publishProgress(inMess);

                }
            } catch (Exception e) {
                Log.d(TAG, "error: " + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class connectServer extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                socket = new Socket("192.168.1.10", 3000);
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                os.write("Userconnect: " + name);
                os.newLine();
                os.flush();
                Log.d(TAG, "connect sucess");
                String inMess;
                while (true) {
                    inMess = is.readLine();
                    lsMess.add(inMess);
                    Toast.makeText(MainActivity.this, "New message: " + inMess, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Server: " + inMess);
                    adapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                Log.d(TAG, "error: " + e);
            }
        }
    }
}
