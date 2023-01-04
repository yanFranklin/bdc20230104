package cn.gtmap.realestate.exchange.service.impl.inf.yancheng.ql;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.exchange.sjpt.BdcExchangeZddz;
import cn.gtmap.realestate.exchange.core.domain.BdcExchangeDefaultValueDO;
import cn.gtmap.realestate.exchange.core.dto.yancheng.yth.YthYwxxDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 盐城一体化-发证数据同步初始化数据
 */
public interface YthInitParamService {

    /**
     * 获取相应的实现类
     * @return
     */
    String getHandleServiceName();

    /**
     *初始化盐城一体化-发证数据同步请求参数
     *
     * @param ythYwxxDTO
     * @param bdcXmDO
     */
    void initYthQlRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO);

    /**
     * 初始化发证所需数据
     * @param ythYwxxDTO
     * @param bdcXmDO
     */
    void initIssueCertificateCommonRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO);

    /**
     * 初始化字典表对照项以及默认值
     * @param ythYwxxDTO
     * @param bdcXmDO
     * @param bdcExchangeZddzMap
     * @param defaultValueDOList
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    void initYthParamZddzAndDefaultValue(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO, Map<String, List<BdcExchangeZddz>> bdcExchangeZddzMap, List<BdcExchangeDefaultValueDO> defaultValueDOList) throws InvocationTargetException, IllegalAccessException;

    /**
     * 初始化盐城一体化审核信息
     * <p>通过项目ID 查询 BDC_SHXX 表获取审核信息</p>
     * @param ythYwxxDTO 一体化请求参数
     * @param bdcXmDO  项目信息
     */
    void initYthShxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO);

    /**
     * 初始化盐城一体化受理申请信息
     * <p>通过项目ID 查询 BDC_XM 表获取受理申请信息</p>
     * @param ythYwxxDTO 一体化请求参数
     * @param bdcXmDO  项目信息
     */
    void initYthSlxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [ythYwxxDTO, bdcXmDO]
     * @return void
     * @description 通知一体化退件时，查询受理申请信息（不关联BDC_SHXX,避免只认领而不受理，BDC_SHXX没有数据导致查不到受理申请信息）
     */
    void initYthSlxxForTjRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO);

    /**
     * 初始化盐城一体化收费信息
     * <p>通过项目ID 查询 BDCSL_SFXX 视图获取受理申请信息</p>
     * @param ythYwxxDTO
     * @param bdcXmDO
     */
    void initYthSfxxRequestParam(YthYwxxDTO ythYwxxDTO, BdcXmDO bdcXmDO,Boolean isFilterSfxx);

    Map<String,Object> initSqlParam(BdcXmDO bdcXmDO);

}
