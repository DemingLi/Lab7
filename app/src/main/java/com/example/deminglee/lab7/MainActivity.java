package com.example.deminglee.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final EditText new_password = (EditText) findViewById(R.id.new_password);
    final EditText confirm_password = (EditText) findViewById(R.id.confirm_password);
    Button ok_password = (Button) findViewById(R.id.ok_password);
    Button clear_password = (Button) findViewById(R.id.clear_password);
    
    sharedPreferences = getSharedPreferences("KEY", MODE_PRIVATE);
    final String key = sharedPreferences.getString("KEY_SCORE", "n");
    
    if (key.equals("n")) {//初始状态
      ok_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (TextUtils.isEmpty(new_password.getText().toString()) || TextUtils.isEmpty(confirm_password.getText().toString())) {
            Toast.makeText(MainActivity.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
          }//password is empty
          else if (!new_password.getText().toString().equals(confirm_password.getText().toString())) {
            Toast.makeText(MainActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
          }//password is not match
          else {
            editor = sharedPreferences.edit();
            editor.putString("KEY_SCORE", new_password.getText().toString());
            editor.apply();
            Intent intent = new Intent(MainActivity.this, FileEdit.class);
            startActivity(intent);
          }
        }
      });
      clear_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          new_password.setText("");
          confirm_password.setText("");
        }
      });
    } else {//再次进入
      confirm_password.setVisibility(GONE);
      new_password.setHint("Password");
      ok_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (new_password.getText().toString().equals(key)) {
            Intent intent = new Intent(MainActivity.this, FileEdit.class);
            startActivity(intent);
          } else {
            Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
          }
    
        }
      });
      clear_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          new_password.setText("");
        }
      });
    }
    
  }
}
