package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.bo.anno.PublicQueryLog;
import cn.gtmap.realestate.exchange.core.domain.BdcXxcxjg;
import cn.gtmap.realestate.exchange.core.domain.BdcXxcxjl;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.GkcxMapper;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/5/29.
 * @description 公开查询
 */
@Aspect
@Component
public class PublicQueryLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicQueryLogAspect.class);
    @Autowired
    EntityMapper sjptEntityMapper;
    @Autowired
    private GkcxMapper gkcxMapper;

    @Pointcut("@annotation(cn.gtmap.realestate.exchange.core.bo.anno.PublicQueryLog)")
    public void doLog() {
    }

    @Around("doLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Map<String, Object> returnMap = new HashMap();
        String param;
        // 获取被切函数 参数
        Object[] args = pjp.getArgs();
        if (args.length > 2) {
            param = args[2].toString();
        } else if (args.length > 1) {
            param = args[1].toString();
        } else {
            param = args[0].toString();
        }
        Object data = getData(pjp.proceed());
        // 获取目标方法参数
        Method method = getMethod(pjp);
        // 组织从目标方法获取的数据
        String cztype = "";
        String cxlb = "";
        String cxrmc = "";
        String czrmc ="";
        String jgid = "";
        String clientusercid = "";
        String cxmd = "";
        String cxjqbh = "";
        String ycxjlid = "";
        String jlid = "";
        Map map = JSON.parseObject(param,Map.class);
        if(MapUtils.isNotEmpty(map)){
            if(StringUtils.equals(MapUtils.getString(map,"cztype"),LogCzxxConstants.CZTYPE_CJZM)){
                cztype = LogCzxxConstants.CZTYPE_CJZM_NAME;
            }
            cxlb = MapUtils.getString(map,"cxlb");
            cxrmc = getCxrmc(map);
            czrmc = MapUtils.getString(map,"user");
            jgid = MapUtils.getString(map,"jgid");
            clientusercid = MapUtils.getString(map,"clientusercid");
            cxmd = StringUtils.isNotBlank(MapUtils.getString(map,"cxmd"))?MapUtils.getString(map,"cxmd"):MapUtils.getString(map,"ytcn");
            cxjqbh = MapUtils.getString(map,"computername");
            ycxjlid = MapUtils.getString(map,"ycxjlid");
            jlid = MapUtils.getString(map,"jlid");
        }
        String name ="";
        if(method != null){
            // 读取自定义注解内容
            PublicQueryLog publicMethodLog = method.getAnnotation(PublicQueryLog.class);
            if(publicMethodLog != null){
                name = publicMethodLog.name();
            }
        }
        // 获取ip地址
        String ip = getIp();
        Map<String, Object> result = (Map<String, Object>) generateLog(StringUtils.isNotBlank(cxlb) ? cxlb :name,data, cxrmc, ip,
                czrmc,jgid, cxmd, clientusercid, cxjqbh, ycxjlid,jlid, cztype);
        returnMap.put("cxbh", MapUtils.getString(result,"cxbh"));
        return returnMap;
    }
    private String getCxrmc(Map map){
        StringBuilder cxrmc = new StringBuilder();
        if(StringUtils.isNotBlank(MapUtils.getString(map,"qlrlist"))){
            cxrmc.append("权利人列表" + ":" +MapUtils.getString(map,"qlrlist") + "  ");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(map,"selarea"))){
            cxrmc.append("行政区域" + ":" + MapUtils.getString(map,"selarea")  + "  ");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(map,"computermac"))){
            cxrmc.append("自助查档机MAC地址" + ":" + MapUtils.getString(map,"computermac")  + "  ");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(map,"psw"))){
            cxrmc.append("认证密码" + ":" + MapUtils.getString(map,"psw")  + "  ");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(map,"clientusername"))){
            cxrmc.append("查询人名称" + ":" + MapUtils.getString(map,"clientusername")  + "  ");
        }
        String cxmd = StringUtils.isNotBlank(MapUtils.getString(map,"cxmd"))?MapUtils.getString(map,"cxmd"):MapUtils.getString(map,"ytcn");
        if(StringUtils.isNotBlank(cxmd)){
            cxrmc.append("查询目的" + ":" +cxmd + "  ");
        }
        return cxrmc.toString();
    }
    /**
     * 处理申请查询数据
     * @param dataTemp
     * @return
     */
    private Object getData(Object dataTemp){
        int size = 0;
        Map<String, Object> mapTypes;
        if (dataTemp instanceof Map || dataTemp instanceof HashMap) {
            dataTemp = JSON.toJSONString(dataTemp);
        }
        mapTypes = JSON.parseObject(dataTemp.toString());
        if (mapTypes.containsKey("rows")) {
            JSONArray jsonArray = (JSONArray) mapTypes.get("rows");
            if (jsonArray != null) {
                size = jsonArray.size();
            }
        }
        mapTypes.put("records", size);
        mapTypes.put("total", "1");
        dataTemp = mapTypes;
        return dataTemp;
    }
    private Method getMethod(JoinPoint joinPoint) {
        // 获取目标方法参数
        Object[] args = joinPoint.getArgs();
        Class[] argTypes = new Class[joinPoint.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        // 获取目标方法
        Method method = null;
        try {
            method = joinPoint.getTarget().getClass()
                    .getMethod(joinPoint.getSignature().getName(), argTypes);
        } catch (Exception e) {
            LOGGER.error("公共查询记录日志异常：", e);
        }
        return method;
    }

    /**
     * 获取ip
     * @return
     */
    private String getIp(){
        String ip = "";
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ip = request.getHeader("x-forwarded-for");
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                ip = ip.split(",")[0];
            }
            String[] header = {"Proxy-Client-IP","WL-Proxy-Client-IP","HTTP_CLIENT_IP","HTTP_X_FORWARDED_FOR","CLIENTIP","X-Real-IP"};
            for(String h:header){
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader(h);
                    break;
                }
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }

    /**
     * 生成日志
     * @return
     */
    private Object generateLog(String name, Object data, String cxrmc, String ip, String czrmc, String jgid, String cxmd, String clientusercid,
                               String cxjqbh, String ycxjlid,String jlid, String cztype) {

        final BdcXxcxjl bdcXxcxjl = new BdcXxcxjl();

        Map<String, Object> resultMap = new HashMap<>();
        // 获取查询编号
        String cxbhTemp = "";
        if (ArrayUtils.contains(LogCzxxConstants.CZTYPE_CXBH, cztype)) {
            if (StringUtils.equals(name, LogCzxxConstants.CXLB_DFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_YFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_YFCX)) {
                cxbhTemp = getYfBh();
            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_WFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_WFCX)) {
                cxbhTemp = getWfBh();
            } else if(StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_FWQS)){
                cxbhTemp = getFwqsBh();
            }
        }
        // 存放查询日志数据
        bdcXxcxjl.setJlid(UUIDGenerator.generate16());
        bdcXxcxjl.setYcxjlid(ycxjlid);
        bdcXxcxjl.setCzsj(Calendar.getInstance().getTime());
        bdcXxcxjl.setCzip(ip);
        if (StringUtils.isNoneBlank(name)) {
            bdcXxcxjl.setCxlb(name);
        }
        bdcXxcxjl.setCxrmc(cxrmc);
        bdcXxcxjl.setCzrmc(czrmc);
        bdcXxcxjl.setCxmd(cxmd);
        bdcXxcxjl.setCxrzjh(clientusercid);
        bdcXxcxjl.setCxjqbh(cxjqbh);
        bdcXxcxjl.setCzlb(cztype);
        if (StringUtils.equalsIgnoreCase(cztype, LogCzxxConstants.CZTYPE_CJZM_NAME)) {
            bdcXxcxjl.setCxbh(cxbhTemp);
        }
        // 存放查询结果数据
        final BdcXxcxjg bdcXxcxjg = new BdcXxcxjg();
        bdcXxcxjg.setJlid(bdcXxcxjl.getJlid());
        bdcXxcxjg.setXxnr(data == null ? "" : JSONObject.toJSONString(data));
        if (StringUtils.isBlank(jgid)) {
            bdcXxcxjg.setJgid(new SimpleDateFormat("yyyyMMddHHmmssSSS")
                    .format(new Date()));
        }
        sjptEntityMapper.saveOrUpdate(bdcXxcxjl, bdcXxcxjl.getJlid());
        sjptEntityMapper.saveOrUpdate(bdcXxcxjg, bdcXxcxjg.getJgid());
        resultMap.put("cxbh", bdcXxcxjl.getCxbh());
        resultMap.put("jlid", bdcXxcxjl.getJlid());
        resultMap.put("jgid", bdcXxcxjg.getJgid());
        return resultMap;
    }

    /**
     * 获取有房编号
     * @return
     */
    private String getYfBh() {
        String cxbh;
        String xzqhTemp = EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshqwm","");
        int lshws = Integer.parseInt(EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshws","6"));
        String lshExample =  EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.yflshExample","有房{nf}区号{qwm}{lsh}");
        String nf = new SimpleDateFormat("yyyy").format(new Date());
        String yf = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());
        String temp = "%0".concat(String.valueOf(lshws)).concat("d");
        @SuppressWarnings("unchecked")
        String lsh = String.format(temp, gkcxMapper.getZfcxLsh());
        lshExample = lshExample.replace("{nf}", nf);
        lshExample = lshExample.replace("{yf}", yf);
        lshExample = lshExample.replace("{day}", day);
        lshExample = lshExample.replace("{lsh}", lsh);
        lshExample = lshExample.replace("{qwm}", xzqhTemp);
        cxbh = lshExample;
        if (lsh.length() > lshws) {
            LOGGER.error("现有序列长度超过设置流水号尾数，请重置!");
            LOGGER.info("现有序列长度超过设置流水号尾数，请重置!");
            cxbh = "现有序列长度超过设置流水号尾数，请重置";
        }
        LOGGER.info("查询编号：{}",cxbh);
        return cxbh;
    }

    /**
     * 获取无房编号
     * @param xzqh
     * @param map
     * @return
     */
    private String getWfBh() {
        String cxbh;
        String xzqhTemp = EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshqwm","");
        int lshws = Integer.parseInt(EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshws","6"));
        String lshExample =  EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.wflshExample","无房{nf}区号{qwm}{lsh}");
        String nf = new SimpleDateFormat("yyyy").format(new Date());
        String yf = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());
        String temp = "%0".concat(String.valueOf(lshws)).concat("d");
        @SuppressWarnings("unchecked")
        String lsh = String.format(temp,gkcxMapper.getZfcxLsh());
        lshExample = lshExample.replace("{nf}", nf);
        lshExample = lshExample.replace("{yf}", yf);
        lshExample = lshExample.replace("{day}", day);
        lshExample = lshExample.replace("{lsh}", lsh);
        lshExample = lshExample.replace("{qwm}", xzqhTemp);
        cxbh = lshExample;
        if (lsh.length() > lshws) {
            LOGGER.error("现有序列长度超过设置流水号尾数，请重置!");
            LOGGER.info("现有序列长度超过设置流水号尾数，请重置!");
            cxbh = "现有序列长度超过设置流水号尾数，请重置";
        }
        return cxbh;
    }

    /**
     * 获取房屋权属编号
     * @param xzqh
     * @param map
     * @return
     */
    private String getFwqsBh() {
        String cxbh;
        String xzqhTemp = EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshqwm","");
        int lshws = Integer.parseInt(EnvironmentConfig.getEnvironment()
                .getProperty("zfcx.lshws","6"));
        String lshExample =  EnvironmentConfig.getEnvironment()
                .getProperty("fwqs.lshExample","公开{nf}区号{qwm}{lsh}");
        String nf = new SimpleDateFormat("yyyy").format(new Date());
        String yf = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());
        String temp = "%0".concat(String.valueOf(lshws)).concat("d");
        @SuppressWarnings("unchecked")
        String lsh = String.format(temp, gkcxMapper.getCxjFwqscxLsh());
        lshExample = lshExample.replace("{nf}", nf);
        lshExample = lshExample.replace("{yf}", yf);
        lshExample = lshExample.replace("{day}", day);
        lshExample = lshExample.replace("{lsh}", lsh);
        lshExample = lshExample.replace("{qwm}", xzqhTemp);
        cxbh = lshExample;
        if (lsh.length() > lshws) {
            LOGGER.error("现有序列长度超过设置流水号尾数，请重置!");
            LOGGER.info("现有序列长度超过设置流水号尾数，请重置!");
            cxbh = "现有序列长度超过设置流水号尾数，请重置";
        }
        return cxbh;
    }
}
