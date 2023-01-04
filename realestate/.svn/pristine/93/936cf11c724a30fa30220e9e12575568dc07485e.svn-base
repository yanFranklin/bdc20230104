package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlqtzkFjQO;
import cn.gtmap.realestate.init.core.qo.InitQlqtzkFjDataQO;

import java.util.List;
import java.util.Map;

/**
 * 生成默认附记和权利其他状况
 *
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/13.
 * @description
 */
public interface BdcXtQlqtzkFjPzService {

    /**
    * @author chenchunxue 2020/9/2
    * @param initQlqtzkFjDataQO
    * @return java.util.Map
    * @description 获取权利其他状况附记数据  服务查询数据
    */
    Map getQlqtzkFjData(InitQlqtzkFjDataQO initQlqtzkFjDataQO);

    /**
     * 获取权利其他状况信息
     * @param zsid 证书ID
     * @param xmid 项目ID
     * @param list 该项目权利人数据
     * @return
     */
    String getQlqtzkMessageByZsxx(String xmid,String zsid,List<BdcQlrDO> list);


    /**
     * 获取权利其他状况信息
     * @param xmid 项目ID
     * @return
     */
    String getQlqtzkMessageByXmid(String xmid);

    /**
      * @param
      * @return
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 补充权利其他状况
      */
    String appendQlqtzk(String qlqtzk,String xmid,String zsid,List<BdcQlrDO> list);

    /**
     * 获取附记信息
     *@param zsid 证书ID
     * @param xmid 项目ID
     * @param list 该项目的权利人数据
     * @return
     */
    String getFjMessageByZsxx(String xmid,String zsid,List<BdcQlrDO> list);

    /**
     * 获取附记信息
     * @param xmid 项目ID
     * @param map  替换数据
     * @return
     */
    String getFjMessageByXmid(String xmid);

    /**
     * 更新权利其他状况附记信息
     * @param xmid
     * @param mode
     * @return
     */
    Boolean updateQlqtzkFjByMode(String xmid, String mode);

    /**
     * @param bdcQlqtzkFjQO 权利其他状况和附记操作QO
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据json 更新项目相关的部分权利其他状况或附记
     */
    void updateQlqtzkFjByQO(BdcQlqtzkFjQO bdcQlqtzkFjQO);

}
