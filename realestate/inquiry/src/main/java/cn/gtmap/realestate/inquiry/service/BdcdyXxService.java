package cn.gtmap.realestate.inquiry.service;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.dazt.DaztDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcdyxxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.DaxxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2019-05-22
 * @description 不动产单元信息服务
 */
public interface BdcdyXxService {


    /**
     * @param bdcdyxxQO 查询Qo
     * @param pageable  分页参数
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.domain.BdcXmDO>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @description 分页查询已登记不动产单元信息
     */
    Page<BdcdyxxDTO> listBdcdyxxByPage(Pageable pageable, BdcdyxxQO bdcdyxxQO);


    /**
     * 根据gzlslid查询档案柜信息
     *
     * @param pageable
     * @param gzlslid
     * @return DaztDTO
     * @Date 2022/1/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    Page<DaztDTO> listDaxxByPage(Pageable pageable, DaxxQO daxxQO);
}
