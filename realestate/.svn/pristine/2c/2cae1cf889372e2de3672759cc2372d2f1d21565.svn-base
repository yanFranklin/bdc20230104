package cn.gtmap.realestate.supervise.service;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.qo.init.BdcXmQO;
import cn.gtmap.realestate.supervise.core.domain.*;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCkbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjCqbjDTO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfYcbjyjFgzsjbjDTO;
import cn.gtmap.realestate.supervise.core.qo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/22
 * @description 廉防1：异常办件预警服务类
 */
public interface LfYcbjyjService {
    /**
     * 分页查询超期办件数据
     * @param pageable 分页参数
     * @param cqbjQO 查询参数
     * @return {Page} 超期办件信息
     */
    Page<BdcLfYcbjyjCqbjDTO> listCqbjData(Pageable pageable, LfCqbjQO cqbjQO);

    /**
     * 分页查询超快办件数据
     * @param pageable 分页参数
     * @param ckbjQO 查询参数
     * @return {Page} 超快办件信息
     */
    Page<BdcLfYcbjyjCkbjDTO> listCkbjData(Pageable pageable, LfCkbjQO ckbjQO);

    /**
     * 分页查询非工作时间办件数据
     * @param pageable 分页参数
     * @param fgzsjbjQO 查询参数
     * @return {Page} 非工作时间办件信息
     */
    Page<BdcLfYcbjyjFgzsjbjDTO> listFgsjbjData(Pageable pageable, LfFgzsjbjQO fgzsjbjQO);

    /**
     * 查询异常办件审核信息
     * @param type 异常办件类型
     * @param id 主键ID
     * @return 异常信息
     */
    Object queryYcbjShxx(String type, String id);

    /**
     * 保存异常办件审核信息
     * @param data 审核信息数据
     * @param type 异常办件类型
     * @return 异常信息ID
     */
    String saveYcbjShxx(String type, Map<String, Object> data);

    /**
     * 获取异常情况申报信息
     * @param processInsId 工作流实例ID
     * @param id 申报信息ID
     * @return {BdcLfYcqksbDO} 申报信息
     */
    BdcLfYcqksbDO getYcqksb(String processInsId, String id);

    /**
     * 保存申报信息
     * @param sbxx 申报信息
     * @return {String} 申报信息ID
     */
    String saveYcqksb(BdcLfYcqksbDO sbxx);

    /**
     * 异常申报信息查询（分页台账）
     * @param pageable 分页参数
     * @param lfYcqksbQO 业务查询参数
     * @return 异常申报信息
     */
    Page<BdcLfYcqksbDO> listYcqksbTableData(Pageable pageable, LfYcqksbQO lfYcqksbQO);

    /**
     * 分页查询异常原因数据
     * @param pageable 分页参数
     * @param ycyyQO 查询参数
     * @return {Page} 异常原因数据
     */
    Page<BdcLfYcbjyjYcyyDO> listYcyyData(Pageable pageable, LfYcyyQO ycyyQO);

    /**
     * 查询指定异常原因记录
     * @param id 异常原因ID
     * @return 异常原因
     */
    BdcLfYcbjyjYcyyDO getYcxxById(String id);

    /**
     * 保存异常原因
     * @param ycyyDO 异常原因
     * @return {String} 异常原因ID
     */
    String saveYcxx(BdcLfYcbjyjYcyyDO ycyyDO);

    /**
     * 删除异常原因
     * @param ycyyDOList 异常原因
     * @return {Integer} 删除记录
     */
    Integer deleteYcxxs(List<BdcLfYcbjyjYcyyDO> ycyyDOList);

    /**
     * 分页查询项目数据
     * @param pageable 分页参数
     * @param bdcXmQO 查询参数
     * @return {Page} 异常原因数据
     */
    Page<BdcXmDO> listXmxxData(Pageable pageable, BdcXmQO bdcXmQO);
}
