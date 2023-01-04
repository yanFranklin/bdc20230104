package cn.gtmap.realestate.common.core.service.rest.exchange;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description //  舒城水过户
 * @Date 2022/5/11 8:56
 **/
public interface ShuChengShuiService {

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //舒城水过户
     * @Date 2022/5/11 9:18
     **/
    @PostMapping("/realestate-exchange/rest/v1.0/shucheng/sgh")
    void sgh(@RequestParam(name = "processInsId") String processInsId,
             @RequestParam(name = "userId", required = false) String userId,
             @RequestParam(name = "qjgldm", required = false) String qjgldm
    );

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //校验舒城需要过户的帐号是否欠费
     * @Date 2022/5/11 9:18
     **/
    @GetMapping("/realestate-exchange/rest/v1.0/shucheng/sghjc")
    CommonResponse<Boolean> sghjc(@RequestParam(name = "processInsId") String processInsId,
                                  @RequestParam(name = "userId", required = false) String userId,
                                  @RequestParam(name = "qjgldm", required = false) String qjgldm
    );


    /**
     * @Author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     * @Description 舒城昆仑燃气过户-工作流事件
     * @Date 2022/7/11 9:10
     **/
    @PostMapping("/realestate-exchange/rest/v1.0/shucheng/kl/qgh")
    void klRqgh(@RequestParam(name = "processInsId") String processInsId);
}
