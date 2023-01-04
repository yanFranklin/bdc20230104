package cn.gtmap.realestate.config.service;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhsymxDO;
import cn.gtmap.realestate.common.core.dto.config.BdcReturnData;
import cn.gtmap.realestate.common.core.qo.config.BdcYzhQO;
import cn.gtmap.realestate.common.core.vo.config.ui.BdcYzhVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/1/30
 * @description 证书印印制号配置逻辑处理接口定义
 */
public interface BdcXtZsyzhService {
    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param pageable 分页对象
     * @param bdcYzhQO 查询条件
     * @return {Page} 证书印制号配置分页数据
     * @description 查询证书印制号配置数据列表
     */
    Page<BdcYzhDO> listBdcXtZsyzh(Pageable pageable, BdcYzhQO bdcYzhQO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDO 证书印制号配置实体
     * @return {int} 操作数据记录数
     * @description 保存证书印制号配置配置
     */
    int saveBdcZsyzh(BdcYzhDO bdcYzhDO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhDOList 证书印制号配置实体集合
     * @return {int} 操作数据记录数
     * @description 删除证书印制号配置
     */
    int deleteBdcZsyzh(List<BdcYzhDO> bdcYzhDOList);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhVO 证书印制号模板
     * @return {int} 操作数据记录数
     * @description 生成证书印制号
     */
    int generateBdcZsyzh(BdcYzhVO bdcYzhVO);

    /**
     * 提取领取或撤销印制号
     * @param bdcYzhVO
     * @return
     */
    BdcReturnData extractData(BdcYzhVO bdcYzhVO);

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 作废证书印制号
     */
    void deleteBdcZsyzh(BdcYzhsymxDO bdcYzhsymxDO);

    /**
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @param bdcYzhsymxDO 证书印制号使用明细
     * @return {int} 操作数据记录数
     * @description 还原证书印制号
     */
    void revertBdcZsyzh(BdcYzhsymxDO bdcYzhsymxDO);
}
