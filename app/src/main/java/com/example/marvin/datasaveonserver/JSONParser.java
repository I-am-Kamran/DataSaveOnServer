package com.example.marvin.datasaveonserver;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by MARVIN on 18-03-2018.
 */

public class JSONParser
{
    static InputStream inputStream=null;
    static JSONObject jsonObject=null;
    static String son="";

    public JSONParser()
    {}

    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> parms)
    {
        try
        {
            if(method=="POST")
            {
                DefaultHttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(parms));
                HttpResponse httpResponse=httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                inputStream=httpEntity.getContent();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);
            StringBuilder stringBuilder = new StringBuilder();
            String line=null;

            while ((line=reader.readLine())!=null)
            {
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            son=stringBuilder.toString();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            jsonObject=new JSONObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
