package cn.gtmap.realestate.etl.service;


import cn.gtmap.realestate.common.core.domain.BdcXmDO;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-06-26
 * @description 共享登记数据给大数据局使用 处理数据方法
 */
public interface ShareDataHandlerService {


    /**
     * @param processInsId
     * @return
     * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
     * @description 共享登记数据给大数据局使用共享方法
     */
    void shareDsjjDataByProcessInsId(String processInsId);

    /**
     * @param xmid
     * @return
     * @author <a href="mailto:shaliyao@gtmap.cn">shaliyao</a>
     * @description 共享登记数据给大数据局使用共享方法
     */
    void shareDsjjDataByXm(BdcXmDO bdcXmDO);

}
