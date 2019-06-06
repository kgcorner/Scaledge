package com.kgcorner.scaledge.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private GsonUtil() {
    }

    public static Gson getGson() {
        return new GsonBuilder().setDateFormat("dd-mm-yyyy").create();
    }
}
