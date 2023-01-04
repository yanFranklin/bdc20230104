package cn.gtmap.realestate.exchange.service.impl.inf.sjpt;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.spring.EnvironmentConfig;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz;
import cn.gtmap.realestate.exchange.service.inf.sjpt.GxRzService;
import cn.gtmap.realestate.exchange.util.CommonUtil;
import cn.gtmap.realestate.exchange.util.constants.SjptConstants;
import cn.gtmap.realestate.exchange.util.enums.SjptRzEnum;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-23
 * @description 省级平台记录日志
 */
@Service
public class GxRzServiceImpl implements GxRzService {

    @Autowired
    private EntityMapper sjptEntityMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GxRzServiceImpl.class);


    /**
     * @return cn.gtmap.realestate.exchange.core.domain.sjpt.GxPeRz
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 初始化一个日志实体
     */
    @Override
    public GxPeRz initRz() {
        Environment envi = EnvironmentConfig.getEnvironment();
        GxPeRz gxPeRz = new GxPeRz();
        gxPeRz.setRzid(UUIDGenerator.generate());
        gxPeRz.setJlsj(new Date());
        gxPeRz.setXzqdm(envi.getProperty("sjpt.xzqdm"));
        gxPeRz.setCxjgbs(envi.getProperty("sjpt.cxjgbs"));
        gxPeRz.setYhm(envi.getProperty("sjpt.username"));
        gxPeRz.setMm(envi.getProperty("sjpt.password"));
        gxPeRz.setZt(SjptConstants.RZ_ZT_FAIL);
        return gxPeRz;
    }

    /**
     * @param gxPeRz
     * @param response
     * @param rzEnum
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 根据相应分析日志
     */
    @Override
    public void anaysisRzByResponse(GxPeRz gxPeRz, Object response, SjptRzEnum rzEnum,Object requestObject) {
        if(response != null){
            String responseJsonString = response instanceof String ? (String)response : JSONObject.toJSONString(response);
            JSONObject responseJson = null;
            try{
                responseJson = JSONObject.parseObject(responseJsonString);
            }catch (Exception e){
                try{
                    JSONArray responseArr = JSONArray.parseArray(responseJsonString);
                    if(CollectionUtils.isNotEmpty(responseArr)){
                        responseJson = responseArr.getJSONObject(0);
                    }
                }catch (Exception e1){
                    LOGGER.error("省级共享处理日志异常:{}",responseJsonString);
                }
            }
            if(responseJson != null && responseJson.getJSONObject("head") != null){
                JSONObject headJson = responseJson.getJSONObject("head");
                // 如果是成功标志
                if(StringUtils.equals(headJson.getString("code"),
                        rzEnum.getSuccessCode())){
                    gxPeRz.setZt(SjptConstants.RZ_ZT_SUCCESS);
                    gxPeRz.setSuccessMsg(StringUtils.substring(JSONObject.toJSONString(response), 0, 2000));

                    // 获取 请求参数 HQQQS
                    Object qqsList = null;
                    if(requestObject != null && StringUtils.isNotBlank(rzEnum.getQqsListNameInRequest())){
                        JSONObject requestTemp = JSONObject.parseObject(JSONObject.toJSONString(requestObject));
                        qqsList = CommonUtil.getKeyFromJsonObject(requestTemp,rzEnum.getQqsListNameInRequest());
                    }
                    if(StringUtils.isNotBlank(rzEnum.getQqsListNameInResponse())){
                        qqsList = CommonUtil.getKeyFromJsonObject(responseJson,rzEnum.getQqsListNameInResponse());
                    }
                    if(qqsList == null){
                        gxPeRz.setHqqqs(0);
                    } else if(qqsList instanceof Collection){
                        gxPeRz.setHqqqs(((Collection)qqsList).size());
                    }
                } else {
                    // 接口返回异常
                    gxPeRz.setZt(SjptConstants.RZ_ZT_FAIL);
                    String errorMsgKey = StringUtils.isNotBlank(rzEnum.getErrorMsgKey())?rzEnum.getErrorMsgKey():"msg";
                    gxPeRz.setErrorMsg(headJson.getString(errorMsgKey));
                }
            }else if(StringUtils.isNotBlank(responseJsonString) && rzEnum.equals(SjptRzEnum.SJPT_TOKEN_REQ)){
                gxPeRz.setZt(SjptConstants.RZ_ZT_SUCCESS);
                gxPeRz.setSuccessMsg(responseJsonString);
            }
        }
    }

    /**
     * @param gxPeRz
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存日志
     */
    @Override
    public void saveRz(GxPeRz gxPeRz) {
        try{
            if(gxPeRz != null && CheckParameter.checkAnyParameter(gxPeRz)){
                sjptEntityMapper.insertSelective(gxPeRz);
            }
        }catch (Exception e){
            LOGGER.error("---saveRz异常,异常信息:{},异常堆栈:{}",e.getMessage(),e);
        }
    }

    /**
     * @param gxPeRz
     * @param e
     * @return void
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 保存失败日志
     */
    @Override
    public void saveFailedRz(GxPeRz gxPeRz, Throwable e) {
        if(StringUtils.isBlank(gxPeRz.getRzid())){
            gxPeRz.setRzid(UUIDGenerator.generate16());
        }
        if(gxPeRz.getJlsj() == null){
            gxPeRz.setJlsj(new Date());
        }
        if(StringUtils.isBlank(gxPeRz.getXzqdm())){
            gxPeRz.setXzqdm(EnvironmentConfig.getEnvironment().getProperty("sjpt.xzqdm"));
        }
        gxPeRz.setZt(SjptConstants.RZ_ZT_FAIL);
        gxPeRz.setErrorMsg(CommonUtil.getStackTrace(e));
        sjptEntityMapper.insertSelective(gxPeRz);
    }

}
