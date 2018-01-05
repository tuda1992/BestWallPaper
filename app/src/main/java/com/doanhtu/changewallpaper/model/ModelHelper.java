package com.doanhtu.changewallpaper.model;

import com.google.gson.Gson;

/**
 * Created by doanhtu on 1/2/18.
 */

public class ModelHelper {

    private Gson gson;
    private static ModelHelper instance;

    private ModelHelper() {
        gson = new Gson();
    }

    public static ModelHelper getInstance() {
        if (instance == null) {
            instance = new ModelHelper();
        }

        return instance;
    }

    public UserLogin getUserLogin(String jsonUserLogin) {
        return gson.fromJson(jsonUserLogin, UserLogin.class);
    }

}
