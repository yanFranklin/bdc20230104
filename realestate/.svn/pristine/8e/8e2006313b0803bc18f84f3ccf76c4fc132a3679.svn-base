package cn.gtmap.realestate.exchange.web.rest.hefei;

import cn.gtmap.realestate.common.core.domain.CommonResponse;
import cn.gtmap.realestate.exchange.core.bo.anno.DsfLog;
import cn.gtmap.realestate.exchange.core.dto.wwsq.dj.request.GyjsydscdjDto;
import cn.gtmap.realestate.exchange.service.hefei.GyjsydsyqdjService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realestate-exchange/rest/v1.0/gyjsyd")
public class GyjsydsyqdjController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GyjsydsyqdjController.class);

    @Autowired
    private GyjsydsyqdjService gyjsydsyqdjService;

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description //国土建设用地首次登记
     * @Date 2022/5/17 11:10
     **/
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/scdj")
    @DsfLog(logEventName = "国有建设用地使用权首次登记创建接口(合肥)", dsfFlag = "JSYD", requester = "JSYD", responser = "JSYD")
    public CommonResponse scdj(@RequestBody GyjsydscdjDto dto) {
        LOGGER.info("国有建设用地使用权首次登记创建接口(合肥)接口接收参数: {}", JSONObject.toJSONString(dto));
        return gyjsydsyqdjService.gyjsydsyqscdj(dto);
    }
}
