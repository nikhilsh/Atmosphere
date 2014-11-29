package com.biting.azure.learntotooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;


public class WiFi_Activity extends Activity {

    Bitmap imageBmp;

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelable("bmp", imageBmp);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        imageBmp = savedInstanceState.getParcelable("bmp");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(imageBmp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wi_fi_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pullImageFromQR(View view){
        Intent qrDroid = new Intent("la.droid.qr.scan");
        startActivityForResult(qrDroid, 0);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0){
            String result = data.getExtras().getString("la.droid.qr.result");
            loadImageFromUrl(result);
        }
    }

    public void pullImage(View view){
        //EditText  editText = (EditText) findViewById(R.id.editText);
        String url = "http://192.168.43.160:5000/static/2.png";
        try {
            new downloadFilesTask().execute(url);
        } catch(Exception e){
            Log.d("Error", e.toString());
            return;
        }

    }
    public void loadImageFromUrl(String url){
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Bitmap bmp = BitmapFactory.decodeStream(is);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bmp);
            String filename = url.substring(url.lastIndexOf("/"));

            FileOutputStream out = new FileOutputStream(filename);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);


        }catch(Exception e){
            Log.d("Error", e.toString());
            return;
        }

    }

    private class downloadFilesTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            for (String url : urls) {
                try {

                    InputStream is = (InputStream) new URL(url).getContent();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    //String filename = getParent().getParent().getFilesDir().getPath().toString() + "/" + url.substring(url.lastIndexOf("/"));

//                    FileOutputStream out = new FileOutputStream(filename);
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out);

                    return bmp;
                } catch (Exception e) {
                    Log.d("Error", e.toString());
                    return null;
                }

            }
            return null;
        }

        protected void onPostExecute(Bitmap bmp){
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bmp);
            imageBmp = bmp;
        }
    }
}
