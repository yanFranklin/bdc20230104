package cn.gtmap.realestate.exchange.web.rest.bengbu;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/18
 * @description 蚌埠水电气——相关操作控制层
 */
@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/bengbusdq")
@Api("蚌埠水电气相关操作控制层")
public class BengbuSdqController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BengbuSdqController.class);

    /**
     * 根据gzlslid进行过户操作，组织参数和请求接口
     *
     * @param gzlslid 工作流实例id
     * @return void
     * @Date 2022/4/18
     * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
     */
    @PostMapping("/workFlow/dianGh")
    public void BengbuDianGh(@RequestParam(value = "processInsId") String processInsId) {
        LOGGER.info("蚌埠工作流电过户操作，gzlslid:{}", processInsId);


    }


}
