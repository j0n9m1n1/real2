package com.example.entitys.real.http;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class GetReport extends AsyncTask<String, Void, String> {

    @Override protected void onPreExecute() {super.onPreExecute();}

    @Override
    protected String doInBackground(String... user_info) {

        String response = "";

        try {
            URL url = new URL("http://ec2-52-78-239-241.ap-northeast-2.compute.amazonaws.com:7121/get_report");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true); // 아웃풋 함
            conn.setDoInput(true); // 인풋 받음
            conn.setRequestMethod("POST"); // 요청 타입 POST
            conn.setRequestProperty("Accept", "application/text; charset=utf-8"); // 응답 타입 jSON

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("lms_id", user_info[0])
                    .appendQueryParameter("lms_pw", user_info[1]);

            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //reponse 200일때
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String inputLine;

                StringBuffer reply = new StringBuffer();

                while ((inputLine = in.readLine()) != null)
                    reply.append(inputLine);

                in.close();
                response = reply.toString();

            } else {
                //response 404, 500등등등
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            e.printStackTrace();
            // ...
        }

        return response;
    }
}
