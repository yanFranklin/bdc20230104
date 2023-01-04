package cn.gtmap.realestate.common.core.service.rest.analysis;

import cn.gtmap.realestate.common.core.dto.analysis.ViewTypeDTO;
import cn.gtmap.realestate.common.core.qo.analysis.config.ZfcxAreaConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 配置信息获取
 */
public interface BdcConfigRestService {

    /**
     * version 1.0
     * @description 住房查询跨地区配置查询
     * @return
     * @date 2019/3/22
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/zfcx/area")
    List<ZfcxAreaConfig> listZfcxAreaConfig();


    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/areacode")
    String getApplicationAreacode();

    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/port")
    String getAnalysisPort();

    /**
     * version 1.0
     * @description 台账通用配置信息
     * @return
     * @date 2019/3/22
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/view/list")
    List<ViewTypeDTO> listViewType();


    /**
     * version 1.0
     * @description 获取台账配置信息
     * @return
     * @date 2019/3/22
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/view/{viewType}")
    ViewTypeDTO getViewType(@PathVariable("viewType") String viewType);


    /**
     * version 1.0
     * @description 日志查询条件英文转中文关系
     * @date 2019/3/25
     * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
     */
    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/log/param")
    Map<String, String> listLogParamConfig();


    @GetMapping("/realestate-analysis/rest/v1.0/bdc/config/log/paramcon")
    Map<String, Map<String, String>> listLogParamConditionConfig();
}
