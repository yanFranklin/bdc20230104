package cn.gtmap.realestate.supervise.service;

import cn.gtmap.gtc.storage.domain.dto.StorageDto;
import cn.gtmap.realestate.supervise.core.domain.BdcLfCxjzjsDO;
import cn.gtmap.realestate.supervise.core.qo.LfWgxwQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/10
 * @description 廉防7：诚信机制建设
 */
public interface LfCxjzjsService {
    /**
     * 违规信息查询（分页台账）
     * @param pageable 分页参数
     * @param lfWgxwQO 业务查询参数
     * @return 违规信息
     */
    Page<BdcLfCxjzjsDO> listWgxxTableData(Pageable pageable, LfWgxwQO lfWgxwQO);

    /**
     * 查询指定违规信息记录
     * @param id 违规信息ID
     * @return 违规信息
     */
    BdcLfCxjzjsDO getWgxxById(String id);

    /**
     * 保存违规信息
     * @param bdcLfCxjzjsDO 违规信息
     * @return {String} 违规信息ID
     */
    String saveWgxx(BdcLfCxjzjsDO bdcLfCxjzjsDO);

    /**
     * 删除违规信息
     * @param wgxxDOList 违规信息
     * @return {Integer} 删除记录
     */
    Integer deleteWgxxs(List<BdcLfCxjzjsDO> wgxxDOList);

    /**
     * 上传违规证明文件
     * @param file 违规报告
     * @param wgxxid 违规信息ID
     * @return {StorageDto} 大云附件存储信息
     */
    StorageDto uploadWgxxFile(MultipartFile file, String wgxxid);

    /**
     * 保存审核信息
     * @param bdcLfCxjzjsDO 违规审核信息
     * @return {String} 违规信息ID
     */
    String saveWgxxShxx(BdcLfCxjzjsDO bdcLfCxjzjsDO);
}
