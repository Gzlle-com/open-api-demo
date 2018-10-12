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


    public static void addEmployee(String token) {
        //调用添加自由职业者接口，返回Json数据，里面包含添加的职业者的id
        String url1 = Constants.API_BASE_URL + "/contracts/employees/add";
        SortedMap<String, String> map1 = new TreeMap<>();
        map1.put("name", "工资来了");
        map1.put("phone", "18888888888");
        //使用32位以类的随机字符串
        map1.put("nonce", "01234567890123456789012345678901");

        //sign使用SignUtil工具类生成签名
        map1.put("sign", SignUtil.createOpenSign(map1, "key"));

        LinkedHashMap<String, String> headers1 = new LinkedHashMap<String, String>();
        //添加请求头信息
        headers1.put("Content-Type", "application/json; charset=UTF-8");
        headers1.put("Authorization", token);
        //调用添加自由职业者方法
        String result2 = HttpUtil.doPost(url1, map1, "UTF-8", headers1);
        System.out.print(result2);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result2).getAsJsonObject();
//        String employeeId = jsonObject.get("employeeId").getAsString();

    }

    public static void main(String[] args) throws Exception {
        //获取accessToken
        AccessToken accessToken = AuthDemo.getAccessToken("appKey", "appSecret");

        //token过期请自己完善逻辑

        //添加自由职业者
        addEmployee(accessToken.getAccessToken());



        String authorization = "Bearer" + " " + accessToken;

        //调用SignUtil工具类获取sign签名值
        SortedMap<String, String> parameters = new TreeMap<>();
        parameters.put("name", "工资来了");
        parameters.put("phone", "18888888888");
        parameters.put("nonce", "01234567890123456789012345678901");
        String sign = SignUtil.createOpenSign(parameters, "1111111111111111");


        //调用获取用户token接口,返回Json数据，里面包含userToken
        String url2 = openApiBaseUrl + "/contracts/employees/userToken/" + employeeId;
        LinkedHashMap<String, String> headers2 = new LinkedHashMap<String, String>();
        headers2.put("Authorization", authorization);
        String result3 = HttpUtil.doGet(url2, charsect, headers2);
        System.out.print(result3);

        //调用查询签约结果接口
        String url3 = openApiBaseUrl + "/contracts/employees/status/" + employeeId;
        LinkedHashMap<String, String> headers3 = new LinkedHashMap<String, String>();
        headers3.put("Authorization", authorization);
        String result4 = HttpUtil.doGet(url3, charsect, headers3);
        System.out.print(result4);

        //调用回调签约结果接口,返回任何Json格式数据都表示成功
        //在saas平台开发者配置设置回调连接地址，没设置不能回调
        String url4 = "http://www.baidu.com";
        Map<String, String> map2 = new HashMap<>();
        //在saas平台开发者配置得到corpId
        map2.put("corpId", "33333333333333");
        //回调通知类型；1业务签约
        map2.put("noticeType", "1");
        //获取accessToken时用的nonce,sign
        map2.put("nonce", "01234567890123456789012345678901");
        map2.put("sign", sign);
        //合同签署状态,0未签署,1已签署,2/3签署中,9作废
        map2.put("contractStatus", "1");
        //添加的自由职业者唯一id
        map2.put("employeeId", employeeId);
        LinkedHashMap<String, String> headers4 = new LinkedHashMap<String, String>();
        String result5 = HttpUtil.doPost(url4, map2, charsect, headers4);
        System.out.print(result5);
    }
}
