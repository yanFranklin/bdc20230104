package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;
import cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcBaxxCxQO;
import cn.gtmap.realestate.common.core.qo.inquiry.BdcPlcxQO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-11-15
 * @description 批量查询
 */
public interface BdcPlcxRestService {

    /**
     * 分页查询房产信息
     *
     * @param pageDTOJson
     * @param bdcPlcxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcPlcxDTO;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *
     * 修改bdcPlcxQO通过@RequestBody获取参数，避免get请求头过大问题
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcPlcx/page")
    Page<BdcPlcxDTO> listBdcPlcxByPage(@RequestParam(name = "pageDTOJson") String pageDTOJson,
                                       @RequestBody BdcPlcxQO bdcPlcxQO);

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [bdcPlcxQOJson]
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.inquiry.yancheng.BdcYcPlcxDTO>
     * @description 盐城 批量查询
     *
     * 修改为post请求，并修改bdcPlcxQOJson通过@RequestBody获取参数，避免get请求头过大问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/bdcPlcx/yancheng")
    List<BdcYcPlcxDTO> listBdcYcPlcx(@RequestBody String bdcPlcxQOJson);


    /**
     * 分页查询备案信息
     *
     * @param pageDTOJson
     * @param bdcBaxxCxQO
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     *
     * 修改为post请求，并修改bdcBaxxCxQO通过@RequestBody获取参数，避免get请求头过大问题
     * 修改分页对象为PageDTO，解决Pageable无法传参问题
     * @date 2022/06/28
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/baxx/bdcPlcx/page")
    Page<BdcBaxxPlcxDTO> listBdcBaxxPlcxByPage(@RequestParam(name = "pageDTOJson", required = true) String pageDTOJson,
                                               @RequestBody BdcBaxxCxQO bdcBaxxCxQO);



    /**
     * 查询备案信息
     *
     * @param bdcBaxxCxQO
     * @return List<cn.gtmap.realestate.common.core.dto.inquiry.BdcBaxxPlcxDTO;>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @PostMapping(value = "/realestate-inquiry/rest/v1.0/baxx/bdcPlcx")
    List<BdcBaxxPlcxDTO> listBdcBaxxPlcx(@RequestBody BdcBaxxCxQO bdcBaxxCxQO);

}
