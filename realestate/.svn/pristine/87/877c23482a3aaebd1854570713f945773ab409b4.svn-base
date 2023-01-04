package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/6/8
 * @description 南通——水相关接口服务
 */
public interface NantongShuiService {

    /**
     * 根据gzlslid进行过户操作
     *
     * @param processInsId
     * @return
     * @Date 2021/6/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/WaterGh/proccessid")
    void sgh(@RequestParam(name = "processInsId") String processInsId);

    /**
     * 水过户核验
     *
     * @param gzlslid 工作流实例id
     * @param consno   新过户人名称
     * @param dwdm   供水单位代码
     * @return
     * @Date 2022/4/26
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @GetMapping("/realestate-exchange/rest/v1.0/WaterGh/checkConsno")
    CommonResponse<Integer> sghjc(@RequestParam(name = "gzlslid")String gzlslid, @RequestParam(name = "consno")String consno, @RequestParam(name = "dwdm")String dwdm);

    /**
     * 推送登记数据至自来水公司
     * @param processInsId 工作流实例ID
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/WaterGh/zls/ts")
    CommonResponse bdcsdqZlsTs(@RequestParam(name = "processInsId") String processInsId);
}
