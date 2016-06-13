package com.sadiqmdasif.messmanager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JoinMess extends AppCompatActivity implements ListView.OnItemClickListener{

    ListView listMess;
    EditText txtSearchMess;
    Button btnMessSearch;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join__mess);

        txtSearchMess = (EditText)findViewById(R.id.editTextMessSearch);
        btnMessSearch = (Button) findViewById(R.id.buttonMessSearch);
        listMess = (ListView) findViewById(R.id.listViewMessSearchResult);
        listMess.setOnItemClickListener(this);

        btnMessSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMess();
            }
        });

    }

    void searchMess(){

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config_DBConnection.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config_DBConnection.TAG_ID);
                String name = jo.getString(Config_DBConnection.TAG_NAME);
                String member = jo.getString(Config_DBConnection.TAG_MEMBER);


                HashMap<String,String> mess = new HashMap<>();
                mess.put(Config_DBConnection.TAG_ID,id);
                mess.put(Config_DBConnection.TAG_NAME,name);
                mess.put(Config_DBConnection.TAG_MEMBER,member);

                list.add(mess);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


     ListAdapter adapter = new SimpleAdapter(
                JoinMess.this, list, R.layout.list_item,
                new String[]{Config_DBConnection.TAG_ID,Config_DBConnection.TAG_NAME, Config_DBConnection.TAG_MEMBER},
                new int[]{R.id.id,R.id.name, R.id.member});

        listMess.setAdapter(adapter);
    }

    private void getMess(){
        class GetMess extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinMess.this,"Fetching Mess List","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config_DBConnection.URL_GET_MESS);
                return s;
            }
        }
        GetMess gj = new GetMess();
        gj.execute();
    }

    private void messJoin(final String mess){
        class MessJoin extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(JoinMess.this,"Joining Mess","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config_DBConnection.URL_GET_MESS);
                joinMess(mess);
                return s;
            }
        }
        MessJoin gj = new MessJoin();
        gj.execute();
    }
    void joinMess(String messID){

        String postReceiverUrl = Config_DBConnection.URL_ADD_MEMBER_TO_MESS;
        //Log.v(TAG, "postURL: " + postReceiverUrl);

// HttpClient
        HttpClient httpClient = new DefaultHttpClient();

// post header
        HttpPost httpPost = new HttpPost(postReceiverUrl);

// add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("name", "test"));
        nameValuePairs.add(new BasicNameValuePair("messID", messID));

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
            //Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        final String messID = map.get(Config_DBConnection.TAG_ID).toString();
        String messName = map.get(Config_DBConnection.TAG_NAME).toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to join with "+messName+" ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    messJoin(messID);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
