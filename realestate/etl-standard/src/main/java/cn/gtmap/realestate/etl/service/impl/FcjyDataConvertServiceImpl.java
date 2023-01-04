package cn.gtmap.realestate.etl.service.impl;

import cn.gtmap.realestate.common.core.domain.etl.HtbaQlrDO;
import cn.gtmap.realestate.common.core.domain.etl.HtbaSpfDO;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.ListUtils;
import cn.gtmap.realestate.etl.core.mapper.exchange.HtbaSpfMapper;
import cn.gtmap.realestate.etl.core.mapper.fcjy.FcjyHtxxMapper;
import cn.gtmap.realestate.etl.service.FcjyDataConvertService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/5/12
 * @description
 */
@Service
public class FcjyDataConvertServiceImpl implements FcjyDataConvertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FcjyDataConvertServiceImpl.class);
    /**
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @description 每次同步合同信息的数量
     */
    @Value("${fcjy.htba.tbsl:500}")
    private Integer tbsl;

    @Autowired(required = false)
    private HtbaSpfMapper htbaSpfMapper;

    @Autowired(required = false)
    @Qualifier("bdcEntityMapper")
    private EntityMapper bdcEntityMapper;

    @Autowired(required = false)
    private FcjyHtxxMapper fcjyHtxxMapper;

    /**
     * @description 转换房产交易合同备案信息到不动产登记
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 9:43
     * @return void
     */
    @Override
    @Transactional(rollbackFor = Exception.class, value = "bdc")
    public void convertFcjyHtbaxxAndImoprtBdcDj() {

        String tempBaid = htbaSpfMapper.queryMaxBaid();
        if (StringUtils.isBlank(tempBaid) || !StringUtils.isNumeric(tempBaid)) {
            throw new AppException("未查询到htba_spf最大baid或最大baid不是数值");
        }
        int maxBaid = Integer.parseInt(tempBaid);
        if (maxBaid < 0) {
            throw new AppException("查询的htba_spf中最大baid小于0，请检查数据");
        }
        Map paramMap = new HashMap();
        paramMap.put("startId", maxBaid + 1);
        paramMap.put("tbsl", tbsl);
        List<HtbaSpfDO> htbaSpfDOList = fcjyHtxxMapper.listFcjySpfHtbaxx(paramMap);
        if (CollectionUtils.isNotEmpty(htbaSpfDOList)) {
            String startId = htbaSpfDOList.get(0).getBaid();
            String endId = htbaSpfDOList.get(htbaSpfDOList.size() - 1).getBaid();
            LOGGER.info("本次合同信息共计{}条数据，开始beiandata_id={}，结束beiandata_id={}，", htbaSpfDOList.size(), startId, endId);
            // 查询合同编号是否在htba_spf表中已存在，如果存在历史htbh，需要将历史htbh状态改为已注销
            zxLsHtbh(htbaSpfDOList);
            // 查询合同信息对应权利人信息
            saveHtbaQlrxx(htbaSpfDOList);
            // 分批保存合同信息
            batchSave(htbaSpfDOList);
        }
    }

    /**
     * @description 注销htba_spf中已存在历史htbh的数据
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 17:31
     * @param htbaSpfDOList
     * @return void
     */
    private void zxLsHtbh(List<HtbaSpfDO> htbaSpfDOList) {
        List<String> htbhList = htbaSpfDOList.stream().map(htbaSpfDO -> htbaSpfDO.getHtbh()).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(htbhList)) {
            List<List> htbhLists = ListUtils.subList(htbhList, 500);
            for (List htbhSubList : htbhLists) {
                Map htbhParamMap = new HashMap();
                htbhParamMap.put("htbhList", htbhSubList);
                List<HtbaSpfDO> htbaWzxSpfDOList = htbaSpfMapper.listWzxHtbaSpfxx(htbhParamMap);
                if (CollectionUtils.isNotEmpty(htbaWzxSpfDOList)) {
                    LOGGER.info("合同状态修改为注销的合同编号列表：{}", htbaWzxSpfDOList.stream().map(htbaSpfDO -> htbaSpfDO.getHtbh()).collect(Collectors.toList()));
                    htbaWzxSpfDOList.stream().forEach(htbaSpfDO -> htbaSpfDO.setBazt(CommonConstantUtils.SF_F_DM));
                    // 批量更新合同备案状态
                    batchSave(htbaWzxSpfDOList);
                }
            }
        }
    }

    /**
     * @description 保存合同信息对应的权利人信息
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 17:47
     * @param htbaSpfDOList
     * @return void
     */
    private void saveHtbaQlrxx(List<HtbaSpfDO> htbaSpfDOList) {
        List<Integer> baidList = htbaSpfDOList.stream().map(htbaSpfDO -> Integer.parseInt(htbaSpfDO.getBaid())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(baidList)) {
            List<List> baidLists = ListUtils.subList(baidList, 500);
            for (List baidSubList : baidLists) {
                Map baidParamMap = new HashMap();
                baidParamMap.put("baidList", baidSubList);
                List<HtbaQlrDO> htbaQlrDOList = fcjyHtxxMapper.listFcjySpfHtbaQlrxx(baidParamMap);
                // 分批保存权利人信息
                batchSave(htbaQlrDOList);
            }
        }
    }

    /**
     * @description 分批保存
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/10/28 17:22
     * @param record
     * @return void
     */
    private <T> void batchSave(List<T> record) {
        if(CollectionUtils.isNotEmpty(record)) {
            List<List> records = ListUtils.subList(record, 500);
            for (List subRecord : records) {
                bdcEntityMapper.batchSaveSelective(subRecord);
            }
        }
    }
}
