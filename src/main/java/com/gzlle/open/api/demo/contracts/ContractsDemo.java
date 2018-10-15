package com.gzlle.open.api.demo.contracts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gzlle.open.api.demo.auth.AuthDemo;
import com.gzlle.open.api.demo.domain.AccessToken;
import com.gzlle.open.api.demo.utils.Constants;
import com.gzlle.open.api.demo.utils.HttpUtil;
import com.gzlle.open.api.demo.utils.SignUtil;

import java.util.*;

/**
 * 业务签约相关接口demo
 */
public class ContractsDemo {

    /**
     * 添加自由职业者接口
     *
     * @param token
     * @return
     */
    public static String addEmployee(String token) {
        //调用添加自由职业者接口，返回Json数据，里面包含添加的职业者的id
        String url = Constants.API_BASE_URL + "/contracts/employees/add";
        SortedMap<String, String> map = new TreeMap<>();
        map.put("name", "工资来了");
        map.put("phone", "18888888888");
        //使用32位以类的随机字符串
        map.put("nonce", SignUtil.buildNonce(32));

        //sign使用SignUtil工具类生成签名, todo key 请填写自己的 appKey
        map.put("sign", SignUtil.createOpenSign(map, "appKey"));

        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        //添加请求头信息
        header.put("Content-Type", "application/json; charset=UTF-8");
        header.put("Authorization", token);
        String result = HttpUtil.doPost(url, map, "UTF-8", header);
        System.out.print(result);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result).getAsJsonObject();
        String employeeId = jsonObject.get("employeeId").getAsString();
        return employeeId;
    }

    /**
     * 获取用户token接口
     *
     * @param token
     */
    public static void getUserToken(String token) {
        //调用获取用户token接口,返回Json数据，里面包含userToken
        String employeeId = addEmployee(token);
        String url = Constants.API_BASE_URL + "/contracts/employees/userToken/" + employeeId;
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("Authorization", token);
        String result = HttpUtil.doGet(url, "UTF-8", header);
        System.out.print(result);
    }

    /**
     * @param token
     */
    public static void queryResult(String token) {
        String employeeId = addEmployee(token);
        String url = Constants.API_BASE_URL + "/contracts/employees/status/" + employeeId;
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("Authorization", token);
        String result = HttpUtil.doGet(url, "UTF-8", header);
        System.out.print(result);
    }

    /**
     * 回调签约结果模拟
     */
    public static void callbackResult() {
        //企业用户在saas平台开发者配置设置的回调链接，没有设置时，open.gzlle.com 将不会发起回调,返回任何Json格式数据都表示成功
        String url = "www.baidu.com";
        SortedMap<String, String> map = new TreeMap<>();
        //在saas平台开发者配置得到corpId
        map.put("corpId", "33333333333333");
        //回调通知类型；1业务签约
        map.put("noticeType", "1");
        //获取accessToken时用的nonce,sign
        map.put("nonce", SignUtil.buildNonce(32));

        //合同签署状态,0未签署,1已签署,2/3签署中,9作废
        map.put("contractStatus", "1");
        //添加的自由职业者唯一id todo 这里的employeeId 需要在添加自由职业者接口中获取
        map.put("employeeId", "employeeId");

        map.put("sign", SignUtil.createOpenSign(map, "appKey"));
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        String result = HttpUtil.doPost(url, map, "UTF-8", header);
        System.out.print(result);
    }


    public static void main(String[] args) throws Exception {
        //获取accessToken
        AccessToken accessToken = AuthDemo.getAccessToken("appKey", "appSecret");
        String token = "Bearer" + " " + accessToken.getAccessToken();
        //todo token过期请自己完善逻辑

        //添加自由职业者
        addEmployee(token);

        //获取用户token
        getUserToken(token);

        //查询签约结果
        queryResult(token);

        //回调签约结果模拟 todo 这里的逻辑是 模拟open.gzlle.com 回调请求
        callbackResult();
    }
}
