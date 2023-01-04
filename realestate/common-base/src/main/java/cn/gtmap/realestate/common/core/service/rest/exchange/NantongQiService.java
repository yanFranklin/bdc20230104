package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/6/8
 * @description 南通——气相关接口服务
 */
public interface NantongQiService {

    /**
     * 根据gzlslid进行过户操作
     *
     * @param processInsId
     * @return
     * @Date 2021/6/8
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/realestate-exchange/rest/v1.0/GasGh/proccessid")
    void qgh(@RequestParam(name = "processInsId") String processInsId);

    /**
     * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description 检查户号的有效性
     * @Date 2022/4/27
     * @Param
     * @return
     **/
    @GetMapping("/realestate-exchange/rest/v1.0/GasGh/checkConsno")
    CommonResponse<Integer> qghjc(@RequestParam(name = "processInsId") String processInsId, @RequestParam(name = "consno") String consno);
}
