package cn.gtmap.realestate.exchange.core.aop;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.bo.anno.AnalysisAuditLog;
import cn.gtmap.realestate.exchange.core.domain.BdcXxcxjg;
import cn.gtmap.realestate.exchange.core.domain.BdcXxcxjl;
import cn.gtmap.realestate.exchange.core.mapper.sjpt.GkcxMapper;
import cn.gtmap.realestate.exchange.service.inf.CommonService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.LogCzxxConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wuhongrui 2017年1月6日
 * @version v1.0
 * @description 公开查询相关 aop 日志
 * 2019-10-24 liyinqiao 从 analysis包中迁移至3.0
 * 2020-05-29 chenchunxue 历史代码太多，不易排查问题，以后不再使用这个拦截公开查询新增加了一个拦截PublicQueryLogAspect
 */
@Aspect
@Component
@Deprecated
public class PublicLogAspect {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PublicLogAspect.class);
    @Autowired
    private GkcxMapper gkcxMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private EntityMapper sjptEntityMapper;


    private final String allAreaCxSufix = "-接口查询";

    @Pointcut("@annotation(cn.gtmap.realestate.exchange.core.bo.anno.AnalysisAuditLog)")
    public void doLog() {
    }

    private String getAuditContents(Object object, String description) {
        String descriptionTemp = description;
        Map<String, Object> contentMap = Maps.newHashMap();
        if (StringUtils.isBlank(description)) {
            descriptionTemp = "操作内容";
        }
        Object[] objectArrary = {object};
        contentMap.put(descriptionTemp, filterArguments(objectArrary));
        return JSON.toJSONString(contentMap);
    }

    private Object filterArguments(Object[] args) {
        List<Object> arguments = Lists.newArrayList();
        for (Object arg : args) {
            if (arg instanceof Model || arg instanceof Pageable || arg instanceof HttpServletResponse
                    || arg instanceof HttpServletRequest) {
                continue;
            }
            arguments.add(arg);
        }
        return arguments;
    }

    @Around("doLog()")
    @SuppressWarnings("unchecked")
    public Object doAround(ProceedingJoinPoint pjp)
            throws Throwable {
        HashMap<String, Object> cxmap = new HashMap<String, Object>();
        Map<String, Object> dataMap = null;
        Object data;
        try {
            String cztype = "";
            String cxrmc = "";
            String jgid = "";
            String xzqh = "";
            String map;
            // 获取目标方法参数
            Method method = getMethod(pjp);
            // 获取被切函数 参数
            Object[] args = pjp.getArgs();
            data = pjp.proceed();
            Object dataTemp = data;
            /**
             * @author <a href="mailto:liangqing@gtmap.cn">liangqing</a>
             * @description 查询记录日志，翻页也相当于查询并记录日志，故此处
             * 是为了保存每次查询的当前页的记录和页码
             */
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
            if (args.length > 2) {
                map = args[2].toString();
            } else if (args.length > 1) {
                map = args[1].toString();
            } else {
                map = args[0].toString();
            }
            // 组织从目标方法获取的数据
            JSONObject json = JSON.parseObject(map);
            String czrmc = "";
            String ip = "";
            String cxlb = "";
            String zzcxjcxbh = "";
            String cxrzjh = "";
            String cxmd = "";
            String clientusercid = "";
            String cxjqbh = "";
            String isCxLc = "";
            String account = "";
            String ycxjlid = "";
            String allAreaJkcx = "";//判断是否大市接口查询，由于大市接口所用service，可能会和本地查询是同一个，无法修改log的name属性时，可添加该参数，会在name属性后添加固定的后缀
            String user = "";
            String cxbh1 = "";
            String jlid1 = "";
            if (json.size() > 0) {
                for (Map.Entry<String, Object> entry : json.entrySet()) {
                    if ("user".equals(entry.getKey()) && entry.getValue() != null) {
                        user = entry.getValue().toString();
                    } else if ("allAreaJkcx".equals(entry.getKey()) && entry.getValue() != null) {
                        allAreaJkcx = entry.getValue().toString();
                    } else if (("czrmc".equals(entry.getKey()) || "jbr".equals(entry.getKey())) && entry.getValue() != null) {// 由于fr3打印的话是不经过登录的，所以获取不到操作人员名称，故此将其作为参数传入，但不作为查询条件记录
                        czrmc += entry.getValue();
                    } else if ("jgid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        jgid += entry.getValue();
                    } else if ("account".equals(entry.getKey())
                            && entry.getValue() != null) {
                        account += entry.getValue();
                    } else if ("isCxLc".equals(entry.getKey())
                            && entry.getValue() != null) {
                        isCxLc += entry.getValue();
                    } else if ("cxlb".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxlb = entry.getValue().toString();
                    } else if (("zzcxjcxbh".equals(entry.getKey()))
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        zzcxjcxbh = entry.getValue().toString();
                    } else if (("qlr".equals(entry.getKey()) || "xm".equals(entry.getKey()))
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        cxrmc += "权利人" + ":" + entry.getValue() + "  ";
                    } else if (("zh".equals(entry.getKey()))
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        cxrmc += "原证号" + ":" + entry.getValue() + "  ";
                    } else if (("qlrzjh".equals(entry.getKey()) || "zjh".equals(entry.getKey()))
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        cxrmc += "权利人证件号" + ":" + entry.getValue() + "  ";
                    } else if ("xms".equals(entry.getKey())
                            && entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                        if (json.get("cztype").toString()
                                .equals(LogCzxxConstants.CZTYPE_JTCX)) {
                            cxrmc += "家庭权利人" + ":" + entry.getValue() + "  ";
                        } else {
                            cxrmc += "权利人" + ":" + entry.getValue() + "  ";
                        }
                    } else if ("zjhs".equals(entry.getKey())
                            && entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                        if (json.get("cztype").toString()
                                .equals(LogCzxxConstants.CZTYPE_JTCX)) {
                            cxrmc += "家庭权利人证件号" + ":" + entry.getValue() + "  ";
                        } else {
                            cxrmc += "权利人证件号" + ":" + entry.getValue() + "  ";
                        }
                    } else if (StringUtils.equals(entry.getKey(), "qlrzjhs")
                            && entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                        cxrmc += "权利人证件号" + ":" + entry.getValue() + "  ";
                    } else if (StringUtils.equals(entry.getKey(), "sfzh")
                            && entry.getValue() != null && StringUtils.isNotBlank(entry.getValue().toString())) {
                        cxrmc += "权利人证件号" + ":" + entry.getValue() + "  ";
                    } else if ("shzzmc".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "社会组织名称" + ":" + entry.getValue() + "  ";
                    } else if ("cqzhs".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "产权证号" + ":" + entry.getValue() + "  ";
                    } else if ("ywr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "义务人" + ":" + entry.getValue() + "  ";
                    } else if ("dwdm".equals(entry.getKey())
                            && entry.getValue() != null) {
                        xzqh = entry.getValue().toString();
                    } else if ("cjr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "受理人" + ":" + entry.getValue() + "  ";
                    } else if ("zstype".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "证书类型" + ":" + entry.getValue() + "  ";
                    } else if ("bdcqzh".equals(entry.getKey())
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        cxrmc += "不动产权证" + ":" + entry.getValue() + "  ";
                    } else if ("zs".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "不动产权证" + ":" + entry.getValue() + "  ";
                    } else if ("zdzhh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "宗地宗海号" + ":" + entry.getValue() + "  ";
                    } else if ("dbr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "登簿人" + ":" + entry.getValue() + "  ";
                    } else if ("szr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "缮证人" + ":" + entry.getValue() + "  ";
                    } else if ("fzqssj".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "发证日期(起)" + ":" + entry.getValue() + "  ";
                    } else if ("fzjssj".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "发证日期(止)" + ":" + entry.getValue() + "  ";
                    } else if ("sdr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定人" + ":" + entry.getValue() + "  ";
                    } else if ("kssj".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定时间(起)" + ":" + entry.getValue() + "  ";
                    } else if ("jssj".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定时间(止)" + ":" + entry.getValue() + "  ";
                    } else if ("sdyy".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定原因" + ":" + entry.getValue() + "  ";
                    } else if ("sdzt".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定状态" + ":" + entry.getValue() + "  ";
                    } else if ("sdly".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "锁定来源" + ":" + entry.getValue() + "  ";
                    } else if ("fw".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if (StringUtils.indexOf("true,fw", entry.getValue().toString()) > -1) {
                            cxrmc += "查询原房产" + ":" + "是" + "  ";
                        }
                    } else if ("td".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if (StringUtils.indexOf("true,td", entry.getValue().toString()) > -1) {
                            cxrmc += "查询原土地" + ":" + "是" + "  ";
                        }
                    } else if ("dj".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if (StringUtils.indexOf("true,dj", entry.getValue().toString()) > -1) {
                            cxrmc += "查询不动产" + ":" + "是" + "  ";
                        }
                    } else if ("notExcludeFW".equals(entry.getKey())
                            && "yes".equals(entry.getValue())) {
                        cxrmc += "查询原房产" + ":" + "是" + "  ";
                    } else if ("notExcludeTD".equals(entry.getKey())
                            && "yes".equals(entry.getValue())) {
                        cxrmc += "查询原土地" + ":" + "是" + "  ";
                    } else if ("notExcludeDJ".equals(entry.getKey())
                            && "yes".equals(entry.getValue())) {
                        cxrmc += "查询不动产" + ":" + "是" + "  ";
                    } else if ("fileName".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "批量查询文件名" + ":" + entry.getValue() + "  ";
                    } else if ("TDFW".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查询房产或房地一体信息" + ":" + "是" + "  ";
                    } else if ("TD".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "查询纯土地信息" + ":" + "是" + "  ";
                    } else if ("HY".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查询海域信息" + ":" + "是" + "  ";
                    } else if ("LQ".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "查询林权信息" + ":" + "是" + "  ";
                    } else if ("type".equals(entry.getKey())
                            && "like".equals(entry.getValue())) {
                        cxrmc += "精确查询" + ":" + "否" + "  ";
                    } else if ("type".equals(entry.getKey())
                            && "sure".equals(entry.getValue())) {
                        cxrmc += "精确查询" + ":" + "是" + "  ";
                    } else if ("sfcxls".equals(entry.getKey())
                            && "true".equals(entry.getValue())) {
                        cxrmc += "是否查询历史" + ":" + "是" + "  ";
                    } else if (StringUtils.equals(entry.getKey(), "iscxLs")
                            && StringUtils.equals(String.valueOf(entry.getValue()), "true")) {
                        cxrmc += "是否查询历史" + ":" + "是" + "  ";
                    } else if ("blr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "办理人" + ":" + entry.getValue() + "  ";
                    } else if ("bljg".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "办理机构" + ":" + entry.getValue() + "  ";
                    } else if ("sfzt".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "收费状态" + ":" + entry.getValue() + "  ";
                    } else if ("proid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "proid" + ":" + entry.getValue() + "  ";
                    } else if ("cxArea".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "查询地区" + ":" + entry.getValue() + "  ";
                    } else if ("cxArea".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "被查询地区" + ":" + entry.getValue() + "  ";
                    } else if ("cxAreaCode".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "查询地区代码" + ":" + entry.getValue() + "  ";
                    } else if ("fjh".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "房间号（室号）" + ":" + entry.getValue() + "  ";
                    } else if ("djh".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "地籍号" + ":" + entry.getValue() + "  ";
                    } else if ("dh".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "幢号" + ":" + entry.getValue() + "  ";
                    } else if ("dlr".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "代理人" + ":" + entry.getValue() + "  ";
                    } else if ("dlrzjh".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "代理人证件号" + ":" + entry.getValue() + "  ";
                    } else if ("lb".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "类别" + ":" + entry.getValue() + "  ";
                    }
                    /*
                     * webService查询条件
                     */
                    else if ("selarea".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "行政区域" + ":" + entry.getValue() + "  ";
                    } else if ("clientusrename".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查询人(代理人)姓名" + ":" + entry.getValue() + "  ";
                    } else if ("clientusercid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        clientusercid = entry.getValue().toString();
                    } else if ("ytcn".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxmd = entry.getValue().toString();
                        cxrmc += "查询目的" + ":" + cxmd + "  ";
                    } else if ("computerid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        ip = entry.getValue().toString();
                    } else if ("computermac".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "自助查档机MAC地址" + ":" + entry.getValue() + "  ";
                    } else if ("clientusername".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查询人名称：" + entry.getValue().toString() + "  ";
                    } else if ("computername".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxjqbh = entry.getValue().toString();
                    } else if ("psw".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "认证密码" + ":" + entry.getValue() + "  ";
                    } else if ("qlrlist".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "权利人列表" + ":" + entry.getValue() + "  ";
                    } else if ("sfxsxzxx".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if ("true".equals(entry.getValue())) {
                            cxrmc += "是否显示限制信息:是  ";
                        } else {
                            cxrmc += "是否显示限制信息:否  ";
                        }
                    } else if ("slbh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "受理编号" + ":" + entry.getValue() + "  ";
                    } else if ("qszt".equals(entry.getKey()) && entry.getValue() != null) {
                        String mc = "";
                        String strQszt = entry.getValue().toString();
                        if (strQszt.contains(",")) {
                            String[] strArray = strQszt.split(",");
                            for (int i = 0; i < strArray.length; i++) {
                                mc += commonService.getBdcZdMcFromDm("qszt",strArray[i]);
                            }
                        } else {
                            mc += commonService.getBdcZdMcFromDm("qszt",strQszt);
                        }
                        cxrmc += "权属状态" + ":" + mc + "  ";
                    } else if (("ghyt".equals(entry.getKey()) || "yt".equals(entry.getKey()))
                            && entry.getValue() != null) {
                        cxrmc += "规划用途" + ":" + entry.getValue() + "  ";
                    } else if ("dldm".equals(entry.getKey())
                            ) {
                        cxrmc += "土地用途" + ":" + entry.getValue() + "  ";
                    } else if ("zl".equals(entry.getKey())
                            && entry.getValue() != null
                            && StringUtils.isNotBlank(entry.getValue()
                            .toString())) {
                        cxrmc += "坐落" + ":" + entry.getValue() + "  ";
                    } else if ("sqlx".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxmap.clear();
                        cxmap.put("dm", entry.getValue());
                        // 3.0 暂不拼接SQLX字段
//                        cxrmc += "申请类型"
//                                + ":"
//                                + bdcZdGlService.getSqlxListByDm(cxmap).get(0)
//                                .getMc() + "  ";
                    } else if ("bdcdyh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "不动产单元号" + ":" + entry.getValue() + "  ";
                    } else if ("cqzh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "不动产权证明号" + ":" + entry.getValue() + "  ";
                    } else if ("ycqzh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "原产权证号" + ":" + entry.getValue() + "  ";
                    } else if ("cfwh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查封文号" + ":" + entry.getValue() + "  ";
                    } else if ("cfjg".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查封机构" + ":" + entry.getValue() + "  ";
                    } else if ("bh".equals(entry.getKey()) && entry.getValue() != null) {
                        cxrmc += "受理编号" + ":" + entry.getValue() + "  ";
                    } else if ("sqr".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "异议申请人" + ":" + entry.getValue() + "  ";
                    } else if ("cflx".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "查封类型" + ":" + entry.getValue() + "  ";
                    } else if ("bdclx".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxmap.clear();
                        cxrmc += "不动产类型"
                                + ":"
                                + commonService.getBdcZdMcFromDm("bdclx",entry.getValue().toString()) + "  ";
                    }
                    // 判断操作类型
                    else if ("cztype".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if (entry.getValue().equals(LogCzxxConstants.CZTYPE_CX)) {
                            cztype = LogCzxxConstants.CZTYPE_CX_NAME;
                        } else if (entry.getValue().equals(
                                LogCzxxConstants.CZTYPE_CJZM)) {
                            cztype = LogCzxxConstants.CZTYPE_CJZM_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_DC)) {
                            cztype = LogCzxxConstants.CZTYPE_DC_NAME;
                        } else if (entry.getValue().equals(
                                LogCzxxConstants.CZTYPE_JTCX)) {
                            cztype = LogCzxxConstants.CZTYPE_JTCX_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_PLCX)) {
                            cztype = LogCzxxConstants.CZTYPE_PLCX_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_CK)) {
                            cztype = LogCzxxConstants.CZTYPE_CK_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_XG)) {
                            cztype = LogCzxxConstants.CZTYPE_XG_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_DY)) {
                            cztype = LogCzxxConstants.CZTYPE_DY_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_GRCX)) {
                            cztype = LogCzxxConstants.CZTYPE_GRCX_NAME;
                        } else if (entry.getValue().equals(LogCzxxConstants.CZTYPE_CKDJBXX)) {
                            cztype = LogCzxxConstants.CZTYPE_CKDJBXX_NAME;
                        }
                    } else if ("cxmd".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxmd = entry.getValue().toString();
                        cxrmc += "查询目的" + ":" + cxmd + "  ";
                    } else if ("activitybeginTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "转入活动节点开始时间" + ":" + entry.getValue() + "  ";
                    } else if ("activityoverTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "转入活动节点结束时间" + ":" + entry.getValue() + "  ";
                    } else if ("instancebeginTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "项目办结时间起" + ":" + entry.getValue() + "  ";
                    } else if ("instanceoverTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "项目办结时间止" + ":" + entry.getValue() + "  ";
                    } else if ("creatbeginTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "项目创建时间起" + ":" + entry.getValue() + "  ";
                    } else if ("creatoverTime".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "项目创建时间止" + ":" + entry.getValue() + "  ";
                    } else if ("isFinished".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if ("1".equals(entry.getValue())) {
                            cxrmc += "项目办结" + ":" + "未办结" + "  ";
                        } else if ("2".equals(entry.getValue())) {
                            cxrmc += "项目办结" + ":" + "已办结" + "  ";
                        }
                    } else if ("zdh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "宗地号" + ":" + entry.getValue() + "  ";
                    } else if ("fwmc".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "房屋名称" + ":" + entry.getValue() + "  ";
                    }else if ("qymc".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "企业名称" + ":" + entry.getValue() + "  ";
                    } else if ("qyzzjgdm".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxrmc += "企业组织结构代码" + ":" + entry.getValue() + "  ";
                    } else if ("cxfs".equals(entry.getKey())
                            && entry.getValue() != null) {
                        if (StringUtils.equals(entry.getValue().toString(), "sureZjh")) {
                            cxrmc += "查询方式:证件号精确  ";
                        } else if (StringUtils.equals(entry.getValue().toString(), "likeZjh")) {
                            cxrmc += "查询方式:证件号模糊  ";
                        }
                    } else if ("ycxjlid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        ycxjlid = CommonUtil.ternaryOperator(entry.getValue());
                    } else if ("cxbh".equals(entry.getKey())
                            && entry.getValue() != null) {
                        cxbh1 = CommonUtil.ternaryOperator(entry.getValue());
                    } else if ("jlid".equals(entry.getKey())
                            && entry.getValue() != null) {
                        jlid1 = CommonUtil.ternaryOperator(entry.getValue());
                    }
                }
            }
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
                ip = request.getHeader("x-forwarded-for");
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.indexOf(",") != -1) {
                    // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                    ip = ip.split(",")[0];
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("WL-Proxy-Client-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("CLIENTIP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeader("X-Real-IP");
                }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
            // 读取自定义注解内容
            AnalysisAuditLog publicMethodLog = method.getAnnotation(AnalysisAuditLog.class);
            String content = getAuditContent(method, pjp, publicMethodLog);
            if (StringUtils.isNotBlank(user)) {
                czrmc = user;
                //苏州构建调用信息公开查询带入用户，作为操作人
            }

//            else {
//                czrmc = SessionUtil.getCurrentUser() != null ? SessionUtil
//                        .getCurrentUser().getUsername() : czrmc;
//            }
            Map<String, Object> result;
            // 3.0 此处注掉 溧阳 版本判断
//            if (StringUtils.equals(AreaCodeConstants.LIYANG_CODE, Constants.PROPERTIES_AREAR_CODE) && (StringUtils.equals(LogCzxxConstants.CXLB_YFCX, publicMethodLog.name()) || StringUtils.equals(LogCzxxConstants.CXLB_WFCX, publicMethodLog.name()))) {
//                result = (Map<String, Object>) generateLog(
//                        publicMethodLog.name(), content, xzqh, dataTemp,
//                        cxrmc, ip, czrmc, jgid, zzcxjcxbh, cxrzjh, cxmd, clientusercid, cxjqbh, ycxjlid, allAreaJkcx, isCxLc, account, cxlb, user, cxbh1, jlid1, cztype);
//            } else {
                result = (Map<String, Object>) generateLog(
                        StringUtils.isNotBlank(cxlb) ? cxlb
                                : publicMethodLog.name(), content, xzqh, dataTemp,
                        cxrmc, ip, czrmc, jgid, zzcxjcxbh, cxrzjh, cxmd, clientusercid, cxjqbh, ycxjlid, allAreaJkcx, isCxLc, account, StringUtils.isNotBlank(cxlb) ? cxlb
                                : publicMethodLog.name(), user, cxbh1, jlid1, cztype);
//            }
            String cxbh = result.get("cxbh") != null ? result.get("cxbh")
                    .toString() : "";
            String jlid = result.get("jlid") != null ? result.get("jlid")
                    .toString() : "";
            jgid = result.get("jgid") != null ? result.get("jgid").toString()
                    : "";
            List<String> logNameList = Arrays.asList(LogCzxxConstants.CXLB_BDCXXGKCX, LogCzxxConstants.CXLB_XXGKCXQD
                    , LogCzxxConstants.CXLB_XXGKCXMXQD, LogCzxxConstants.CXLB_XXGKCXEXCELQD, LogCzxxConstants.CXLB_WFCX
                    , LogCzxxConstants.CXLB_YFCX, LogCzxxConstants.CXLB_DFCX, LogCzxxConstants.CXLB_CXJCX_FWQS,
                    LogCzxxConstants.CXLB_CXJCX_YFCX, LogCzxxConstants.CXLB_CXJCX_WFCX,
                    LogCzxxConstants.CXLB_XXGK_GJFJGZM, LogCzxxConstants.CXLB_XXGK_GJFWJGZM);
            if (StringUtils.isBlank(publicMethodLog.name())
                    || logNameList.contains(publicMethodLog.name())) {
                dataMap = (Map<String, Object>) data;
                dataMap.put("cxbh", cxbh);
                dataMap.put("jgid", jgid);
                dataMap.put("jlid", jlid);
            } else {
                if (StringUtils.equals("java.lang.String", data.getClass().getName())) {
                    dataMap = JSONObject.parseObject((String) data);
                    dataMap.put("cxbh", cxbh);
                    dataMap.put("jgid", jgid);
                    dataMap.put("jlid", jlid);
                } else {
                    dataMap = (Map<String, Object>) data;
                    dataMap.put("cxbh", cxbh);
                    dataMap.put("jgid", jgid);
                    dataMap.put("jlid", jlid);
                }
            }
        } catch (Exception e) {
            LOGGER.error("msg", e);
        }
        return dataMap;
    }

    /**
     * aop存储操作数据方法
     *
     * @param
     * @return
     * @author wuhongrui
     * @description
     */
    @SuppressWarnings("unchecked")
    private Object generateLog(String name, String content,
                               String xzqh, Object data, String cxrmc, String ip, String czrmc,
                               String jgid, String cxbh, String cxrzjh,
                               String cxmd, String clientusercid, String cxjqbh, String ycxjlid, String allAreaJkcx, String isCxLc, String account, String cxlb, String user, String cxbh1, String jlid, String cztype) {

        final BdcXxcxjl bdcXxcxjl = new BdcXxcxjl();
        /**
         * 获取流水号
         */
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String xzqhTemp = xzqh;
        // 3.0 暂只有根据传过来的XZQH 的 查询场景,所以注释掉获取当前用户的查询逻辑
//        String xzqhTemp = StringUtils.isBlank(xzqh) ? CommonUtil.ternaryOperator(super.getWhereXzqdm(), StringUtils.EMPTY) : xzqh;
        HashMap map = new HashMap();
        String cxbhTemp = "";
        if (StringUtils.isBlank(cxbh) && ArrayUtils.contains(LogCzxxConstants.CZTYPE_CXBH, cztype)) {
            if (StringUtils.equals(name, LogCzxxConstants.CXLB_BDCXXGKCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_XXGKCXQD)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_XXGKCXMXQD)
                    ) {
//                cxbhTemp = getXxgkCxbh(xzqhTemp, "lsh", map);
            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_DFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_YFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_YFCX)) {
                cxbhTemp = getYfBh(xzqhTemp, map);
            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_WFCX)
                    || StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_WFCX)) {
                cxbhTemp = getWfBh(xzqhTemp, map);
            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_QSZMDQD)) {
                int lshTemp = gkcxMapper.getFwqsCxLsh();
                cxbhTemp = new SimpleDateFormat("yyyyMM").format(new Date()) + xzqh + String.format("%06d", lshTemp);
            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_XXLYZMD)) {
                String nyr = new SimpleDateFormat("yyyyMMdd").format(new Date());
                String lsh = String.format("%05d", gkcxMapper.getBdcxxLybh());
                cxbhTemp = nyr.concat(xzqhTemp).concat(lsh);
            }else if(StringUtils.equals(name, LogCzxxConstants.CXLB_CXJCX_FWQS)){
                cxbhTemp = getFwqsBh(xzqhTemp, map);
            }
