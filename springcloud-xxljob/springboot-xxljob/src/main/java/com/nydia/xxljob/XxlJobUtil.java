package com.nydia.xxljob;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.commons.httpclient.HttpException;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: nydia
 * @Date: 2024-6-22 9:51
 * @Description:
 */
public class XxlJobUtil {
    private static String xxlJobAdminUrl  = "http://127.0.0.1:8080/xxl-job-admin";
    private static String xxlJobAdminUser  = "admin";
    private static String xxlJobAdminPass  = "123456";

    private final static Map<String,String> loginCookie = new HashMap<>();


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
    public static JSONObject addJob(String url, JSONObject requestInfo){
        String path = "/jobinfo/add";
        String targetUrl = url + path;
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Cookie", "XXL_JOB_LOGIN_IDENTITY=" + getCookie()); // 设置Token
//        HttpEntity<String> jobEntity = new HttpEntity<>(requestInfo.toJSONString(), headers);
//        String addJobResponse = restTemplate.postForObject(targetUrl, jobEntity, String.class);

        // JSON格式的参数
        String jsonParams = "{\"param1\":\"value1\", \"param2\":\"value2\"}";
        // 创建请求对象
        HttpRequest request = HttpRequest.post(url);
        // 设置请求体为JSON格式
        request.body(jsonParams);
        // 设置内容类型为JSON
        request.header("Content-Type", "application/json");
        // 发起POST请求
        String result = HttpUtil.execute(request);

        return null;
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

    /**
     * 登录
     * @param url
     * @param userName
     * @param password
     * @return
     * @throws HttpException
     * @throws IOException
     */

    public static String login(String url, String userName, String password) {
        String path = "/login";
        String targetUrl = url + path;

        Map<String, String> formData = new HashMap<>();
        formData.put("userName", userName);
        formData.put("password", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(targetUrl))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new RuntimeException("xxl-job login error!");
        }

        String loginIdentity = "";
        Map<String, List<String>> headers = response.headers().map();
        // 检查是否存在 Set-Cookie 头
        if (headers.containsKey("Set-Cookie")) {
            // 获取 Set-Cookie 的值列表
            List<String> setCookieHeaders = headers.get("Set-Cookie");

            // 解析 Set-Cookie 值
            List<String> cookies = setCookieHeaders.stream()
                    .flatMap(setCookieHeader -> parseCookies(setCookieHeader).stream())
                    .collect(Collectors.toList());
            for (String cookie : cookies){
                if(cookie.startsWith("XXL_JOB_LOGIN_IDENTITY=")){
                    String[] cookieArr = cookie.split("=");
                    loginIdentity = cookieArr[1];
                    break;
                }
            }
        } else {
            throw new RuntimeException("get xxl-job cookie error!");
        }
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", loginIdentity);
        return loginIdentity;
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

    // 解析 Set-Cookie 头部中的 Cookie
    private static List<String> parseCookies(String setCookieHeader) {
        Pattern pattern = Pattern.compile("(.*?)(?=;|$)");
        return pattern.matcher(setCookieHeader)
                .results()
                .map(matchResult -> matchResult.group(1))
                .collect(Collectors.toList());
    }

    private static String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr !=null) {
                return cookieStr;
            }
            login(xxlJobAdminUrl,xxlJobAdminUser,xxlJobAdminPass);
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }

}