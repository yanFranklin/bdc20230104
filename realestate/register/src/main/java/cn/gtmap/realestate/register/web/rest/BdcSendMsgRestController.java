package cn.gtmap.realestate.register.web.rest;

import cn.gtmap.realestate.common.core.domain.BdcMsgConfig;
import cn.gtmap.realestate.common.core.domain.BdcMsgLog;
import cn.gtmap.realestate.common.core.domain.BdcMsgMain;
import cn.gtmap.realestate.common.core.dto.register.BdcSendMsgDTO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.service.feign.init.BdcRunSqlFeignService;
import cn.gtmap.realestate.common.core.service.rest.register.BdcSendMsgRestService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.MessageMatcher;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.register.web.main.BaseController;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
 * @version
 * @description 发送短信服务
 */
@RestController
@Api(tags = "发送短信")
public class BdcSendMsgRestController extends BaseController implements BdcSendMsgRestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcSendMsgRestController.class);

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Autowired
    private MessageMatcher messageMatcher;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private BdcRunSqlFeignService bdcRunSqlFeignService;

    /**
     * 发送短信
     */
    @Override
    public void sendMsg(@RequestBody BdcSendMsgDTO bdcSendMsgDTO) {

        if(bdcSendMsgDTO == null){
            throw new AppException("缺失发送短信的参数");
        }

        // 获取电话号码
        List<String> dhList = new ArrayList<>();
        if(StringUtils.isNotBlank(bdcSendMsgDTO.getDh())){
            dhList = Arrays.asList(bdcSendMsgDTO.getDh().split(","));
        }else{
            throw new AppException("缺失发送短信的电话的配置");
        }

        Map resultMap = new HashMap();
        // msgType需要对应上运维部署中心的短信模板配置
        if(StringUtils.isBlank(bdcSendMsgDTO.getMsgtype())){
            throw new AppException("缺失msgType配置");
        }
        resultMap.put("msgType",bdcSendMsgDTO.getMsgtype());

        // 模板参数
        if(MapUtils.isNotEmpty(bdcSendMsgDTO.getTemplateparam())){
            Map map = bdcSendMsgDTO.getTemplateparam();
            for(Object key : map.keySet()){
                String value = map.get(key) == null?"":map.get(key).toString();
                // 因为要存日志 阿里云短信平台规定参数长度不能超过20
                if(StringUtils.isNotBlank(value) && value.length() < 20){
                    resultMap.put(key,map.get(key));
                }
            }
        }else{
            throw new AppException("缺失模板参数配置");
        }

        for(String phone : dhList){
            resultMap.put("dh",phone);
            if (checkPhone(phone)) {
                LOGGER.info("开始调取大云短信发送接口，此时参数合集：{}", JSONObject.toJSONString(resultMap));
                smsMsg(resultMap, phone, resultMap.get("msgType").toString());
                saveMsgLog(JSON.toJSONString(resultMap), "200", phone, "0", "发送成功");
            } else {
                saveMsgLog(JSON.toJSONString(resultMap), "500", phone, "1", "电话号格式不正确");
                throw new AppException(phone + "电话号格式不正确");
            }
        }
    }

    /**
     * @description 交接单发送短信服务
     * @param map
     * @param mainid
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/24 14:21
     */
    @Override
    public void sendSmsMsg(@RequestBody Map map, @RequestParam(name = "mainid") String mainid) {
        BdcMsgMain main = entityMapper.selectByPrimaryKey(BdcMsgMain.class, mainid);
        // 当存在对一个信息时 组织对应的短信信息
        if (main == null) {
            LOGGER.info("无法找到对应id的短信模板，模板id：" + mainid);
            return;
        }
        List<String> phoneList = new ArrayList<>();
        // 1.获取MsgType（大云需要）
        String msgType = getMsgType(main);

        //2.获取数据
        if (StringUtils.isNotBlank(main.getParamWhere()) && StringUtils.isNotBlank(main.getParamSql())) {
            String parmWhereSql = "";
            if (StringUtil.isNotBlank(MapUtils.getString(map, main.getParamWhere()))) {
                parmWhereSql = main.getParamSql().replace("@{" + main.getParamWhere() + "}", "'" + map.get(main.getParamWhere()) + "'");
            }
            LOGGER.info("发送短信功能sql执行：" + parmWhereSql);
            //执行sql获取参数
            List<HashMap> msgInfoList = bdcRunSqlFeignService.runSql(parmWhereSql);
            if (CollectionUtils.isNotEmpty(msgInfoList)) {
                LOGGER.info("短信发送sql查询结果：{}",msgInfoList);
                for (HashMap msgInfoMap : msgInfoList) {
                    String phone = MapUtils.getString(msgInfoMap, "dh");
                    // 3.判断参数 如果都全，发送，不全不发送
                    if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(msgType) && msgInfoMap != null) {
                        if (checkPhone(phone)) {
                            if (!phoneList.contains(phone)) {
                                smsMsg(msgInfoMap, phone, msgType);
                                saveMsgLog(JSON.toJSONString(msgInfoMap), "200", phone, "0", "发送成功");
                                phoneList.add(phone);
                            }
                        } else {
                            saveMsgLog(JSON.toJSONString(msgInfoMap), "500", phone, "1", "电话号格式不正确");
                        }
                    } else {
                        saveMsgLog(JSON.toJSONString(msgInfoMap), "500", phone, "1", "参数缺失");
                    }
                }
            }
        }
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
     * @description 获取MsgType（大云需要）
     * @param main
     * @author <a href="mailto:huanghui@gtmap.cn">huanghui</a>
     * @date 2022/11/24 14:11
     */
    private String getMsgType(BdcMsgMain main) {
        if (main != null && org.apache.commons.lang3.StringUtils.isNotBlank(main.getConfigid())) {
            BdcMsgConfig msgConfig = entityMapper.selectByPrimaryKey(BdcMsgConfig.class, main.getConfigid());
            if (msgConfig != null && org.apache.commons.lang3.StringUtils.isNotBlank(msgConfig.getMsgtype())) {
                return msgConfig.getMsgtype();
            }
        }
        return null;
    }

}
