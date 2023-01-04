package cn.gtmap.realestate.accept.service.impl;

import cn.gtmap.realestate.accept.service.BdcSlFgfService;
import cn.gtmap.realestate.common.core.domain.BdcFgfDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.common.core.service.feign.exchange.ExchangeInterfaceFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXmFeignService;
import cn.gtmap.realestate.common.core.service.feign.register.BdcFgfFeignService;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class BdcSlFgfServiceImpl implements BdcSlFgfService {

    private final Logger logger = LoggerFactory.getLogger(BdcSlFgfServiceImpl.class);

    @Autowired
    BdcFgfFeignService bdcFgfFeignService;
    @Autowired
    private BdcXmFeignService bdcXmFeignService;
    @Autowired
    ExchangeInterfaceFeignService exchangeInterfaceFeignService;

    public static final int SUCCESS_CODE = 200;

    @Value("${tsszfgb.djyy:}")
    private String djyy;

    @Override
    public void tsszfgb(String gzlslid) {
        // 如果已经推送过就不再推送
        List<BdcFgfDO> bdcFgfDOList = bdcFgfFeignService.getLcFgfxm(gzlslid);
        if(CollectionUtils.isNotEmpty(bdcFgfDOList)){
            logger.info("根据工作流实例id查询房改房实体：{}", bdcFgfDOList);
            return;
        }

        BdcXmQO bdcXmQO = new BdcXmQO();
        bdcXmQO.setGzlslid(gzlslid);
        List<BdcXmDO> bdcXmDOList = bdcXmFeignService.listBdcXm(bdcXmQO);

        // 获取登记原因为 “已购共有主房买卖（省直）” 并且 登记小类 为 2000402的项目数据进行推送
        if(CollectionUtils.isEmpty(bdcXmDOList)){
            return;
        }
        Map<String, String> param = new HashMap<>(2);
        for(BdcXmDO bdcXmDO : bdcXmDOList){
            String bdcXmDjyy = bdcXmDO.getDjyy();
            if(StringUtils.isNotBlank(bdcXmDjyy)  && djyy.indexOf(bdcXmDjyy) > -1){
                param.put("xmid", bdcXmDO.getXmid());
                param.put("slbh", bdcXmDO.getSlbh());
                break;
            }
        }
        // 没有满足条件的流程 则不再继续走下面的逻辑
        if(!param.containsKey("xmid")){
            return;
        }
        // 推送前判断是否已近推送过了
        logger.info("房改房调用exchange接口参数：{}", param);
        Object response = exchangeInterfaceFeignService.requestInterface("fgf_tsywxx", param);
        logger.info("房改房调用exchange接口返回值：{}",response);

        if(Objects.nonNull(response)){
            Map<String,Integer> mapResult =  (Map<String,Integer>)response;
            if(mapResult.containsKey("yxcode") &&SUCCESS_CODE == mapResult.get("yxcode")){
                //推送成功，调用插入接口
                List<BdcFgfDO> listBdcFgfDO = new ArrayList<>(1);
                BdcFgfDO bdcFgfDO = new BdcFgfDO();
                bdcFgfDO.setId(UUIDGenerator.generate16());
                bdcFgfDO.setXmid(param.get("xmid"));
                bdcFgfDO.setSlbh(param.get("slbh"));
                listBdcFgfDO.add(bdcFgfDO);
                bdcFgfFeignService.saveFgfxm(listBdcFgfDO);

            }
        }
    }

}
