package com.gzlle.open.api.demo.utils;

import com.google.gson.Gson;
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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Creator: hadeslong
 * @Created: 2018/10/12 下午3:20
 * @Description:
 * @Modifier: hadeslong
 * @Modified By: 2018/10/12 下午3:20
 */
public class HttpUtil {

    public static String doPost(String url, Map<String,String> map, String charset, LinkedHashMap<String,String> headers){
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
