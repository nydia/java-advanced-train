//package com.nydia.xxljob.demo;
//
//import com.xxl.job.core.biz.model.RegistryParam;
//import com.xxl.job.core.biz.model.ReturnT;
//import com.xxl.job.core.enums.RegistryConfig;
//import com.xxl.job.core.util.DateUtil;
//import com.xxl.job.core.util.IpUtil;
//import com.xxl.job.core.util.NetUtil;
//import com.xxl.job.core.util.XxlJobRemotingUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.groovy.util.Maps;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class XxlJobServiceImpl implements XxlJobService {
//
//    @Value("${xxl.job.admin.login-username:admin}")
//    private String loginUsername;
//
//    @Value("${xxl.job.admin.login-pwd:123456}")
//    private String loginPwd;
//
//    @Value("${xxl.job.executor.ip:}")
//    private String ip;
//
//    @Value("${xxl.job.admin.addresses:''}")
//    private String adminAddresses;
//
//    @Value("${xxl.job.executor.port:-1}")
//    private int port;
//
//    @Value("${spring.profiles.active}")
//    private String profiles;
//
//    //@Value("${xxl.job.executor.appname}")
//    //private String appName;
//
//    @Value("${xxl.job.accessToken:}")
//    private String accessToken;
//
//    @Value("${xxl.job.myjob.payconfirm-executor:goldOrderPayStatusConfirmSingleJobHandler}")
//    private String payConfirmExecutor;
//
//    @Value("${xxl.job.myjob.appname:gold-pay-e}")
//    private String appName;
//
//    private final static String TITLE = "Gold订单自动查询";
//
//    /**
//     * 增加执行任务
//     *
//     * @param executorDate 执行日期
//     */
//    public void addExecutorTask(String orderNo, Date executorDate) {
//        addExecutorTask(Long.valueOf(StringUtils.genXxlJobTaskEventId()), orderNo, executorDate);
//    }
//
//    /**
//     * 增加执行任务
//     *
//     * @param eventId      事件id
//     * @param executorDate 执行日期
//     */
//    public String addExecutorTask(Long eventId, String orderNo, Date executorDate) {
//
//        Assert.notNull(eventId, "eventId must be not null");
//        String desc = "订单查询任务(" + orderNo + ")";
//        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(16);
//        //执行器主键ID,获取执行器主键
//        paramMap.put("jobGroup", getJobGroupId());
//        paramMap.put("jobDesc", desc);
//        paramMap.put("executorRouteStrategy", "FIRST");
//        paramMap.put("scheduleType", "CRON");//调度类型
//
//        String cron = getCron(executorDate);
//        paramMap.put("cronGen_display", cron);
//        //任务执行CRON表达式
//        paramMap.put("jobCron", cron);
//        paramMap.put("scheduleConf", cron);
//
//        //任务模式，可选值参考 com.xxl.job.core.glue.GlueTypeEnum
//        paramMap.put("glueType", "BEAN");
//        //执行器，任务Handler名称
//        paramMap.put("executorHandler", payConfirmExecutor);
//        //任务阻塞策略，可选值参考 com.xxl.job.core.enums.ExecutorBlockStrategyEnum
//        paramMap.put("executorBlockStrategy", "SERIAL_EXECUTION");
//        //任务超时时间，单位秒，大于零时生效
//        paramMap.put("executorTimeout", 5);
//        //失败重试次数
//        paramMap.put("executorFailRetryCount", 2);
//        //负责人
//        paramMap.put("author", "admin");
//        //GLUE备注
//        paramMap.put("glueRemark", desc);
//        //调度状态：0-停止，1-运行
//        paramMap.put("triggerStatus", 1);
//        //过期策略
//        paramMap.put("misfireStrategy", "DO_NOTHING");
//        //子任务
//        paramMap.put("childJobId", "");
//        HashMap<String, Object> executorParam = Maps.newHashMap();
//        executorParam.put("eventId", eventId);
//        executorParam.put("orderNo", orderNo);
////        //执行器，任务参数
//        paramMap.put("executorParam", JsonUtils.objectToJson(executorParam));
//        log.info(String.format("增加xxl执行任务,请求参数: %s", paramMap));
//
//        try {
//            String cookie = getCookie();
//            Map<String, Object> headMap = new HashMap<String, Object>() {{
//                put("Cookie", cookie);
//            }};
//
//            Object resp = OkHttpUtils.postForm(adminAddresses + "/jobinfo/add", paramMap, headMap);
//            log.info(String.format("增加xxl执行任务成功,返回信息: %s", resp));
//            if (resp == null) {
//                return "fail";
//            }
//            Map<String, Object> respMap = JsonUtils.jsonString2Object((String) resp, Map.class);
//            log.info("code:" + respMap.get("code"));
//            if((Integer)respMap.get("code") != 200){
//                return "success";
//            }
//            return "fail";
//        } catch (Exception e) {
//            log.error("增加xxl执行任务失败：" + e.getMessage(), e);
//        }
//        return "success";
//
//    }
//
//    /**
//     * 获取执行id
//     *
//     * @return int
//     */
//    private Integer getJobGroupId() {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appname", appName);
//        paramMap.put("start", "0");
//        paramMap.put("length", "100");
//        log.info(String.format("获取xxl执行id,请求参数:%s", paramMap));
//        //通过appname 查询appname对应的id
//
//        try {
//            String cookie = getCookie();
//            Map<String, Object> headMap = new HashMap<String, Object>() {{
//                put("Cookie", cookie);
//            }};
//
//            Object resp = OkHttpUtils.postBody(adminAddresses + "/jobgroup/pageList", paramMap, headMap);
//            log.info(String.format("获取xxl执行器成功,返回信息:%s", resp));
//            if (resp == null) {
//                log.error(String.format("调用xxl获取执行器失败: %s", resp));
//                return null;
//            }
//            XxlSearchDto xxlSearchDto = JsonUtils.jsonString2Object((String) resp, XxlSearchDto.class);
//            log.info(String.format("获取xxl执行器成功,返回信息: %s", xxlSearchDto));
//            List<XxlSearchDto.Data> list = xxlSearchDto.getData();
//            if (CollectionUtils.isEmpty(list)) {
//                //新增成功没有返回id
//                createJobGroup();
//                //在调一次列表查询 获取组id
//                return getJobGroupId();
//            }
//            for(XxlSearchDto.Data data : list){
//                if(appName.equals(data.getAppname())){
//                    return data.getId();
//                }
//            }
//        } catch (Exception e) {
//            log.error("获取xxl执行器失败：" + e.getMessage(), e);
//        }
//
//        try {
//            //新增成功没有返回id
//            createJobGroup();
//            //在调一次列表查询 获取组id
//            return getJobGroupId();
//        }catch (Exception e){
//            log.error("新增xxl执行器失败：" + e.getMessage(), e);
//        }
//
//        return 0;
//    }
//
//    /**
//     * 创建执行器
//     */
//    private void createJobGroup() {
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appname", appName);
//        paramMap.put("title", TITLE + profiles);
//        //注册方式  0自动  1 为手动注册
//        paramMap.put("addressType", "0");
//        log.info(String.format("调用xxl增加执行器,请求参数：%s", paramMap));
//
//        try {
//            String cookie = getCookie();
//            Map<String, Object> headMap = new HashMap<String, Object>() {{
//                put("Cookie", cookie);
//            }};
//
//            Object resp = OkHttpUtils.postForm(adminAddresses + "/jobgroup/save", paramMap, headMap);
//            log.info(String.format("调用xxl增加执行器,成功返回信息: %s", resp));
//
//            //注册执行器(上面使用的是自动注册，所以下面不用执行)
//            //registry();
//
//            return;
//        } catch (Exception e) {
//            log.error("获取xxl执行器失败：" + e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 注册执行器
//     * 不能设置超时
//     * AdminBiz adminBiz = new AdminBizClient(adminAddresses, accessToken)
//     * ReturnT<String> returnT = adminBiz.registry(registryParam);
//     */
//    private void registry() {
//        //copy xxl-job 源码
//        port = port > 0 ? port : NetUtil.findAvailablePort(9999);
//        ip = (ip != null && ip.trim().length() > 0) ? ip : IpUtil.getIp();
//        String ipPort = IpUtil.getIpPort(ip, port);
//        String address = "http://{ip_port}/".replace("{ip_port}", ipPort);
//        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appName, address);
//        ReturnT<String> returnT = XxlJobRemotingUtil.postBody(adminAddresses + "api/registry", accessToken, 6, registryParam, String.class);
//        log.info("注册执行器返回结果：{}", returnT);
//
//    }
//
//
//    /***
//     * 生成 日期对应的  cron表达式
//     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
//     * @param date  : 时间点
//     * @return String
//     */
//    private String getCron(Date date) {
//        String dateFormat = "ss mm HH dd MM ? yyyy";
//        return DateUtil.format(dateFormat, date);
//    }
//
//
//    /**
//     * 获取cookie
//     *
//     * @return String
//     */
//    private String getCookie() {
//        String path = adminAddresses + "/login";
//        Map<String, Object> hashMap = new HashMap<>();
//        hashMap.put("userName", loginUsername);
//        hashMap.put("password", loginPwd);
//        log.info("获取xxl cookie,请求参数：{}", hashMap);
//
//        try {
//            Map<String, String> cookieMap = new HashMap<>();
//            Object resp = OkHttpUtils.postForm(path, hashMap, true, cookieMap);
//            log.info("调用xxl增加执行器,成功返回信息:{}", resp);
//            if (resp != null) {
//                return cookieMap.get("cookie");
//            }
//
//        } catch (Exception e) {
//            log.error("调用xxl获取cookie失败：" + e.getMessage(), e);
//        }
//
//        return "";
//
//    }
//
//
//}
