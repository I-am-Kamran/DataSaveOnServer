package com.example.marvin.datasaveonserver;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    EditText editTextname,editTextPhone,editTextAddress;
    Button buttonSubmit;
    String name,contact,address;
    ProgressDialog progressDialog;
    JSONParser jsonParser;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextname=findViewById(R.id.editTextName);
        editTextPhone=findViewById(R.id.editText2Contact);
        editTextAddress=findViewById(R.id.editText3Address);
        buttonSubmit=findViewById(R.id.button);

        jsonParser=new JSONParser();
        url="https://tasktodo.000webhostapp.com/set.php";

        buttonSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name=editTextname.getText().toString();
                contact=editTextPhone.getText().toString();
                address=editTextAddress.getText().toString();

                new CreateNewProduct().execute();

            }
        });
    }

    public class CreateNewProduct extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            List<NameValuePair> parms = new ArrayList<NameValuePair>();
            parms.add(new BasicNameValuePair("name",name));
            parms.add(new BasicNameValuePair("contact",contact));
            parms.add(new BasicNameValuePair("address",address));
            JSONObject jObj=jsonParser.makeHttpRequest(url,"POST",parms);
            Log.d("CREATE RESPONSE",jObj.toString());

            try
            {
                int success=jObj.getInt("TAG SUCCESSFULLY");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progressDialog.dismiss();
            editTextname.setText("");
            editTextPhone.setText("");
            editTextAddress.setText("");
            Toast.makeText(MainActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("SAVED DATA");
            progressDialog.show();
        }
    }
}
