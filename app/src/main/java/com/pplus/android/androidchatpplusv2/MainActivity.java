package com.pplus.android.androidchatpplusv2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "socket";
    Socket socket = new Socket();
    TextView txtMess;
    EditText edtMess;
    Button btnSubmit;
    BufferedReader is;
    BufferedWriter os;


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
    }

    private void initDisplay() {

    }

    private void initControls() {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        mapped();
        new connect().execute();


//        if (ContextCompat.checkSelfPermission(getApplicationContext(),
//                Manifest.permission.INTERNET)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getApplicationContext(),
//                    Manifest.permission.INTERNET)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions((Activity) getApplicationContext(),
//                        new String[]{Manifest.permission.READ_CONTACTS},
//                        1);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//        }


    }

    private void mapped() {
        txtMess = (TextView) findViewById(R.id.txtMess);
        edtMess = (EditText) findViewById(R.id.edtInputMess);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsubmit:
                new putmess().start();
                break;
        }
    }

    private class putmess extends Thread{
        @Override
        public void run() {
            try {
                Log.d(TAG, "putting mess");
                String putMess = edtMess.getText().toString().trim();
                os.write(putMess);
                os.newLine();
                os.flush();
                Log.d(TAG, "put mess conplete");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "read mess\n"+e);
            }

        }
    }

    private class connect extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                socket = new Socket("10.200.200.244", 3000);
                is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Log.d(TAG, "connect sucess");
                String inMess;
                while(true){
                    inMess = is.readLine();
                    Log.d(TAG, "Server: " + inMess);
                }
            } catch (IOException e) {
                Log.d(TAG, "error: "+ e);
            }


            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
