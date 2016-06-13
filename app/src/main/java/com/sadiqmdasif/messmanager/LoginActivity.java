package com.sadiqmdasif.messmanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin;
    private String JSON_STRING;
    private boolean validUser=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.signInID);
        password = (EditText) findViewById(R.id.signInPassword);
        btnLogin = (Button) findViewById(R.id.buttonSignIN);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON();
                if (validUser)
                {
                    Intent intent = new Intent(getApplicationContext(),RegisterMess.class);
                    startActivity(intent);
                }

            }
        });


    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                checkUser();
            }

            @Override
            protected String doInBackground(Void... params) {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httppost = new HttpGet(Config_DBConnection.URL_GET_USERS);
                HttpResponse response = null;
                String str = null;
                try {
                    response = httpclient.execute(httppost);
                    str = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return str;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void checkUser(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config_DBConnection.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String name="name";
                String pass="pass";
                String user = jo.getString(name);
                String pwd = jo.getString(pass);

                if (user.equals(username.getText().toString()) && pwd.equals(password.getText().toString())){
                    Intent intent = new Intent(getApplicationContext(),RegisterMess.class);
                    startActivity(intent);
                    break;
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}