package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzBdsTsxxDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlCsDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzSjlDO;
import cn.gtmap.realestate.common.core.domain.engine.BdcGzZgzDO;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/8
 * @description 子规则DAO服务层
 */
public interface BdcGzZgzDaoService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@return bdcGzZgzDO
     *@description 根据规则id获取子规则
     */
    BdcGzZgzDO queryBdcGzZgzDO(String gzid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @return {List} 子规则集合
     * @description 获取所有的子规则
     */
    List<BdcGzZgzDO> listBdcGzZgzDO();

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZgzDO
     *@return bdcGzZgzDO
     *@description 新增子规则记录
     */
    BdcGzZgzDO insertBdcGzZgz(BdcGzZgzDO bdcGzZgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzSjlDO
     *@return bdcGzSjlDO
     *@description 新增子规则数据流记录
     */
    BdcGzSjlDO insertBdcGzSjl(BdcGzSjlDO bdcGzSjlDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzSjlCsDO
     *@return bdcGzSjlCsDO
     *@description 新增子规则数据流参数记录
     */
    BdcGzSjlCsDO insertBdcGzSjlCs(BdcGzSjlCsDO bdcGzSjlCsDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcGzBdsTsxxDO 规则表达式
     * @return {int} 数量
     * @description 新增规则表达式、提示信息
     */
    int insertBdcGzBdsTsxx(BdcGzBdsTsxxDO bdcGzBdsTsxxDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzZgzDO
     *@return num
     *@description 删除不动产规则子规则
     */
    int delBdcGzZgz(BdcGzZgzDO bdcGzZgzDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gzid
     *@return bdcGzSjlDOList
     *@description 通过gzid获取子规则数据流
     */
    List<BdcGzSjlDO> listBdcGzSjlDOByGzid(String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzSjlDO
     *@return num
     *@description 删除不动产规则数据流
     */
    int delBdcGzSjl(BdcGzSjlDO bdcGzSjlDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param sjlid
     *@return bdcGzSjlCsDOList
     *@description 通过sjid获取数据流参数
     */
    List<BdcGzSjlCsDO> queryBdcGzSjlCsDOListBySjlid(String sjlid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param sjlid
     *@return num
     *@description 删除sjlid对应的数据流参数
     */
    int delBdcGzSjlCsBySjlid(String sjlid);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param gzid 子规则ID
     * @description 删除子规则关联的表达式、提示信息
     */
    int deleteGzBdsTsxxByGzid(String gzid);
}
