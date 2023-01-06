package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.core.service.BdcSlHsxxService;
import cn.gtmap.realestate.accept.core.service.BdcSlSfssDdxxService;
import cn.gtmap.realestate.accept.service.BdcYczfService;
import cn.gtmap.realestate.accept.service.PosService;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlHsxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.dto.accept.PosInputDTO;
import cn.gtmap.realestate.common.core.dto.accept.PosOutputDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfssDdxxQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0, 2021/12/23
 * @description
 */
@Service
public class PosServiceImpl implements PosService {

    private final Logger logger = LoggerFactory.getLogger(PosServiceImpl.class);

    @Autowired
    BdcXmFeignService bdcXmFeignService;

    @Autowired
    BdcSlHsxxService bdcSlHsxxService;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    BdcYczfService bdcYczfService;

    @Autowired
    BdcSlSfssDdxxService bdcSlSfssDdxxService;

    @Override
    public Object getXfjyxx(String gzlslid, String qlrlb) {
        Map<String, Object> resultMap = Maps.newHashMap();
        PosInputDTO input = new PosInputDTO();
        input.setTransType("01"); // 交易指令： 01 消费交易
        input.setTransAmount(getZje(gzlslid, qlrlb));
        input.setMerOrderID(getZfddh(gzlslid, qlrlb));
        try {
            resultMap.put("input", getObjectToStr(input, "|"));
        } catch (IllegalAccessException e) {
            logger.error("getXfjyxx:", e);
        }
        return resultMap;
    }

    @Override
    public Object drcxjy(String gzlslid, String qlrlb) {
        Map<String, Object> resultMap = Maps.newHashMap();
        PosInputDTO input = new PosInputDTO();
        input.setTransType("02");// 交易指令： 02 当日撤销交易
        input.setTransAmount(getZje(gzlslid, qlrlb));
        input.setOldPostrace(getZfddh(gzlslid, qlrlb));
        try {
            resultMap.put("input", getObjectToStr(input, "|"));
        } catch (IllegalAccessException e) {
            logger.error("getXfjyxx:", e);
        }
        return resultMap;
    }

    /**
     * @param gzlslid
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 15:37
     * @description 退货交易
     **/
    @Override
    public Object thjy(String gzlslid, String qlrlb) {
        Map<String, Object> resultMap = Maps.newHashMap();
        PosInputDTO input = new PosInputDTO();
        input.setTransType("03");// 交易指令： 03 退货交易
        input.setTransAmount(getZje(gzlslid, qlrlb));
        // 原交易检索号(OldHostTrace) 12 位。
        input.setOldHostTrace(getZfddh(gzlslid, qlrlb));
        input.setOldTransDate(DateUtil.format(new Date(), "MMdd"));
        try {
            resultMap.put("input", getObjectToStr(input, "|"));
        } catch (IllegalAccessException e) {
            logger.error("getXfjyxx:", e);
        }
        return resultMap;
    }

    @Override
    public void saveJyxx(String jyxx, String gzlslid, String qlrlb) {
        if (StringUtils.isNotBlank(jyxx)) {
            logger.info("jyxx:" + jyxx);
            jyxx = jyxx + "|" + gzlslid;
            String[] split = jyxx.split("\\|");
            int i = 0;
            PosOutputDTO output = new PosOutputDTO();
            for (Field field : PosOutputDTO.class.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    field.set(output, split[i]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                i++;
            }
            logger.info("output:" + JSON.toJSONString(output));
            if (StringUtils.isNotBlank(output.getPosTraceNum())) {
                List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
                if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
                    for (BdcSlHsxxDO bdcSlHsxxDO : bdcSlHsxxDOList) {
                        bdcSlHsxxDO.setSphm(output.getPosTraceNum());
                    }
                    entityMapper.batchSaveSelective(bdcSlHsxxDOList);
                }
                output.setGzlslid(gzlslid);

                bdcYczfService.posZfcgtz(gzlslid, qlrlb);
            }
        }
    }

