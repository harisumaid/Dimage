package com.firstone.dimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView myImage;

    public class Downloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection =(HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                Bitmap myBitmap= BitmapFactory.decodeStream(inputStream);

                return myBitmap;

            }
                catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this, "Error while loading the image", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myImage=findViewById(R.id.myImage);

    }
   public void action(View view) throws ExecutionException, InterruptedException {
        Downloader task=new Downloader();
        Bitmap getImage=task.execute("https://cdn.images.express.co.uk/img/dynamic/78/590x/secondary/Tom-and-Jerry-533128.jpg").get();
        myImage.setImageBitmap(getImage);
    }
}
