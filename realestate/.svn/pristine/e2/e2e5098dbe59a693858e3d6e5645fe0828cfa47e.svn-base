package cn.gtmap.realestate.inquiry.ui.web.rest.huaian;

import cn.gtmap.realestate.common.core.dto.accept.BdcSfjfblResponseDTO;
import cn.gtmap.realestate.common.core.ex.MissingArgumentException;
import cn.gtmap.realestate.common.core.qo.accept.BdcSlJfblQO;
import cn.gtmap.realestate.common.core.service.feign.accept.BdcSfxxFeignService;
import cn.gtmap.realestate.inquiry.ui.web.main.BaseController;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @version 1.0, 2022/11/22
 * @description  对公转账登记费月结
 */
@Api(tags = "对公转账登记费月结")
@RestController
@RequestMapping("/rest/v1.0/djfyj")
public class DjfYjController extends BaseController {

    @Autowired
    BdcSfxxFeignService bdcSfxxFeignService;

    /**
     * @description 生成收费二维码
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/22 15:36
     * @param bdcSlJfblQO
     * @return BdcSfjfblResponseDTO
     */
    @PostMapping(value = "/jfbl")
    @ResponseStatus(HttpStatus.OK)
    public BdcSfjfblResponseDTO jfbl(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        return bdcSfxxFeignService.jfbl(bdcSlJfblQO);

    }

    /**
     * @description 查询缴费情况
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/22 15:36
     * @param bdcSlJfblQO
     * @return void
     */
    @PostMapping(value = "/cxjfqk")
    @ResponseStatus(HttpStatus.OK)
    public void cxjfqk(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        bdcSfxxFeignService.cxjfqk(bdcSlJfblQO, true);
    }

    /**
     * @description 作废缴款通知书
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/22 15:36
     * @param bdcSlJfblQO
     * @return Map
     */
    @PostMapping(value = "/zfjktzs")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> zfjktzs(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getSfxxid())) {
            throw new MissingArgumentException("请求参数为空或收费信息id为空");
        }
        return bdcSfxxFeignService.zfjktzs(bdcSlJfblQO);
    }

    /**
     * @description 非税开票
     * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
     * @date 2022/11/22 15:36
     * @param bdcSlJfblQO
     * @return Map
     */
    @PostMapping(value = "/fskp")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> fskp(@RequestBody BdcSlJfblQO bdcSlJfblQO) {
        if (Objects.isNull(bdcSlJfblQO) || StringUtils.isBlank(bdcSlJfblQO.getGzlslid())) {
            throw new MissingArgumentException("请求参数为空或工作流实例id为空");
        }
        return bdcSfxxFeignService.fskp(bdcSlJfblQO);
    }

}