    @Override
    public Object rePrintJy(String gzlslid, String qlrlb) {
        Map<String, Object> resultMap = Maps.newHashMap();
        List<BdcSlHsxxDO> bdcSlHsxxDOList = bdcSlHsxxService.listBdcSlHsxxByGzlslidAndSqrlb(gzlslid, qlrlb);
        if (CollectionUtils.isNotEmpty(bdcSlHsxxDOList)) {
            PosInputDTO input = new PosInputDTO();
            input.setTransType("61"); // 交易指令： 61 重打印交易
            input.setOldPostrace(bdcSlHsxxDOList.get(0).getSphm());
            try {
                resultMap.put("input", getObjectToStr(input, "|"));
            } catch (IllegalAccessException e) {
                logger.error("rePrintJy:", e);
            }
        }
        return resultMap;
    }

    @Override
    public Object gkjhZfJy(String gzlslid, String qlrlb, String fkm) {
        Map<String, Object> resultMap = Maps.newHashMap();
        PosInputDTO input = new PosInputDTO();
        input.setTransType("92");// 交易指令： 92 广开聚合支付被扫交易
        input.setTransAmount(getZje(gzlslid, qlrlb));
        input.setMerOrderID(getZfddh(gzlslid, qlrlb));
        input.setLongQRPay(fkm);
        input.setPlatID("");
        try {
            resultMap.put("input", getObjectToStr(input, "|"));
        } catch (IllegalAccessException e) {
            logger.error("gkjhZfJy:", e);
        }
        return resultMap;
    }

    /**
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.Object
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/22 15:43
     * @description 广开聚合退货交易
     **/
    @Override
    public Object gkjhthjy(String gzlslid, String qlrlb) {
        Map<String, Object> resultMap = Maps.newHashMap();
        PosInputDTO input = new PosInputDTO();
        input.setTransType("94");// 交易指令： 94 广开聚合支付退货交易
        input.setTransAmount(getZje(gzlslid, qlrlb));
        input.setMerOrderID(getZfddh(gzlslid, qlrlb));
        try {
            resultMap.put("input", getObjectToStr(input, "|"));
        } catch (IllegalAccessException e) {
            logger.error("gkjhZfBd:", e);
        }
        return resultMap;
    }

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/31 10:29
     * @description 获取金额
     **/
    private String getZje(String gzlslid, String qlrlb) {
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            BigDecimal zje = new BigDecimal("0.00");
            for (BdcSlSfssDdxxDO bdcSlSfssDdxxDO : bdcSlSfssDdxxDOList) {
                BigDecimal hj = BigDecimal.valueOf(Optional.ofNullable(bdcSlSfssDdxxDO.getZe()).orElse(0.00));
                zje = zje.add(hj);
            }
            return toTransAmount(zje);
        }
        return null;
    }

    /**
     * @param
     * @param gzlslid
     * @param qlrlb
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/31 10:34
     * @description 获取支付单单号
     **/
    private String getZfddh(String gzlslid, String qlrlb) {
        BdcSlSfssDdxxQO bdcSlSfssDdxxQO = new BdcSlSfssDdxxQO();
        bdcSlSfssDdxxQO.setGzlslid(gzlslid);
        bdcSlSfssDdxxQO.setQlrlb(qlrlb);
        List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList = bdcSlSfssDdxxService.listBdcSlSfssDdxx(bdcSlSfssDdxxQO);
        if (CollectionUtils.isNotEmpty(bdcSlSfssDdxxDOList)) {
            return bdcSlSfssDdxxDOList.get(0).getDdbh();
        }
        return null;
    }

    /**
     * @param
     * @param zje
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/31 10:34
     * @description 转pos传输金额
     **/
    private String toTransAmount(BigDecimal zje) {
        String transAmount = zje.multiply(BigDecimal.valueOf(100)).toBigInteger().toString();
        if (StringUtils.isNotBlank(transAmount) && transAmount.length() < 12) {
            transAmount = StringUtils.substring("000000000000", 0, 12 - transAmount.length()) + transAmount;
        } else {
            transAmount = "000000000000";
        }
        return transAmount;
    }

    /**
     * @param
     * @param obj
     * @param split 分隔符
     * @return java.lang.String
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @date 2022/8/16 11:31
     * @description 对象转str
     **/
    private String getObjectToStr(Object obj, String split) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = obj.getClass();
        int i = 0;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            if (i == 0) {
                sb.append(value);
            } else {
                sb.append(split).append(value);
            }
            i++;
        }
        return sb.toString();
    }
}
