package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.impl;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXtJgDO;
import cn.gtmap.realestate.common.core.domain.exchange.DjfDjSfDO;
import cn.gtmap.realestate.common.core.domain.exchange.QlfQlDyaqDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.service.feign.init.BdcQlrFeignService;
import cn.gtmap.realestate.common.core.service.feign.init.BdcXtJgFeignService;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.DjfDjSfOldDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old.QlfQlDyaqOldDO;
import cn.gtmap.realestate.exchange.core.mapper.server.DjfDjSfMapper;
import cn.gtmap.realestate.exchange.core.mapper.server.QlfQlDyaqMapper;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.AbstractYthInitParamService;
import cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql.YthInitParamService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class YthDyaInitParamServiceImpl extends AbstractYthInitParamService implements YthInitParamService {

    private static final Logger logger = LoggerFactory.getLogger(YthDyaInitParamServiceImpl.class);

    public static final String DYA_YTH_QL_INIT_PARAM_SERVICE_KEY = "ythDyaQlInitParamServiceImpl";

    @Autowired
    private QlfQlDyaqMapper qlfQlDyaqMapper;

    @Autowired
    private DjfDjSfMapper djfDjSfMapper;

    @Autowired
    private BdcXtJgFeignService bdcXtJgFeignService;

    @Autowired
    private BdcQlrFeignService bdcQlrFeignService;

    /**
     * ????????????????????????
     *
     * @return
     */
    @Override
    public String getHandleServiceName() {
        return DYA_YTH_QL_INIT_PARAM_SERVICE_KEY;
    }

    /**
     * ????????????????????????-??????????????????????????????
     *
     * @param bdcXmDO
     */
    @Override
    public void initYthQlRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO) {
        Map<String, Object> sqlParam = super.initSqlParam(bdcXmDO);
        //????????????
        List<QlfQlDyaqOldDO> qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqListOld(sqlParam);
        if (CollectionUtils.isEmpty(qlfQlDyaqList)) {
            sqlParam.clear();
            sqlParam.put("yywh", bdcXmDO.getXmid());
            qlfQlDyaqList = qlfQlDyaqMapper.queryQlfQlDyaqListOld(sqlParam);
        }
        this.handleScdjDyaqxx(qlfQlDyaqList, bdcXmDO);
        if (CollectionUtils.isNotEmpty(qlfQlDyaqList)) {
            if (ythYwxxDTO.getQlfQlDyaqDO() != null) {
                ythYwxxDTO.getQlfQlDyaqDO().addAll(qlfQlDyaqList);
            } else {
                ythYwxxDTO.setQlfQlDyaqDO(qlfQlDyaqList);
            }
        }
    }

    /**
     * ?????????????????? ???????????????????????? 0
     */
    private void handleScdjDyaqxx(List<QlfQlDyaqOldDO> qlfQlDyaqList, BdcXmDO bdcXmDO) {
        if (CollectionUtils.isNotEmpty(qlfQlDyaqList)) {
            for (QlfQlDyaqOldDO dyaqDO : qlfQlDyaqList) {
                if (CommonConstantUtils.DJLX_SCDJ_DM.equals(bdcXmDO.getDjlx())) {
                    dyaqDO.setScywh("0");
                }
            }
        }
    }

    /**
     * ???????????????????????????????????? isFilterSfxx true ????????????????????????bdc_xt_jg??????????????????????????????????????????
     **/
    @Override
    public void initYthSfxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO, Boolean isFilterSfxx) {
        logger.info("??????????????????????????????:{},isFilterSfxx:{}",bdcXmDO,isFilterSfxx);
        AtomicBoolean sfxxFlag = new AtomicBoolean(false);
        if (isFilterSfxx) {
            BdcQlrQO bdcQlrQO = new BdcQlrQO();
            bdcQlrQO.setXmid(bdcXmDO.getXmid());
            bdcQlrQO.setQlrlb(CommonConstantUtils.QLRLB_QLR);
            List<BdcQlrDO> bdcQlrDOList = bdcQlrFeignService.listBdcQlr(bdcQlrQO);
            logger.info("???????????????????????????:{}", JSON.toJSONString(bdcQlrDOList));
            if (CollectionUtils.isNotEmpty(bdcQlrDOList)) {
                for (BdcQlrDO bdcQlrDO : bdcQlrDOList) {
                    sfxxFlag.set(querySfayjsByQlr(bdcQlrDO.getQlrmc()));
                    if (sfxxFlag.get()) {
                        break;
                    }
                }
            }
        }
        if (!sfxxFlag.get()) {
            List<DjfDjSfOldDO> djfDjSfDOList = this.djfDjSfMapper.queryDjfDjSfListOld(initSqlParam(bdcXmDO));
            if (CollectionUtils.isNotEmpty(djfDjSfDOList)) {
                if (Objects.nonNull(ythYwxxDTO.getDjfDjSfDO())) {
                    ythYwxxDTO.getDjfDjSfDO().addAll(djfDjSfDOList);
                } else {
                    ythYwxxDTO.setDjfDjSfDO(djfDjSfDOList);
                }
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    private boolean querySfayjsByQlr(String qlr) {
        if (StringUtils.isNotBlank(qlr)) {
            List<String> yjyhmcList = this.bdcXtJgFeignService.listYjyhmc();
            if(CollectionUtils.isEmpty(yjyhmcList)){
                return false;
            }

            if(yjyhmcList.contains(qlr)){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

}
