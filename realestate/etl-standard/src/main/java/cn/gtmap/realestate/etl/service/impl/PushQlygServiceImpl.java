package cn.gtmap.realestate.etl.service.impl;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.yzw.InfApplyDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.Example;
import cn.gtmap.realestate.etl.core.domian.huaian.BnInfSpare;
import cn.gtmap.realestate.etl.service.PushQlygDataService;
import cn.gtmap.realestate.etl.service.PushQlygService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PushQlygServiceImpl implements PushQlygService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PushQlygDataService pushQlygDataService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired(required = false)
    @Qualifier("sjptEntityMapper")
    private EntityMapper entityMapper;

    @Override
    public void pushQlygData(Map queryMap) {
        logger.info("开始推送一张网数据！！！");
        try {
            String gzlslid = MapUtils.getString(queryMap, "gzlslid");
            if (StringUtils.isNotBlank(gzlslid)) {
                List<InfApplyDO> infApplies = new ArrayList<InfApplyDO>();
                BdcXmQO bdcXmQO=new BdcXmQO();
                bdcXmQO.setGzlslid(gzlslid);
                List<BdcXmDO> list=bdcXmFeignService.listBdcXm(bdcXmQO);
                Map<String,BnInfSpare> map =new HashMap<>();
                if(CollectionUtils.isNotEmpty(list) ){
                    //时间
                    Date updateDate=new Date();
                    for(BdcXmDO bdcXm:list){
                        if(StringUtils.isNotBlank(bdcXm.getZfxzspbh())){
                            Example example = new Example(InfApplyDO.class);
                            Example.Criteria criteria = example.createCriteria();
                            criteria.andEqualTo("internalNo", bdcXm.getZfxzspbh());
                            List<InfApplyDO> infApplyList = entityMapper.selectByExampleNotNull(example);
                            if (CollectionUtils.isNotEmpty(infApplyList)) {
                                infApplies.addAll(infApplyList);
                                BnInfSpare bnInfSpare=new BnInfSpare();
                                bnInfSpare.setSlbh(bdcXm.getSlbh());
                                bnInfSpare.setUpDate(updateDate);
                                bnInfSpare.setGzlslid(bdcXm.getGzlslid());
                                bnInfSpare.setXmid(bdcXm.getXmid());
                                if(StringUtils.isNotBlank(bdcXm.getBdcqzh())){
                                    bnInfSpare.setBdcqzh(bdcXm.getBdcqzh());
                                    //字段先判断赋值，不做查询
                                    if(bdcXm.getBdcqzh().indexOf("证明") > -1){
                                        bnInfSpare.setZslx(2);
                                    }else{
                                        bnInfSpare.setZslx(1);
                                    }
                                }
                                bnInfSpare.setJhptUpdateTime(new Date());
                                map.put(bdcXm.getZfxzspbh(),bnInfSpare);
                            }
                        }
                    }
                }
                logger.info("infApplies：" + infApplies.size());
                if (CollectionUtils.isNotEmpty(infApplies)) {
                    //更新保存INF_APPLY基本信息表
                    pushQlygDataService.pushInfApply(infApplies,map);
                }
            }
        } catch (Exception e) {
            logger.error("msg",e);
        }
        logger.info("推送结束！！！");
    }
}
