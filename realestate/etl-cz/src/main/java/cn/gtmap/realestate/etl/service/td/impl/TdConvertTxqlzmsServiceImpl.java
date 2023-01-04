package cn.gtmap.realestate.etl.service.td.impl;

import cn.gtmap.realestate.common.core.domain.*;
import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.ex.ErrorCode;
import cn.gtmap.realestate.common.core.service.feign.register.BdcZdFeignService;
import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.etl.core.mapper.td.TdMapper;
import cn.gtmap.realestate.etl.core.qo.TdsqQO;
import cn.gtmap.realestate.etl.service.td.TdConvertAbstractService;
import cn.gtmap.realestate.etl.service.td.TdConvertStrategyFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2022-06-15
 * @description 他项权利证明书类型土地数据转换服务
 */
@Service
public class TdConvertTxqlzmsServiceImpl implements TdConvertAbstractService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 不动产单元国标字样
     */
    private final static String BDCDYH_GB = "GB";
    /**
     * 土地不动产单元号后缀
     */
    private final static String TD_BDCDYH_HZ = "W00000000";

    @Qualifier("bdcEntityMapper")
    @Autowired
    EntityMapper bdcEntityMapper;

    @Autowired
    private TdMapper tdMapper;

    @Autowired
    BdcZdFeignService bdcZdFeignService;

    @Autowired
    @Qualifier("bdc")
    private DataSourceTransactionManager transactionManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        TdConvertStrategyFactory.register(95, this);
    }

    @Override
    public void convertTdDataAndImoprtBdcDj(TdsqQO tdsqQO) {
        // 注： 查询土地时，将DJH作为BDCDYH进行返回，代码中对BDCDYH进行处理并重新赋值
        // djh 和 bdcdyh 的对照Map
        Map<String, String> djhAndBdcdyhMap = new HashMap<>(10);

        //手动实现数据库事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);

        try{
            // 1、生成项目信息
            List<BdcXmDO> bdcXmDOList = this.tdMapper.queryTxqlzmsXmxx(tdsqQO);
            if(CollectionUtils.isNotEmpty(bdcXmDOList)){
                for(BdcXmDO bdcXmDO : bdcXmDOList){
                    String bdcdyh = this.generateBdcdyh(bdcXmDO.getBdcdyh());
                    if(StringUtils.isNotBlank(bdcdyh)){
                        djhAndBdcdyhMap.put(bdcXmDO.getBdcdyh(), bdcdyh);
                    }else{
                        throw new AppException(ErrorCode.MISSING_ARG, "生成不动产单元号失败");
                    }
                    bdcXmDO.setQlxz(this.convertZdx("qlxz", bdcXmDO.getQlxz()));
                    bdcXmDO.setZdzhyt(this.convertZdx("tdyt", bdcXmDO.getZdzhyt()));
                    bdcXmDO.setBdcdyh(bdcdyh);
                    bdcXmDO.setSlsj(new Date());
                }
                this.bdcEntityMapper.batchSaveSelective(bdcXmDOList);
            }

            // 2、生成权利人
            List<BdcQlrDO> bdcQlrDOList = this.tdMapper.queryTxqlzmsQlrxx(tdsqQO);
            if(CollectionUtils.isNotEmpty(bdcQlrDOList)){
                this.bdcEntityMapper.batchSaveSelective(bdcQlrDOList);
            }

            // 3、生成义务人
            List<BdcQlrDO> bdcYwrxxList = this.tdMapper.queryTxqlzmsYwrxx(tdsqQO);
            if(CollectionUtils.isNotEmpty(bdcYwrxxList)){
                this.bdcEntityMapper.batchSaveSelective(bdcYwrxxList);
            }

            // 4、生成证书信息

            List<BdcZsDO> bdcZsDOList = this.tdMapper.queryTxqlzmsZsxx(tdsqQO);
            if(CollectionUtils.isNotEmpty(bdcZsDOList)){
                for(BdcZsDO bdcZsDO : bdcZsDOList){
                    // 土地表查询出来的BDCDYH为 DJH, 需要转换后，重新赋值BDCDYH
                    String bdcdyh = djhAndBdcdyhMap.get(bdcZsDO.getBdcdyh());
                    bdcZsDO.setBdcdyh(bdcdyh);
                    this.bdcEntityMapper.saveOrUpdate(bdcZsDO,bdcZsDO.getZsid());


                }
            }
            if(tdsqQO.getTdzt() != null && tdsqQO.getTdzt() == 5){
                logger.info("当前土地项目"+tdsqQO.getXmid()+"重复插入，不需要生成项目证书信息");
            }else {
                // 5、生成项目证书关系
                List<BdcXmZsGxDO> bdcXmZsGxDOList =this.tdMapper.queryTxqlzmsXmZsGxxx(tdsqQO);
                if(CollectionUtils.isNotEmpty(bdcXmZsGxDOList)) {
                    for(BdcXmZsGxDO bdcXmZsGxDO:bdcXmZsGxDOList) {
                        bdcXmZsGxDO.setGxid(UUIDGenerator.generate16());
                        this.bdcEntityMapper.insert(bdcXmZsGxDO);
                    }
                }
            }

            transactionManager.commit(transactionStatus);
        }catch(Exception e){
            transactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    /**
     * 根据地籍号生成bdcdyh
     */
    private String generateBdcdyh(String djh){
        if(StringUtils.isNotBlank(djh) && djh.indexOf(BDCDYH_GB) > -1 && djh.length() == 19 ){
            return djh + TD_BDCDYH_HZ;
        }
        List<String> djhList = this.tdMapper.queryDjh(djh);
        if(CollectionUtils.isNotEmpty(djhList)){
            return djhList.get(0) + TD_BDCDYH_HZ;
        }
        return null;
    }

    /**
     * 字典项转换: MC 转 DM
     */
    private String convertZdx(String zdName, String mc){
        List<Map> zdList = this.bdcZdFeignService.queryBdcZd(zdName);
        if(CollectionUtils.isNotEmpty(zdList) && StringUtils.isNotBlank(mc)){
            for (Map map : zdList) {
                if (mc.equals(MapUtils.getString(map, "MC"))) {
                    return MapUtils.getString(map, "DM");
                }
            }
            return mc;
        }
        return null;
    }

}
