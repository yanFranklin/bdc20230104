package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcqzsDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcqzsCxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.01
 * @description: 不动产权证书查询接口
 */
public interface BdcqzsCxRestService {
    /**
     * @param page
     * @param size
     * @param sort
     * @param bdcqzsCxQO 证书查询qo对象
     * @return org.springframework.data.domain.Page<cn.gtmap.realestate.common.core.dto.analysis.BdcqzmDto>
     * @date 2019.03.01 14:30
     * @author hanyaning
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdcqzscx/page")
    Page<BdcqzsDTO> listBdcqzscxByPage(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) Sort sort, @RequestBody BdcqzsCxQO bdcqzsCxQO);

}