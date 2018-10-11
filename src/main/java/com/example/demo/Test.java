package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception{
        String url = "http://openapi-test.gzlle.com/token";
        Map<String,String> map = new HashMap<>();
        map.put("grantType","client_credentials");
        map.put("appKey","227398938947223552");
        map.put("appSecret","4E5122E7D658C3ADD0BE49C0A1BEFD61");
        LinkedHashMap<String,String> headers = new LinkedHashMap<String,String>();
        headers.put("Content-Type", "application/json; charset=UTF-8");
        //调用获取accessToken方法
        String result = doPost(url,map,"UTF-8",headers);
        System.out.print(result);
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(result).getAsJsonObject();
        String accessToken = jsonObject.get("accessToken").getAsString();

        String url1 = "http://openapi-test.gzlle.com/contracts/employees/add";
        Map<String,String> map1 = new HashMap<>();
        map1.put("name","罗豪强");
        map1.put("phone","13047220479");
        map1.put("nonce","01234567890123456789012345678901");
        map1.put("sign","FBE4ABACFD147946634196B60DAB2C9BE6687DE7BDB0268B686AFE3695DABDAE");
        LinkedHashMap<String,String> headers1 = new LinkedHashMap<String,String>();
        headers1.put("Content-Type", "application/json; charset=UTF-8");
        headers1.put("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiaW1hZ2luZS1tYWluIiwiaW1hZ2luZS1vcGVuIiwiaW1hZ2luZS1hdXRoIl0sInNjb3BlIjpbInByb2ZpbGUiXSwiZXhwIjoxNTM5MTY4MjgyLCJqdGkiOiI3NDFiOWFkZS0xMDk0LTRiMTEtYjJjNC01ZDMxMjU0YjgyMjAiLCJjbGllbnRfaWQiOiIyMjczOTg5Mzg5NDcyMjM1NTIifQ.0b1nEoDZpCfUDV7Go2dco7jI85ywOfJcQaJinGesBiGI6TyE5nDa6Wo8rKiFbDToi3xo6GPQWZpZcMNNxpthpPASLJDZDGycS6xRytpz3VMEObmz8n11441l7_tuVzmKCsJtOCLsHo3HIzNAfm1gpU7v_z0IEDLGD6Rb9EgFZUo");
        //调用添加自由职业者方法
        String result2 = doPost(url1,map1,"UTF-8",headers1);
        System.out.print(result2);


        String url2 = "http://openapi-test.gzlle.com/contracts/employees/userToken/227850788230135808";
        //调用获取用户token方法
        String result3 = doGet(url2,"UTF-8");
        System.out.print(result3);

        String url3 = "http://openapi-test.gzlle.com/contracts/employees/status/227850788230135808";
        //调用查询签约结果方法
        String result4 = doGet(url3,"UTF-8");
        System.out.print(result4);

        //在saas平台开发者配置设置回调连接地址
        String url4 = "https://www.baidu.com";
        Map<String,String> map2 = new HashMap<>();
        //在saas平台开发者配置得到corpId
        map2.put("corpId","227374883552624640");
        //回调通知类型；1业务签约
        map2.put("noticeType","1");
        //获取accessToken时用的nonce,sign
        map2.put("nonce","01234567890123456789012345678901");
        map2.put("sign","FBE4ABACFD147946634196B60DAB2C9BE6687DE7BDB0268B686AFE3695DABDAE");
        //合同签署状态,0未签署,1已签署,2/3签署中,9作废
        map2.put("contractStatus","1");
        //添加的自由职业者唯一id
        map2.put("employeeId","227850788230135808");
        LinkedHashMap<String,String> headers2 = new LinkedHashMap<String,String>();
        String result5 = doPost(url4,map2,"UTF-8",headers2);
        System.out.print(result5);
    }


   public static String doPost(String url, Map<String,String> map, String charset,LinkedHashMap<String,String> headers){
       CloseableHttpClient httpClient = null;
       HttpPost httpPost = null;
       String result = null;
       try {
           httpClient = HttpClients.createDefault();
           httpPost = new HttpPost(url);
           Gson gson = new Gson();
           //转换成json字符串
           String jsonString=gson.toJson(map);
           //设置编码格式防止出现中文乱码
           StringEntity stringEntity = new StringEntity(jsonString, Charset.forName("UTF-8"));
           //给HttpPost 设置请求头
           if (headers != null) {
               for (String key : headers.keySet()) {
                   httpPost.setHeader(key, headers.get(key));
               }
           }
           stringEntity.setContentType("application/json");
           httpPost.setEntity(stringEntity);
           HttpResponse response = httpClient.execute(httpPost);
           HttpEntity resEntity = response.getEntity();
           result = EntityUtils.toString(resEntity,charset);
       } catch (IOException e) {
           e.printStackTrace();
       }
        return result;
   }
    public static String doGet(String url, String charset){
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(url);
            //设置请求头
            httpGet.setHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiaW1hZ2luZS1tYWluIiwiaW1hZ2luZS1vcGVuIiwiaW1hZ2luZS1hdXRoIl0sInNjb3BlIjpbInByb2ZpbGUiXSwiZXhwIjoxNTM5MTY4MjgyLCJqdGkiOiI3NDFiOWFkZS0xMDk0LTRiMTEtYjJjNC01ZDMxMjU0YjgyMjAiLCJjbGllbnRfaWQiOiIyMjczOTg5Mzg5NDcyMjM1NTIifQ.0b1nEoDZpCfUDV7Go2dco7jI85ywOfJcQaJinGesBiGI6TyE5nDa6Wo8rKiFbDToi3xo6GPQWZpZcMNNxpthpPASLJDZDGycS6xRytpz3VMEObmz8n11441l7_tuVzmKCsJtOCLsHo3HIzNAfm1gpU7v_z0IEDLGD6Rb9EgFZUo");
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity,charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
