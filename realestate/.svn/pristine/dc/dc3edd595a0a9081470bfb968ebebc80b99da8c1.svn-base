package cn.gtmap.realestate.init.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcLsxmQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/2
 * @description 项目历史管理mapper
 */
public interface BdcXmLsgxMapper {

    /**
     * 通过slbh或gzlslid获取项目历史关系
     * @param map  支持 slbh  gzlslid  zxyql  yxmid  xmid  order  排序 需要根据sql赋值
     * @return List<BdcXmLsgxDO>
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxList(Map map);

    /**
     * 根据工作流实例ID删除相关的历史关系数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid 工作流实例ID
     *@description
     */
    void deleteBdcXmLsgx(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID删除流程项目作为yxmid的项目历史关系
     */
    void deleteBdcYxmLsgx(String gzlslid);

    /**
     * @param bdcLsxmQO 历史项目查询对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID递归查询所有历史项目
     */
    List<BdcXmDO> getAllLsXmByLsgx(BdcLsxmQO bdcLsxmQO);


}
