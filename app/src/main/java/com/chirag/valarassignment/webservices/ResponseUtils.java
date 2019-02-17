package com.chirag.valarassignment.webservices;

import android.content.Context;
import android.util.Log;

import com.chirag.valarassignment.R;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Response;

public class ResponseUtils {

    public static String getRequestAPIURL(String API, SessionManager session) {
        try {
            if (API != null)
                return API;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static void printLogs(String webUrl, String reqParam, String method) {
        char[] chars = reqParam.toCharArray();
        String reqParameter = "";
        for (char aChar : chars) {
            if ('&' == aChar) {
                reqParameter += "\n";
            } else {
                reqParameter += aChar;
            }
        }
        Log.e("Web URl ==>", String.valueOf(webUrl));
        Log.e("Method ==>", String.valueOf(method));
        Log.e("Request Parameters ==>>", String.valueOf(reqParameter));
    }

    private static void printResponse(String response) {
        Log.e("Response ==>", String.valueOf(response));
    }


    public static String getResponseData(Response<JsonObject> response) {
        try {
            if (response != null)
                return response.body().get("data").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isSuccess(JsonObject jsonObject) {
        try {
            if (jsonObject != null && jsonObject.get("api_status").getAsString().equals("200"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSuccessMessage(Context mContext, String response) {
        try {
            JSONObject jObj = new JSONObject(response);
            return jObj.getString("Message");
        } catch (Exception e) {
            e.printStackTrace();
            return "Success";
        }
    }

    public static String getFailMessage(Context mContext, String failMessage) {
        String msg = "";
        try {
            JSONObject jObj = new JSONObject(failMessage);
            return jObj.getString("Message");
        } catch (Exception e) {
            e.printStackTrace();
            msg = mContext.getResources().getString(R.string.something_went_wrong);
        }
        return msg;
    }

}
