package com.example.deminglee.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEdit extends AppCompatActivity {
  private String file_name_all;
  private EditText file_name;
  private EditText file_content;
  private Button save;
  private Button load;
  private Button clear;
  private Button delete;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_edit);
    file_name = (EditText) findViewById(R.id.file_name);
    file_content = (EditText) findViewById(R.id.file_content);
    save = (Button) findViewById(R.id.save_button);
    load = (Button) findViewById(R.id.load_button);
    clear = (Button) findViewById(R.id.clear_button);
    delete = (Button) findViewById(R.id.delete_button);
    file_name_all = file_name.getText().toString()+".txt";
    
    save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try(FileOutputStream fileOutputStream = openFileOutput(file_name_all, MODE_PRIVATE)) {
          String content = file_content.getText().toString();
          fileOutputStream.write(content.getBytes());
          fileOutputStream.close();
          Log.i("TAG", "Successfully saved file");
          Toast.makeText(FileEdit.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
          e.printStackTrace();
          Log.e("TAG", "Fail to save file");
        }
      }
    });
    
    load.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try(FileInputStream fileInputStream = openFileInput(file_name_all)) {
          byte[] contents = new byte[fileInputStream.available()];
          int read_result = fileInputStream.read(contents);
          file_content.setText(new String(contents));
          Log.i("TAG", "Successfully load");
          Toast.makeText(FileEdit.this, "Load Successfully", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
          Log.e("TAG", "Fail to load file");
          Toast.makeText(FileEdit.this, "Fail to load file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
          Log.e("TAG", "Fail to read");
          e.printStackTrace();
        }
      }
    });
    
    clear.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        file_content.setText("");
      }
    });
    
    delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        File dir = getFilesDir();
        File file = new File(dir, file_name_all);
        boolean deleted = file.delete();
        
        if (deleted) {
          Toast.makeText(FileEdit.this, "Delete successfully", Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(FileEdit.this, "Fail to delete file", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
  
  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
