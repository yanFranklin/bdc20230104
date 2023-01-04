package cn.gtmap.realestate.certificate.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.dto.register.BdcAjxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 交接单
 *
 * @author <a href="mailto:lixin1@gtmap.cn>lixin</a>"
 * @version 1.0, 2019/8/27
 */
public interface BdcJjdMapper {

    /**
     * 查询在一段时间内指定受理人的注销流程
     *
     * @param bdcJjdXmQO
     * @return BdcJjdDTO 不动产交接单 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXmDO> queryJjdXm(BdcJjdXmQO bdcJjdXmQO);

    /**
     * 查询不动产交接单信息
     * @param bdcJjdQO 查询对象
     * @return 交接单对象
     */
    List<BdcJjdDO> listBdcJjd(BdcJjdQO bdcJjdQO);

    /**
     * 删除交接单的所有关系
     * @param jjdid 交接单 ID
     */
    void deleteJjdGx(String jjdid);

    /**
     * 查询全部案件信息
     * @param map
     * @return {BdcAjxxDTO}
     */
    List<BdcAjxxDTO> listBdcAjxx(Map map);

    /**
     * 查询不动产交接单信息
     * @param gzlslid gzlslid
     * @return 交接单对象
     */
    List<BdcJjdDO> queryBdcJjdByGzlslid(String gzlslid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params []
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJjdDO>
     * @description 查询状态不是已接收的交接单
     */
    List<BdcJjdDO> queryBdcJjd();

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcJjdDOList]
     * @return int
     * @description 更新交接单状态
     */
    int updateJjdzt(@Param("bdcJjdDOlist") List<BdcJjdDO> bdcJjdDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJjdDO>
     * @description 根据工作流实例id查询该流程所有交接单
     */
    List<BdcJjdDO> getBdcJjdListByGzlslid(@Param("gzlslid") String gzlslid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [jjdid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJjdDO>
     * @description 根据交接单id查询该单上所有件所创建的新交接单
     */
    List<BdcJjdDO> getAllNewJjdByJjdid(@Param("jjdid") String jjdid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [jjdid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 查询该交接单所对应的项目列表
     */
    List<BdcXmDO> getBdcXmDOByJjdid(@Param("jjdid") String jjdid);

    /**
     * 根据工作流实例ID查询交接单关系
     * @param gzlslidList 工作流实例ID集合
     * @return 项目移交单关系对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXmYjdGxDO> queryJjdGxByGzlslidList(@Param("gzlslidList") List<String> gzlslidList);

}
