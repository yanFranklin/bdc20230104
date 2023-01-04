package cn.gtmap.realestate.engine.core.service;

import cn.gtmap.realestate.common.core.domain.engine.BdcGzGxDO;

import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2019/3/6
 * @description 不动产组合关系服务
 */
public interface BdcGzGxService {
    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@return bdcGzGxDOList
     *@description 通过zhid获取bdcGzGxList
     */
    public List<BdcGzGxDO> queryBdcGzGxListByZhid(String zhid);

    /**
     * @param gzid
     * @return bdcGzGxDOList
     * @author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu</a>
     * @description 通过gzid获取所有组合关系
     */
    public List<BdcGzGxDO> queryBdcGzGxListByGzid(String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcGzGxDO
     *@return bdcGzGxDO
     *@description 新增bdcGzGxDO记录
     */
    public BdcGzGxDO insertBdcGzGx(BdcGzGxDO bdcGzGxDO);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param zhid
     *@return num
     *@description 根据zhid批量删除组合关系
     */
    public int delBdcGzGxByZhid(String zhid);

    /**
     *@author <a href="mailto:zhangxinyu@gtmap.cn">zhangxinyu2</a>
     *@param  gzid
     *@return num 删除的记录数
     *@description 通过gzid删除规则关系
     */
    public int delBdcGzGxByGzid(String gzid);

    /**
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param gxid
     *@return num
     *@description 根据zhid删除组合关系
     */
    public int delBdcGzGxByGxid(String gxid);
}
