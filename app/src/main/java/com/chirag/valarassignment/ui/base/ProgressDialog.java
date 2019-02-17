package com.chirag.valarassignment.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.databinding.DialogProgressBinding;

public class ProgressDialog extends AlertDialog {
    private String message;

    public ProgressDialog(Context context) {
        this(context, null);
    }

    public ProgressDialog(Context context, String message) {
        super(context, R.style.ProgressDialog);
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DialogProgressBinding mBinder = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_progress, null, false);
        setContentView(mBinder.getRoot());
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        if (message != null) mBinder.tvMessage.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }
}
