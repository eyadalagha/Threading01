package com.example.iyad.threading01;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements MyAsyncFragment.DownloaderListener{

    MyProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            dialog = new MyProgressDialog();

        final MyAsyncFragment fff = new MyAsyncFragment();
        this.getSupportFragmentManager().beginTransaction().add(fff, "ff").commit();
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fff.downloadFile("http://mirror.internode.on.net/pub/test/1meg.test");
                dialog.show(getSupportFragmentManager(),"ddd");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void downloadUpdate(double update) {
        dialog.setProgress((int)update);
    }

    @Override
    public void downloadCompleted(String s) {

    }

    @Override
    public void downloadError(String errorMsg) {
        dialog.dismiss();
    }
}
