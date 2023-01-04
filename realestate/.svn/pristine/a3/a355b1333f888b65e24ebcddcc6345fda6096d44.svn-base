package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * 不动产登记薄服务
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/12/21.
 * @description
 */
public interface BdcdjbService {

    /**
     * 通过宗海代码获取用海基本信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhdm
     *@return BdcBdcdjbZhjbxxDO
     *@description
     */
    BdcBdcdjbZhjbxxDO queryBdcdjbZhJbxx(String zhdm);

    /**
     * 通过宗地代码获取宗地基本信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhdm
     *@return BdcBdcdjbZdjbxxDO
     *@description
     */
    BdcBdcdjbZdjbxxDO queryBdcdjbZdJbxx(String zhdm);

    /**
     * 通过宗地宗海代码获取登记簿信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhdm
     *@return BdcBdcdjbDO
     *@description
     */
    BdcBdcdjbDO queryBdcdjb(String zhdm);

    /**
     * 通过宗海代码获取用海情况信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhdm
     *@return List<BdcBdcdjbZhjbxxYhzkDO>
     *@description
     */
    List<BdcBdcdjbZhjbxxYhzkDO> queryBdcdjbYhzk(String zhdm);

    /**
     * 通过宗地代码获取宗地变化情况信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zddm
     *@return List<BdcBdcdjbZdjbxxZdbhqkDO>
     *@description
     */
    List<BdcBdcdjbZdjbxxZdbhqkDO> queryBdcdjbZdbhqk(String zddm);

    /**
     * 通过宗海代码获取宗海变化情况信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhdm
     *@return List<BdcBdcdjbZhjbxxZhbhqkDO>
     *@description
     */
    List<BdcBdcdjbZhjbxxZhbhqkDO> queryBdcdjbZhbhqk(String zhdm);

    /**
     * 通过宗海/海岛代码代码获取用海用岛坐标信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param zhhddm
     *@return List<BdcBdcdjbZhjbxxYhydzbDO>
     *@description
     */
    List<BdcBdcdjbZhjbxxYhydzbDO> queryBdcdjbYhydzb(String zhhddm);


    /**
     * 通过djh查询登记簿要删除数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param djh
     *@param sfdzbflpbsj
     *@return List<Object>
     *@description
     */
    List<Object> queryBdcdjbDelData(String djh,Boolean sfdzbflpbsj);


    /**
     *初始化不动产单元号
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param bdcdyh
     *@description
     */
    void initBdcdjb(@NotBlank(message = "不动产单元号不能为空")String bdcdyh,String qjgldm) throws Exception;
}
