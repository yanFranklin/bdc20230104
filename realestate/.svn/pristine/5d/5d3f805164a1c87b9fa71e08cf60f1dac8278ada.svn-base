package cn.gtmap.realestate.init.service.tshz.yancheng;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.util.CheckWwsqOrYcslUtils;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.init.core.dto.InitServiceDTO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;
import cn.gtmap.realestate.init.core.service.BdcQlrService;
import cn.gtmap.realestate.init.core.service.BdcXmService;
import cn.gtmap.realestate.init.service.tshz.InitBdcTsHzService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 抵押业务中，在创建项目的时候，将义务人同步到债务人
 *
 * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
 * @version 上午10:30 2021/3/1
 */
@Service("bdcDyaqZwrServiceImpl")
public class InitBdcDyaqZwrServiceImpl implements InitBdcTsHzService {
    private static Logger logger = LoggerFactory.getLogger(InitBdcDyaqZwrServiceImpl.class);

    /**
     * 需要带入上一手债务人的流程
     */
    @Value("#{'${zwr.drsys.gzldyids:}'.split(',')}")
    private List<String> gzldyids;

    @Autowired
    BdcXmService bdcXmService;

    @Autowired
    BdcQlrService bdcQlrService;

    /**
     * 特殊后置处理
     *
     * @param initServiceQO
     * @param initServiceDTO *@return InitServiceDTO
     * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     * @description
     */
    @Override
    public InitServiceDTO tshz(InitServiceQO initServiceQO, InitServiceDTO initServiceDTO) throws Exception {
        logger.info("initServiceQO数据：{}", JSON.toJSONString(initServiceQO));
        logger.info("initServiceDTO数据：{}", JSON.toJSONString(initServiceDTO));

        // 如果存在 dsqlr 则读取 dsqlr 数据不再执行抵押逻辑
        if (CollectionUtils.isNotEmpty(initServiceQO.getBdcDsQlrDOList()) && CollectionUtils.isEmpty(initServiceDTO.getBdcDsQlrDOList())) {
            initServiceDTO.setBdcDsQlrDOList(initServiceQO.getBdcDsQlrDOList());
            for(BdcDsQlrDO bdcDsQlrDO : initServiceDTO.getBdcDsQlrDOList()) {
                bdcDsQlrDO.setXmid(initServiceDTO.getBdcXm().getXmid());
            }
            logger.info("赋值initServiceDTO第三权利人后数据：{}", JSON.toJSONString(initServiceDTO));
            return initServiceDTO;
        }

        if(CollectionUtils.isNotEmpty(initServiceDTO.getBdcDsQlrDOList())) {
            return initServiceDTO;
        }

        // 带入上一手债务人
        if(containsProcess(initServiceDTO)){
            List<BdcDsQlrDO> bdcDsQlrDOList = this.getBeforeZwr(initServiceDTO);
            if(CollectionUtils.isNotEmpty(bdcDsQlrDOList)){
                BdcXmDO bdcXmDO = initServiceDTO.getBdcXm();
                if(Objects.nonNull(bdcXmDO)){
                    for(BdcDsQlrDO bdcDsQlrDO:bdcDsQlrDOList){
                        bdcDsQlrDO.setXmid(bdcXmDO.getXmid());
                        bdcDsQlrDO.setQlrid(UUIDGenerator.generate16());
                    }
                    initServiceDTO.setBdcDsQlrDOList(bdcDsQlrDOList);
                    return initServiceDTO;
                }
            }
        }

        // 抵押业务处理
        if (CommonConstantUtils.QLLX_DYAQ_DM.equals(initServiceDTO.getBdcXm().getQllx())) {
            return dealZwr(initServiceDTO);
        }
        // 预告业务同步处理
        BdcQl bdcQl = initServiceDTO.getBdcQl();
        if (bdcQl instanceof BdcYgDO) {
            BdcYgDO bdcYgDO = (BdcYgDO) bdcQl;
            if (bdcYgDO.getYgdjzl() != null && ArrayUtils.contains(CommonConstantUtils.YG_YGDJZL_YDY_ARR, bdcYgDO.getYgdjzl())) {
                return dealZwr(initServiceDTO);
            }
        }
        return initServiceDTO;
    }

    /**
     * 将ywr 处理到 dsqlr 中 qlrlb 为 zwr
     *
     * @param initServiceDTO initServiceDTO
     * @return cn.gtmap.realestate.init.core.dto.InitServiceDTO
     * @author <a href="mailto:lixin1@gtmap.com">lixin</a>
     */
    private InitServiceDTO dealZwr(InitServiceDTO initServiceDTO) {
        BdcXmDO bdcXmDO = initServiceDTO.getBdcXm();
        if(Objects.nonNull(bdcXmDO)&& !CheckWwsqOrYcslUtils.checkIsWwsq(bdcXmDO.getSply())
                && !CheckWwsqOrYcslUtils.checkIsYhxt(bdcXmDO.getSply())){
            List<BdcDsQlrDO> bdcDsQlrDOS = Lists.newArrayList();
            for (BdcQlrDO bdcQlrDO : initServiceDTO.getBdcQlrList()) {
                if (bdcQlrDO != null && StringUtils.equals(bdcQlrDO.getQlrlb(), CommonConstantUtils.QLRLB_YWR)) {
                    BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                    BeanUtils.copyProperties(bdcQlrDO, bdcDsQlrDO);
                    bdcDsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                    bdcDsQlrDOS.add(bdcDsQlrDO);
                }
            }
            initServiceDTO.setBdcDsQlrDOList(bdcDsQlrDOS);
        }
        return initServiceDTO;
    }


    /**
     * 获取上一手债务人信息
     */
    private List<BdcDsQlrDO> getBeforeZwr(InitServiceDTO initServiceDTO){
        List<BdcDsQlrDO> bdcDsQlrDOList = Lists.newArrayList();
        List<BdcXmLsgxDO> bdcXmLsgxList =  initServiceDTO.getBdcXmLsgxList();
        if(CollectionUtils.isNotEmpty(bdcXmLsgxList)){
            for(BdcXmLsgxDO bdcXmLsgxDO : bdcXmLsgxList){
                if(StringUtils.isNotBlank(bdcXmLsgxDO.getXmid())){
                    BdcXmDO yxm = bdcXmService.queryBdcXmByPrimaryKey(bdcXmLsgxDO.getYxmid());
                    if(Objects.nonNull(yxm)){
                        BdcDsQlrDO bdcDsQlrDO = new BdcDsQlrDO();
                        bdcDsQlrDO.setQlrlb(CommonConstantUtils.DSQLR_QLRLB_ZWR);
                        bdcDsQlrDO.setXmid(yxm.getXmid());
                        List<BdcDsQlrDO> yxmDsqlrList = this.bdcQlrService.queryBdcDsQlr(bdcDsQlrDO, null);
                        if(CollectionUtils.isNotEmpty(yxmDsqlrList)){
                            bdcDsQlrDOList.addAll(yxmDsqlrList);
                        }
                    }
                }
            }
        }

        return bdcDsQlrDOList;
    }

    /**
     * 判断当前流程是否需要获取上一手的债务人信息
     */
    private boolean containsProcess(InitServiceDTO initServiceDTO) {
        return CollectionUtils.isNotEmpty(gzldyids)
                && null != initServiceDTO.getBdcXm()
                && gzldyids.contains(initServiceDTO.getBdcXm().getGzldyid());
    }
}
