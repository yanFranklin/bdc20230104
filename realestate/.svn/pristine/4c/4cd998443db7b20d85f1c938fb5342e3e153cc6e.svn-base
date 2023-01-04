package cn.gtmap.realestate.supervise.service;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.dto.certificate.BdcYzhDTO;
import cn.gtmap.realestate.supervise.core.qo.LfYzhQO;
import cn.gtmap.realestate.supervise.core.dto.LfYzhtjDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/08/25
 * @description 证书使用监管逻辑定义
 */
public interface LfZssyjgService {
    /**
     * 查询证书印制号
     * @param pageable 分页对象
     * @param lfYzhQO 查询条件
     * @return 证书印制号信息
     */
    Page<BdcYzhDO> listBdcZsyzh(Pageable pageable, LfYzhQO lfYzhQO);

    /**
     * 查询印制号详细信息
     * @param yzhid 印制号ID
     * @return {BdcYzhDTO} 印制号信息
     */
    BdcYzhDTO queryBdcYzh(String yzhid);

    /**
     * 废弃印制号统计（统计哪些人废弃了多少印制号）
     * @param lfYzhQO 印制号查询条件
     * @return {List} 统计信息
     */
    List<LfYzhtjDTO> fqyzhtj(LfYzhQO lfYzhQO);
}
