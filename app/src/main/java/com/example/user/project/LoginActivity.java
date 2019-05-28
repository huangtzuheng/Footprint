package com.example.user.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText login_name,login_password;
    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        SharedPreferences remdname=getPreferences(LoginActivity.MODE_PRIVATE);
//        String name_str=remdname.getString("name", "");
//        String pass_str=remdname.getString("pass", "");
//        login_name.setText(name_str);
//        login_password.setText(pass_str);
    }
    public void forgetPS(View view){
        AlertDialog ag = new AlertDialog.Builder(this)
                .setTitle("密碼提示")
                .setMessage("4321")
                .setPositiveButton("OK", null)
                .show();}

    public void sendString(View view) {

        Intent intent = new Intent(this, Login2Activity.class);
        login_name = (EditText) findViewById(R.id.input_email);
        login_password = (EditText) findViewById(R.id.input_password);
        String stringID = login_name.getText().toString();
        String seringPS = login_password.getText().toString();
        if (login(stringID, seringPS) == true) {
//            Toast t =new Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            intent.putExtra("userID", stringID);
            intent.putExtra("userPS", seringPS);
            SharedPreferences remdname=getPreferences(LoginActivity.MODE_PRIVATE);
            SharedPreferences.Editor edit=remdname.edit();
            edit.putString("name", login_name.getText().toString());
            edit.putString("pass", login_password.getText().toString());
            edit.commit();
            startActivity(intent);
        } else {
//            AlertDialog ag = new AlertDialog.Builder(this)
//                    .setTitle("WARING")
//                    .setMessage("登入失敗")
//                    .setPositiveButton("OK", null)
//                    .show();
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            //            intent.putExtra("userID","ERROR");
            //        intent.putExtra("userPS","ERROR");
            //        startActivity(intent);}


        }
    }
    protected void onPause(){
        super.onPause();
        SharedPreferences remdname=getPreferences(LoginActivity.MODE_PRIVATE);
        SharedPreferences.Editor edit=remdname.edit();
        edit.putString("name", login_name.getText().toString());
        edit.putString("pass", login_password.getText().toString());
        edit.commit();


    }
    public boolean login(String id,String ps){
        if (id.equals("ntust") && ps.equals("1234")){ //登入成功
            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
            return true;
        }else {
            AlertDialog ag = new AlertDialog.Builder(this)
                    .setTitle("WARING")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK", null)
                    .show();
            return false;}
    }
}
