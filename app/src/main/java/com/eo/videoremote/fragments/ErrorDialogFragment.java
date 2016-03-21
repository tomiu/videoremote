package com.eo.videoremote.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatPopupWindow;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import junit.framework.Assert;

/**
 * Created by tomiurankar on 21/03/16.
 */
public class ErrorDialogFragment extends AppCompatDialogFragment {
    private static final String KEY_ERROR_MSG = "errorMsg";
    private String mErrorMsg;

    public static Fragment newInstance(String errorMsg) {
        if(TextUtils.isEmpty(errorMsg))
            Assert.fail("ErrorDialogFragment error message must not be empty");

        Fragment fragment = new ErrorDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_ERROR_MSG, errorMsg);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorMsg = getArguments().getString(KEY_ERROR_MSG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setMessage(mErrorMsg);
        //null should be your on click listener
        alertDialogBuilder.setPositiveButton("OK", null);

        return alertDialogBuilder.create();   }
}
