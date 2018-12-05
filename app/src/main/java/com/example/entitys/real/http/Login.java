package com.example.entitys.real.http;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AsyncTask<String, Void, Integer> {

    @Override protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Integer doInBackground(String... user_info) {

        int login_check = 0;

        try {
            URL url = new URL("http://ec2-52-78-239-241.ap-northeast-2.compute.amazonaws.com:7121/login");
//            URL url = new URL("http://123.214.121.100/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true); // 아웃풋 함
            conn.setDoInput(true); // 인풋 받음
            conn.setRequestMethod("POST"); // 요청 타입 POST
//            conn.setRequestProperty("Accept", "application/json"); // 응답 타입 jSON
            conn.setRequestProperty("Accept", "charset=utf-8"); // 응답 타입 jSON
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("lms_id", user_info[0])
                    .appendQueryParameter("lms_pw", user_info[1])
                    .appendQueryParameter("token", user_info[2]);

            String query = builder.build().getEncodedQuery();
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();



//            System.out.println("in doInBackgorund: " + sb);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //reponse 200일때
                String reply;
                InputStream in = conn.getInputStream();
                StringBuffer sb = new StringBuffer();

                try {
                    int chr;
                    while ((chr = in.read()) != -1) {
                        sb.append((char) chr);
                    }
                    reply = sb.toString();
                    login_check = Integer.parseInt(reply);

                } finally {
                    in.close();
                }
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

        return login_check;
    }

    protected void onProgressUpdate(Integer... progress) {
        // 로딩 퍼센트?
    }

    protected void onPostExcuted(){
        // doinbackground에서 끝나면 서버에서 json 받아오기
    }
}
