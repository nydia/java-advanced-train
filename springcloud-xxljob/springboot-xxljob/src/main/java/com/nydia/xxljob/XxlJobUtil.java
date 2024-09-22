package com.nydia.xxljob;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: nydia
 * @Date: 2024-6-22 9:51
 * @Description:
 */
public class XxlJobUtil {
    private static String cookie="";


    /**
     * 查询现有的任务（可以关注这个整个调用链，可以自己模仿着写其他的拓展接口）
     * @param url
     * @param requestInfo
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject pageList(String url,JSONObject requestInfo) throws HttpException, IOException {
//        String path = "/api/jobinfo/pageList";
//        String targetUrl = url + path;
//        HttpClient httpClient = new HttpClient();
//        PostMethod post = new PostMethod(targetUrl);
//        post.setRequestHeader("cookie", cookie);
//        RequestEntity requestEntity = new StringRequestEntity(requestInfo.toString(), "application/json", "utf-8");
//        post.setRequestEntity(requestEntity);
//        httpClient.executeMethod(post);
//        JSONObject result = new JSONObject();
//        result = getJsonObject(post, result);
//        System.out.println(result.toJSONString());
//        return result;

        return null;
    }



    /**
     * 新增/编辑任务
     * @param url
     * @param requestInfo
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject addJob(String url,JSONObject requestInfo) throws HttpException, IOException {
        String path = "/api/jobinfo/save";
        String targetUrl = url + path;
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(targetUrl);
        post.setRequestHeader("cookie", cookie);
        RequestEntity requestEntity = new StringRequestEntity(requestInfo.toString(), "application/json", "utf-8");
        post.setRequestEntity(requestEntity);
        httpClient.executeMethod(post);
        JSONObject result = new JSONObject();
        result = getJsonObject(post, result);
        System.out.println(result.toJSONString());
        return result;
    }

    /**
     * 删除任务
     * @param url
     * @param id
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject deleteJob(String url,int id) throws HttpException, IOException {
        String path = "/api/jobinfo/delete?id="+id;
        return doGet(url,path);
    }

    /**
     * 开始任务
     * @param url
     * @param id
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject startJob(String url,int id) throws HttpException, IOException {
        String path = "/api/jobinfo/start?id="+id;
        return doGet(url,path);
    }

    /**
     * 停止任务
     * @param url
     * @param id
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static JSONObject stopJob(String url,int id) throws HttpException, IOException {
        String path = "/api/jobinfo/stop?id="+id;
        return doGet(url,path);
    }

    public static JSONObject doGet(String url,String path) throws HttpException, IOException {
//        String targetUrl = url + path;
//        HttpClient httpClient = new HttpClient();
//        HttpMethod get = new GetMethod(targetUrl);
//        get.setRequestHeader("cookie", cookie);
//        httpClient.executeMethod(get);
//        JSONObject result = new JSONObject();
//        result = getJsonObject(get, result);
//        return result;

        return null;
    }

    private static JSONObject getJsonObject(HttpMethod postMethod, JSONObject result) throws IOException {
        if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
            InputStream inputStream = postMethod.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while((str = br.readLine()) != null){
                stringBuffer.append(str);
            }
            String response = new String(stringBuffer);
            br.close();

            return (JSONObject) JSONObject.parse(response);
        } else {
            return null;
        }


    }

    /**
     * 登录
     * @param url
     * @param userName
     * @param password
     * @return
     * @throws HttpException
     * @throws IOException
     */

    public static String login(String url, String userName, String password) throws HttpException, IOException {
        String path = "/login";
        String targetUrl = url + path;

        Map<String, String> formData = new HashMap<>();
        formData.put("userName", userName);
        formData.put("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();

        HttpMethod post = new PostMethod((targetUrl));
        post.for.executeMethod(get);
        if (get.getStatusCode() == 200) {
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
            }
            cookie = tmpcookies.toString();
        } else {
            try {
                cookie = "";
            } catch (Exception e) {
                cookie="";
            }
        }
        return cookie;
    }

    private static String getFormDataAsString(Map<String, String> formData) {
        StringBuilder formBodyBuilder = new StringBuilder();
        for (Map.Entry<String, String> singleEntry : formData.entrySet()) {
            if (formBodyBuilder.length() > 0) {
                formBodyBuilder.append("&");
            }
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getKey(), StandardCharsets.UTF_8));
            formBodyBuilder.append("=");
            formBodyBuilder.append(URLEncoder.encode(singleEntry.getValue(), StandardCharsets.UTF_8));
        }
        return formBodyBuilder.toString();
    }
}