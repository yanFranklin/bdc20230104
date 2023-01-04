package cn.gtmap.realestate.exchange.service.impl.inf;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcZfxxDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcZfxxQO;
import cn.gtmap.realestate.common.core.service.feign.inquiry.BdcZfxxCxFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.dto.ExchangeDsfCommonResponse;
import cn.gtmap.realestate.exchange.core.dto.zfxxcx.BdcZsResponseDTO;
import cn.gtmap.realestate.exchange.core.mapper.server.BdcdjMapper;
import cn.gtmap.realestate.exchange.service.inf.BdcZfxxcxService;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/5/13
 * @description 住房信息查询相关服务
 */
@Service(value = "bdcZfxxcxServiceImpl")
public class BdcZfxxcxServiceImpl implements BdcZfxxcxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcZfxxcxServiceImpl.class);

    /**
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 是否调用公积金上链
      */
    @Value("${gjjsjsl.enable:true}")
    private Boolean gjjsjsl;

    /**
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 住房信息查询服务
     */
    @Autowired
    BdcZfxxCxFeignService bdcZfxxCxFeignService;

    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    private BdcdjMapper bdcdjMapper;

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据姓名和证件号查询有房无房
     */
    @Override
    public ExchangeDsfCommonResponse yfwfcx(JSONObject jsonObject) {
        if (jsonObject == null) {
            return ExchangeDsfCommonResponse.fail("未找到查询参数");
        }
        BdcZfxxQO bdcZfxxQO = JSONObject.parseObject(JSONObject.toJSONString(jsonObject), BdcZfxxQO.class);
        //参数验证
        //验证是否传参
        if (bdcZfxxQO == null || CollectionUtils.isEmpty(bdcZfxxQO.getQlrxx())) {
            return ExchangeDsfCommonResponse.fail("未找到查询参数");
        }
        //验证权利人名称或证件号是否存在为空
        for (BdcQlrQO qlr : bdcZfxxQO.getQlrxx()) {
            if (null == qlr || (StringUtils.isAnyBlank(qlr.getQlrmc(), qlr.getZjh()))) {
                return ExchangeDsfCommonResponse.fail("存在权利人名称或证件号为空情况");
            }
        }
        ExchangeDsfCommonResponse result;
        try {
            JSONObject responseData = new JSONObject();
            List<BdcZfxxDTO> zfxxList = bdcZfxxCxFeignService.listBdcNtZfxxDTO(bdcZfxxQO);
            if (CollectionUtils.isNotEmpty(zfxxList)) {
                responseData.put("sfyf", CommonConstantUtils.SF_S_DM);
            } else {
                responseData.put("sfyf", CommonConstantUtils.SF_F_DM);
            }
            result = ExchangeDsfCommonResponse.ok(responseData);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
        //数据上链
        if (Boolean.TRUE.equals(gjjsjsl)) {
            try {
                sjsl(result,"gryfwfcx");
            }catch (Exception e){
                return ExchangeDsfCommonResponse.fail("数据上链" + e.getMessage());
            }

        }
        return result;


    }

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 根据姓名和证件号查询证书信息
      */
    public ExchangeDsfCommonResponse bdccqzxxcx(Map<String,Object> paramMap){
        if (paramMap == null) {
            return ExchangeDsfCommonResponse.fail("未找到查询参数");
        }
        if(paramMap.get("qlrmc") ==null ||paramMap.get("zjh") ==null){
            return ExchangeDsfCommonResponse.fail("姓名或证件号不能为空");
        }
        ExchangeDsfCommonResponse result;
        //证件号转换
        try {
            String revertZjh = CardNumberTransformation.zjhTransformation(paramMap.get("zjh").toString());
            paramMap.put("zjhList", Arrays.asList(revertZjh.split(CommonConstantUtils.ZF_YW_DH)));
            paramMap.put("zjh", null);
            List<BdcZsResponseDTO> bdcZsList = bdcdjMapper.listBdcZsxx(paramMap);
            //获取证书的qlr信息
            if (CollectionUtils.isNotEmpty(bdcZsList)) {
                for (BdcZsResponseDTO zs : bdcZsList) {
                    if (StringUtils.isNotBlank(zs.getZsid())) {
                        List<BdcQlrDO> bdcQlrDOList = bdcdjMapper.listBdcQlrByZsid(zs.getZsid());
                        if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                            zs.setQlrzjh(StringToolUtils.resolveBeanToAppendStr(bdcQlrDOList, "getZjh", CommonConstantUtils.ZF_YW_DH));
                        }
                    }
                }
            }
            result = ExchangeDsfCommonResponse.ok(bdcZsList);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ExchangeDsfCommonResponse.fail(e.getMessage());
        }
        //数据上链
        if (Boolean.TRUE.equals(gjjsjsl)) {
            try {
                sjsl(result,"bdccqzxxcx");
            }catch (Exception e){
                return ExchangeDsfCommonResponse.fail("数据上链" + e.getMessage());
            }

        }
        return result;

    }

    public void sjsl(Object result,String ywdm) {
        //ywbh 和时间戳是数据上链回执查询的必要条件
        String ywbh = DateUtil.formatDate("yyyyMMddHHmmss") + "0001";
//        String dataTimestamp=String.valueOf(System.currentTimeMillis() / 1000);
        Date newDate = new Date();
        String dataTimestamp = DateUtils.formateDateToString(newDate, DateUtils.DATE_FORMAT);
        JSONObject object = new JSONObject();
        object.put("data", result);
        object.put("ywbh", ywbh);
        object.put("ywdm", ywdm);
        object.put("dataTimestamp", dataTimestamp);
        Object response = exchangeBeanRequestService.request("gjjsjsl", object);
        LOGGER.info("数据上链:{},结果：{}", object, response);
        //回执查询
        JSONObject hzcxObject = new JSONObject();
        //ywbh 和时间戳是数据上链回执查询的必要条件
        hzcxObject.put("ywbh", ywbh);
        hzcxObject.put("dataTimestamp", dataTimestamp);
        Object hzcxResponse = exchangeBeanRequestService.request("gjjslhzcx", hzcxObject);
        LOGGER.info("数据上链回执查询:{},结果：{}", object, hzcxResponse);

    }
}
