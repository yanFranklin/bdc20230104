package cn.gtmap.realestate.engine.core.service.impl;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZgzDTO;
import cn.gtmap.realestate.common.core.dto.engine.BdcGzZhgzDTO;
import cn.gtmap.realestate.common.core.qo.engine.BdcGzYzQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.StringToolUtils;
import cn.gtmap.realestate.engine.core.mapper.BdcXmMapper;
import cn.gtmap.realestate.engine.core.service.BdcGzYwService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 规则针对业务场景处理逻辑
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 */
@Service
public class BdcGzYwServiceImpl implements BdcGzYwService {
    private static final Logger logger = LoggerFactory.getLogger(BdcGzYwServiceImpl.class);

    @Autowired
    private BdcXmMapper bdcXmMapper;

    /**
     * 规则验证是否根据区划排除掉不需要验证的子规则
     */
    @Value("${engine.check.isfilterarea:false}")
    private Boolean isFilterArea;


    /**
     * 排除不需要验证的子规则
     *
     * 子规则必须满足两个条件才能不验证
     * 1、开启了根据区划排除子规则功能
     * 2、子规则排除验证的区划和当前要验证的项目区划一致，通过bdc_xmfb的qjgldm
     *
     * @param bdcGzYzQO    验证参数
     * @param bdcGzZhgzDTO 组合规则信息
     */
    @Override
    public void filterNotNeedCheckZgz(BdcGzYzQO bdcGzYzQO, BdcGzZhgzDTO bdcGzZhgzDTO) {
        if(Boolean.FALSE.equals(isFilterArea)) {
            // 默认无需排除
            return;
        }

        // 获取当前项目的区划
        String qjgldm = this.getQjgldm(bdcGzYzQO, bdcGzZhgzDTO);
        if (StringUtils.isBlank(qjgldm)){
            return;
        }

        // 排除掉无需验证的子规则
        List<BdcGzZgzDTO> needCheckGzList = new ArrayList<>();
        StringBuilder notNeedCheckGzStr = new StringBuilder();

        for(BdcGzZgzDTO bdcGzZgzDTO : bdcGzZhgzDTO.getBdcGzZgzDTOList()) {
            if(StringUtils.isNotBlank(bdcGzZgzDTO.getPcqh())) {
                String[] pcqhArray = bdcGzZgzDTO.getPcqh().split(CommonConstantUtils.ZF_YW_DH);
                if(Arrays.asList(pcqhArray).contains(qjgldm)) {
                    notNeedCheckGzStr.append(bdcGzZgzDTO.getGzmc()).append(CommonConstantUtils.ZF_YW_DH);
                } else {
                    needCheckGzList.add(bdcGzZgzDTO);
                }
            } else {
                needCheckGzList.add(bdcGzZgzDTO);
            }
        }

        logger.info("组合规则{}针对区划{}不验证以下子规则：{}", bdcGzZhgzDTO.getZhbs(), qjgldm, notNeedCheckGzStr);
        bdcGzZhgzDTO.setBdcGzZgzDTOList(needCheckGzList);
    }

    /**
     *  获取当前项目的区划（权籍管理代码）
     *  1、如果调用端传了参数qjgldm，那么直接采用这个参数
     *  2、如果调用端没有传qjgldm，那么通过传入的xmid、slbh、gzlslid、bdcdyh几个参数判断当前项目的qjgldm
     *
     * @param bdcGzYzQO    验证参数
     * @param bdcGzZhgzDTO 组合规则信息
     * @return 权籍管理代码
     */
    private String getQjgldm(BdcGzYzQO bdcGzYzQO, BdcGzZhgzDTO bdcGzZhgzDTO) {
        String qjgldm = "";

        // 1、获取验证参数中直接传值的权籍管理代码
        if(MapUtils.isNotEmpty(bdcGzYzQO.getParamMap())) {
            qjgldm = (String) bdcGzYzQO.getParamMap().get("qjgldm");
            if(StringUtils.isNotBlank(qjgldm)) {
                return qjgldm;
            }
        }

        if(CollectionUtils.isNotEmpty(bdcGzYzQO.getParamList())) {
            for(Map<String, Object> paramMap : bdcGzYzQO.getParamList()) {
                if(MapUtils.isEmpty(paramMap)) {
                    continue;
                }
                qjgldm = (String) paramMap.get("qjgldm");
                if(StringUtils.isNotBlank(qjgldm)) {
                    return qjgldm;
                }
            }
        }

        // 2、未直接传值权籍管理代码qjgldm则查询项目记录
        // 2.1、获取项目相关查询参数
        BdcXmQO bdcXmQO = new BdcXmQO();
        setXmParam(bdcXmQO, bdcGzYzQO.getParamMap());

        if(CollectionUtils.isNotEmpty(bdcGzYzQO.getBdcGzYzsjDTOList())) {
            for(BdcGzYzsjDTO yzsj : bdcGzYzQO.getBdcGzYzsjDTOList()) {
                setXmParam(bdcXmQO, JSON.parseObject(JSON.toJSONString(yzsj), Map.class));
            }
        }

        if(CollectionUtils.isNotEmpty(bdcGzYzQO.getParamList())) {
            for(Map<String, Object> paramMap : bdcGzYzQO.getParamList()) {
                setXmParam(bdcXmQO, paramMap);
            }
        }

        if(StringToolUtils.isAllNullOrEmpty(bdcXmQO.getXmid(), bdcXmQO.getSlbh(), bdcXmQO.getGzlslid(), bdcXmQO.getBdcdyh())) {
            logger.info("组合规则{}排除无需验证子规则处理中止：未传值qjgldm或项目相关信息字段", bdcGzZhgzDTO.getZhbs());
            return null;
        }

        // 2.2、查询项目权籍管理代码
        qjgldm = bdcXmMapper.queryXmQjgldm(bdcXmQO);
        if(StringUtils.isBlank(qjgldm)) {
            logger.info("组合规则{}排除无需验证子规则处理中止：待验证项目{}未设置qjgldm，无需排除", bdcGzZhgzDTO.getZhbs(), getXmxx(bdcXmQO));
            return null;
        }
        return qjgldm;
    }

    private void setXmParam(BdcXmQO bdcXmQO, Map<String, Object> map) {
        if(MapUtils.isNotEmpty(map)) {
            if (StringUtils.isBlank(bdcXmQO.getXmid())) {
                bdcXmQO.setXmid((String) map.get("xmid"));
            }
            if (StringUtils.isBlank(bdcXmQO.getSlbh())) {
                bdcXmQO.setSlbh((String) map.get("slbh"));
            }

            if (StringUtils.isBlank(bdcXmQO.getGzlslid())) {
                bdcXmQO.setGzlslid((String) map.get("gzlslid"));
            }

            if (StringUtils.isBlank(bdcXmQO.getBdcdyh())) {
                bdcXmQO.setBdcdyh((String) map.get("bdcdyh"));
            }
        }
    }

    private Object getXmxx(BdcXmQO bdcXmQO) {
        return String.format("xmid:%s，slbh:%s，gzlslid:%s，bdcdyh:%s",
                bdcXmQO.getXmid(), bdcXmQO.getSlbh(), bdcXmQO.getGzlslid(), bdcXmQO.getBdcdyh());
    }
}
