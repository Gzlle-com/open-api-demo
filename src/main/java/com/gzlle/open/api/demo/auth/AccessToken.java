package com.gzlle.open.api.demo.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gzlle.open.api.demo.utils.HttpUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccessToken {
    public static String getAccessToken(String url, Map<String,String> map, String charset, LinkedHashMap<String,String> headers){
        String result = HttpUtil.doPost(url,map,charset,headers);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result).getAsJsonObject();
        String accessToken = jsonObject.get("accessToken").getAsString();
        return accessToken;
    }
}
