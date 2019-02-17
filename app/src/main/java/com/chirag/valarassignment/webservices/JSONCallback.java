package com.chirag.valarassignment.webservices;

import android.content.Context;
import android.util.Log;

import com.chirag.valarassignment.R;
import com.chirag.valarassignment.ui.base.ProgressDialog;
import com.chirag.valarassignment.utils.Logger;
import com.chirag.valarassignment.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class JSONCallback implements Callback<ResponseBody> {
    private Context context;
    ProgressDialog dialog;

    public JSONCallback(Context context) throws Exception {
        this(context, null);
    }

    public JSONCallback(Context context, ProgressDialog dialog) throws Exception {
        this.dialog = dialog;
        this.context = context;
        if (dialog != null) dialog.show();

        if (Utils.isConnectingToInternet(context)) {
            throw new Exception(context.getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onResponse(Call call, Response response) {
        String body = null;
        try {//Converting string to JSONObject
            if (response.isSuccessful()) {
                body = response.body() != null ? ((ResponseBody) response.body()).string() : null;
                JSONObject object = new JSONObject(body);
                Log.e("Response", call.request().url().toString() + "\n" + object.toString());
                if (object.optBoolean("Response")) {
                    onSuccess(response.code(), object);
                } else {
                    onFailure(response.code(), object);
                }
            } else {
                if (response.errorBody() != null) {
                    body = response.errorBody().string();
                    if (body.isEmpty()) {
                        String message = response.raw().message();
                        Log.e("Response", call.request().url().toString() + "\n" + message);
                        onFailed(response.code(), message);
                    } else {
                        JSONObject object = new JSONObject(body);
                        Log.e("Response", call.request().url().toString() + "\n" + object.toString());
                        onFailure(response.code(), object);
                    }
                }

            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            if (body != null) Logger.e(body);
//            Utils.generateCrashReport(context, call, body);
            onFailed(response.code(), context.getString(R.string.something_went_wrong));
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("Response", call.request().url().toString() + "\n" + t.toString());
        if (Utils.isConnectingToInternet(context)) {
            onFailed(0, context.getString(R.string.no_internet_connection));
        } else if (t instanceof ConnectException
                || t instanceof SocketTimeoutException
                || t instanceof UnknownHostException) {
            onFailed(0, context.getString(R.string.failed_to_connect_with_server));
        } else if (t instanceof IOException) {
            onFailed(0, context.getString(R.string.no_internet_connection));
        } else {
            onFailed(0, t.getMessage());
        }
    }

    private void onFailure(int statusCode, JSONObject object) {
        if (statusCode == 401) {
            onFailed(statusCode, object.optString("Message"));

        } else {
            onFailed(statusCode, object.optString("Message"));
        }
    }

    protected abstract void onFailed(int statusCode, String message);

    protected abstract void onSuccess(int statusCode, JSONObject jsonObject) throws JSONException;
}
