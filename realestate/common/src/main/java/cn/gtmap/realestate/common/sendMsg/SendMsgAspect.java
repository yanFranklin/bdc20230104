package cn.gtmap.realestate.common.sendMsg;

import cn.gtmap.realestate.common.config.SendMsgConfig;
import cn.gtmap.realestate.common.core.annotations.SendMsg;
import cn.gtmap.realestate.common.core.domain.BdcMsgLog;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZhgzDO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzZhGzQO;
import cn.gtmap.realestate.common.core.service.feign.engine.BdcGzZhGzFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.MessageMatcher;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.DateUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/10/20
 * @description 规则变动发送短信通知注解
 * value:功能描述
 * action:动作类型
 * excutepoint:执行切点
 * gzlx:扩展字段
 */
@Component
@Aspect
@Import({MessageMatcher.class})

public class SendMsgAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgAspect.class);

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Autowired
    private UserManagerUtils userManagerUtils;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private MessageMatcher messageMatcher;

    @Autowired
    private BdcGzZhGzFeignService bdcGzZhGzFeignService;

    @Autowired
    private SendMsgConfig sendMsgConfig;


    @Pointcut(value = "@annotation(sendMsg)")
    public void pointCut(SendMsg sendMsg){
    }

    @AfterReturning(value="pointCut(sendMsg)",returning="result")
    public void doAfter(JoinPoint point, SendMsg sendMsg, Object result){
        try{
            // 通过方法名称与切点做对比，判断是否在当前方法中执行
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if(stackTrace != null && stackTrace.length > 1){
                // 当前方法名称
                String clazz = Thread.currentThread().getStackTrace()[1].getMethodName();
                LOGGER.info("获取当前类中执行的方法名为：{}",clazz);
                // 在是否在后置增强通知中执行发送短信
                if(!StringUtils.equals(clazz,sendMsg.excutePoint())){
                    return;
                }
            }else{
                LOGGER.error("无法获取切面中当前方法名称！");
            }

            /*if(null == point.getArgs() && point.getArgs().length > 0){
                throw new AppException("短信通知切面为获取到入参！");
            }*/

            // 被注解的方法名
            String methodName = point.getSignature().getName();

            LOGGER.info("开始发送短信操作,当前方法为：{}",methodName);
            // 解析参数和返回值 都放到短信map中
            Map resultMap = dealParam(point,result);
            LOGGER.info("参数合集：{}",JSONObject.toJSONString(resultMap));

            // 特殊处理的短信模板集合
            Map map = new HashMap();

            // 特殊处理
            this.tscl(map,methodName,point,result);

            for(Object key : resultMap.keySet()){
                if(!map.containsKey(key) || null == map.get(key)){
                    map.put(key,resultMap.get(key));
                }
            }

            sendMsg(map,sendMsg,methodName);

        }catch (Exception e){
            LOGGER.error("规则改动短信通知逻辑异常",e);
        }
    }

    @Before(value="pointCut(sendMsg)")
    public void doBefore(JoinPoint point, SendMsg sendMsg){
        try{
            // 通过方法名称与切点做对比，判断是否在当前方法中执行
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if(stackTrace != null && stackTrace.length > 1){
                // 当前方法名称
                String clazz = Thread.currentThread().getStackTrace()[1].getMethodName();
                LOGGER.info("获取当前类中执行的方法名为：{}",clazz);
                // 在是否在前置增强通知中执行发送短信
                if(!StringUtils.equals(clazz,sendMsg.excutePoint())){
                    return;
                }
            }else{
                LOGGER.error("无法获取切面中当前方法名称！");
            }


            if(null == point.getArgs() && point.getArgs().length > 0){
                throw new AppException("短信通知切面为获取到入参！");
            }
            String methodName = point.getSignature().getName();

            LOGGER.info("开始发送短信操作,当前方法为：{}",methodName);

            // 根据参数的类型 来解析参数,获取规则名称和规则id
            Map map = new HashMap();

            // 解析参数和返回值 都放到短信map中
            Map resultMap = dealParam(point,null);

            // 特殊处理
            this.tscl(map,methodName,point,null);

            for(Object key : resultMap.keySet()){
                if(!map.containsKey(key) || null == map.get(key)){
                    map.put(key,resultMap.get(key));
                }
            }

            sendMsg(map,sendMsg,methodName);

        }catch (Exception e){
            LOGGER.error("规则改动短信通知逻辑异常",e);
        }
    }

    private void sendMsg (Map map,SendMsg sendMsg,String methodName) throws Exception{

        // 读取短信模板配置
        Map<String,String> mmsgxxMap = sendMsgConfig.getMsgxxMap().get(methodName);

        // 因为要存日志 阿里云短信平台规定参数长度不能超过20
        Map resultMap = new HashMap();
        if(MapUtils.isNotEmpty(map)){
            for(Object key : map.keySet()){
                String value = map.get(key) == null?"":map.get(key).toString();
                if(StringUtils.isNotBlank(value) && value.length() < 20){
                    resultMap.put(key,map.get(key));
                }
            }
        }else{
            return;
        }

        resultMap.put("action",sendMsg.action());
        resultMap.put("gzlx",sendMsg.gzlx());
        resultMap.put("userName",userManagerUtils.getUserAlias());
        resultMap.put("description",sendMsg.description());

        List<String> dhList = new ArrayList<>();

        if(MapUtils.isNotEmpty(mmsgxxMap)){
            // 优先取dh字段
            if(mmsgxxMap.containsKey("dh") && StringUtils.isNotBlank(mmsgxxMap.get("dh"))){
                dhList = Arrays.asList(mmsgxxMap.get("dh").split(","));
            }else if(mmsgxxMap.containsKey("wbdhkey") && StringUtils.isNotBlank(mmsgxxMap.get("wbdhkey"))){
                String wbdhkeys = mmsgxxMap.get("wbdhkey");
                List<String> wbdhkeyList = Arrays.asList(wbdhkeys.split(","));
                for(String key : wbdhkeyList){
                    if(resultMap.containsKey(key)){
                        dhList.add(resultMap.get(key).toString());
                    }
                }
                if(CollectionUtils.isEmpty(dhList)){
                    throw new AppException("参数中缺少外部电话的键值，无法获取电话");
                }
            }else{
                throw new AppException("缺失发送短信的电话的配置");
            }

            if(!mmsgxxMap.containsKey("msgtype") || StringUtils.isBlank(mmsgxxMap.get("msgtype"))){
                throw new AppException("缺失msgType配置");
            }

            resultMap.putAll(mmsgxxMap);
        }else{
            throw new AppException("缺失短信配置信息");
        }

        LOGGER.info("开始调取大云短信发送接口，此时参数合集：{}",JSONObject.toJSONString(resultMap));

        for(String phone : dhList){
            resultMap.put("dh",phone);
            resultMap.put("time",DateUtils.formateYmdhms(new Date()));
            if (checkPhone(phone)) {
                LOGGER.info( "{}方法短信发送参数集：{}",methodName, resultMap.toString());
                smsMsg(resultMap, phone, resultMap.get("msgtype").toString());
                saveMsgLog(JSON.toJSONString(resultMap), "200", phone, "0", "发送成功");
            } else {
                saveMsgLog(JSON.toJSONString(resultMap), "500", phone, "1", "电话号格式不正确");
            }
        }
    }

    /**
     * 验证电话号码符合规范
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
     * 短信发送（大云接口）
     *
     * @param data
     * @param phone
     * @param msgType
     */
    public void smsMsg(Map<String, String> data, String phone, String msgType) {
        messageMatcher.sendMsg(clientId, data, phone, msgType);
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
     * 处理注解方法的入参和返回值
     * @param point
     * @param resultParam
     * @return
     */
    private static Map dealParam(JoinPoint point, Object resultParam) throws Exception{
        Map map = new HashMap();
        if(point != null){
            // 所有参数集合
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] strings = methodSignature.getParameterNames();
            List<String> argNames = Arrays.asList(strings);
            List <Object> listParam = Arrays.asList(point.getArgs());
            if(CollectionUtils.isNotEmpty(argNames) && CollectionUtils.isNotEmpty(listParam)){
                LOGGER.info("当前参数集合为：{}",JSONObject.toJSONString(listParam));
                LOGGER.info("当前参数名集合为：{}",JSONObject.toJSONString(argNames));
                if(argNames.size() != listParam.size()){
                    throw new AppException("参数长度和参数名长度不一致，请检查");
                }

                for(int i=0 ;i< listParam.size();i++){
                    dealSingleParam(map,listParam.get(i),argNames.get(i),0);
                }
                dealSingleParam(map,resultParam,"",0);
            }

        }
        return map;
    }

    /**
     *
     * @param map 解析参数返回值
     * @param item 单个参数
     * @param paramName 入参的参数名称
     * @param qtcs 参数解析的嵌套次数
     * @throws Exception
     */
    private static void dealSingleParam(Map map,Object item,String paramName,int qtcs) throws Exception{
        if(null == item){
            return;
        }
        // 解析基础类型
        if(item instanceof String || item instanceof Integer
                || item instanceof Double || item instanceof Float
                || item instanceof Boolean || item instanceof Short
                || item instanceof Long
                ){
            if(StringUtils.isNotBlank(paramName)){
                map.put(paramName,item);
            }
        // 第一次的时候处理集合 如果是第二次即 参数为集合里面套集合 这里参数将不再处理，需要自行特殊处理
        }else if(item instanceof List  && qtcs == 0){
            LOGGER.info("解析集合类型的参数：{}",JSONObject.toJSONString(item));
            for(Object item2 : (List)item){
                dealSingleParam(map,item2,"",1);
            }
        }else{
            String jsonStr = JSONObject.toJSONString(item, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
            Map singleMap = JSONObject.parseObject(jsonStr, HashMap.class);
            for(Object key : singleMap.keySet()){
                if(null == key){
                    continue;
                }
                String keyStr = key.toString();
                if(map.containsKey(keyStr) && map.get(keyStr) != null){
                    String value = map.get(keyStr).toString();
                    String resultValue = singleMap.get(keyStr) == null?"":singleMap.get(keyStr).toString();
                    if(!StringUtils.equals(value,resultValue)){
                        value = value + "," + singleMap.get(keyStr);
                        map.put(keyStr,value);
                    }
                }else{
                    map.put(keyStr,singleMap.get(keyStr));
                }
            }
        }
    }


    /**
     * 参数与短信模板的一些特殊业务处理
     * @param map 短信模板需要的参数
     * @param methodName 方法名
     * @param point 切点
     * @param result 返回值
     */
    private void tscl(Map map ,String methodName,JoinPoint point,Object result){

        // 保存子规则拦截的参数 还没有生成规则id 需要在返回值中拿到生成的规则ID
        if(StringUtils.equals(methodName,CommonConstantUtils.METHOD_NAME_ZGZ_SAVE)){// 保存子规则
            Object param = point.getArgs()[0];
            BdcGzZgzDTO bdcGzZgzDTO = (BdcGzZgzDTO)param;
            String gzid = StringUtils.isNotBlank(bdcGzZgzDTO.getGzid())?bdcGzZgzDTO.getGzid():String.valueOf(result);
            map.put("gzid",gzid);
            map.put("gzmc",bdcGzZgzDTO.getGzmc());
        // 保存组合规则拦截的参数 还没有生成规则id 需要在返回值中拿到生成的规则ID
        }else if(StringUtils.equals(methodName,CommonConstantUtils.METHOD_NAME_ZHGZ_SAVE)
                || StringUtils.equals(methodName,CommonConstantUtils.METHOD_NAME_ZHGZ_UPDATE)){// 保存组合规则
            Object param = point.getArgs()[0];
            BdcGzZhgzDO bdcGzZhgzDO = (BdcGzZhgzDO)param;
            String gzid = StringUtils.isNotBlank(bdcGzZhgzDO.getZhid())?bdcGzZhgzDO.getZhid():String.valueOf(result);
            map.put("gzid",gzid);
            map.put("gzmc",bdcGzZhgzDO.getZhmc());
        // 删除组合规则，入参和返回值中只有zgid,需要通过gzid查询规则实体
        }else if(StringUtils.equals(methodName,CommonConstantUtils.METHOD_NAME_ZHGZ_DELETE)){// 删除组合规则
            Object param = point.getArgs()[0];
            String gzid = param.toString();
            map.put("gzid",gzid);
            BdcGzZhGzQO bdcGzZhGzQO = new BdcGzZhGzQO();
            bdcGzZhGzQO.setZhid(gzid);
            List<BdcGzZhgzDO> listZhgz = bdcGzZhGzFeignService.queryBdcGzZhGzDOList(bdcGzZhGzQO);
            if(CollectionUtils.isNotEmpty(listZhgz)){
                map.put("gzmc",listZhgz.get(0).getZhmc());
            }
        }
    }

}
