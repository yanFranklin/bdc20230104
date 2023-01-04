package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmYjdGxDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.register.BdcAjxxDTO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdQO;
import cn.gtmap.realestate.common.core.qo.certificate.BdcJjdXmQO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 交接单业务信息
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/8/27 18:16
 */
public interface BdcJjdService {

    /**
     * 生成并保存移交单信息
     *
     * @param bdcJjdQO 交接单查询对象
     * @return {BdcJjdDO} 不动产交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    BdcJjdDO generateAndSaveJjdxx(BdcJjdQO bdcJjdQO);

    /**
     * 生成移交单编号
     *
     * @param bdcJjdQO 交接单查询QO
     * @return 移交单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    BdcJjdDO generateJjdBh(BdcJjdQO bdcJjdQO);

    /**
     * 获取移交单打印Xml
     *
     * @param jjdid     交接单ID
     * @param bdcUrlDTO 地址对象
     * @return {String} 打印 xml
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    String jjdPrintXml(String jjdid, BdcUrlDTO bdcUrlDTO);

    /**
     * 删除交接单项目关系
     *
     * @param gzlslid gzlslid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void delJjdXmGx(String gzlslid);

    /**
     * 删除交接单项目关系
     *
     * @param jjdid jjdid
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    void delJjd(String jjdid);

    /**
     * 转发交接单
     *
     * @param bdcJjdDO 交接单对象
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    BdcJjdDO forwardJjd(BdcJjdDO bdcJjdDO);

    /**
     * 确认接收交接单
     *
     * @param jjdid 交接单 id
     * @return {Boolean} false 不允许接收
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Boolean acceptJjd(String jjdid);


    /**
     * 退回交接单
     *
     * @param jjdid 交接单id
     * @return {Boolean} false 不允许退回
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    Boolean backJjd(String jjdid);

    /**
     * 查询在一段时间内指定受理人的流程
     *
     * @param bdcJjdXmQO 交接单项目查询对象
     * @return BdcJjdDTO 不动产交接单 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcXmDO> queryJjdXm(BdcJjdXmQO bdcJjdXmQO);

    List<BdcXmYjdGxDO> queryJjdGx(String jjdid);

    List<BdcXmYjdGxDO> queryJjdGxByXmid(String xmid);


    /**
     * 根据工作流实例ID查询交接单关系
     * @param gzlslidList 工作流实例ID集合
     * @return 项目移交单关系对象
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    List<BdcXmYjdGxDO> queryJjdGxByGzlslidList(List<String> gzlslidList);

    List<BdcJjdDO> listBdcJjd(BdcJjdQO bdcJjdQO);

    /**
     * 查询出全部的案件信息
     *
     * @param map 交接单项目查询对象
     * @return BdcAjxxDTO 案件信息 DTO
     * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
     */
    List<BdcAjxxDTO> listBdcAjxx(Map map);

    /**
     * 判断是否交接单可以创建
     *
     * @param gzlslid gzlslid
     * @return {Integer} 0 表示可以创建， 1 不能创建 当前用户已经用此gzlslid创建过交接单，不能再创建
     */
    Integer checkIsCreat(String gzlslid);

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
    int updateJjdzt(List<BdcJjdDO> bdcJjdDOList);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [gzlslid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJjdDO>
     * @description 根据工作流实例id查询该流程所有交接单
     */
    List<BdcJjdDO> getBdcJjdListByGzlslid(String gzlslid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [jjdid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcJjdDO>
     * @description 根据交接单id查询该单上所有件所创建的新交接单
     */
    List<BdcJjdDO> getAllNewJjdByJjdid(String jjdid);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [jjdid]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @description 查询该交接单所对应的项目列表
     */
    List<BdcXmDO> getBdcXmDOByJjdid(String jjdid);

    /**
     * 批量转发交接单
     * @param bdcJjdDOList 交接单对象集合
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    void forwardJjd(List<BdcJjdDO> bdcJjdDOList);

    /**
     * 生成南通交接单批量打印xml
     * @param key 打印参数缓存Redis Key
     * @return {String} 移交单打印xml
     */
    String jjdBatchPrintXml(String key);
}
