package cn.gtmap.realestate.common.core.service.rest.analysis.count;

import cn.gtmap.realestate.common.core.dto.analysis.BdcqzsDTO;
import cn.gtmap.realestate.common.core.dto.analysis.count.BdcCountDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcSztjQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.09
 * @description: 缮证统计rest服务接口
 */
public interface SztjRestService {
    /**
     * version 1.0
     * @description 缮证统计
     * @param type djlx qllx
     * @param qo 查询条件qo对象
     * @return
     * @date 2019/3/8
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/sztj/{type}")
    List<BdcCountDTO> listSztjByType(@PathVariable("type") String type,@RequestBody BdcSztjQO qo);

    /**
     * 缮证统计明细获取
     * @date 2019.03.11 11:07
     * @author hanyaning
     * @param qo 缮证统计qo对象
     * @param page
     * @param size
     * @param sort
     * @return java.util.List<cn.gtmap.realestate.common.core.dto.analysis.BdcqzsDto>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/sztj/list")
    Page<BdcqzsDTO> listSztjMx(@RequestBody BdcSztjQO qo, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) Sort sort);

}