package com.example.entitys.real.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.entitys.real.R;
import com.example.entitys.real.http.Login;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    //ProgressDialog dialog = null;

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
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        id = editTextID.getText().toString().trim();
        pw = editTextPW.getText().toString().trim();
        token = FirebaseInstanceId.getInstance().getToken();

        editor.putString("id", editTextID.getText().toString().trim());
        editor.putString("pw", editTextPW.getText().toString().trim());
        editor.commit();

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
    private boolean validateID(){

        id = editTextID.getText().toString().trim();

        if(id.isEmpty()){
            editTextID.setError("아이디를 입력해주세요!");
            return false;
        } else {
            editTextID.setError(null);
            return true;
        }
    }

    private boolean validatePW(){

        pw = editTextPW.getText().toString().trim();

        if(pw.isEmpty()){
            editTextPW.setError("비밀번호를 입력해주세요!");
            return false;
        } else {
            editTextPW.setError(null);
            return true;
        }
    }

    public void loginCheck(View v){

        id = editTextID.getText().toString().trim();
        pw = editTextPW.getText().toString().trim();
        //
        // System.out.println(ID + "\n" + PW);
        //getInfo(ID, PW);
        int isStudent = 1;
        if(isStudent==0){


        }

        else if(isStudent==1){

            Intent intent = new Intent(this, ReportActivity.class);
            intent.putExtra("state", "launch");
            startActivity(intent);
            finish();

        }
    }
}