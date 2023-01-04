package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;

/**
 *
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description 不动产查封服务
 */
public interface BdcCfService {

    /**
     *处理查封顺序
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param  bdcCfDO
     *@return  BdcCfDO
     *@description
     */
    BdcCfDO dealCfsx(BdcCfDO bdcCfDO);



    /**
     * 根据不动产单元和项目ID判定查封类型
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@param xmid
     *@param yxmid
     *@return 查封类型
     *@description
     */
    int queryCflx(String bdcdyh,String xmid,String yxmid);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 批量更新查封编号
      */
     void updateCfbhPl(String gzlslid,String cfbh);


}
