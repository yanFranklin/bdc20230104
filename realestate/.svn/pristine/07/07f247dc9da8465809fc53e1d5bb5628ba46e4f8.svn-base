package cn.gtmap.realestate.exchange.service.impl.inf.wwsq;

import cn.gtmap.realestate.common.core.domain.BdcMsgConfig;
import cn.gtmap.realestate.common.core.domain.BdcMsgLog;
import cn.gtmap.realestate.common.core.domain.BdcMsgMain;
import cn.gtmap.realestate.common.core.service.feign.init.BdcRunSqlFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.matcher.MessageMatcher;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.service.inf.wwsq.SendMsgService;
import com.alibaba.fastjson.JSON;
import jodd.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2020/3/19
 * @description
 */
@Service
@Import({MessageMatcher.class})
public class SendMsgServiceImpl implements SendMsgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendMsgServiceImpl.class);
    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Autowired
    private MessageMatcher messageMatcher;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private BdcRunSqlFeignService bdcRunSqlFeignService;

    @Override
    public void sendMessage(Map map, String mainid) {
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
            List<HashMap> parmMapList = bdcRunSqlFeignService.runSql(parmWhereSql);
            if (CollectionUtils.isNotEmpty(parmMapList)) {
                parmMapList.forEach(hashMap -> {
                    String phone = MapUtils.getString(hashMap, "dh");
                    //3.判断参数 如果都全，发送，不全不发送
                    if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(msgType) && hashMap != null) {
                        if (checkPhone(phone)) {
                            if (!phoneList.contains(phone)) {
                                smsMsg(hashMap, phone, msgType);
                                saveMsgLog(JSON.toJSONString(hashMap), String.valueOf(map), phone, "0", "发送成功");
                                phoneList.add(phone);
                            }
                        } else {
                            saveMsgLog(JSON.toJSONString(hashMap), String.valueOf(map), phone, "1", "电话号格式不正确");
                        }
                    } else {
                        saveMsgLog(JSON.toJSONString(hashMap), String.valueOf(map), phone, "1", "参数缺失");
                    }
                });
            }
        }
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
    @Override
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
    @Override
    public void saveMsgLog(String parma, String status, String phone, String issuccess, String errorMsg) {
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
    @Override
    public boolean checkPhone(String phone) {
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
}