//            else if (StringUtils.equals(name, LogCzxxConstants.CXLB_XXGK_GJFJGZM)) {
//                cxbhTemp = getXxgkCxbh(xzqhTemp, "gjfYjgzmlsh", map);
//            } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_XXGK_GJFWJGZM)) {
//                cxbhTemp = getXxgkCxbh(xzqhTemp, "gjfWjgzmlsh", map);
//            }
            else {
                /*
                 *查询不进入生成编号的同步锁
                 * */
                if (StringUtils.isNotBlank(name) && (!StringUtils.contains(name, "查询") || StringUtils.contains(name, "查询机查询"))) {
                    cxbhTemp = getCxhTempForOtherQuery(map, xzqhTemp, name);
                }
            }
        }
        if (!StringUtils.equals(isCxLc, "1")) {
            if (StringUtils.equals("true", isCxLc)) {
                bdcXxcxjl.setJlid(jgid);
                jgid = "";
            } else {
                bdcXxcxjl.setJlid(UUIDGenerator.generate16());
            }
            bdcXxcxjl.setYcxjlid(ycxjlid);
            bdcXxcxjl.setCzsj(Calendar.getInstance().getTime());
            bdcXxcxjl.setCzip(ip);
            if (StringUtils.isNoneBlank(name)) {
                // 3.0 此处注掉 溧阳 版本判断
//                if (StringUtils.equals(AreaCodeConstants.LIYANG_CODE, Constants.PROPERTIES_AREAR_CODE)) {
//                    name = cxlb;
//                }
                bdcXxcxjl.setCxlb(name);
            } else {
                // 获取返回值判断查询结果是否为空
                Map<String, Object> dataMap = (Map<String, Object>) data;
                if ("0".equals(dataMap.get("records").toString())) {
                    bdcXxcxjl.setCxlb(LogCzxxConstants.CXLB_WFCX);
                } else if ("1".equals(dataMap.get("records").toString())) {
                    bdcXxcxjl.setCxlb(LogCzxxConstants.CXLB_YFCX);
                } else {
                    bdcXxcxjl.setCxlb(LogCzxxConstants.CXLB_DFCX);
                }
            }
            if (StringUtils.equalsIgnoreCase("true", allAreaJkcx)) {
                bdcXxcxjl.setCxlb(bdcXxcxjl.getCxlb() + allAreaCxSufix);
            }
            bdcXxcxjl.setCxrmc(cxrmc);
            bdcXxcxjl.setCzrmc(czrmc);
            bdcXxcxjl.setCxmd(cxmd);
            bdcXxcxjl.setCxrzjh(clientusercid);
            bdcXxcxjl.setCxjqbh(cxjqbh);
            bdcXxcxjl.setCzlb(cztype);
            if (StringUtils.equalsIgnoreCase(cztype, LogCzxxConstants.CZTYPE_CJZM_NAME) ||
                    StringUtils.equalsIgnoreCase(cztype, LogCzxxConstants.CZTYPE_CKDJBXX_NAME)) {
                bdcXxcxjl.setCxbh(cxbhTemp);
            }
        } else {
            bdcXxcxjl.setJlid(jgid);
        }

        final BdcXxcxjg bdcXxcxjg = new BdcXxcxjg();
        bdcXxcxjg.setJlid(bdcXxcxjl.getJlid());
        if (LogCzxxConstants.CXLB_WFCX.equals(bdcXxcxjl.getCxlb())
                || LogCzxxConstants.CXLB_YFCX.equals(bdcXxcxjl.getCxlb())
                || LogCzxxConstants.CXLB_DFCX.equals(bdcXxcxjl.getCxlb())
                || LogCzxxConstants.CZTYPE_GRCX_NAME.equals(bdcXxcxjl.getCxlb())
                || LogCzxxConstants.CZTYPE_JTCX_NAME.equals(bdcXxcxjl.getCxlb())) {
            /**
             * @author <a href="mailto:liangqing@gtmap.cn">liangqing</a>
             * @description 转换成xml存入bdc_xxcxjg
             */
            Map<String, Object> xmlMap = (Map<String, Object>) data;
            if (xmlMap != null && xmlMap.get("xml") != null) {
                String xml = xmlMap.get("xml").toString();
                if (StringUtils.isNotBlank(bdcXxcxjl.getCxbh())) {
                    xml = xml.replace(
                            "</datas>",
                            "<data name=\"CXBH\" type=\"String\">"
                                    + bdcXxcxjl.getCxbh() + "</data></datas>");
                    bdcXxcxjg.setXxnr("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
                            + xml);
                } else {
                    xml = xml.replace(
                            "</datas>",
                            "<data name=\"CXBH\" type=\"String\">"
                                    + cxbh1 + "</data></datas>");
                    bdcXxcxjg.setXxnr("<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
                            + xml);
                }
            }
            if (LogCzxxConstants.CZTYPE_CX_NAME.equals(bdcXxcxjl.getCzlb())
                    || LogCzxxConstants.CZTYPE_JTCX_NAME.equals(bdcXxcxjl.getCzlb())
                    || LogCzxxConstants.CZTYPE_PLCX_NAME.equals(bdcXxcxjl.getCzlb())
                    || LogCzxxConstants.CZTYPE_GRCX_NAME.equals(bdcXxcxjl.getCzlb())) {
                bdcXxcxjg.setXxnr(data == null ? "" : JSONObject.toJSONString(data));
            }

        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_XXGKCXMXQD)
                && bdcXxcxjl.getCzlb().equals(LogCzxxConstants.CZTYPE_CJZM_NAME)) {
            String xml = ((Map) data).get("xml").toString();
            if (xml.contains("<page>")) {
                xml = xml.replace("<page>", "<fetchdatas>");
                xml = xml.replace("</page>", "</fetchdatas>");
            }
            xml = xml.replace(
                    "</datas>",
                    "<data name=\"CXBH\" type=\"String\">"
                            + bdcXxcxjl.getCxbh() + "</data></datas>");
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + xml;
            bdcXxcxjg.setXxnr(xml);
        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_XXGKCXQD)
                && bdcXxcxjl.getCzlb().equals(LogCzxxConstants.CZTYPE_CJZM_NAME)) {
            String xml = ((Map) data).get("xml").toString();
            if (xml.contains("<page>")) {
                xml = xml.replace("<page>", "<fetchdatas>");
                xml = xml.replace("</page>", "</fetchdatas>");
            }
            xml = xml.replace(
                    "</datas>",
                    "<data name=\"CXBH\" type=\"String\">"
                            + bdcXxcxjl.getCxbh() + "</data></datas>");
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + xml;
            bdcXxcxjg.setXxnr(xml);
        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_BDCXXGKCX)
                && (bdcXxcxjl.getCzlb().equals(LogCzxxConstants.CZTYPE_CJZM_NAME) || bdcXxcxjl.getCzlb().equals(LogCzxxConstants.CZTYPE_CKDJBXX_NAME))) {
            String xml = ((Map) data).get("xml").toString();
            if (xml.contains("<page>")) {
                xml = xml.replace("<page>", "<fetchdatas>");
                xml = xml.replace("</page>", "</fetchdatas>");
            }
            xml = xml.replace(
                    "</datas>",
                    "<data name=\"CXBH\" type=\"String\">"
                            + bdcXxcxjl.getCxbh() + "</data></datas>");
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + xml;
            bdcXxcxjg.setXxnr(xml);
        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_QSZMDQD)
                && bdcXxcxjl.getCzlb().equals(LogCzxxConstants.CZTYPE_CJZM_NAME)) {
            String xml = ((Map) data).get("xml").toString();
            if (xml.contains("<page>")) {
                xml = xml.replace("<page>", "<fetchdatas>");
                xml = xml.replace("</page>", "</fetchdatas>");
            }
            xml = xml.replace(
                    "</datas>",
                    "<data name=\"CXBH\" type=\"String\">"
                            + bdcXxcxjl.getCxbh() + "</data></datas>");
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" + xml;
            bdcXxcxjg.setXxnr(xml);
        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_CXJCX_FWQS)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_CXJCX_YFCX)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_CXJCX_WFCX)) {
            String json = ((Map) data).get("json").toString();
            if (json.contains("@YWBH@")) {
                json = json.replace("@YWBH@", bdcXxcxjl.getCxbh());
            }
            bdcXxcxjg.setXxnr(json);
        } else if (bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_SJDDY)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_SPBDY)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_SQSDY)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_FZJLDY)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_ZMSDY)
                || bdcXxcxjl.getCxlb().equals(LogCzxxConstants.CXLB_ZSDY)) {
            String xml = ((Map) data).get("xml") != null ? ((Map) data).get("xml").toString() : "";
            if (StringUtils.isNotBlank(xml)) {
                bdcXxcxjg.setXxnr(xml);
            } else {
                bdcXxcxjg.setXxnr(data == null ? "" :JSONObject.toJSONString(data));
            }
        } else if (StringUtils.equals(name, LogCzxxConstants.CXLB_XXGK_GJFJGZM) || StringUtils.equals(name, LogCzxxConstants.CXLB_XXGK_GJFWJGZM)) {
            String xml = ((Map) data).get("xml") != null ? ((Map) data).get("xml").toString() : "";
            xml = xml.replace("@CXBH@", bdcXxcxjl.getCxbh());
            bdcXxcxjg.setXxnr(xml);
        } else {
            bdcXxcxjg.setXxnr(data == null ? "" : JSONObject.toJSONString(data));
        }
        if (LogCzxxConstants.CXLB_BDCXXGKCX.equals(bdcXxcxjl.getCxlb()) && StringUtils.isNotBlank(user)) {
            bdcXxcxjl.setCxlb("住建查询");
        }
        if (StringUtils.isBlank(jgid)) {
            if (StringUtils.equals("true", isCxLc)) {
                bdcXxcxjg.setJgid(bdcXxcxjg.getJlid());
            } else {
                bdcXxcxjg.setJgid(new SimpleDateFormat("yyyyMMddHHmmssSSS")
                        .format(new Date()));
            }
            sjptEntityMapper.saveOrUpdate(bdcXxcxjl, bdcXxcxjl.getJlid());
            sjptEntityMapper.saveOrUpdate(bdcXxcxjg, bdcXxcxjg.getJgid());
        } else if (StringUtils.isNotBlank(jgid)) {
            bdcXxcxjg.setJgid(jgid);
            String jgbz = null;
            BdcXxcxjg bdcxxjg = sjptEntityMapper.selectByPrimaryKey(BdcXxcxjg.class,jgid);
            jgbz = bdcxxjg.getJgbz() != null ? bdcxxjg.getJgbz() : "";
            if (StringUtils.isNotBlank(jgbz)) {
                bdcXxcxjg.setJgbz(jgbz);
            }
            BdcXxcxjl bdcXxjl = sjptEntityMapper.selectByPrimaryKey(BdcXxcxjl.class,jlid);
            if (null == bdcXxjl) {
                sjptEntityMapper.saveOrUpdate(bdcXxcxjl, bdcXxcxjl.getJlid());;
                sjptEntityMapper.saveOrUpdate(bdcXxcxjg, jgid);
            } else {
                sjptEntityMapper.saveOrUpdate(bdcXxcxjg, jgid);
            }
        }
        if (!cztype.equals(LogCzxxConstants.CZTYPE_CJZM_NAME)) {
            cxbhTemp = bdcXxcxjg.getJgid();
            LOGGER.info(cxbhTemp);
        }
        resultMap.put("cxbh", bdcXxcxjl.getCxbh());
        resultMap.put("jlid", bdcXxcxjl.getJlid());
        resultMap.put("jgid", bdcXxcxjg.getJgid());
        cztype = "";
        return resultMap;
    }

    /**
     * @param xzqh
     * @param map
     * @return java.lang.String
     * @author <a href ="mailto:liangqing@gtmap.cn"></a>
     * @version 1.3
     * @date 13:35 2017/11/20
     * @description 获取有房编号
     */
    private String getYfBh(String xzqh, HashMap map) {
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
     * @param xzqh
     * @param map
     * @return java.lang.String
     * @author <a href ="mailto:liangqing@gtmap.cn"></a>
     * @version 1.3
     * @date 13:35 2017/11/20
     * @description 获取无房编号
     */
    private String getWfBh(String xzqh, HashMap map) {
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
     * @param xzqh
     * @param map
     * @return java.lang.String
     * @author <a href ="mailto:chenchunxue@gtmap.cn">chenchunxue</a>
     * @version 1.3
     * @date 13:35 2020/05/09
     * @description 获取房屋权属编号 qwm和ws可以使用zfcx的后面有变动可以在新加配置项
     */
    private String getFwqsBh(String xzqh, HashMap map) {
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
    //同步锁生成编号
    public synchronized String getCxhTempForOtherQuery(Map<String, Object> map, String xzqhTemp, String name) {
        String cxbhTemp;

        List<Map<String, Object>> lsbhList = gkcxMapper.getBdcXxcxbh(map);
        if ("0".equals(lsbhList.get(0).get("cxbh"))) {
            if (StringUtils.equals(name, LogCzxxConstants.CXLB_QSZMDQD)) {
                cxbhTemp = new SimpleDateFormat("yyyyMM").format(new Date()) + xzqhTemp
                        + "000000";
            } else {
                cxbhTemp = new SimpleDateFormat("yyyyMM").format(new Date()) + xzqhTemp
                        + "000001";
            }
        } else {
            String oldls = (String) lsbhList.get(0).get("cxbh");
            String ols = oldls.substring(oldls.length() - 6, oldls.length());
            String lsh = String.format("%06d", Integer.parseInt(ols) + 1);
            cxbhTemp = new SimpleDateFormat("yyyyMM").format(new Date()) + xzqhTemp + lsh;
        }
        return cxbhTemp;
    }

    private Object dealArguments(Method method, JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        // 获取参数名称 性能很低
        String[] paraNameArr = new LocalVariableTableParameterNameDiscoverer()
                .getParameterNames(method);
        List<Object> arguments = Lists.newArrayList();
        Object arg;
        for (int i = 0; i < args.length; i++) {
            Map<String, String> item = Maps.newHashMap();
            if (args[i] instanceof Model || args[i] instanceof Pageable
                    || args[i] instanceof RedirectAttributes
                    || args[i] instanceof HttpServletResponse
                    || args[i] instanceof HttpServletRequest) {
                continue;
            } else {
                arg = args[i];
                item.put("parameterType", arg.getClass().getSimpleName());
                item.put("parameterValue", JSONObject.toJSONString(arg));
                item.put("parameterName", paraNameArr[i]);
                arguments.add(item);
            }
        }

        return arguments;
    }

    /**
     * 获取自定注解方法
     *
     * @param
     * @return
     * @author wuhongrui
     * @description
     */
    private String getAuditContent(Method method, ProceedingJoinPoint pjp,
                                   AnalysisAuditLog publicLog) {
        Map contentMap = Maps.newHashMap();
        String description;
        if (StringUtils.isNotBlank(publicLog.description())) {
            description = publicLog.description();
        } else {
            description = "操作内容";
        }
        contentMap.put(description, dealArguments(method, pjp));
        return JSON.toJSONString(contentMap);
    }


    /**
     * @param
     * @return
     * @author wuhongrui
     * @description 返回注解的方法对象
     */
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
        } catch (NoSuchMethodException e) {
            LOGGER.error("msg", e);
        } catch (SecurityException e) {
            LOGGER.error("msg", e);
        }
        return method;
    }

}
