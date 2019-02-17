package com.chirag.valarassignment.ui.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.interfaces.OnSnackBarActionListener;


public abstract class BaseActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public Toolbar toolbar;
    public SessionManager session;
    protected boolean shouldPerformDispatchTouch = true;
    TextView title;
    private long lastClickTime = 0;
    //protected GlideUtils glideUtils;
    private ProgressDialog dialog;
    private Snackbar snackbar;
    private setPermissionListener permissionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        if (snackbar != null && snackbar.isShown()) snackbar.dismiss();
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showSnackBar(View view, String msg) {
        showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    public void showSnackBar(View view, String msg, int LENGTH) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.red));
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(getColor(getApplicationContext(), R.color.white));
        snackbar.show();
    }

    public void showSuccessSnackBar(View view, String msg, int LENGTH) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.green_500));
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(getColor(getApplicationContext(), R.color.white));
        snackbar.show();
    }

    public void showSnackBar(View view, String msg, int LENGTH,
                             String action, final OnSnackBarActionListener actionListener) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        if (actionListener != null) {
            snackbar.setAction(action, view1 -> {
                snackbar.dismiss();
                actionListener.onAction();
            });
        }
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(getColor(getApplicationContext(), R.color.white));
        snackbar.show();
    }

    public void setUpToolbar(String strTitle) {
        setUpToolbarWithBackArrow(strTitle, false);
    }

    public void setUpToolbarWithBackArrow(String strTitle) {
        setUpToolbarWithBackArrow(strTitle, true);
    }

    private void setUpToolbarWithBackArrow(String strTitle, boolean isBackArrow) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            if (isBackArrow) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
            }
        }

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        title = toolbar.findViewById(R.id.tvTitle);
        title.setText(strTitle);
    }

    public void setupToolBarWithBackArrow(Toolbar toolbar, @Nullable String Title) {
        ActionBar actionBar;

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        }
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        title = toolbar.findViewById(R.id.tvTitle);
        title.setText(Title != null ? Title : "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public ProgressDialog showProgressBar() {
        return showProgressBar(null);
    }

    public ProgressDialog showProgressBar(String message) {
        if (dialog == null) dialog = new ProgressDialog(this, message);
        return dialog;
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void preventDoubleClick(View view) {
        // preventing double, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();
    }

    public int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public void showSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean ret = false;
        try {
            View view = getCurrentFocus();
            ret = super.dispatchTouchEvent(event);
            if (shouldPerformDispatchTouch) {
                if (view instanceof EditText) {
                    View w = getCurrentFocus();
                    int scrCords[] = new int[2];
                    if (w != null) {
                        w.getLocationOnScreen(scrCords);
                        float x = event.getRawX() + w.getLeft() - scrCords[0];
                        float y = event.getRawY() + w.getTop() - scrCords[1];

                        if (event.getAction() == MotionEvent.ACTION_UP
                                && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    }
                }
            }
            return ret;
        } catch (Exception e) {
            return ret;
        }
    }

    public boolean hasAppPermissions(final String[] requestedPermissions) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
        }
        return (permissionCheck == PackageManager.PERMISSION_GRANTED);
    }

    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int requestCode, setPermissionListener listener) {
        this.permissionListener = listener;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
        } else {
            if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
        } else {
            if (permissionListener != null) permissionListener.onPermissionDenied(requestCode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //EditText View Focus
    public void focusOnView(final NestedScrollView scroll, final View view) {
        new Handler().post(() -> {
            int vLeft = view.getTop();
            int vRight = view.getBottom();
            int sWidth = scroll.getWidth();
            scroll.smoothScrollTo(0, ((vLeft + vRight - sWidth) / 2));
        });
    }

    public interface setPermissionListener {
        public void onPermissionGranted(int requestCode);

        public void onPermissionDenied(int requestCode);
    }

    // Internet Connection
    public void checkNetworkConnection() {
        // Create an Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the Alert Dialog Message
        builder.setMessage("Internet Connection Required")
                .setCancelable(false)
                .setPositiveButton("Retry",
                        (dialog, id) -> {
                            // Restart the Activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public boolean isNetworkAvailable() {
        // Using ConnectivityManager to check for Network Connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
