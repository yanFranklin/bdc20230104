package cn.gtmap.realestate.init.core.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import cn.gtmap.realestate.common.core.qo.init.BdcLsxmQO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmLsgxQO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/10/31
 * @description 不动产项目历史关系服务
 */
public interface BdcXmLsgxService {

    /**
     * 查询项目的一级历史关系数据
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param bdcXmLsgxQO
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO>
     * @description 查询项目的一级历史关系数据
     */
    List<BdcXmLsgxDO> listBdcXmLsgx(BdcXmLsgxQO bdcXmLsgxQO);


    /**
     * 批量查询历史关系
     * @param bdcXmLsgxQO
     * @return
     */
    List<BdcXmLsgxDO> listBdcXmLsgxs(BdcXmLsgxQO bdcXmLsgxQO);

    /**
     * 通过项目slbh或工作流实例id获得不动产项目历史关系
     *@param gzlslid 工作流实例id
     * @param slbh 不动产项目受理编号
     * @param zxyql 是否注销原权利
     * @return
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxList(String slbh,String gzlslid,Integer zxyql);

    /**
     * 通过项目id去获取关系表
     * @param xmid 不动产项目ID
     * @param order 排序
     * @return
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxByXmid(String xmid,String order);

    /**
     * 通过项目id去获取关系表
     * @param xmid 不动产项目ID
     * @param wlxm 排序
     * @return
     */
    List<BdcXmLsgxDO> queryBdcXmLsgxByXmids(List<String> xmids,Integer wlxm);

    /**
     * 通过项目id去获取下手数据的关系表
     * @param xmid 不动产项目ID
     * @param order 排序
     * @return
     */
    List<BdcXmLsgxDO> queryBdcXmNextLsgxByXmid(String xmid,String order,Integer wlxm);

    /**
     * 外联证书，批量插入不动产项目历史关系
     *@author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     *@param bdcXmLsgxDOList
     *@return
     *@description 外联证书，批量插入不动产项目历史关系
     */
    void insertBatchBdcXmLsgx(List<BdcXmLsgxDO> bdcXmLsgxDOList);


    /**
     * 嵌套获取下手项目关系信息
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param xmid
     *@param list
     *@param isCq 是否查询产权 true为查询产权，false为查询限制权利
     *@return
     *@description
     */
    List<BdcXmLsgxDO> nextBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list,Integer wlxm,boolean isCq);

   /**
    * @param isCq 是否查询产权 true为查询产权，false为查询限制权利
    * @return
    * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
    * @description 嵌套获取上手项目关系信息
    */
    List<BdcXmLsgxDO> prevBdcXmLsgx(String xmid, List<BdcXmLsgxDO> list,Integer wlxm,boolean isCq);


    /**
     * 根据工作流实例ID删除相关的历史关系数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gzlslid 工作流实例ID
     *@description
     */
    void deleteBdcXmLsgx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid);

    /**
     * 根据关系id删除相关的历史关系数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gxid 历史关系ID
     *@description
     * @return
     */
    int deleteLsgxById(@NotBlank(message = "历史关系ID") String gxid);

    /**
     * 根据关系ids删除相关的历史关系数据
     *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
     *@param gxids 历史关系ID
     *@description
     * @return
     */
    int deleteLsgxByIds(String[] gxids);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID删除流程项目作为yxmid的项目历史关系
     */
    void deleteBdcYxmLsgx(@NotBlank(message = "工作流实例ID不能为空") String gzlslid);

    /**
     * @param bdcLsxmQO 历史项目查询对象
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID递归查询所有历史项目
     */
    List<BdcXmDO> getAllLsXmByLsgx(BdcLsxmQO bdcLsxmQO);

}
