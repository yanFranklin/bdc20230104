package cn.gtmap.realestate.certificate.service;

import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO;
import cn.gtmap.realestate.common.core.dto.BdcUrlDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcHaimenYhYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcXmYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdDTO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYjdGdxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmFilesDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.BdcYjdXmxxDTO;
import cn.gtmap.realestate.common.core.dto.certificate.yjd.ResultCode;
import cn.gtmap.realestate.common.core.qo.certificate.BdcYjdQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/4
 * @description 移交单业务信息
 */
public interface BdcYjdService {
    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果，移交单是主表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询移交单信息，以及当前移交单的多有项目
     */
    List<BdcYjdDTO> listYjdAndXm(BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 查询结果，项目是主表
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 查询拥有项目的移交单
     */
    List<BdcXmYjdDTO> listXmOwnYjd(BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO 移交单查询QO
     * @return 移交单对象
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成移交单编号
     */
    BdcYjdDO generateYjdBh(BdcYjdQO bdcYjdQO);

    /**
     * @param bdcYjdQO
     * @return
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 生成并保存移交单信息
     */
    BdcYjdDO generateAndSaveYjdxx(BdcYjdQO bdcYjdQO);

    /**
     * @param yjdid 移交单ID
     * @param dylx
     * @param bdcUrlDTO 地址对象
     * @return String
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 获取移交单打印Xml
     */
    String yjdPrintXml(String yjdid, String dylx, BdcUrlDTO bdcUrlDTO);

    /**
     * @param bdcYjdDO 移交单DO
     * @return 更新结果
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 更新移交单(空值也会更新)
     */
    int updateBdcYjd(BdcYjdDO bdcYjdDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页信息
     * @param bdcYjdQO 移交单项目信息查询参数
     * @description  查询流程项目信息（用于海门新增移交单场景，根据流程移交）
     */
    Page<BdcYjdXmxxDTO> listBdcYjdXmxx(Pageable pageable, BdcYjdQO bdcYjdQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdXmxxDTO 项目信息
     * @return wjy : 没有移交过； yyj : 已经移交过
     * @description  核查当前项目是否已经移交过
     */
    String checkState(BdcYjdXmxxDTO bdcYjdXmxxDTO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbhList 受理编号集合
     * @return 移交单信息
     * @description  （海门）生成移交单
     */
    BdcYjdDO generateBdcYjd(List<String> slbhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYjdDOList 移交单集合
     * @return  操作返回信息
     * @description （海门）进行移交单移交到档案
     */
    String executeYj(List<BdcYjdDO> bdcYjdDOList);

    /**
     * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
     * @param slbhList 受理编号集合
     * @return  受理编号相关文件信息
     * @description （海门）提供受理编号相关文件信息给档案
     */
    List<BdcYjdXmFilesDTO> getFilesBySlbhList(List<String> slbhList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param yjxx 移交信息
     * @return {ResultCode} 返回状态信息实体
     * @description （海门）提供给档案接口，档案系统接收档案后回调该接口进行更新接收人等信息
     */
    ResultCode updateYjxx(List<BdcYjdDO> yjxx);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 上一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取上一手项目受理编号
     */
    List<String> listPreSlbh(String slbh);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param slbh 当前项目受理编号
     * @return {List} 下一手受理编号
     * @description （海门提供给档案接口）根据受理编号获取下一手项目受理编号
     */
    List<String> listNextSlbh(String slbh);

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param bdcHaimenYhYjdDTO
     * @description 海门银行系统提供移交单接口
     */
    void saveHaimenYhYjd(BdcHaimenYhYjdDTO bdcHaimenYhYjdDTO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [taskids]
     * @return int
     * @description 修改移交单打印状态
     */
    int updateYjdDyztByTaskid(List<String> taskids);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [paramMap]
     * @return java.util.List<cn.gtmap.realestate.common.core.domain.BdcYjdTaskGxDO>
     * @description 查询移交单任务关系
     */
    List<BdcYjdTaskGxDO> getYjdTaskGx(Map paramMap);

    Page<BdcYjdGdxxDTO> listBdcYjdGdxx(Pageable pageable, HashMap<String, Object> paramMap);
}
