package edu.sjsu.android.threaddownloader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

class ThreadHandler extends Handler {
    private ImageView temp;

    public ThreadHandler(ImageView image) {
        temp =image;
    }
    public void handleMessage(Message message) {
        if((Bitmap)message.obj!=null) {
            temp.setImageBitmap((Bitmap)message.obj);
        }
    }
}

public class MainActivity extends Activity {

    ThreadHandler handler;
    String urlString;
    ImageView imageView;
    Button runnableBTN,messagesBTN,asyncBTN,resetBTN;
    EditText urlText;

    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlText = (EditText) findViewById(R.id.urlText);
        imageView = (ImageView) findViewById(R.id.image);
        runnableBTN = findViewById(R.id.runnableBTN);
        messagesBTN = findViewById(R.id.messagesBTN);
        int imageResource = getResources().getIdentifier("@drawable/airplane", null, this.getPackageName());
        imageView.setImageResource(imageResource);
        resetBTN = findViewById(R.id.resetBTN);
        asyncBTN = findViewById(R.id.asyncBTN);
        asyncBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImage().execute(urlString);
            }
        });
    }
    Bitmap downloadBitmap (String url) throws IOException {
        Bitmap bitmap = null;
        URL theURL = new URL(urlText.getContext().toString());
        HttpURLConnection urlC = (HttpURLConnection) theURL.openConnection();
        try {
            InputStream input = new BufferedInputStream(urlC.getInputStream());
            bitmap = BitmapFactory.decodeStream(input);
        } catch(IOException e) {
            bitmap = null;

            handler.post(new Runnable()
            {
                public void run()
                {
                    (Toast.makeText(getApplicationContext(), "Download Failed.\nInvalid Image URL.", Toast.LENGTH_LONG)).show();
                }
            });
        }
        finally {
            urlC.disconnect();
        }
        return bitmap;
    }
    private class DownloadImage extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Download Image Tutorial");
            progressDialog.setMessage("Loading...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            AtomicReference<Bitmap> bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap.set(BitmapFactory.decodeStream(input));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap.get();
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
            progressDialog.dismiss();
        }
    }

    public void runRunnable(View view) {

            new Thread(() -> {
                Bitmap bitmap = null;
                try {
                    bitmap = downloadBitmap(urlText.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap finalBitmap = bitmap;
                handler.post(() -> {
                    if (finalBitmap != null) {
                        imageView.setImageBitmap(finalBitmap);
                    }
                });
            }).start();
    }
    public void runMessages(View view) {
        new Thread(() -> {
            Bitmap temp = null;
            try {
                temp = downloadBitmap(urlText.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage(0,temp);
            handler.sendMessage(message);
        }).start();
    }

    public void resetImage(View view) throws IOException {
        int imageResource = getResources().getIdentifier("@drawable/airplane", null, this.getPackageName());
        imageView.setImageResource(imageResource);
            }
}

