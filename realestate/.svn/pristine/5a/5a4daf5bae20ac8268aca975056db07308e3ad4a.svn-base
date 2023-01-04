package cn.gtmap.realestate.inquiry.core.mapper;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcSdqBlztRequestDTO;
import cn.gtmap.realestate.common.core.qo.init.BdcSdqywQO;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/05/16
 * @description 水电气Mapper
 */
public interface BdcSdqghMapper {
    /**
     * 查询水电气业务信息
     * @param bdcSdqywQO
     * @return
     */
    List<BdcSdqghDO> listBdcSdqghIn(BdcSdqywQO bdcSdqywQO);

    /**
     * 权利人信息
     * @param gzlslid
     * @return
     */
    List<BdcQlrDO> getQlrxxByGzlslidIn(String gzlslid);

    /**
     * 更新水电气办理状态
     * @param bdcSdqywQO
     * @return
     */
    Integer updateSdqBlztIn(BdcSdqywQO bdcSdqywQO);

    /**
     * 查询业务数据
     * @param gzlslid
     * @return
     */
    List<Map> getSdqSqbYwDyData(String gzlslid);

    /**
     * 更新水电气业务办理状态
     * @param bdcSdqBlztRequestDTO
     * @return
     */
    Integer updateSdqBlzt(BdcSdqBlztRequestDTO bdcSdqBlztRequestDTO);
}
