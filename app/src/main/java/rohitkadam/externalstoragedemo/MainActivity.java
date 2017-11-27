package rohitkadam.externalstoragedemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText editTextData;
    Button buttonAdd,buttonView,buttonDelete,buttonClear;
    String filename="rohit.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextData = findViewById(R.id.editTextData);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonView = findViewById(R.id.buttonView);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonClear=findViewById(R.id.buttonClear);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

       buttonView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               view();
           }
       });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
        permissionCheck();
    }
        public void permissionCheck(){
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(MainActivity.this,permissions,123);
                return;
            }

    }
    public  void add(){
        String state= Environment.getExternalStorageState();
        if(state == Environment.MEDIA_MOUNTED){

        }
        if(state== Environment.MEDIA_MOUNTED_READ_ONLY){
            buttonAdd.setEnabled(false);
        }
        File root=Environment.getExternalStorageDirectory();
        File newFile=new File(root,filename);


                try {
                    if( ! newFile.exists())
                    newFile.createNewFile();
                     String fileContent=editTextData.getText().toString();
                        FileOutputStream  fileOutputStream=new FileOutputStream(newFile);
                        fileOutputStream.write(fileContent.getBytes());
                        fileOutputStream.close();
                        Log.d("ExternalStorage","Content is Written");



            } catch (IOException e) {
                    e.printStackTrace();
                }
    }
    public  void  view(){


        String state=Environment.getExternalStorageState();
        if(state==Environment.MEDIA_MOUNTED)
        {

        }
        if(state == Environment.MEDIA_MOUNTED_READ_ONLY){

        }
        File root=Environment.getExternalStorageDirectory();
        File newFile=new File(root,filename);
        try {

                FileInputStream fileInputStream = new FileInputStream(newFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String line = bufferedReader.readLine();
                while (line != null) {

                    editTextData.append(line);
                    line = bufferedReader.readLine();
                }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void delete(){
        File root=Environment.getExternalStorageDirectory();
        File newFile=new File(root,filename);

        newFile.delete();
         Log.d("MainActivity","File Deleted Successfully");
    }
    public  void clear(){
        editTextData.setText("");
    }
}
