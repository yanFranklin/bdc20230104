package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.nantong.dazt.DaztDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2019-05-22
 * @description 不动产单元查询信息服务
 */
public interface BdcdyXxCxRestService {

    /**
     * 分页查询已登记不动产单元
     *
     * @param pageable
     * @param bdcdyxxQOJson
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcdyxxDTO ;>
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcdyxx/page")
    Page<BdcdyxxDTO> listBdcdyByPage(Pageable pageable,
                                     @RequestParam(name = "bdcdyxxQOJson", required = false) String bdcdyxxQOJson);


    /**
     * 根据gzlslid查询档案柜信息
     *
     * @param toJSONString
     * @return DaztDTO
     * @Date 2022/1/24
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/dag/page")
    Page<DaztDTO> listDagxxByPage(Pageable pageable, @RequestParam(name = "dzxxJson", required = false) String dzxxJson);
}
