package cn.gtmap.interchange.web;

import cn.gtmap.interchange.core.dto.CommonResponse;
import cn.gtmap.interchange.core.vo.BdcXtggVO;
import cn.gtmap.interchange.service.BdcXtggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0  2021-04-21
 * @description 公告信息处理接口
 */
@RestController
@RequestMapping(value = "/realestate-interchange/rest/v1.0/xtgg")
public class BdcXtggRestController {
    @Autowired
    private BdcXtggService bdcXtggService;

    /**
     * 推送系统公告接口（用于手动推送）
     * @param bdcXtggVO 查询公告信息参数
     */
    @PostMapping("")
    public CommonResponse pushXtgg(@RequestBody BdcXtggVO bdcXtggVO){
        return bdcXtggService.pushXtgg(bdcXtggVO);
    }
}
