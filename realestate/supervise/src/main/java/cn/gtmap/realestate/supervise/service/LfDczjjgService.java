package cn.gtmap.realestate.supervise.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZgqkDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfDczjjgZjxxDO;
import cn.gtmap.realestate.supervise.core.qo.LfZjxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/07
 * @description 廉防6：督查质检监管逻辑定义
 */
public interface LfDczjjgService {
    /**
     * 质检信息查询（分页台账）
     * @param pageable 分页参数
     * @param zjxxQO 业务查询参数
     * @return 质检信息
     */
    Page<BdcLfDczjjgZjxxDO> listZjxxTableData(Pageable pageable, LfZjxxQO zjxxQO);

    /**
     * 查询指定质检信息记录
     * @param id 质检信息ID
     * @return 质检信息
     */
    BdcLfDczjjgZjxxDO getZjxxById(String id);

    /**
     * 保存质检信息
     * @param bdcLfDczjjgZjxxDO 质检信息
     * @return {String} 质检信息ID
     */
    String saveZjxx(BdcLfDczjjgZjxxDO bdcLfDczjjgZjxxDO);

    /**
     * 删除质检信息
     * @param zjxxDOList 质检信息
     * @return {Integer} 删除记录
     */
    Integer deleteZjxxs(List<BdcLfDczjjgZjxxDO> zjxxDOList);

    /**
     * 上传质检报告文件
     * @param file 质检报告
     * @param zjxxid 质检信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    StorageDto uploadZjxxFile(MultipartFile file, String zjxxid);

    /**
     * 保存整改情况信息
     * @param zgqkDO 整改信息
     * @return {String} 整改情况ID
     */
    String saveZgqk(BdcLfDczjjgZgqkDO zgqkDO);

    /**
     * 删除整改情况信息
     * @param zgqkDOList 整改信息
     * @return {Integer} 删除记录数
     */
    Integer deleteZgqk(List<BdcLfDczjjgZgqkDO> zgqkDOList);

    /**
     * 上传整改报告文件
     * @param file 整改报告
     * @param zgqkid 整改信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    StorageDto uploadZgbgFile(MultipartFile file, String zgqkid);

    /**
     * 查询质检记录关联的所有整改情况
     * @param zjxxid 质检信息ID
     * @return {List} 整改情况
     */
    List<BdcLfDczjjgZgqkDO> listZgqkOfZjxx(String zjxxid);
}
