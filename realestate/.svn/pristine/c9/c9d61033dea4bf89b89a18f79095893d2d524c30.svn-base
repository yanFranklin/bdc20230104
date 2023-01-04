package cn.gtmap.realestate.check.core.mapper;


import cn.gtmap.realestate.check.core.vo.SelectCgsjVo;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzjcLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckGzxxDO;
import cn.gtmap.realestate.common.core.domain.check.CheckLogDO;
import cn.gtmap.realestate.common.core.domain.check.CheckPlanDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 不动产项目查询Mapper接口
 *@author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 *@version 1.0
 *@description
 */
public interface BdcXmMapper {

    /**
     * 获取bdcxm列表
     * @author lisongtao
     * @param parMap 查询参数
     * @return 返回的BdcXm列表
     */
     List<BdcXmDO> selectAllBdcXm(Map<String, Object> parMap);
    /**
     * 获取bdcxm列表 根据xmid排序  检查计划任务使用
     * @author lisongtao
     * @param parMap 查询参数
     * @return 返回的BdcXm列表
     */
    List<BdcXmDO> selectAllBdcXmOrderByXmid(Map<String, Object> parMap);
    /**
     * 获取bdcxm数量
     * @author ccx
     * @param parMap 查询参数
     * @return 返回的BdcXm列表
     */
    int countBdcXms(Map<String, Object> parMap);
    /**
     * 获取bdcxm数量
     * @author ccx
     * @param parMap 查询参数
     * @return 返回的BdcXm列表
     */
    int getCheckPlanVersion();
    /**
     * 查询检查计划
     * @param checkPlanDO
     */
    List<CheckPlanDO> queryCheckPlan(CheckPlanDO checkPlanDO);
    /**
     * 获取规则列表
     * @author lisongtao
     * @param parMap 查询参数
     * @return 返回的BdcGzxx列表
     */
     List<CheckGzxxDO> selectAllBdcGzxx(Map<String, Object> parMap);

    /**
     * 获取规则列表
     * @author lisongtao
     * @param parMap 查询参数
     * @return 返回的CheckGzjcLogDO列表
     */
     List<CheckGzjcLogDO> selectAllBdcGzjcLog(Map<String, String> parMap);

    /**
     * 查询wbjxm表中记录的已办结的项目
     * @author lisongtao
     * @return 返回的BdcXm列表
     */
    List<BdcXmDO> selectBjxmFromWbj();


    /**
     * 获取分组规则信息
     * @param map
     * @return
     */
    List<Map<String,Object>> getGzxxGroupByPage(Map<String, Object> map);
    /**
     * 获取分组规则信息
     * @param map
     * @return
     */
    List<Map<String,Object>> quertCgsjListByPage(SelectCgsjVo selectCgsjVo);

    /**
     * 获取规则分组
     * @return
     */
    List<String> queryGzxxGroup();

    /**
     * 获取上一次运行的日志
     * @return
     */
    CheckLogDO queryPrevRunLog();

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 批量更新规则是否忽略字段
     */
    void updateBatchCheckGzxxSfhl(@Param("sfhl") Integer sfhl, @Param("gzidList") List<String> gzidList);

    /**
     * @param parMap
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询项目数量
     * @date : 2022/5/7 9:01
     */
    int queryXmsl(Map<String, Object> parMap);

}
