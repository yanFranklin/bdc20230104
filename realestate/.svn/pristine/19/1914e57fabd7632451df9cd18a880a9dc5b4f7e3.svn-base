package cn.gtmap.realestate.common.core.service.rest.exchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/4/2 14:10
 * @description 附件信息相关Service 南通
 */
public interface FjxxRestService {

    /**
     * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
     * @params [slbh]
     * @return java.lang.Object
     * @description 查询附件图片信息 南通
     */
    @GetMapping("/realestate-exchange/rest/v1.0/queryFjxxBySlbh/{slbh}")
    Object queryFjxxBySlbh(@PathVariable("slbh") String slbh);

    /**
     * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
     * @params [bdcqzh]
     * @return java.lang.Object
     * @description 通过产权证号查询附件图片信息
     */
    @GetMapping("/realestate-exchange/rest/v1.0/queryFjxxByBdcqzh/{bdcqzh}")
    Object queryFjxxByBdcqzh(@PathVariable("bdcqzh") String bdcqzh);
}
