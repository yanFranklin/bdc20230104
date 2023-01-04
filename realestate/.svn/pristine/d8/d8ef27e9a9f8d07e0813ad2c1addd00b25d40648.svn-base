package cn.gtmap.realestate.accept.core.mapper;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfxxhzDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjfDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcYjSfxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.accept.BdcYjSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxmxDTO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-11
 * @description 收费信息Mapper
 */
@Repository
public interface BdcSlSfxxMapper {
    /**
     * @param bdcSlSfxxQO 受理收费信息查询QO
     * @return List<BdcSlSfxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询收费信息
     */
     List<BdcSlSfxxVO> listBdcSlSfxx(BdcSlSfxxQO bdcSlSfxxQO);
	
	/**
	 * @param bdcSlSfxxQO 受理收费信息查询QO
	 * @return List<BdcSlSfxxVO>
	 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
	 * @description 查询收费信息
	 */
	List<BdcSlSfxxVO> listBdcSlSfxxYh(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）
	 * @param xmids  项目ID集合
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 * @return map key: xmid  value: 收费合计金额
	 */
	List<BdcSlSfxxDO> queryHjGroupByXmids(@Param("xmids") List<String> xmids);

	/**
	 * @param
	 * @return 不动产受理收费信息
	 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
	 * @description 批量查询收费信息
	 */
	List<BdcSlSfxxDO> listBdcSlSfxxPl(@Param("xmid") String xmid,@Param("qlrlb") String qlrlb,@Param("sfxxidList") List<String> sfxxidList,@Param("sfyj") Integer sfyj,@Param("hjfk") boolean hjfk);

	/**
	 * 查询月结银行收费信息数据
	 * @param bdcYjSfxxQO 受理银行月结缴费查询QO
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 * @return 不动产受理银行月结缴费信息
	 */
	List<BdcYjSfxxDTO> queryYjSfxx(BdcYjSfxxQO bdcYjSfxxQO);

	/**
	 * 批量更新收费状态
	 * @param sfzt  收费状态
	 * @param sfxxidList 收费信息ID集合
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 */
	void modifyBdcSlSfxxSfztPl(@Param("sfzt") Integer sfzt, @Param("sfxxidList") List<String> sfxxidList);

	/**
	 * 根据收费信息ID集合，批量更新收费信息
	 * @param bdcSlSfxxQO 不动产收费信息QO
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 */
	void modifyBdcSlSfxxPl(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * @param bdcSlSfxxQO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description 发票查询收费信息
	 * @date : 2020/11/27 15:19
	 */
	List<BdcSlSfxxDO> listFphSfxx(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 查询已缴费未缴库的收费信息
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 * @return 已缴费、未缴库的收费信息数据
	 */
	List<BdcSlSfxxDO> listYjfAndWjkSfxx();

	/**
	 * （南通）汇总不同部门缴费金额
	 * @param param 查询参数
	 * @return {List} 汇总缴费金额信息
	 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
	 */
    List<BdcSfxxhzDTO> hzjfsj(Map<String, String> param);

	/**
	 * 查询明细记录数量
	 * @param param 查询参数
	 * @return 记录数量
	 */
	Integer countMxjfsj(Map<String, String> param);

	/**
	 * 查询明细记录
	 * @param param 查询参数
	 * @return 明细记录
	 */
	List<BdcSfxxmxDTO> mxjfsj(Map<String, String> param);

	/**
	 * 查询未缴库入库的月结收费项目数据
	 * @param bdcSlSfxxQO 收费信息查询QO
	 * @return {BdcSlSfxxDO} 收费信息数据
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 */
	List<BdcSlSfxxDO> listNotJkrkSfxx(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 批量更新收费信息
	 *
	 * @param map
	 * @return 更新数量
	 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
	 * @description 批量更新收费信息
	 */
	int updateBatchBdcSlSfxx(Map map);


	/**
	 * @param bdcSlSfxxQO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description 查询需要推送的收费信息
	 * @date : 2021/9/15 8:50
	 */
	List<BdcdjjfglVO> listBdcSfxxByPage(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * @param bdcSlSfxxQO 不动产收费信息QO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description 计算未缴费合计
	 * @date : 2021/11/4 23:16
	 */
	Map countWjfhj(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * @param bdcSlSfxxQO 不动产收费信息QO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description
	 * @date : 2021/11/5 11:21
	 */
	List<BdcSlWjfDTO> listWjfTzByPage(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 查询缴费信息（查询常州创建的 BDC_SL_PLJFTZ 视图，解决跨库调用接口获取登记数据慢问题）
	 * @param bdcSlSfxxQO 不动产收费信息QO
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 * @return 需要推送的收费信息
	 */
	List<BdcdjjfglVO> listPlTsJfxx(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 根据权利人名称和权利人证件号查询未缴费的项目ID信息（查询常州创建的 BDC_SL_SFQLR 视图）
	 *
	 * @param bdcQlrQO 权利人QO
	 * @return 项目ID集合
	 */
	List<String> listWjfXmidByQlrxx(BdcQlrQO bdcQlrQO);

	/**
	 * @param bdcSlSfxxQO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description 查询收费信息
	 * @date : 2022/5/18 9:36
	 */
	List<BdcSlSfxxDO> listBdcSlSfxxDO(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * 批量根据收费信息ID,修改收费信息 减免原因、是否收登记费、重新计算收费项目合计
	 *
	 * @param bdcSlSfxxQO 不动产收费信息QO
	 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
	 */
	void plxgSfxxJmyyAndCountHj(BdcSlSfxxQO bdcSlSfxxQO);

	/**
	 * @param bdcSlSfxxQO
	 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
	 * @description 查询收费信息
	 * @date : 2022/8/1 13:49
	 */
	List<BdcSlSfxxDO> listSfxxByQo(BdcSlSfxxQO bdcSlSfxxQO);
}
