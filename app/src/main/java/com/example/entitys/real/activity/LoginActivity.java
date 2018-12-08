package com.example.entitys.real.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entitys.real.R;
import com.example.entitys.real.http.Login;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences setting;
    SharedPreferences.Editor editor;

    Button buttonLogin;
    EditText editTextID;
    EditText editTextPW;

    String id = "";
    String pw = "";
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("로그인");

        setting = getSharedPreferences("setting", 0);
        editor = setting.edit();

        editTextID = findViewById(R.id.editTextID);
        editTextPW = findViewById(R.id.editTextPW);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        editTextPW.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    id = editTextID.getText().toString().trim();
                    pw = editTextPW.getText().toString().trim();
                    token = FirebaseInstanceId.getInstance().getToken();

                    int login_check = 0;

                    try {
                        login_check = new Login().execute(id, pw, token).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    if(login_check == 0){

                        Toast toast = Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT);
                        editTextPW.setText("");
                        toast.show();
                    }

                    else if (login_check == 1){

                        editor.putString("id", editTextID.getText().toString().trim());
                        editor.putString("pw", editTextPW.getText().toString().trim());
                        editor.putString("token", token);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intent);
                        //dialog.dismiss();
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = editTextID.getText().toString().trim();
                pw = editTextPW.getText().toString().trim();
                token = FirebaseInstanceId.getInstance().getToken();

                int login_check = 0;

                try {
                    //dialog = ProgressDialog.show(getApplicationContext(), "로그인중...", "Please wait...", true);
                    login_check = new Login().execute(id, pw, token).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                if(login_check == 0){

                    Toast toast = Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요!", Toast.LENGTH_SHORT);
                    editTextPW.setText("");
                    toast.show();
                }

                else if (login_check == 1){

                    editor.putString("id", editTextID.getText().toString().trim());
                    editor.putString("pw", editTextPW.getText().toString().trim());
                    editor.putString("token", token);
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                    startActivity(intent);
                    //dialog.dismiss();
                    finish();
                }
            }
        });
    }
}