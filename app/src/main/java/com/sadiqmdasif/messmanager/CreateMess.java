package com.sadiqmdasif.messmanager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class CreateMess extends AppCompatActivity {

    EditText txtMessName,txtMessMember;
    Button btnCreateMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__mess);

        txtMessName = (EditText) findViewById(R.id.editTextMessName);
        txtMessMember = (EditText) findViewById(R.id.editTextMessMember);
        btnCreateMess = (Button) findViewById(R.id.buttonMessRegCreate);
        btnCreateMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJSON();
            }
        });
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CreateMess.this,"Creating Mess","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

            }

            @Override
            protected String doInBackground(Void... params) {

                createMess();
                return "";
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    void createMess(){

        String postReceiverUrl = Config_DBConnection.URL_ADD;
        //Log.v(TAG, "postURL: " + postReceiverUrl);

// HttpClient
        HttpClient httpClient = new DefaultHttpClient();

// post header
        HttpPost httpPost = new HttpPost(postReceiverUrl);

// add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("name", txtMessName.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("member", txtMessMember.getText().toString()));

        try {


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

// execute HTTP post request
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                //  Log.v(TAG, "Response: " +  responseStr);

                // you can add an if statement here and do other actions based on the response
            }
        }

        catch (Exception e){
            Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT);
        }
    }
}
