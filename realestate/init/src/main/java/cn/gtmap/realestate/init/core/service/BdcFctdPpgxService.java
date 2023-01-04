package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcFctdPpgxDO;

import java.util.List;

/**
 * 房产土地匹配关系服务
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface BdcFctdPpgxService {

    /**
     *房产证和土地证进行匹配记录关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid 房产证项目ID
     *@param tdxmid 土地证项目id
     *@description
     */
    void pptd(String fcxmid,String tdxmid,String requestClientRealIP) throws Exception;

    /**
     *取消房产证和土地证匹配关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid 房产证项目ID
     *@return
     *@description
     */
    void qxpptd(String fcxmid) throws Exception;


    /**
     * 根据房产项目ID获取匹配关系
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param fcxmid
     *@return List<BdcFctdPpgxDO>
     *@description
     */
    List<BdcFctdPpgxDO> queryBdcFctdPpgx(String fcxmid);

    /**
     * 根据土地项目ID获取匹配关系
     *@author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     *@param tdxmid
     *@return List<BdcFctdPpgxDO>
     *@description
     */
    List<BdcFctdPpgxDO> queryBdcFctdPpgxByTdxmid(String tdxmid);
}
