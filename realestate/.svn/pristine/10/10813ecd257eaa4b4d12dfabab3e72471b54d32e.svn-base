package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdGdxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/4
 * @description
 */
public interface BdcYjdMapper {

    List<BdcYjdGdxxDTO> listBdcYjdGdxxByPage(Map map);


    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询到的项目信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询存在移交的项目信息
     */
    List<BdcXmYjdDTO> listXmOwnYjd(BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 移交单信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询移交单信息
     */
    List<BdcYjdDO> listYjd(BdcYjdQO bdcYjdQO);

    /**
     * @param dateStr
     * @return int
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取满足当前条件的数据总数
     */
    int countYjdbh(String dateStr);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 项目ID集合
     * @description  查询受理编号对应的所有项目信息ID
     */
    List<String> listBdcXmid(List<String> slbhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdQO 查询参数
     * @return 移交单对应项目信息
     * @description  查询指定受理编号对应的项目信息
     */
    List<BdcYjdXmxxDTO> listBdcYjdXmxxByPageOrder(BdcYjdQO bdcYjdQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 受理编号
     * @return 移交单信息
     * @description  查询当前项目对应的移交单信息
     */
    List<BdcYjdDO> listBdcYjd(String slbh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 上一手项目信息
     * @description （海门提供给档案接口）根据受理编号获取上一手项目信息
     */
    List<BdcXmDO> listPreXmxx(@Param(value="slbh") String slbh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 下一手项目信息
     * @description （海门提供给档案接口）根据受理编号获取下一手项目信息
     */
    List<BdcXmDO> listNextXmxx(@Param(value="slbh") String slbh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 当前项目受理编号
     * @return {List} 项目信息集合
     * @description  查询指定受理编号对应的项目集合
     */
    List<BdcXmDO> listBdcXm(List<String> slbhList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [taskids]
     * @return int
     * @description 修改移交单状态
     */
    int updateYjdDyztByTaskid(@Param("taskids") List<String> taskids);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [map]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO>
     * @description 查询移交单任务关系
     */
    List<BdcYjdTaskGxDO> getYjdTaskGx(Map map);
}
