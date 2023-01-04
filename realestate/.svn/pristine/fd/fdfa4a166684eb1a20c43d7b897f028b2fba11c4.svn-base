package cn.gtmap.realestate.common.core.service.rest.etl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author <a herf="mailto:shaoliyao@gtmap.cn">sly</a>
 * @version 1.0, 2020/6/19
 * @description
 */
public interface HlwCreateRestService {

    /**
     * @param pageable
     * @param paramJson
     * @return java.lang.Object
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description 互联网业务数据分页查询方法
     */
    @PostMapping("/realestate-etl/rest/v1.0/hlwxx/page")
    Page<Map> listHlwYwxxByPageJson(Pageable pageable,
                                    @RequestParam(name = "paramJson", required = false) String paramJson);


    @PostMapping("/realestate-etl/rest/v1.0/hlwxx/initlist")
    JSONObject listHlwYwxxByHlwXmid(@RequestParam(name = "hlwxmid") String hlwxmid,@RequestParam(name = "slr", required = false) String slr);

    /**
     * 提供给大云工作流事件，用于更新互联网审核状态
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @GetMapping("/realestate-etl/rest/v1.0/hlwxx/shzt/{processInsId}")
    void modifyHlwSlzt(@PathVariable(name= "processInsId") String processInsId);

    /**
     * @param
     * @return
     * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
     * @description 创建不动产登记业务
     */
    @PostMapping("/realestate-etl/rest/v1.0/hlwxx/cj/{hlwxmid}")
    Map<String,Object> cjBdcXm(@PathVariable(name= "hlwxmid") String hlwxmid,@RequestParam(name = "slr", required = false) String slr);


}
