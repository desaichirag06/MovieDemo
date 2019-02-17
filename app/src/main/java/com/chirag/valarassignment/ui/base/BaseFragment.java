package com.chirag.valarassignment.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.ui.home.MainActivity;
import com.chirag.valarassignment.utils.SessionManager;

public class BaseFragment extends Fragment {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public Context mContext;
    public Activity mActivity;
    public TextView title;
    public SessionManager session;

    //protected GlideUtils glideUtils;
    public setPermissionListener permissionListener;
    private Snackbar snackbar;
    private ProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
        session = new SessionManager(mContext);
        //glideUtils = new GlideUtils(ApplicationClass.getAppContext());
    }

    public void updateToolBarTitle(String Title) {
        title.setText(Title != null ? Title : mContext.getResources().getString(R.string.app_name));
    }

    // Set false for Tab Back press management. It will manage from HomeActivity
    public boolean onBackPressed() {
        return false;
    }

    public void showShortToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public void showSnackBar(View view, String msg) {
        showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    public void showSnackBar(View view, String msg, int LENGTH) {
        if (view == null) return;
        snackbar = Snackbar.make(view, msg, LENGTH);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        snackbar.show();
    }

    public void showSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            if (mActivity.getWindow().getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(mActivity.getWindow().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProgressDialog showProgressBar() {
        return showProgressBar(null);
    }

    public ProgressDialog showProgressBar(String message) {
        if (dialog == null) dialog = new ProgressDialog(mContext, message);
        return dialog;
    }

    public void hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions, final int requestCode,
                                      setPermissionListener listener) {
        this.permissionListener = listener;
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(mActivity, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(requestedPermissions, requestCode);
        } else {
            if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED) {
                if (permissionListener != null) permissionListener.onPermissionGranted(requestCode);
                break;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                if (permissionListener != null) permissionListener.onPermissionDenied(requestCode);
                break;
            } else {
                if (permissionListener != null) {
                    permissionListener.onPermissionNeverAsk(requestCode);
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (snackbar != null && snackbar.isShown()) snackbar.dismiss();
    }

    protected void pushFragment(Fragment fragment) {
        if (mActivity instanceof MainActivity) {
            //((MainActivity) mActivity).pushFragment(fragment);
        }
    }

    public interface setPermissionListener {
        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);

        void onPermissionNeverAsk(int requestCode);
    }

}