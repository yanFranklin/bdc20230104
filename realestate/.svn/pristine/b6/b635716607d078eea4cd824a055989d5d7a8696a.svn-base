package cn.gtmap.realestate.etl.core.service;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.etl.core.domian.*;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-26
 * @description 共享登记数据给大数据局使用 查询数据方法
 */
public interface QueryBdcdjDataService {


    /**
     * @param xmid
     * @return
     * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
     * @description 共享登记数据给大数据局 查询证书方法
     */
    List<YttBdcqzsDo> queryYttBdcqzs(String xmid);

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
     * @description 共享登记数据给大数据局 查询证书方法
     */
    List<GtBdcqzmDo> queryGtBdcqzms(String xmid);


    /**
     * sly 查询产权信息，GtCqxxDo
     *
     * @param xmid
     * @return
     */
    List<GtCqxxDo> queryGtCqxxs(String xmid);

    /**
     * sly 查询抵押信息，GtDyaqFwDo
     *
     * @param xmid
     * @return
     */
    List<GtDyaqFwDo> queryGtDyaqFws(String xmid);

    /**
     * sly 查询抵押信息，queryGtDyaqTds
     *
     * @param xmid
     * @return
     */
    List<GtDyaqTdDo> queryGtDyaqTds(String xmid);

    /**
     * sly 查询查封信息，GtCfFwDo
     *
     * @param xmid
     * @return
     */
    List<GtCfFwDo> queryGtCfFws(String xmid);

    /**
     * sly 查询查封信息，GtCfFwDo
     *
     * @param xmid
     * @return
     */
    List<GtCfTdDo> queryGtCfTds(String xmid);

    /**
     * 根据工作流实例id查询项目
     *
     * @param gzlslid
     * @return
     */
    List<BdcXmDO> listBdcXmListByGzlslid(String gzlslid);
}
