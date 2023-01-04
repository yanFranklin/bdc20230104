package cn.gtmap.realestate.init.service.other;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.init.core.qo.InitServiceQO;

/**
 * 创建initServiceQo的服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/5/6
 * @description
 */
public interface InitServiceQoService {


   /**
    * 创建QO对象
    *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
    *@param
    *@return
    *@description
    */
    InitServiceQO creatInitServiceQO();

    /**
     * 从QO对象中获取原不动产项目
     * @param initServiceQO
     * @param yxmid
     * @return
     */
    BdcXmDO queryYbdcxm(String yxmid,InitServiceQO initServiceQO);

    /**
     * 从QO对象中获取原权利
     * @param initServiceQO
     * @param yxmid
     * @return
     */
    BdcQl queryYql(String yxmid,InitServiceQO initServiceQO);

    /**
     * @param initServiceQO
     * @return 原同权利项目ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 获取原同权利项目ID
     */
    String getYtqlXmid(InitServiceQO initServiceQO);
}
