package cn.gtmap.realestate.common.core.service.rest.inquiry;

import cn.gtmap.realestate.common.core.dto.inquiry.BdcZhjgDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-11-15
 * @description 综合监管
 */
public interface BdcZhjgRestService {

    /**
     * 分页综合监管信息
     *
     * @param pageable
     * @param bdcZhjgQOJson
     * @return Page<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcZhjg/page")
    Page<BdcZhjgDTO> listBdcZhjgByPage(Pageable pageable,
                                       @RequestParam(name = "bdcZhjgQOJson", required = false) String bdcZhjgQOJson);


    /**
     * 综合监管信息
     *
     * @param bdcZhjgQOJson
     * @return Page<BdcZhjgDTO>
     * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
     */
    @GetMapping(value = "/realestate-inquiry/rest/v1.0/bdcZhjg/list")
    List listBdcZhjg(@RequestParam(name = "bdcZhjgQOJson", required = false) String bdcZhjgQOJson);

}
