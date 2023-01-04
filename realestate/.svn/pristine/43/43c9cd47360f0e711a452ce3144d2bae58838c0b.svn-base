package cn.gtmap.realestate.register.service.impl;

import cn.gtmap.gtc.workflow.clients.manage.ProcessTaskClient;
import cn.gtmap.gtc.workflow.domain.manage.TaskData;
import cn.gtmap.realestate.common.core.domain.BdcPjdxDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.register.core.mapper.BdcPjdxMapper;
import cn.gtmap.realestate.register.service.BdcPjdxService;
import cn.gtmap.realestate.register.util.HttpPost;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sviolet.smcrypto.SmCryptoUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author <a href="mailto:qianguoyi@gtmap.cn">qianguoyi</a>
 * @version 1.0
 * @date 2021/6/25 14:16
 * @description 评价短信下发
 */
@Service
public class BdcPjdxServiceImpl implements BdcPjdxService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BdcPjdxServiceImpl.class);

    @Autowired
    private BdcPjdxMapper bdcPjdxMapper;

    @Autowired
    ProcessTaskClient taskClient;

    @Value("${yancheng.pjdx.appword:}")
    private String appword;

    @Value("${yancheng.pjdx.appmark:}")
    private String appmark;

    @Value("${yancheng.pjdx.url:}")
    private String pjdxUrl;

    @Value("${yancheng.pjdx.Address:盐城市不动产登记中心}")
    private String pjdxAddress;

    @Value("${yancheng.pjdx.publickeysm2:}")
    private String publickeysm2;

    @Value("${yancheng.pjdx.taskType:}")
    private String taskType;


    @Override
    public void queryPjdxMsg(String processInsId,String taskId){
        if(StringUtils.isEmpty(processInsId)){
            // 错误返回
            throw new AppException("评价短信查询参数缺失");
        }
        LOGGER.info("评价短信==》工作流实例ID:{},任务ID:{}",processInsId,taskId);
        List<BdcPjdxDO> bdcPjdxDOList = bdcPjdxMapper.queryPjdxMsg(processInsId);
        if(CollectionUtils.isEmpty(bdcPjdxDOList)){
            throw new AppException("无法查询到该数据");
        }
        LOGGER.info("查询到的数据:{}",JSONObject.toJSONString(bdcPjdxDOList));
        String response;
        try{
            response = HttpPost.doPost(pjdxUrl,dataEncryption(bdcPjdxDOList.get(0),taskId));
            LOGGER.info("调用接口的返回:{}",response);
        }catch(Exception e){
            LOGGER.error("接口调用异常:",e);
            throw new AppException("短信接口调用失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(response);
        if("true".equals(jsonObject.get("success").toString())){
            LOGGER.info("盐城短信接口调用成功");
        }else{
            throw new AppException("短信下发失败");
        }
    }

    private Part[] dataEncryption(BdcPjdxDO bdcPjdxDO,String taskId) throws ParseException{
        // 未加密打印Map
        Map<String,Object> logMap = new HashMap<>(8);
        SimpleDateFormat stringTime = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat utcDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ukDateTime = new
                SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy",Locale.UK);
        Date date = new Date();
        LOGGER.info("配置文件参数为appmark{},appword{},publickeysm2{},pjdxAddress{}",appmark,appword,publickeysm2,pjdxAddress);
        logMap.put("appMark",appmark);
        logMap.put("time",stringTime.format(date));
        // md5加密  应用标识、应用密钥、当前时间按顺序连接并进行md5加密
        String sign = SmCryptoUtil.sm2Encode(stringTime.format(date) + appmark + appword,publickeysm2);
        JSONObject jsonObject = new JSONObject();
        logMap.put("sign",stringTime.format(date) + appmark + appword);
        // 下面进行SM2加密
        jsonObject.put("mobile",bdcPjdxDO.getLzrdh());

        // "受理编号"
        jsonObject.put("projectNo",bdcPjdxDO.getSlbh());
        jsonObject.put("taskName",bdcPjdxDO.getGzldymc());

        // 调用接口获取参数
        LOGGER.info("任务ID:{}:",taskId);
        TaskData taskDataList = taskClient.getTaskById(taskId);
        if(StringUtils.isEmpty(taskDataList)){
            throw new AppException("未获取到节点信息");
        }
        if(taskDataList.getTaskName().contains("办结")){
            // 办结节点外转发时都取2，办结节点转发时取3
            jsonObject.put("proStatus","3");
        }else{
            jsonObject.put("proStatus","2");
        }
        jsonObject.put("acceptDate",utcDateTime.format(ukDateTime.parse(taskDataList.getStartTime().toString())));
        jsonObject.put("proDepart",pjdxAddress);
        jsonObject.put("taskType",taskType);
        String params = SmCryptoUtil.sm2Encode(jsonObject.toString(),publickeysm2);
        LOGGER.info("评价短信==》未加密参数内容:{}",JSONObject.toJSONString(logMap));
        Part[] parts = {new StringPart("appMark",appmark),new StringPart("time",stringTime.format(date)),new StringPart("sign",sign),new StringPart("params",params)};
        LOGGER.info("评价短信==》加密后的参数内容:{}",JSONObject.toJSONString(parts));
        return parts;
    }
}
