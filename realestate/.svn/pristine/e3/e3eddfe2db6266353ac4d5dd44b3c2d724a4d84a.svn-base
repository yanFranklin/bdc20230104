package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.BdcInterfaceDTO;
import cn.gtmap.realestate.common.core.dto.analysis.BdcResponseDTO;
import cn.gtmap.realestate.common.core.dto.analysis.BdcZfcxDTO;
import cn.gtmap.realestate.common.core.dto.analysis.FileUploadDTO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcXxCxQO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcZfcxExportQO;
import cn.gtmap.realestate.common.core.qo.analysis.BdcZfcxQO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 住房查询（有房无房查询）
 */
public interface BdcZfcxRestService {

    /**
     * version 1.0
     * @description 住房查询（有房无房查询）
     * @param qo
     * @date 2018/12/20
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/bdc/zfcx")
    BdcResponseDTO listZfcx(@RequestBody BdcXxCxQO qo);
    /**
     * version 1.0
     * @description 分页查询住房查询列表
     * @param page 当前页 从0开始
     * @param size 每页显示记录数
     * @param sort 排序字段
     * @return
     * @date 2019/1/2
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/zfcx/page")
    Page<BdcZfcxDTO> listZfcxByPage(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false) Sort sort,
            @RequestBody BdcZfcxQO bdcZfcxQO);
    /**
     * version 1.0
     *
     * @param bdcZfcxQO  查询条件
     * @return
     * @description 住房查询选择地区查询接口
     * @date 2019/2/26
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/zfcx/num")
    BdcInterfaceDTO listZfcxByNum(@RequestBody BdcZfcxQO bdcZfcxQO);
    /**
     * version 1.0
     *
     * @param exportQO
     * @return
     * @description 住房查询结果导出
     * @date 2019/1/2
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @PostMapping(value = "/realestate-analysis/rest/v1.0/zfcx/detail")
    FileUploadDTO zfcxExport(@RequestBody BdcZfcxExportQO exportQO);


}
