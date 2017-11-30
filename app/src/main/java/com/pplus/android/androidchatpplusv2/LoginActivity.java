package com.pplus.android.androidchatpplusv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtName;
    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControls();
        initEvents();
    }

    private void initEvents() {
        btnJoin.setOnClickListener(this);
    }

    private void initControls() {
        edtName = (EditText) findViewById(R.id.edtName);
        btnJoin = (Button) findViewById(R.id.btnLogin);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", edtName.getText().toString().trim());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }

}
