package cn.gtmap.realestate.common.core.service.rest.building;

import cn.gtmap.realestate.common.core.domain.exchange.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021/07/14
 * @description 获取权籍数据接口(和上报分开,不影响上报的功能,逻辑很接近)
 */
public interface FwqsxxRestService {
    /**
     * 获取KTT_FW_ZRZ--
     * @param bdcdyh
     * @return
     */
    @GetMapping("/building/rest/v1.0/fwqsxx/fwzrz")
    List<Map> queryQsxxKttFwZrzList(@RequestParam("bdcdyh") String bdcdyh);


    /**
     *
     * @param bdcdyh
     * @return
     */
    @GetMapping("/building/rest/v1.0/fwqsxx/fwljz")
    List<KttFwLjzDO> queryQsxxKttFwLjzList(@RequestParam("bdcdyh") String bdcdyh);


    /**
     *
     * @param bdcdyh
     * @param sfdz
     * @return
     */
    @GetMapping("/building/rest/v1.0/fwqsxx/fwh")
    List<KttFwHDO> queryQsxxKttFwHList(@RequestParam("bdcdyh") String bdcdyh, @RequestParam("sfdz") boolean sfdz);


    /**
     *
     * @param bdcdyh
     * @return
     */
    @GetMapping("/building/rest/v1.0/fwqsxx/fwc")
    List<KttFwCDO> queryQsxxKttFwCList(@RequestParam("bdcdyh") String bdcdyh);

}
