package cn.gtmap.realestate.certificate.core.service;


import cn.gtmap.realestate.certificate.core.model.domain.BdcDzzzMlxxDO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/4/29
 * @description
 */
public interface BdcDzzzMlxxHjService {

    /**
     * 目录信息汇交方法(单条)
     *
     * @param bdcDzzzMlxxDO
     * @param ycmlid
     * @param xzqts
     * @param xzqdm
     * @return
     */
    Map mlxxHj(BdcDzzzMlxxDO bdcDzzzMlxxDO, String ycmlid, Integer xzqts, String xzqdm);

    /**
     * 目录信息汇交方法(批量)
     *
     * @param bdcDzzzMlxxList
     * @param xzqts
     * @param xzqdm
     * @return
     */
    Map mlxxPlHj(List<BdcDzzzMlxxDO> bdcDzzzMlxxList, Integer xzqts, String xzqdm);

    /**
     * 目录信息归档
     *
     * @param gdsjjzsj
     * @return
     */
    String mlxxGd(Timestamp gdsjjzsj, String xzqdm);
}
