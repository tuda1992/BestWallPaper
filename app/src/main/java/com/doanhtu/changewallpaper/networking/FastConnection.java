package com.doanhtu.changewallpaper.networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by doanhtu on 1/2/18.
 */

public class FastConnection {

    private static final String BASE_URL = "http://153.122.67.192/api/";
    private static final String AUTH_LOGIN = "auth/login";


    private Context mContext;
    private CallBackResponseListener mListener;

    public interface CallBackResponseListener {
        void onResponeSuccess(String json);

        void onError(ANError anError);
    }

    public FastConnection(Context context, CallBackResponseListener listener) {
        mContext = context;
        mListener = listener;
    }

    public void callApi(String endUrl, JSONObject jsonObject) {
        AndroidNetworking.post(BASE_URL + endUrl)
                .addHeaders(initCustomHeader(null))
                .addJSONObjectBody(jsonObject) // posting json
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            Log.d("TUDA", "msg = " + msg);
                            if (code == 200) {
                                if (response.has("data")) {
                                    JSONObject dataObject = response.getJSONObject("data");
                                    mListener.onResponeSuccess(dataObject.toString());
                                } else {
                                    mListener.onResponeSuccess("");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        // Error Connection
                        mListener.onError(anError);
                    }
                });
    }

    public void callApiMethodGETRequestList(String endUrl, String token, HashMap<String, String> queryParams) {
        HashMap<String, String> headers = initCustomHeader(token);
        AndroidNetworking.get(BASE_URL + endUrl)
                .addHeaders(headers)
                .addQueryParameter(queryParams)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            String msg = response.getString("msg");
                            if (code == 200) {
                                if (response.has("data")) {
                                    JSONObject dataObject = response.getJSONObject("data");
                                    mListener.onResponeSuccess(dataObject.toString());
                                } else {
                                    mListener.onResponeSuccess("");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mListener.onError(anError);
                    }
                });
    }

    @NonNull
    private HashMap<String, String> initCustomHeader(String token) {
        HashMap<String, String> headers = new HashMap<>();
        if (!TextUtils.isEmpty(token))
            headers.put("Authorization", "Bearer " + token);
        headers.put("Accept", "application/json");
        return headers;
    }

}
