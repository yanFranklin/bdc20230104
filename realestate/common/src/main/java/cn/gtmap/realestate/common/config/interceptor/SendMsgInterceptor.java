package cn.gtmap.realestate.common.config.interceptor;

import cn.gtmap.realestate.common.core.domain.BdcMsgConfig;
import cn.gtmap.realestate.common.core.domain.BdcMsgLog;
import cn.gtmap.realestate.common.core.domain.BdcMsgMain;
import cn.gtmap.realestate.common.core.service.feign.init.BdcRunSqlFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.common.matcher.MessageMatcher;
import cn.gtmap.realestate.common.util.CommonUtil;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Import({EntityMapper.class, MessageMatcher.class, BdcRunSqlFeignService.class})
public class SendMsgInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SendMsgInterceptor.class);

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private MessageMatcher messageMatcher;

    @Autowired
    private BdcRunSqlFeignService bdcRunSqlFeignService;

    public static List<BdcMsgMain> msgMainList;

    /** 短信异步发送线程池 */
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
            // 核心线程数量
            2,
            // 最大线程数量
            2,
            // 空闲等待
            0L, TimeUnit.SECONDS,
            // 阻塞队列100
            new ArrayBlockingQueue<>(100),
            // 过多任务直接主线程处理
            new ThreadPoolExecutor.CallerRunsPolicy());


    // 在构造方法执行后执行
    @PostConstruct
    public void init() {
        initMap();
    }

    public void initMap() {
        msgMainList = entityMapper.selectByExample(new Example(BdcMsgMain.class));
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        logger.info("当前待处理URL：{}{}{}", httpServletRequest.getRequestURL(), "?", httpServletRequest.getQueryString());

        // 请求路径参数
        Map<String, String> pathVariableMap = this.getPathVariableMap(httpServletRequest);
        // 请求后缀参数
        Map<String, String> requestParamMap = CommonUtil.convertMap(httpServletRequest.getParameterMap());
        // 响应状态
        int responseStatus = httpServletResponse.getStatus();

        // 采用线程池异步发送短信处理 modified by zhuyong 20200514
        // 这里不要单独传request参数，会有请求参数被拦截问题，直接传需要参数
        threadPool.execute(() -> sendMsg(httpServletRequest.getRequestURI(), pathVariableMap,requestParamMap, responseStatus));
    }

    /**
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param requestUri 请求路径
     * @param pathVariableMap 请求路径参数
     * @param requestParamMap 请求后缀参数
     * @param responseStatus  响应状态
     * @description  短信异步发送 ( 拦截请求url，判断是否发送短信 )
     */
    public void sendMsg(String requestUri, Map<String, String> pathVariableMap, Map<String, String> requestParamMap, int responseStatus) {
        //logger.info("发送短信参数：{}，{}，{}，{}",requestUri,pathVariableMap,requestParamMap,responseStatus);
        if (CollectionUtils.isEmpty(msgMainList) || responseStatus != 200) {
            //logger.info("短信配置为空！");
            return;
        }

        PathMatcher matcher = new AntPathMatcher();
        List<String> phoneList = new ArrayList<>();

        for(BdcMsgMain main : msgMainList){
            //logger.info("当前短信配置为：{}",main.toString());
            boolean flag = (null == main || StringUtils.isAnyBlank(requestUri, main.getUrl(), main.getParamWhere(), main.getParamSql()) || !matcher.match(main.getUrl(), requestUri));
            //logger.info("当前短信发送过滤：{}",flag);
            if(flag) {
                // 非目标拦截URL不做处理
                continue;
            }
            logger.info("当前url被拦截，拦截为：{}, 被拦截url为：{},拦截的数据id为:{}", main.getUrl(), requestUri,main.getMainid());

            // 1.获取MsgType（大云需要）
            String msgType = getMsgType(main);

            // 2.获取数据
            String msgInfoSql = this.getMsgInfoSql(pathVariableMap, requestParamMap, main);
            logger.info("发送短信功能sql执行：{}", msgInfoSql);

            List<HashMap> msgInfoList = bdcRunSqlFeignService.runSql(msgInfoSql);
            if (CollectionUtils.isEmpty(msgInfoList)) {
                logger.info("当前sql未查询到数据");
                continue;
            }

            logger.info("短信发送sql查询结果：{}",msgInfoList);
            // 3.判断参数 如果都全，发送，不全不发送
            msgInfoList.forEach(msgInfoMap -> {
                String phone = MapUtils.getString(msgInfoMap, "dh");
                if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(msgType) && msgInfoMap != null) {
                    if (checkPhone(phone)) {
                        if (!phoneList.contains(phone)) {
                            smsMsg(msgInfoMap, phone, msgType);
                            saveMsgLog(JSON.toJSONString(msgInfoMap), String.valueOf(responseStatus), phone, "0", "发送成功");
                            phoneList.add(phone);
                        }
                    } else {
                        saveMsgLog(JSON.toJSONString(msgInfoMap), String.valueOf(responseStatus), phone, "1", "电话号格式不正确");
                    }
                } else {
                    saveMsgLog(JSON.toJSONString(msgInfoMap), String.valueOf(responseStatus), phone, "1", "参数缺失");
                }
            });
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param httpServletRequest request请求
     * @return {Map} 参数Map集合
     * @description  获取请求路径中的参数
     */
    private Map<String, String> getPathVariableMap(HttpServletRequest httpServletRequest) {
        NativeWebRequest webRequest = new ServletWebRequest(httpServletRequest);
        return  (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pathVariableMap 请求路径参数
     * @param requestParamMap 请求后缀参数
     * @param config  短信处理SQL配置
     * @return  {String} 替换完参数值的SQL
     * @description
     *      获取发送短信需要的信息处理SQL，例如查询接收短信的手机号码
     */
    private String getMsgInfoSql(Map<String, String> pathVariableMap, Map<String, String> requestParamMap, BdcMsgMain config) {
        if (StringUtils.isNotBlank(MapUtils.getString(pathVariableMap, config.getParamWhere()))) {
            // 从请求路径参数中匹配参数值
            return config.getParamSql().replace("@{" + config.getParamWhere() + "}", "'" + pathVariableMap.get(config.getParamWhere()) + "'");
        } else {
            // 从请求后缀参数中匹配参数值
            if (StringUtils.isNotBlank(MapUtils.getString(requestParamMap, config.getParamWhere()))) {

                return config.getParamSql().replace("@{" + config.getParamWhere() + "}", "'" + requestParamMap.get(config.getParamWhere()) + "'");

            }
        }

        return config.getParamSql().replace("@{" + config.getParamWhere() + "}", "'***###'");
    }

    /**
     * 记录日志
     *
     * @param parma     替换的参数
     * @param status    需要发送短信方法的状态值
     * @param phone     电话号
     * @param issuccess 是否发送成功
     * @param errorMsg  失败信息
     */
    private void saveMsgLog(String parma, String status, String phone, String issuccess, String errorMsg) {
        BdcMsgLog log = new BdcMsgLog();
        log.setLogid(UUIDGenerator.generate16());
        log.setIssucc(issuccess);
        log.setParam(parma);
        log.setPhone(phone);
        log.setReturncode(status);
        log.setSendtime(new Date());
        log.setException(errorMsg);
        entityMapper.insertSelective(log);
    }

    /**
     * 验证电话号码是否扶额和规范
     *
     * @param phone
     * @return
     */
    private boolean checkPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (StringUtils.isNotBlank(phone)) {
            if (phone.length() != 11) {
                return false;
            } else {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(phone);
                return m.matches();
            }
        }
        return false;
    }

    /**
     * 获取MsgType（大云需要）
     *
     * @param main
     * @return
     */
    private String getMsgType(BdcMsgMain main) {
        if (main != null && StringUtils.isNotBlank(main.getConfigid())) {
            BdcMsgConfig msgConfig = entityMapper.selectByPrimaryKey(BdcMsgConfig.class, main.getConfigid());
            if (msgConfig != null && StringUtils.isNotBlank(msgConfig.getMsgtype())) {
                return msgConfig.getMsgtype();
            }
        }
        return null;
    }


    /**
     * 短信发送（大云接口）
     *
     * @param data
     * @param phone
     * @param msgType
     */
    public void smsMsg(Map<String, String> data, String phone, String msgType) {
        messageMatcher.sendMsg(clientId, data, phone, msgType);
    }
}
