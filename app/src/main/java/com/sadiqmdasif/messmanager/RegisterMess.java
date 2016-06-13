package com.sadiqmdasif.messmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegisterMess extends AppCompatActivity {

    Button btnCreate;
    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mess);

        btnCreate = (Button) findViewById(R.id.buttonMessRegCreate);
        btnJoin = (Button) findViewById(R.id.buttonMessRegJoin);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateMess.class);
                startActivity(intent);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),JoinMess.class);
                startActivity(intent);
            }
        });

    }
}
