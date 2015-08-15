package com.example.iyad.threading01;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by iyad on 8/15/2015.
 */
public class MyAsyncFragment extends Fragment{
    DownloaderListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    public void downloadFile(String filePath){
        new AsyncTask<String, Integer, String>(){

            @Override
            protected String doInBackground(String... params) {
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    fos = new FileOutputStream("/sdcard/tmp_file");
                    URL url = new URL(params[0]);
                    URLConnection conn = url.openConnection();
                    double fileSize = conn.getContentLength();
                    is = conn.getInputStream();
                    byte[] data = new byte[1024];
                    double total = 0;
                    int count = 0;
                    while ((count = is.read(data)) != -1) {
                        total += count;
                        fos.write(data, 0, count);
                        if (fileSize > 0) {

                            this.publishProgress((int)(total * 100 / fileSize));
                        }
                    }
                } catch (java.io.IOException e) {

                    e.printStackTrace();
                }finally {
                    if(fos != null)
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    if(is != null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return "finished";
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                if(listener != null) {
                    Log.d("ttt",values[0]+"");
                    listener.downloadUpdate(values[0]);
                }else{
                    Log.d("ttt","NULLLLLLLLLLL");

                }
            }

            @Override
            protected void onPostExecute(String s) {
                if(listener != null)
                    listener.downloadCompleted(s);
            }
        }.execute(filePath);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof DownloaderListener){
            listener = (DownloaderListener) activity;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface DownloaderListener{
        public void downloadUpdate(double update);
        public void downloadCompleted(String s);
        public void downloadError(String errorMsg);
    }
}

