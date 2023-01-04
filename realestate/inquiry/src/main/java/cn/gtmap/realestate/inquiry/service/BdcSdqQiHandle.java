package cn.gtmap.realestate.inquiry.service;/**
 * @author <a href="mailto:wangyinghao@gtmap.cn">wangyinghao</a>
 * @params [taskName]
 * @return java.lang.Object
 * @description
 */

import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;
import cn.gtmap.realestate.common.core.service.InterfaceCode;

/**
 *
 * @description:
 * @author: cy
 * @create: 2021-10-21
 **/

public interface BdcSdqQiHandle extends InterfaceCode {
    /**
     * 气 是否满足过户条件
     * @param consNo
     * @param gzlslid
     * @param rqlx
     * @return
     */
    public Object getQiZt(String consNo, String gzlslid, String rqlx);

    /**
     * 气 过户
     * @param gzlslid
     * @param isOneWebSource
     * @return
     */
    public boolean qigh(String gzlslid,String isOneWebSource);

    /**
     * 气 录办理的户号，和3.0件关联上
     * @param bdcSdqywQO
     * @return
     */
    public Object saveData(BdcSdqywQO bdcSdqywQO);
}
