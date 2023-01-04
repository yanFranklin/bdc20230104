package cn.gtmap.realestate.common.core.service.rest.inquiry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
 * @version 1.0, 2020/7/28
 * @description 查询子系统：办件时长统计
 */
public interface BdcBjscTjRestService {

    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @param  bjsctjParamJson 查询条件
     * @return 办件时长统计集合
     * @description  办件时长统计
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bjsctj/countBjsc")
    List<Map> listBjscCount(@RequestParam(name = "bjsctjParamJson", required = false) String bjsctjParamJson);


    /**
     * @author  <a href="mailto:chenyucehng@gtmap.cn">chenyucehng</a>
     * @return  时间段配置
     * @description  时间段配置
     */
    @GetMapping("/realestate-inquiry/rest/v1.0/bjsctj/getSjdpz")
    String sjdpz();


}
