package cn.gtmap.realestate.accept.core.service;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxCzrzDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSfxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSfxxZzcxjResponseDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlWjfDTO;
import cn.gtmap.realestate.common.core.dto.accept.ZzcxjSfxxDTO;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlSfxxQO;
import cn.gtmap.realestate.common.core.qo.init.BdcQlrQO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxhzVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSfxxmxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcSlSfxxVO;
import cn.gtmap.realestate.common.core.vo.accept.ui.BdcdjjfglVO;
import cn.gtmap.realestate.common.core.vo.portal.BdcGzyzVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理收费信息数据服务
 */
public interface BdcSlSfxxService {
    /**
     * @param sfxxid 收费信息ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID获取不动产受理收费信息
     */
    BdcSlSfxxDO queryBdcSlSfxxBySfxxid(String sfxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息（不包含已作废的数据）
     */
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据工作流实例ID获取不动产受理收费信息（包含作废的数据）
     */
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidBhzf(String gzlslid);

    /**
     * 根据不动产收费信息DO获取不动产受理收费信息
     *
     * @param bdcSlSfxxDO 收费信息DO
     * @return 不动产受理收费信息（不包含已作废的数据）
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcSlSfxxDO> queryBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param pageable bdcSlSfxxDOJSON
     * @return Page<BdcSlSfxxDO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 分页查询受理收费信息
     */
    Page<BdcSlSfxxVO> listBdcSlSfxxByPage(Pageable pageable, String bdcSlSfxxDOJSON);

    /**
     * @param bdcSlSfxxQO
     * @return List<BdcSlSfxxVO>
     * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
     * @description 查询 收费信息
     */
    List<BdcSlSfxxVO> listBdcSlSfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param gzlslid 工作流实例ID
     * @return List<BdcSlSfxxDO>
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询 合计非空的收费信息
     */
    List<BdcSlSfxxDO> listBdcSlSfxxByGzlslidHjFk(String gzlslid);

    /**
     * @param bdcSlSfxxQO
     * @return List<BdcSlSfxxVO>
     * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 查询 银行收费信息
     */
    List<BdcSlSfxxVO> listBdcSlSfxxYh(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param xmid 项目ID
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 根据项目ID获取不动产受理收费信息（不包含已作废的数据）
     */
    List<BdcSlSfxxDO> listBdcSlSfxxByXmid(String xmid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [xmid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO>
     * @description 根据项目id获取不动产受理收费信息（包含作废数据）
     */
    List<BdcSlSfxxDO> listBdcSlSfxxByXmidBhzf(String xmid);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 新增不动产受理收费信息
     */
    BdcSlSfxxDO insertBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 更新不动产受理收费信息
     */
    Integer updateBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * @param bdcSlSfxxDOList 不动产受理收费信息集合
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 批量更新不动产受理收费信息
     */
    void batchUpdateBdcSlSfxx(List<BdcSlSfxxDO> bdcSlSfxxDOList);

    /**
     * 批量更新不动产收费信息
     * @param bdcSlSfxxQO 不动产收费信息QO
     */
    void batchUpdateBdcSlSfxxBySfxxidList(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxDO 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 保存或更新不动产受理收费信息
     */
    Integer saveOrUpdateBdcSlSfxx(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 根据 sfxxid 更新不动产收费信息和关联的收费项目
     * @param sfxxid  收费信息ID
     * @param bdcSlSfxxDO  更新内容
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void updateBdcSlSfxxIdWithSfxm(String sfxxid, BdcSlSfxxDO bdcSlSfxxDO);
    /**
     * @author: <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param: parm 不动产受理收费信息参数 支持以下参数参数（gzlslid, jbxxid, sfxxid, xmid）
     * @param: value 不动产受理需要更新的值
     * @return: Integer
     * @description 根据不动产受理收费参数更新收费信息
     */
    void updateBdcSlSfxx(BdcSlSfxxDO param, BdcSlSfxxDO value);

    /**
     * @param sfxxid 收费信息ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费信息
     */
    Integer deleteBdcSlSfxxBySfxxid(String sfxxid);

    /**
     * @param gzlslid 工作流实例ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据收费信息ID删除不动产受理收费信息
     */
    Integer deleteBdcSlSfxxByGzlslid(String gzlslid);

    /**
     * @param gzlslid 工作流实例ID
     * @param xmid 项目ID
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description 根据工作流实例ID和项目ID 删除不动产受理收费信息
     */
    Integer deleteBdcSlSfxxByGzlslidAndXmid(String gzlslid, String xmid);

    /**
     * @param processInsId 工作流实例ID
     * @return 规则验证信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 验证流程的收费状态
     */
    BdcGzyzVO checkLcSfzt(String processInsId);

    /**
     * 根据xmid分组查询项目的收费金额（权利人、义务人收费金额求和）
     *
     * @param xmids 项目ID集合
     * @return map key: xmid  value: 收费合计金额
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    Map<String, Object> queryHjGroupByXmids(List<String> xmids);

    /**
     * @param sfxxidList 收费信息ID集合
     * @param sfyj       是否月结
     * @param hjfk       合计是否非空
     * @return 不动产受理收费信息
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description
     */
    List<BdcSlSfxxDO> listBdcSlSfxxPl(String xmid, String sqrlb, List<String> sfxxidList, Integer sfyj, boolean hjfk);


    /**
     * 批量更新收费状态
     *
     * @param sfxxIdList 收费信息ID集合
     * @param sfzt       收费状态
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void modifyBdcSlSfxxSfztPl(List<String> sfxxIdList, Integer sfzt);

    /**
     * 提供自助查询机查询收费信息接口（盐城）
     *
     * @param zzcxjSfxxDTO 收费信息查询接口
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    List<BdcSlSfxxZzcxjResponseDTO> zzcxjQuerySfxx(ZzcxjSfxxDTO zzcxjSfxxDTO);


    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息
     * @date : 2020/11/27 15:17
     */
    List<BdcSlSfxxDO> listFpcxSfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 发票查询收费信息-批量查询返回收费信息和收费项目信息
     * @date : 2020/11/27 15:17
     */
    List<BdcSfxxDTO> listFpcxSfxxPl(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 登记缴费管理
     * @param pageable
     * @param bdcSlSfxxQOJson
     * @return
     */
    Page<BdcdjjfglVO> djjfgl(Pageable pageable, String bdcSlSfxxQOJson);

    /**
     * @param jfList 查询条件
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 缴费
     * @date : 2020/11/27 15:15
     */
    void jfcz(List<BdcdjjfglVO> jfList,String type);

    /**
     * 非税开票
     * @param pageable
     * @return
     */
    Page<BdcSlSfxxDO> fskp(Pageable pageable, String gzlslid);

    /**
     * 查询已缴费未缴库的收费信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 已缴费、未缴库的收费信息数据
     */
    List<BdcSlSfxxDO> queryYjfAndWjkSfxx();

    /**
     * （南通）汇总缴费数据
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxhzVO} 汇总缴费信息
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    BdcSfxxhzVO hzjfsj(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 查询缴费数据明细
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSfxxmxVO} 缴费数据明细
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    BdcSfxxmxVO mxjfsj(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 查询未缴库入库的月结收费项目数据
     * @param bdcSlSfxxQO 收费信息查询QO
     * @return {BdcSlSfxxDO} 收费信息数据
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcSlSfxxDO> queryNotJkrkSfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param bdcSlSfxxCzrzDO 不动产受理收费操作人信息
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @description 新增不动产受理收费操作人信息
     */
    BdcSlSfxxCzrzDO insertBdcSlSfxxCzrz(BdcSlSfxxCzrzDO bdcSlSfxxCzrzDO);

    /**
     * 工作流事件，用于登簿时，更新收费信息表中的登簿时间
     *
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    void modifySlSfxxDbsj(String processInsId);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询推送收费信息管理台账数据
     * @date : 2021/9/13 14:36
     */
    Page<BdcdjjfglVO> listTssfByPage(Pageable pageable, String json);

    /**
     * @param bdcSlSfxxQO 查询参数
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 查询需要推送的信息
     * @date : 2021/9/15 8:48
     */
    List<BdcdjjfglVO> listTssfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据相关查询条件查询未缴费数据 fph 不为空，缴费书编码不为空且未缴费的数据
     * @date : 2021/9/13 14:36
     */
    Page<BdcSlWjfDTO> listWjftzByPage(Pageable pageable, String json);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/4 23:14
     */
    Map countWjfhj(String json);

    /**
     * @param json
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description
     * @date : 2021/11/5 11:19
     */
    List<BdcSlWjfDTO> listWjfxxByPage(String json);

    /**
     * 查询缴费信息（查询常州创建的 BDC_SL_PLJFTZ 视图，解决跨库调用接口获取登记数据慢问题）
     * @param bdcSlSfxxQO 不动产收费信息QO
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @return 需要推送的收费信息
     */
    List<BdcdjjfglVO> listPlTsJfxx(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * 根据权利人名称和权利人证件号查询未缴费的项目ID信息（查询常州创建的 BDC_SL_SFQLR 视图）
     * @param bdcQlrQO 权利人QO
     * @return 项目ID集合
     */
    List<String> listWjfXmidByQlrxx(BdcQlrQO bdcQlrQO);

    /**
     * 月结银行更新收费状态操作时间
     *
     * @param gzlslid 工作流实例ID
     */
    void yjyhUpdateSfztAndSfsj(String gzlslid);

    /**
     * @param bdcSlSfxxQO
     * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
     * @description 根据受理编号查询收费信息
     * @date : 2022/8/1 10:18
     */
    List<BdcSlSfxxDO> listBdcSlSfxxBySlbh(BdcSlSfxxQO bdcSlSfxxQO);

    /**
     * @param
     * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
     * @description 更新不动产受理收费信息并且根据收费状态推送登记流程
     * @date : 2022/9/26
     */
    Integer updateBdcSlSfxxAndTsdjlc(BdcSlSfxxDO bdcSlSfxxDO);

    /**
     * 查询不动产收费信息和收费项目信息
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param bdcSlSfxxQO 不动产受理收费信息查询QO
     * @return 收费信息和收费项目信息
     */
    List<BdcSfxxDTO> queryBdcSlSfxxAndSfxm(BdcSlSfxxQO bdcSlSfxxQO);
}
