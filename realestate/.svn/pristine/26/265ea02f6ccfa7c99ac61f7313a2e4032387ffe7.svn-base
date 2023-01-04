package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcBaxxCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-07-10
 * @description 批量查询
 */
public interface BdcPlcxService {


    /**
     * @param bdcPlcxQO 查询Qo
     * @param pageable 分页参数
     * @return list<BdcPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量查询
     */
    Page<BdcPlcxDTO> listBdcPlcxByPage(Pageable pageable, BdcPlcxQO bdcPlcxQO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcPlcxQO, importFlag]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询
     */
    List<BdcYcPlcxDTO> listBdcYcPlcx(BdcPlcxQO bdcPlcxQO, boolean importFlag);


    /**
     * @param bdcBaxxCxQO 查询Qo
     * @param pageable 分页参数
     * @return list<BdcBaxxPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量查询
     */
    Page<BdcBaxxPlcxDTO> listBdcBaxxPlcxByPage(Pageable pageable, BdcBaxxCxQO bdcBaxxCxQO);


    /**
     * @param bdcBaxxCxQO 查询Qo
     * @return list<BdcBaxxPlcxDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     * @description 批量查询
     */
    List<BdcBaxxPlcxDTO> listBdcBaxxPlcx(BdcBaxxCxQO bdcBaxxCxQO);

}
