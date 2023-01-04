package cn.gtmap.realestate.common.core.service.rest.exchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/1/11
 * @description 舒城房产交易视图查询服务
 */
public interface ShuChengFcjyViewRestService {

    /**
     * 根据合同号查询交易信息
     *
     * @param htbh 合同编号
     * @return map map
     * @Date 2022/1/11
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-exchange/rest/v1.0/shucheng/queryFcjyView")
    Map queryShuchengFcjyView(@RequestParam(name = "htbh") String htbh);
}
