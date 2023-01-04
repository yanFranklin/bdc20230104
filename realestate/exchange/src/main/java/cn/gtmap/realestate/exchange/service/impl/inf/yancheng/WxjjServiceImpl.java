package cn.gtmap.realestate.exchange.service.impl.inf.yancheng;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSlWxzjFeignService;
import cn.gtmap.realestate.common.util.*;
import cn.gtmap.realestate.exchange.core.dto.yancheng.wxjj.WxjjResponseDTO;
import cn.gtmap.realestate.exchange.service.inf.ExchangeBeanRequestService;
import cn.gtmap.realestate.exchange.util.constants.Constants;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.util.*;

import static cn.gtmap.realestate.common.util.encrypt.SM3.byteArrayToHexString;

/**
 * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
 * @version 1.0  2022/11/22
 * @description 盐城维修基金服务
 */
@Service("wxjjServiceImpl")
public class WxjjServiceImpl {

    /**
     * 维修基金密钥key
     */
    @Value("${wxjj.appKey:ycwxjj}")
    private String appKey;

    /**
     * 需要验证维修基金的区县
     */
    @Value("#{'${wxjj.qxdm:320902,320913}'.split(',')}")
    private List<String> wxjjQxdm;

    /**
     * 日志处理
     */
    private static final Logger logger = LoggerFactory.getLogger(WxjjServiceImpl.class);


    @Autowired
    private ExchangeBeanRequestService exchangeBeanRequestService;

    @Autowired
    BdcSlWxzjFeignService bdcSlWxzjFeignService;

    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcdyh
     * @return  维修基金信息
     * @description 调用维修基金接口查询维修基金信息,并保存数据到维修基金表
     */
    @RedissonLock(lockKey = Constants.WXJJ_REDIS_LOCK_NAME, description = "获取维修基金信息", waitTime = 60L, leaseTime = 30L)
    public Object queryWxjj(String bdcdyh) {
        if (StringUtils.isBlank(bdcdyh)) {
            throw new MissingArgumentException("未获取到bdcdyh");
        }
        if (CollectionUtils.isEmpty(wxjjQxdm)) {
            throw new MissingArgumentException("缺少配置wxjj.qxdm(需要验证维修基金的区县)");
        }
        //判断区县是否需要查询维修基金信息
        String qxdm = bdcdyh.substring(0, 6);
        if (! wxjjQxdm.contains(qxdm)) {
            logger.info("当前区县不验证维修基金,bdcdyh为{},需要验证维修基金的区县为{}", bdcdyh, JSON.toJSONString(wxjjQxdm));
            return null;
        }

        Object response =  queryWxjjxx(bdcdyh, "queryWxjjxx");
        WxjjResponseDTO wxjjResponseDTO = JSON.parseObject(JSON.toJSONString(response), WxjjResponseDTO.class);
        logger.info("调用维修基金接口,bdcdyh为{},结果为{}", bdcdyh, JSON.toJSONString(response));

        //保存结果到维修基金表
        if (Objects.nonNull(wxjjResponseDTO) && wxjjResponseDTO.getHourseCount() == 1 && wxjjResponseDTO.getPaymentCount() > 0) {
            BdcSlWxjjxxDO bdcSlWxjjxxDO = new BdcSlWxjjxxDO();
            bdcSlWxjjxxDO.setBdcdyh(bdcdyh);
            List<BdcSlWxjjxxDO> bdcSlWxjjxxList = bdcSlWxzjFeignService.queryBdcSlWxjjxx(bdcSlWxjjxxDO);

            bdcSlWxjjxxDO.setSfsj(wxjjResponseDTO.getSfsj());
            bdcSlWxjjxxDO.setHj(wxjjResponseDTO.getHj());
            bdcSlWxjjxxDO.setWxjjjfr(wxjjResponseDTO.getWxjjjfr());
            bdcSlWxjjxxDO.setSfzt(wxjjResponseDTO.getSfzt());

            if (CollectionUtils.isNotEmpty(bdcSlWxjjxxList)) {
                bdcSlWxjjxxDO.setWxjjxxid(bdcSlWxjjxxList.get(0).getWxjjxxid());
                bdcSlWxzjFeignService.updateBdcSlWxjjxx(bdcSlWxjjxxDO);
            } else {
                bdcSlWxjjxxDO.setWxjjxxid(UUIDGenerator.generate16());
                bdcSlWxzjFeignService.insertBdcSlWxjjxxDO(bdcSlWxjjxxDO);
            }

        }
        return response;
    }


    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcdyh
     * @param beanName
     * @return  维修基金信息
     * @description 调用维修基金接口查询维修基金信息
     */
    public Object queryWxjjxx(String bdcdyh, String beanName) {

        Map paramMap = new HashMap();
        paramMap.put("bdcdyh", bdcdyh);
        String token = this.getToken(bdcdyh);
        paramMap.put("token", token);
        logger.info("开始调用维修基金接口, paramMap参数为{}, beanName为{}", JSON.toJSONString(paramMap), beanName);
        return exchangeBeanRequestService.request(beanName, paramMap);

    }

    /**
     * @author <a href="mailto:zxy@gtmap.cn">zxy</a>
     * @param bdcdyh
     * @return  维修基金接口token
     * @description 根据不动产单元号获取token
     */
    private String getToken(String bdcdyh) {
        if (StringUtils.isNotBlank(bdcdyh) && StringUtils.isNotBlank(appKey)) {
            String code = Base64Utils.encodeByteToBase64Str(StringToolUtils.strToByteUtf8(bdcdyh));
            String code2 = appKey + code;
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md5.digest(code2.getBytes());
                // 将得到的字节数组变成字符串返回
                String result = byteArrayToHexString(results);
                logger.info("生成维修基金接口token,bdcdyh为{},token为{}", bdcdyh, result);
                return result;
            } catch (Exception e) {
                logger.error("未生成维修基金接口token, bdcdyh为{}", bdcdyh);
                e.printStackTrace();
            }
        }
        return null;
    }

}
