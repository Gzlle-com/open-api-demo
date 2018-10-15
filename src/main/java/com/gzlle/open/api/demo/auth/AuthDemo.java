package com.gzlle.open.api.demo.auth;

import com.google.gson.Gson;
import com.gzlle.open.api.demo.domain.AccessToken;
import com.gzlle.open.api.demo.utils.Constants;
import com.gzlle.open.api.demo.utils.HttpUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证相关接口
 */
public class AuthDemo {
    public static AccessToken getAccessToken(String appKey, String appSecret) {
        String url = Constants.API_BASE_URL + "/token";
        String charset = "UTF-8";
        Map<String, String> map = new HashMap<>();
        map.put("grantType", "client_credentials");
        //appKey、appSecret在saas平台的开发者配置获取
        map.put("appKey", appKey);
        map.put("appSecret", appSecret);
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        String result = HttpUtil.doPost(url, map, charset, headers);
        return new Gson().fromJson(result, AccessToken.class);
    }
}
