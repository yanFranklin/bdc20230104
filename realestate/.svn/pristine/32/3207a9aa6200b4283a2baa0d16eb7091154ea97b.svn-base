package cn.gtmap.realestate.supervise.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxSqryDO;
import cn.gtmap.realestate.supervise.core.dto.BdcLfSqxxDTO;
import cn.gtmap.realestate.supervise.core.qo.BdcLfSqxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 4 职责权能监管-授权信息管理逻辑定义
 */
public interface LfZzqnjgService {
    /**
     * 授权信息查询（分页台账）
     * @param pageable 分页参数
     * @param bdcLfSqxxQO 业务查询参数
     * @return 授权信息
     */
    Page<BdcLfSqxxDTO> listSqxxTableData(Pageable pageable, BdcLfSqxxQO bdcLfSqxxQO);

    /**
     * 查询指定授权信息记录
     * @param id 授权信息ID
     * @return 授权信息
     */
    BdcLfSqxxDO getSqxxById(String id);

    /**
     * 查询指定授权信息关联的授权用户
     * @param sqxxid 授权信息ID
     * @return 授权用户
     */
    List<BdcLfSqxxSqryDO> getSqryBySqxxid(String sqxxid);

    /**
     * 保存授权信息
     * @param sqxx 授权信息
     * @return {String} 授权信息ID
     */
    String saveSqxx(BdcLfSqxxDTO sqxx);

    /**
     * 删除授权信息
     * @param sqxxDOList 授权信息
     * @return {Integer} 删除记录
     */
    Integer deleteSqxxs(List<BdcLfSqxxDO> sqxxDOList);

    /**
     * 上传授权证明文件
     * @param file 授权报告
     * @param sqxxid 授权信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    StorageDto uploadSqxxFile(MultipartFile file, String sqxxid);
}
