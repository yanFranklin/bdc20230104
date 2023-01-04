package cn.gtmap.realestate.etl.core.service;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.etl.core.domian.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-26
 * @description 共享登记数据给大数据局使用 共享数据方法
 */
public interface ShareDataService {

    /**
     * 更新或保存
     *
     * @param yttBdcqzsDos
     */
    void saveOrUpdateYttBdcqzs(List<YttBdcqzsDo> yttBdcqzsDos);

    /**
     * 更新或保存
     *
     * @param gtBdcqzmDos
     */
    void saveOrUpdateGtBdcqzmDos(List<GtBdcqzmDo> gtBdcqzmDos);

    /**
     * 更新或保存
     *
     * @param gtCqxxDos
     */
    void saveOrUpdateGtCqxxDos(List<GtCqxxDo> gtCqxxDos);

    /**
     * 更新或保存
     *
     * @param gtDyaqFwDos
     */
    void saveOrUpdateGtDyaqFwDos(List<GtDyaqFwDo> gtDyaqFwDos);

    /**
     * 更新或保存
     *
     * @param gtDyaqTdDos
     */
    void saveOrUpdateGtDyaqTdDos(List<GtDyaqTdDo> gtDyaqTdDos);

    /**
     * 更新或保存
     *
     * @param gtCfFwDos
     */
    void saveOrUpdateGtCfFwDos(List<GtCfFwDo> gtCfFwDos);

    /**
     * 更新或保存
     *
     * @param gtCfTdDos
     */
    void saveOrUpdateGtCfTdDos(List<GtCfTdDo> gtCfTdDos);

}
