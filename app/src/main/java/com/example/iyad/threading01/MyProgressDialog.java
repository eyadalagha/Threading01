package com.example.iyad.threading01;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Created by iyad on 8/15/2015.
 */
public class MyProgressDialog extends DialogFragment {

    ProgressDialog progressDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(this.getActivity());
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return progressDialog;
    }



    public void setProgress(int value){
        if(progressDialog != null)
            progressDialog.setProgress(value);
    }

}
