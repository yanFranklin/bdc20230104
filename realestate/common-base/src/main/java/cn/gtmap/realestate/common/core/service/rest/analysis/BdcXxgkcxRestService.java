package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcXxgkcxDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcXxgkcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/1/11
 * @description 信息公开查询
 */
public interface BdcXxgkcxRestService {


    /***
     * 获取信息公开查询列表
     * @param page 当前页
     * @param size 每页显示记录数
     * @param sort 排序字段
     * @param bdcXxgkcxQO 信息公开查询对象
     * @return
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/xxgkcx/page")
    Page<BdcXxgkcxDTO> listXxgkcxByPage(
            @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) Sort sort, @RequestBody BdcXxgkcxQO bdcXxgkcxQO);

    /***
     * 获取信息公开全部记录
     * @param bdcXxgkcxQO 信息公开查询对象
     * @return
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/xxgkcx/list")
    List<BdcXxgkcxDTO> listXxgkcx(@RequestBody BdcXxgkcxQO bdcXxgkcxQO);


    /**
     * version 1.0
     *
     * @param
     * @return
     * @description 导出清单
     * @date 2019/1/17
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/xxgkcx/detail")
    Object exportDetailList(@RequestBody BdcXxgkcxQO qo);

}
