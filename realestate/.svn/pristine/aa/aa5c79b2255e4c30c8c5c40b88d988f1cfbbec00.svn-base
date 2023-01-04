package cn.gtmap.realestate.common.core.service.rest.etl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.gtmap.realestate.common.core.vo.etl.HxdjxxbVO;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/10
 * @description 互联网验证服务
 */
public interface HlwYzRestService {

    /**
      * @param bdcdyh 不动产单元号
      * @return 验证信息
      * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
      * @description 互联网办件验证
      */
    @GetMapping(value = "/realestate-etl/rest/v1.0/hlwyz/blzt/{bdcdyh}")
    Map<String, String> checkHlwBjzt(@PathVariable(name = "bdcdyh") String bdcdyh);

    /**
     * @return
     * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
     * @description 回写登记信息到共享
     */
    @PostMapping(value = "/realestate-etl/rest/v1.0/hlwyz/hxDjxx")
    void hxDjxx(@RequestBody HxdjxxbVO hxdjxxbVO);

}
